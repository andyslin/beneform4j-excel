package com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.beneform4j.excel.core.model.em.tree.impl.component.AbstractTreeEMComponent;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 表格组件<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class Grid extends AbstractTreeEMComponent {

    /**
     * 
     */
    private static final long serialVersionUID = 137887696373466885L;

    /**
     * 页面展示属性
     */
    private List<Td> list = null; // 所有表头的Td节点
    private Map<Integer, Td> tdMap = new HashMap<Integer, Td>();
    private Tr[] th = null; // 所有表头
    private int rowspan = 0; // 表头所占行数
    private int colspan = 0; // 表头所占列数
    private List<Td> leaf = null; // 叶子节点
    private String[] properties = null; // 叶子节点对应的数据属性
    private String[] fieldCodes = null; // 叶子节点对应的数据属性
    private int lockedIndex = 0;

    /**
     * 构建模型
     * 
     * @param list
     */
    public void build(List<Td> list) {
        this.setRowspan(list);
        this.setTh();
        this.setSpan();
        this.setLeft(null);
        this.setLeaf();
        this.colspan = this.leaf.size();
        for (Td td : list) {
            tdMap.put(td.getFieldSeqno(), td);
        }
    }

    /**
     * 根据节点序号获取节点模型
     * 
     * @param fieldSeqno
     * @return
     */
    public Td getNode(int fieldSeqno) {
        return tdMap.get(fieldSeqno);
    }

    /**
     * 获取数据属性
     * 
     * @return
     */
    public String[] getDataProperties() {
        if (null == this.properties) {
            List<String> properties = new ArrayList<String>();
            for (Td td : this.getLeaf()) {
                properties.add(td.getExpression());
            }
            this.properties = new String[properties.size()];
            properties.toArray(this.properties);
        }
        return this.properties;
    }

    /**
     * 获取数据属性
     * 
     * @return
     */
    public String[] getDataFieldCodes() {
        if (null == this.fieldCodes) {
            List<String> fieldCodes = new ArrayList<String>();
            for (Td td : this.getLeaf()) {
                fieldCodes.add(td.getFieldCode());
            }
            this.fieldCodes = new String[fieldCodes.size()];
            fieldCodes.toArray(this.fieldCodes);
        }
        return this.fieldCodes;
    }

    /**
     * 插入节点
     * 
     * @param td
     * @return
     */
    public Grid insert(Td... td) {
        if (null != td && 0 != td.length) {
            for (int l = td.length - 1; l >= 0; l--) {
                insert(td[l]);
            }
        }
        return this;
    }

    /**
     * 附加节点
     * 
     * @param td
     * @return
     */
    public Grid append(Td... td) {
        if (null != td && 0 != td.length) {
            for (Td af : td) {
                append(af);
            }
        }
        return this;
    }

    /**
     * 返回上传参数类型
     * 
     * @return
     */
    public int[] getUploadArgTypes() {
        int length = leaf.size();
        int[] types = new int[length];
        int i = 0;
        for (Td td : leaf) {
            types[i++] = td.getDataTypeEnum().getSqlType();
        }
        return types;
    }

    /**
     * =========================属性的读写（getter/setter）方法================================
     */
    public List<Td> getList() {
        return list;
    }

    public void setList(List<Td> list) {
        this.list = list;
    }

    public Tr[] getTh() {
        return th;
    }

    public void setTh(Tr[] th) {
        this.th = th;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public List<Td> getLeaf() {
        return this.leaf;
    }

    public void setLeaf(List<Td> leaf) {
        this.leaf = leaf;
    }

    public int getLockedIndex() {
        return lockedIndex;
    }

    /**
     * =========================私有方法================================
     */
    /**
     * 设置垂直偏移量
     * 
     * @param list
     */
    private void setRowspan(List<Td> list) {
        int size = list.size();
        List<Td> lockList = new ArrayList<Td>();
        List<Td> commonList = new ArrayList<Td>();
        List<Td> orderList = new ArrayList<Td>(); // 排好序的Td节点
        List<Integer> parentSeqno = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            Td td = list.get(i);
            if (td.getFieldSeqno() > 0) {
                parentSeqno.add(td.getFieldSeqno());
            }
            switch (td.getShowTypeEnum()) {
                case LOCK_HIDDEN:
                case LOCK_SHOW:
                    lockList.add(td);
                    break;
                default:
                    commonList.add(td);
                    break;
            }
        }
        lockList.addAll(commonList);
        this.list = lockList;

        for (int i = 0; i < size; i++) {
            Td td = lockList.get(i);
            if (0 == td.getParentFieldSeqno() || !parentSeqno.contains(td.getParentFieldSeqno())) {
                td.setTop(0);
                if (td.isShow()) {
                    rowspan = Math.max(rowspan, 1);
                }
                orderList.add(td);
                setRowspan(orderList, td);
            }
        }
        this.list = orderList;
    }

    /**
     * 递归设置垂直偏移量
     * 
     * @param orderList
     * @param parent
     */
    private void setRowspan(List<Td> orderList, Td parent) {
        for (int i = 0, size = list.size(); i < size; i++) {
            Td td = list.get(i);
            if (td.getParentFieldSeqno() == parent.getFieldSeqno()) {
                td.setTop(parent.getTop() + 1);
                if (td.isShow()) {
                    rowspan = Math.max(rowspan, parent.getTop() + 2);
                }
                parent.addChild(td);
                orderList.add(td);
                setRowspan(orderList, td);
            }
        }
    }

    /**
     * 获取某一指定深度的子节点
     * 
     * @param list
     * @param top
     * @return
     */
    private List<Td> getDepthChildren(int top) {
        List<Td> children = new ArrayList<Td>();
        for (int i = 0, size = list.size(); i < size; i++) {
            Td td = list.get(i);
            if (top == td.getTop()) {
                children.add(td);
            }
        }
        return children;
    }

    /**
     * 设置表头
     */
    private void setTh() {
        if (rowspan > 0) {
            th = new Tr[rowspan];
            for (int i = 0; i < rowspan; i++) {
                th[i] = new Tr(getDepthChildren(i));
            }
        }
    }

    /**
     * 设置跨度
     */
    private void setSpan() {
        for (int d = rowspan - 1; d >= 0; d--) {
            List<Td> children = getDepthChildren(d);
            for (int i = 0, size = children.size(); i < size; i++) {
                Td td = children.get(i);
                if (!td.isShow()) {
                    td.setColspan(0);
                    td.setRowspan(0);
                } else if (td.isLeaf())// 叶子节点，跨列数为1，跨行数位表头部分
                {
                    td.setColspan(1);
                    td.setRowspan(rowspan - td.getTop());
                } else// 非叶子节点，跨行数为1，跨列数为各个子节点的跨列数之和
                {
                    td.setRowspan(1);
                    td.setColspan(td.getChildrenColspan());
                }
            }
        }
    }

    /**
     * 设置叶子节点
     */
    private void setLeaf() {
        List<Td> children = new ArrayList<Td>();
        for (int i = 0, size = list.size(); i < size; i++) {
            Td td = list.get(i);
            if (td.isLeaf()) {
                children.add(td);
                if (td.isLocked() && td.isShow()) {// 去掉不显示的数据
                    this.lockedIndex++;
                }
            }
        }
        this.leaf = children;
    }

    /**
     * 设置横坐标
     * 
     * @param parent
     */
    private void setLeft(Td parent) {
        List<Td> children = null == parent ? getDepthChildren(0) : parent.getChildren();
        if (null != children && !children.isEmpty()) {
            int left = null == parent ? 0 : parent.getLeft();
            for (Td td : children) {
                td.setLeft(left);
                left += td.getColspan();
                setLeft(td);
            }
        }
    }

    /**
     * 前面添加节点
     * 
     * @param td
     */
    private void insert(Td td) {
        td.setLeft(0);
        td.setRowspan(this.rowspan);
        td.setColspan(1);
        for (Td t : this.list) {
            t.setLeft(t.getLeft() + 1);
        }
        this.list.add(0, td);
        this.leaf.add(0, td);
        this.th[0].getTds().add(0, td);
        this.colspan = this.colspan + 1;
    }

    /**
     * 后面添加节点
     * 
     * @param td
     */
    private void append(Td td) {
        td.setLeft(this.colspan);
        td.setRowspan(this.rowspan);
        td.setColspan(1);
        this.list.add(td);
        this.leaf.add(td);
        this.th[0].getTds().add(td);
        this.colspan = this.colspan + 1;
    }
}
