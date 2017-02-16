package com.forms.beneform4j.excel.core.model.loader.base;

import com.forms.beneform4j.excel.core.model.em.IEM;
import com.forms.beneform4j.excel.core.model.loader.IEMLoader;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 加载Excel模型的抽象加载器，空实现移除和清除功能<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public abstract class AbstractEMLoader implements IEMLoader {

    /**
     * 实现模型加载器接口：加载模型
     */
    @Override
    public abstract IEM load(String modelId);

    /**
     * 实现模型加载器接口：移除模型
     */
    @Override
    public void remove(String modelId) {
        // do nothing
    }

    /**
     * 实现模型加载器接口：清除模型
     */
    @Override
    public void clear() {
        // do nothing
    }
}
