package com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid;

import java.io.Serializable;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.enums.DataType;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.enums.ShowType;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 表格组件中的列<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class Td implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2672694190456767969L;

    /**
     * ==== 对应配置文件或者数据库结构中的字段 ====
     */

    /**
     * 模型ID
     */
    private String modelId;
    /**
     * 域序号，在同一个模型中唯一
     */
    private int fieldSeqno;
    /**
     * 域代码，取数据时，如果property为空，就根据该属性转换为驼峰式字符串，再取值
     */
    private String fieldCode;
    /**
     * 域名称
     */
    private String fieldName;
    /**
     * 父域序号
     */
    private int parentFieldSeqno;
    /**
     * 是否主键
     */
    private String isKey;
    /**
     * 是否允许为空
     */
    private String allowEmpty;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 数据格式
     */
    private String dataFormat;
    /**
     * 计算公式
     */
    private String formula;
    /**
     * 校验规则
     */
    private String checkRule;
    /**
     * 校验规则参数
     */
    private String checkRuleParam;
    /**
     * 统计规则
     */
    private String statRule;
    /**
     * 显示类型
     */
    private String showType;
    /**
     * 列宽度
     */
    private String columnWidth;
    /**
     * 对齐方式
     */
    private String alignType;
    /**
     * 渲染方法
     */
    private String renderer;
    /**
     * 头部样式
     */
    private String headerCls;
    /**
     * 数据样式
     */
    private String dataCls;
    /**
     * 排序序号
     */
    private int seqno;
    /**
     * 备注说明
     */
    private String desc;

    /**
     * ==== 用于渲染的辅助变量 ====
     */

    /**
     * 上偏移量
     */
    private int top = 0;
    /**
     * 左偏移量
     */
    private int left = 0;
    /**
     * 跨列数
     */
    private int colspan = 1;
    /**
     * 跨行数
     */
    private int rowspan = 1;
    /**
     * 数据属性
     */
    private String expression;
    /**
     * 是否隐藏
     */
    private boolean hidden;
    /**
     * 是否锁定
     */
    private boolean locked;
    /**
     * 是否显示
     */
    private boolean show;
    /**
     * 直接子节点
     */
    private List<Td> children = null;
    /**
     * 数据类型
     */
    private DataType dataTypeEnum = null;
    /**
     * 显示类型
     */
    private ShowType showTypeEnum = null;

    public Td() {}

    public Td(String fieldCode) {
        this(fieldCode, null, null);
    }

    public Td(String fieldCode, String dataType) {
        this(fieldCode, null, dataType);
    }

    public Td(String fieldCode, String fieldName, String dataType) {
        this.setFieldCode(fieldCode);
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.dataTypeEnum = DataType.instance(dataType);
    }

    /**
     * 添加一个子节点
     * 
     * @param child
     */
    public void addChild(Td child) {
        if (null == children) {
            children = new ArrayList<Td>();
        }
        children.add(child);
    }

    /**
     * 是否为叶子节点
     * 
     * @return
     */
    public boolean isLeaf() {
        return null == children || children.isEmpty();
    }

    /**
     * 获取所有子节点所占列数的总和
     * 
     * @return
     */
    public int getChildrenColspan() {
        int colspan = 1;
        if (null != children && !children.isEmpty()) {
            colspan = 0;
            for (Td td : children) {
                if (td.isShow()) {
                    colspan += td.getColspan();
                }
            }
        }
        return colspan;
    }

    public boolean isDBCharType() {
        int type = this.getDataTypeEnum().getSqlType();
        return type == Types.CHAR || type == Types.VARCHAR;
    }

    /**
     * ===============getter/setter方法==============================
     */
    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
        this.expression = CoreUtils.convertToCamel(fieldCode);
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
        this.dataTypeEnum = DataType.instance(dataType);
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
        getShowTypeEnum();
    }

    public String getAlignType() {
        if (CoreUtils.isBlank(alignType)) {
            alignType = getDataTypeEnum().getAlignType();
        }
        return alignType;
    }

    public void setAlignType(String alignType) {
        this.alignType = alignType;
    }

    public String getDataFormat() {
        if (CoreUtils.isBlank(dataFormat)) {
            dataFormat = getDataTypeEnum().getDataFormat();
        }
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public List<Td> getChildren() {
        return children;
    }

    public void setChildren(List<Td> children) {
        this.children = children;
    }

    public String getExpression() {
        if (CoreUtils.isBlank(expression)) {
            expression = CoreUtils.convertToCamel(this.fieldCode);
        }
        return expression;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public int getFieldSeqno() {
        return fieldSeqno;
    }

    public void setFieldSeqno(int fieldSeqno) {
        this.fieldSeqno = fieldSeqno;
    }

    public int getParentFieldSeqno() {
        return parentFieldSeqno;
    }

    public void setParentFieldSeqno(int parentFieldSeqno) {
        this.parentFieldSeqno = parentFieldSeqno;
    }

    public String getCheckRule() {
        return checkRule;
    }

    public void setCheckRule(String checkRule) {
        this.checkRule = checkRule;
    }

    public String getCheckRuleParam() {
        return checkRuleParam;
    }

    public void setCheckRuleParam(String checkRuleParam) {
        this.checkRuleParam = checkRuleParam;
    }

    public String getStatRule() {
        return statRule;
    }

    public void setStatRule(String statRule) {
        this.statRule = statRule;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getIsKey() {
        return isKey;
    }

    public void setIsKey(String isKey) {
        this.isKey = isKey;
    }

    public String getAllowEmpty() {
        return allowEmpty;
    }

    public void setAllowEmpty(String allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    public String getRenderer() {
        return renderer;
    }

    public void setRenderer(String renderer) {
        this.renderer = renderer;
    }

    public DataType getDataTypeEnum() {
        if (null == dataTypeEnum) {
            this.dataTypeEnum = DataType.instance(dataType);
        }
        return dataTypeEnum;
    }

    public ShowType getShowTypeEnum() {
        if (null == showTypeEnum) {
            this.showTypeEnum = ShowType.instance(getShowType());
            this.showTypeEnum.setTdProperties(this);
            this.show = !ShowType.NO_SHOW.equals(showTypeEnum);
        }
        return showTypeEnum;
    }

    public String getHeaderCls() {
        return headerCls;
    }

    public void setHeaderCls(String headerCls) {
        this.headerCls = headerCls;
    }

    public String getDataCls() {
        return dataCls;
    }

    public void setDataCls(String dataCls) {
        this.dataCls = dataCls;
    }

    public String getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(String columnWidth) {
        this.columnWidth = columnWidth;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isShow() {
        this.getShowTypeEnum();
        return show;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}
