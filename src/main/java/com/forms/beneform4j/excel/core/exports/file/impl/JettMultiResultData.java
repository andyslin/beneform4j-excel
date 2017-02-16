package com.forms.beneform4j.excel.core.exports.file.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Jett中一个模板Sheet输出多个Sheet的数据对象<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class JettMultiResultData {

    /**
     * 模板表单名称
     */
    private final List<String> templateSheetNamesList;

    /**
     * 数据表单名称
     */
    private final List<String> dataSheetNamesList;

    /**
     * 数据列表
     */
    private final List<Map<String, Object>> datasList;

    /**
     * 构造函数，对每一份数据，都使用相同的模板表单，新生成的数据表单命名为模板表单名加序号
     * 
     * @param templateSheetName 模板表单名称
     * @param datasList 数据列表
     */
    public JettMultiResultData(List<Map<String, Object>> datasList) {
        this(new ArrayList<String>(), new ArrayList<String>(), datasList);
    }

    /**
     * 构造函数，对每一份数据，都使用相同的模板表单，新生成的数据表单命名为模板表单名加序号
     * 
     * @param templateSheetName 模板表单名称
     * @param datasList 数据列表
     */
    public JettMultiResultData(String templateSheetName, List<Map<String, Object>> datasList) {
        this(fillSameList0(templateSheetName, datasList.size()), fillOrderList0(templateSheetName, datasList.size()), datasList);
    }

    /**
     * 构造函数，使用相同的模板表单
     * 
     * @param templateSheetName 模板表单名称
     * @param dataSheetNamesList 新生成的数据表单名称列表
     * @param datasList 数据列表
     */
    public JettMultiResultData(String templateSheetName, List<String> dataSheetNamesList, List<Map<String, Object>> datasList) {
        this(fillSameList0(templateSheetName, datasList.size()), dataSheetNamesList, datasList);
    }

    /**
     * 构造函数，使用模板文件的第一个表单作为模板表单
     * 
     * @param dataSheetNamesList 新生成的数据表单名称列表
     * @param datasList 数据列表
     */
    public JettMultiResultData(List<String> dataSheetNamesList, List<Map<String, Object>> datasList) {
        this(new ArrayList<String>(), dataSheetNamesList, datasList);
    }

    /**
     * 构造函数
     * 
     * @param templateSheetNamesList 模板表单名称列表
     * @param dataSheetNamesList 新生成的数据表单名称列表
     * @param datasList 数据列表
     */
    public JettMultiResultData(List<String> templateSheetNamesList, List<String> dataSheetNamesList, List<Map<String, Object>> datasList) {
        super();
        this.templateSheetNamesList = templateSheetNamesList;
        this.dataSheetNamesList = dataSheetNamesList;
        this.datasList = datasList;
    }

    public List<String> getTemplateSheetNamesList() {
        return templateSheetNamesList;
    }

    public List<String> getDataSheetNamesList() {
        return dataSheetNamesList;
    }

    public List<Map<String, Object>> getDatasList() {
        return datasList;
    }

    public void fillTemplateSheetNamesList(String src) {
        int size = this.datasList.size();
        if (this.templateSheetNamesList.isEmpty()) {
            for (int i = 1; i <= size; i++) {
                templateSheetNamesList.add(src);
            }
        }
        if (this.dataSheetNamesList.isEmpty()) {
            for (int i = 1; i <= size; i++) {
                dataSheetNamesList.add(src + i);
            }
        }
    }

    private static List<String> fillSameList0(String src, int size) {
        List<String> list = new ArrayList<String>(size);
        for (int i = 1; i <= size; i++) {
            list.add(src);
        }
        return list;
    }

    private static List<String> fillOrderList0(String src, int size) {
        List<String> list = new ArrayList<String>(size);
        for (int i = 1; i <= size; i++) {
            list.add(src + i);
        }
        return list;
    }
}
