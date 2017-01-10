package com.forms.beneform4j.excel.model.file.loader.xml;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.config.BaseConfig;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.core.util.xml.parser.XmlParserUtils;
import com.forms.beneform4j.excel.model.base.EMType;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.base.em.BaseEM;
import com.forms.beneform4j.excel.model.base.loader.AbstractResourceEMLoader;
import com.forms.beneform4j.excel.model.dynamic.em.ResourceFreemarkerTreeEM;
import com.forms.beneform4j.excel.model.file.em.ResourceFileEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 将Resource对象作为一个包含了模型ID和对应EXCEL模板文件路径的XML配置文件，并进一步解析这个XML文件<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public class XmlFileEMLoader extends AbstractResourceEMLoader {

    private static final String GROUP = "group";
    private static final String GROUP_ID_PREFIX = "idPrefix";
    private static final String GROUP_LOCATION_PATTERNS = "locationPatterns";
    private static final String GROUP_SUFFIXS = "suffixs";
    private static final String GROUP_EXCLUDES = "excludes";
    private static final String GROUP_PRIOR = "prior";

    private static final String MODEL = "model";
    private static final String MODEL_ID = "id";
    private static final String MODEL_NAME = "name";
    private static final String MODEL_DESC = "desc";
    private static final String MODEL_PRIOR = "prior";
    private static final String MODEL_LOCATION = "location";

    @Override
    protected void registerExcelModel(Resource resource, boolean force) {
        Document document = XmlParserUtils.buildDocument(resource);
        List<Element> elements = XmlHelper.getChildElementsByTagName(document.getDocumentElement(), GROUP, MODEL);
        if (null != elements) {
            for (Element ele : elements) {
                String name = XmlHelper.getLocalName(ele);
                if (GROUP.equalsIgnoreCase(name)) {
                    parseGroupElement(ele, force);
                } else {
                    resolveModelElement(ele, force);
                }
            }
        }
    }

    protected String resolveId(String location, String filename) {
        int index = filename.indexOf(".");
        if (-1 == index) {
            return filename;
        } else {
            return filename.substring(0, index);
        }
    }

    private void parseGroupElement(Element ele, boolean force) {
        String locations = ele.getAttribute(GROUP_LOCATION_PATTERNS);
        if (CoreUtils.isBlank(locations)) {
            Throw.throwRuntimeException("资源匹配模式不能为空");
        }
        String[] locationPatterns = splitByBlank(locations);

        String idPrefix = ele.getAttribute(GROUP_ID_PREFIX);
        if (CoreUtils.isBlank(idPrefix)) {
            idPrefix = "";
        }

        String priorStr = ele.getAttribute(GROUP_PRIOR);
        int prior = 0;
        if (!CoreUtils.isBlank(priorStr)) {
            prior = Integer.parseInt(priorStr);
        }

        String excludess = ele.getAttribute(GROUP_EXCLUDES);
        Set<Resource> excludes = parseExcludes(excludess);

        String suffixss = ele.getAttribute(GROUP_SUFFIXS);
        Set<String> suffixs = getSuffixs(suffixss);

        parseResources(force, locationPatterns, idPrefix, prior, excludes, suffixs);
    }

    private void parseResources(boolean force, String[] locationPatterns, String idPrefix, int prior, Set<Resource> excludes, Set<String> suffixs) {
        try {
            ResourcePatternResolver loader = BaseConfig.getResourcePatternResolver();
            for (String location : locationPatterns) {
                for (Resource resource : loader.getResources(location)) {
                    String filename = resource.getFilename();
                    if (!excludes.contains(resource) && checkSuffix(suffixs, filename)) {
                        String id = idPrefix + resolveId(location, filename);
                        String type = EMType.getTypeByFilename(filename);
                        IEM em = getBaseEM(resource, id, filename, type, prior, resource.getDescription());
                        addEM(em, force);
                    }
                    excludes.add(resource);
                }
            }
        } catch (IOException e) {
            Throw.throwRuntimeException(e);
        }
    }

    private Set<Resource> parseExcludes(String excludess) {
        try {
            Set<Resource> resources = new HashSet<Resource>();
            if (!CoreUtils.isBlank(excludess)) {
                ResourcePatternResolver loader = BaseConfig.getResourcePatternResolver();
                for (String location : splitByBlank(excludess)) {
                    int prefixEnd = location.indexOf(":") + 1;
                    String path = location.substring(prefixEnd);
                    if (path.indexOf('*') == -1 && path.indexOf('?') == -1) {
                        location += "*";
                    }
                    for (Resource resource : loader.getResources(location)) {
                        resources.add(resource);
                    }
                }
            }
            return resources;
        } catch (IOException e) {
            throw Throw.createRuntimeException(e);
        }
    }

    private boolean checkSuffix(Set<String> suffixs, String filename) {
        if (null != suffixs && !suffixs.isEmpty()) {
            String suffix = FilenameUtils.getExtension(filename);
            if (CoreUtils.isBlank(suffix) || !suffixs.contains(suffix.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    private Set<String> getSuffixs(String suffixs) {
        if (!CoreUtils.isBlank(suffixs)) {
            Set<String> ss = new HashSet<String>();
            for (String suffix : splitByBlank(suffixs)) {
                ss.add(suffix.toLowerCase());
            }
            return ss;
        }
        return null;
    }

    private void resolveModelElement(Element ele, boolean force) {
        String location = ele.getAttribute(MODEL_LOCATION);
        if (CoreUtils.isBlank(location)) {
            Throw.throwRuntimeException("资源位置不能为空");
        }

        ResourcePatternResolver loader = BaseConfig.getResourcePatternResolver();
        Resource resource = loader.getResource(location);

        if (null == resource || !resource.exists()) {
            Throw.throwRuntimeException("资源不存在:" + location);
        }

        String filename = resource.getFilename();

        String id = ele.getAttribute(MODEL_ID);
        if (CoreUtils.isBlank(id)) {
            id = resolveId(location, filename);
        }

        String name = ele.getAttribute(MODEL_NAME);
        if (CoreUtils.isBlank(name)) {
            name = filename;
        }

        String desc = ele.getAttribute(MODEL_DESC);
        if (CoreUtils.isBlank(desc)) {
            desc = resource.getDescription();
        }

        int prior = 0;
        String priorStr = ele.getAttribute(MODEL_PRIOR);
        if (!CoreUtils.isBlank(priorStr)) {
            prior = Integer.parseInt(priorStr);
        }

        String type = EMType.getTypeByFilename(filename);
        IEM em = getBaseEM(resource, id, name, type, prior, desc);
        addEM(em, force);
    }

    private IEM getBaseEM(Resource resource, String id, String name, String type, int prior, String desc) {
        BaseEM em = EMType.MODEL_TYPE_EXCEL.equals(type) ? new ResourceFileEM(resource) : new ResourceFreemarkerTreeEM(resource);

        em.setType(type);
        em.setId(id);
        em.setName(name);
        em.setDesc(desc);
        em.setPrior(prior);

        return em;
    }

    private String[] splitByBlank(String src) {
        return src.split("\\s+");
    }
}
