package com.forms.beneform4j.excel.model.file.loader;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.core.util.xml.parser.XmlParserUtils;
import com.forms.beneform4j.excel.model.base.IEM;
import com.forms.beneform4j.excel.model.base.loader.AbstractResourceEMLoader;
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

    @Override
    protected void registerExcelModel(Resource resource, boolean force) {
        Document document = XmlParserUtils.buildDocument(resource);
        List<Element> elements = XmlHelper.getChildElementsByTagName(document.getDocumentElement(), "folder", "resource", "file");
        if (null != elements) {
            for (Element ele : elements) {
                String name = XmlHelper.getLocalName(ele);
                if ("folder".equals(name)) {
                    resolveFolderElement(ele, force);
                } else if ("resource".equals(name)) {
                    IEM em = this.resolveResourceElement(ele);
                    addEM(em, force);
                } else if ("file".equals(name)) {
                    IEM em = this.resolveFileElement(ele, null, null);
                    addEM(em, force);
                }
            }
        }
    }

    private void resolveFolderElement(Element ele, boolean force) {
        List<Element> fileEles = XmlHelper.getChildElementsByTagName(ele, "file");
        if (null != fileEles) {
            String folder = ele.getAttribute("dir");
            if (CoreUtils.isBlank(folder)) {
                Throw.throwRuntimeException("the dir attribute not allow empty.");
            }

            String priorStr = ele.getAttribute("prior");
            Integer prior = null;
            if (!CoreUtils.isBlank(priorStr)) {
                prior = Integer.parseInt(priorStr);
            }

            for (Element fEle : fileEles) {
                IEM em = this.resolveFileElement(fEle, folder, prior);
                addEM(em, force);
            }
        }
    }

    private IEM resolveFileElement(Element ele, String folder, Integer parentPrior) {
        String id = ele.getAttribute("id");
        if (CoreUtils.isBlank(id)) {
            Throw.throwRuntimeException("the id attribute not allow empty.");
        }

        String path = ele.getAttribute("path");
        if (CoreUtils.isBlank(path)) {
            Throw.throwRuntimeException("the path attribute not allow empty, the id is " + id + ".");
        }

        File file = null;
        if (null == folder) {
            file = new File(path);
        } else {
            file = new File(folder + "/" + path);
        }

        Resource resource = new FileSystemResource(file);
        if (null == resource || !resource.exists()) {
            Throw.throwRuntimeException("the file " + file.getPath() + " is not exist, the id is " + id + ".");
        }
        ResourceFileEM em = new ResourceFileEM(resource);
        em.setId(id);

        String name = ele.getAttribute("name");
        if (CoreUtils.isBlank(name)) {
            String filename = resource.getFilename();
            name = FilenameUtils.getBaseName(filename);
        }
        em.setName(name);

        String desc = ele.getAttribute("desc");
        if (CoreUtils.isBlank(name)) {
            desc = resource.getDescription();
        }
        em.setDesc(desc);

        String priorStr = ele.getAttribute("prior");
        if (!CoreUtils.isBlank(priorStr)) {
            em.setPrior(Integer.parseInt(priorStr));
        } else if (null != parentPrior) {
            em.setPrior(parentPrior);
        }

        return em;
    }

    private IEM resolveResourceElement(Element ele) {
        String id = ele.getAttribute("id");
        if (CoreUtils.isBlank(id)) {
            Throw.throwRuntimeException("the id attribute not allow empty.");
        }

        String resourceLocation = ele.getAttribute("location");
        if (CoreUtils.isBlank(resourceLocation)) {
            Throw.throwRuntimeException("the location attribute not allow empty, the id is " + id + ".");
        }
        Resource resource = CoreUtils.getResource(resourceLocation);
        if (null == resource || !resource.exists()) {
            Throw.throwRuntimeException("the location " + resource + " is not exist, the id is " + id + ".");
        }
        ResourceFileEM em = new ResourceFileEM(resource);
        em.setId(id);

        String name = ele.getAttribute("name");
        if (CoreUtils.isBlank(name)) {
            String filename = resource.getFilename();
            name = FilenameUtils.getBaseName(filename);
        }
        em.setName(name);

        String desc = ele.getAttribute("desc");
        if (CoreUtils.isBlank(name)) {
            desc = resource.getDescription();
        }
        em.setDesc(desc);

        String priorStr = ele.getAttribute("prior");
        if (!CoreUtils.isBlank(priorStr)) {
            em.setPrior(Integer.parseInt(priorStr));
        }

        return em;
    }
}
