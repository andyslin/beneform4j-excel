package com.forms.beneform4j.excel.core.model.loader.xml.file;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.config.BaseConfig;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.base.BaseEM;
import com.forms.beneform4j.excel.core.model.loader.IEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 解析XML配置中的<file-workbook-group><br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class FileWorkbookGroupParser extends AbstractFileWorkbookParser {

    private static final String ID_PREFIX = "idPrefix";
    private static final String LOCATION_PATTERNS = "locationPatterns";
    private static final String SUFFIXS = "suffixs";
    private static final String EXCLUDES = "excludes";
    private static final String PRIOR = "prior";

    @Override
    public void parse(IResourceEMLoadContext context, Element ele) {
        String locations = ele.getAttribute(LOCATION_PATTERNS);
        if (CoreUtils.isBlank(locations)) {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS21);
        }
        String[] locationPatterns = splitByBlank(locations);

        String idPrefix = ele.getAttribute(ID_PREFIX);
        if (CoreUtils.isBlank(idPrefix)) {
            idPrefix = "";
        }

        String priorStr = ele.getAttribute(PRIOR);
        int prior = 0;
        if (!CoreUtils.isBlank(priorStr)) {
            prior = Integer.parseInt(priorStr);
        }

        String excludess = ele.getAttribute(EXCLUDES);
        Set<Resource> excludes = parseExcludes(excludess);

        String suffixss = ele.getAttribute(SUFFIXS);
        Set<String> suffixs = getSuffixs(suffixss);

        parseResources(context, locationPatterns, idPrefix, prior, excludes, suffixs);
    }

    private void parseResources(IEMLoadContext context, String[] locationPatterns, String idPrefix, int prior, Set<Resource> excludes, Set<String> suffixs) {
        ResourcePatternResolver loader = BaseConfig.getResourcePatternResolver();
        for (String location : locationPatterns) {
            for (Resource resource : loadResources(loader, location)) {
                String filename = resource.getFilename();
                if (!excludes.contains(resource) && checkSuffix(suffixs, filename)) {
                    String id = idPrefix + resolveFileWorkbookId(location, filename);
                    EMType type = getEMTypeByFilename(filename);
                    BaseEM em = super.getBaseWorkbookEM(resource, type);
                    em.setId(id);
                    em.setDesc(resource.getDescription());
                    em.setName(filename);
                    em.setPrior(prior);
                    context.register(em);
                }
                excludes.add(resource);
            }
        }
    }

    private Resource[] loadResources(ResourcePatternResolver loader, String location) {
        try {
            return loader.getResources(location);
        } catch (IOException e) {
            throw Throw.createRuntimeException(ExcelExceptionCodes.BF0XLS01, e, location);
        }
    }

    private Set<Resource> parseExcludes(String excludess) {
        Set<Resource> resources = new HashSet<Resource>();
        if (!CoreUtils.isBlank(excludess)) {
            ResourcePatternResolver loader = BaseConfig.getResourcePatternResolver();
            for (String location : splitByBlank(excludess)) {
                int prefixEnd = location.indexOf(":") + 1;
                String path = location.substring(prefixEnd);
                if (path.indexOf('*') == -1 && path.indexOf('?') == -1) {
                    location += "*";
                }
                for (Resource resource : loadResources(loader, location)) {
                    resources.add(resource);
                }
            }
        }
        return resources;
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

    private String[] splitByBlank(String src) {
        return src.split("\\s+");
    }
}
