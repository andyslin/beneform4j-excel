package com.forms.beneform4j.excel.core.model.em.bean;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型的提取器接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface IBeanEMExtractor extends Serializable {

    /**
     * 提取Excel数据
     * 
     * @param property Bean模型的属性配置
     * @param cell 源单元格对象
     * @param type 目标类型
     * @return 包含了提取值和下一步处理方式等信息的提取结果
     */
    public BeanEMExtractResult extract(IBeanEMProperty property, Cell cell, Class<?> type);
}
