package com.forms.beneform4j.excel.web.download;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 下载类型<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2013-10-6<br>
 */
public enum DownloadType {
    MODEL, // 下载模型
    DATA_EXPORT, // 先查询数据，然后生成文件下载
    OBJECT; // 先生成对象，然后下载对象

    public static DownloadType instance(String code) {
        for (DownloadType type : values()) {
            if (type.name().equalsIgnoreCase(code)) {
                return type;
            }
        }
        return OBJECT;
    }
}
