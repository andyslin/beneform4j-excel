package com.forms.beneform4j.excel.model.file;

import java.io.IOException;
import java.io.InputStream;

import com.forms.beneform4j.excel.model.base.IEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用Excel文件作为Excel模型<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public interface IFileEm extends IEM {

    /**
     * 获取文件输入流
     * 
     * @return
     */
    public InputStream getInputStream() throws IOException;
}
