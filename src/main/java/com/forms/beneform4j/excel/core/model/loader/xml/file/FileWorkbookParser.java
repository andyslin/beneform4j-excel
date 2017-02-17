package com.forms.beneform4j.excel.core.model.loader.xml.file;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.config.BaseConfig;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.base.BaseEM;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConsts;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 解析XML配置中的<file-workbook><br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class FileWorkbookParser extends AbstractFileWorkbookParser {

    private static final String NAME = "name";
    private static final String DESC = "desc";
    private static final String PRIOR = "prior";
    private static final String LOCATION = "location";

    @Override
    public void parse(IResourceEMLoadContext context, Element ele) {
        String location = ele.getAttribute(LOCATION);
        if (CoreUtils.isBlank(location)) {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS22);
        }

        ResourcePatternResolver loader = BaseConfig.getResourcePatternResolver();
        Resource resource = loader.getResource(location);
        if (null == resource || !resource.exists()) {
            Throw.throwRuntimeException(ExcelExceptionCodes.BF0XLS23, location);
        }

        String filename = resource.getFilename();
        EMType type = getEMTypeByFilename(filename);
        BaseEM em = getBaseWorkbookEM(resource, type);

        String id = ele.getAttribute(XmlEMLoaderConsts.ID_PROPERTY);
        if (CoreUtils.isBlank(id)) {
            id = resolveFileWorkbookId(location, filename);
        }
        em.setId(id);

        String name = ele.getAttribute(NAME);
        if (CoreUtils.isBlank(name)) {
            name = filename;
        }
        em.setName(name);

        String desc = ele.getAttribute(DESC);
        if (CoreUtils.isBlank(desc)) {
            desc = resource.getDescription();
        }
        em.setDesc(desc);

        int prior = 0;
        String priorStr = ele.getAttribute(PRIOR);
        if (!CoreUtils.isBlank(priorStr)) {
            prior = Integer.parseInt(priorStr);
        }
        em.setPrior(prior);

        context.register(em);
    }
}
