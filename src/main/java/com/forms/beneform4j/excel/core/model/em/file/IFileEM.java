package com.forms.beneform4j.excel.core.model.em.file;

import java.io.InputStream;

import com.forms.beneform4j.excel.core.model.em.IEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 使用Excel文件作为Excel模型配置的接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2016-12-22<br>
 */
public interface IFileEM extends IEM {

    /**
     * 获取文件输入流
     * 
     * @return 文件输入流
     */
    public InputStream getInputStream();

    /**
     * 获取文件名称
     * 
     * @return 文件名称
     */
    public String getFilename();
}
