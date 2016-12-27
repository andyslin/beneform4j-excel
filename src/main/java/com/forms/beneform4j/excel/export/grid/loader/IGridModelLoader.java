package com.forms.beneform4j.excel.export.grid.loader;

import com.forms.beneform4j.excel.export.grid.Grid;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 表模型加载器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2014-6-16<br>
 */
public interface IGridModelLoader {

    /**
     * 初始化
     */
    public void init();

    /**
     * 加载模型
     * 
     * @param modelId
     * @return
     */
    public Grid loadGridModel(String modelId);

}
