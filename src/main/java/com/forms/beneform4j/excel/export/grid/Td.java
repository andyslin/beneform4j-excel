package com.forms.beneform4j.excel.export.grid;

import java.io.Serializable;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.export.grid.enumtype.DataType;
import com.forms.beneform4j.excel.export.grid.enumtype.ShowType;
import com.forms.beneform4j.excel.export.render.ITdRender;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 上传下载模型明细列<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2013-10-6<br>
 */
public class Td implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2672694190456767969L;

    /**
     * 对应数据库表字段的变量
     */
    private String modelId; // 模型ID
    private int fieldSeqno; // 域序号
    private String fieldCode; // 域代码/域属性
    private String fieldName; // 域名称/显示文本
    private int parentFieldSeqno;// 父域序号
    private String dataType; // 数据类型
    private String dataFormat; // 数据格式
    private String showType; // 显示类型
    private String columnWidth; // 列宽度
    private String alignType; // 对齐方式
    private int seqno; // 域排序序号
    private String memo; // 域说明

    private ITdRender render;

    /**
     * 用于页面展示的变量
     */
    private int top = 0; // 上偏移量
    private int left = 0; // 左偏移量
    private int colspan = 1; // 跨列数
    private int rowspan = 1; // 跨行数
    private String property; // 数据属性
    private boolean hidden; // 是否隐藏
    private boolean locked; // 是否锁定
    private boolean show; // 是否显示

    /**
     * 组成树型结构
     */
    private List<Td> children = null; // 直接子节点
    private DataType dataTypeEnum = null;
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
     * 判断是否为叶子节点
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
            for (int i = 0, size = children.size(); i < size; i++) {
                if (children.get(i).isShow()) {
                    colspan += children.get(i).getColspan();
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
        this.property = CoreUtils.convertToCamel(fieldCode);
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
        if (null == alignType || "".equals(alignType.trim())) {
            alignType = getDataTypeEnum().getAlignType();
        }
        return alignType;
    }

    public void setAlignType(String alignType) {
        this.alignType = alignType;
    }

    public String getDataFormat() {
        if (null == dataFormat || "".equals(dataFormat.trim())) {
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

    public String getProperty() {
        if (null == property || "".equals(property.trim())) {
            property = CoreUtils.convertToCamel(this.fieldCode);
        }
        return property;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setProperty(String property) {
        this.property = property;
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

    public ITdRender getRender() {
        return render;
    }

    public void setRender(ITdRender render) {
        this.render = render;
    }
}
