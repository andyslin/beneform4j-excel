package com.forms.beneform4j.excel.core.model.em.text;

import com.forms.beneform4j.excel.core.model.em.IEM;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 转换为文本文件的Excel模型配置接口<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public interface ITextEM extends IEM {

    /**
     * 获取字符集
     * 
     * @return
     */
    public String getCharset();

    /**
     * 获取开始跳过的行数
     * 
     * @return
     */
    public int getSkipRows();

    /**
     * 获取每一行最少的列数，如果不足此数，使用默认单元格的值填充
     * 
     * @return
     */
    public int getMinCellsOfRow();

    /**
     * 获取默认单元格的值
     * 
     * @return
     */
    public String getDefaultCellValue();

    /**
     * 一行数据的分隔符，默认为英文逗号,
     * 
     * @return
     */
    public String getSeparator();

    /**
     * 是否忽略空行，默认为忽略
     * 
     * @return
     */
    public boolean isIgnoreEmptyRow();

    /**
     * 批次号的格式 [d{yyyyMMdd}(a|n|an){8}]，其中d{}表示日期格式,a表示字母,n表示数字,an表示字母数字组合,a{8}表示8位随机字母
     * 
     * @return
     */
    public String getBatchNoFormat();

    /**
     * 是否添加批次号
     * 
     * @return
     */
    public boolean isAddBatchNo();

    /**
     * 是否添加行索引，每个Sheet从1开始
     * 
     * @return
     */
    public boolean isAddRowIndex();

    /**
     * 是否添加数据索引，索引从1开始，所有Sheet累加
     * 
     * @return
     */
    public boolean isAddDataIndex();
}
