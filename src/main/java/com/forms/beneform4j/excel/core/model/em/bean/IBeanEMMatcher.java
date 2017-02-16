package com.forms.beneform4j.excel.core.model.em.bean;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Bean模型的匹配器接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface IBeanEMMatcher {

    /**
     * 是否匹配
     * @param cell 检查的Cell对象
     * 
     * @return 是否匹配
     */
    public boolean isMatch(Cell cell);
}
