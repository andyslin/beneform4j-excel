package com.forms.beneform4j.excel.core.model.loader.base;

import java.util.Set;

import org.springframework.core.io.Resource;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.loader.IEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 从资源匹配模式表示的对象中加载Excel模型的抽象加载器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public abstract class AbstractResourceEMLoader extends AbstractOnceEMLoader {

    /**
     * 资源匹配模式
     */
    private String[] locationPatterns;

    /**
     * 注入资源匹配模式
     * 
     * @param locationPatterns
     */
    public void setLocationPatterns(String[] locationPatterns) {
        this.locationPatterns = locationPatterns;
    }

    /**
     * 实现初始化方法
     */
    @Override
    protected void init(final IEMLoadContext context) {
        if (null == locationPatterns || 0 == locationPatterns.length) {
            return;
        }
        Set<Resource> resources = CoreUtils.getResources(locationPatterns);
        if (null != resources) {
            for (final Resource resource : resources) {
                loadResourceEM(convertResourceEMLoadContext(context, resource));
            }
        }
    }

    /**
     * 解析一个资源，并注册Excel模型
     * 
     * @param context 加载上下文
     */
    protected abstract void loadResourceEM(IResourceEMLoadContext context);

    protected IResourceEMLoadContext convertResourceEMLoadContext(IEMLoadContext context, Resource resource) {
        return new ResourceEMLoadContext(context, resource);
    }

    private class ResourceEMLoadContext implements IResourceEMLoadContext {

        private final IEMLoadContext context;

        private final Resource resource;

        private ResourceEMLoadContext(IEMLoadContext context, Resource resource) {
            this.context = context;
            this.resource = resource;
        }

        @Override
        public void register(IEM model) {
            context.register(model);
        }

        @Override
        public Resource getResource() {
            return resource;
        }
    }
}
