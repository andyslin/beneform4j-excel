package com.forms.beneform4j.excel.core.model.em.bean.impl.extractor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

import com.forms.beneform4j.excel.core.ExcelUtils;
import com.forms.beneform4j.excel.core.model.em.bean.BeanEMExtractResult;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 抽象的提取器实现类<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public abstract class AbstractBeanEMExtractor implements IBeanEMExtractor {

    /**
     * 
     */
    private static final long serialVersionUID = -8134639397219503255L;

    private static final DataFormatter formatter = new DataFormatter();

    /**
     * 构建提取结果
     * 
     * @return
     */
    protected BeanEMExtractResult newExtractResult() {
        return new BeanEMExtractResult();
    }

    /**
     * 设置提取结果值
     * 
     * @param result
     * @param cell
     * @param type
     */
    protected void setExtractResultValue(BeanEMExtractResult result, Cell cell, Class<?> type) {
        if (null == type) {
            result.setValue(formatter.formatCellValue(cell));
        } else {
            Object value = ExcelUtils.getCellValue(cell, type);
            if (null != value) {
                result.setValue(value);
            }
        }
    }

    /**
     * 设置提取结果值
     * 
     * @param result
     * @param cell
     * @param type
     * @param defaultValue
     */
    protected void setExtractResult(BeanEMExtractResult result, Cell cell, Class<?> type, Object defaultValue) {
        Object value = null;
        if (null == type) {
            value = formatter.formatCellValue(cell);
        } else {
            value = ExcelUtils.getCellValue(cell, type);
        }
        if (null != value) {
            result.setValue(value);
        } else {
            result.setValue(defaultValue);
        }
    }
}
