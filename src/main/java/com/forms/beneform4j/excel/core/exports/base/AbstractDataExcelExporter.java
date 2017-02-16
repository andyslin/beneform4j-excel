package com.forms.beneform4j.excel.core.exports.base;

import java.io.OutputStream;

import com.forms.beneform4j.excel.core.model.em.IEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 抽象的Excel导出实现类，未传入参树时，设置为null<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public abstract class AbstractDataExcelExporter extends AbstractOutputStreamExcelExporter {

    @Override
    public void exports(IEM model, Object data, OutputStream output) {
        this.exports(model, null, data, output);
    }
}
