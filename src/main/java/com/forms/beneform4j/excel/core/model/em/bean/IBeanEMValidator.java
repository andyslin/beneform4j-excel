package com.forms.beneform4j.excel.core.model.em.bean;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型的校验器接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface IBeanEMValidator extends Serializable {

    /**
     * 校验，通过返回null，不通过时抛出异常
     * 
     * @param cell 检验的单元格
     * @param type 目标类型
     */
    public void validate(Cell cell, Class<?> type);
}
