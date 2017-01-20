package com.forms.beneform4j.excel.core.model.loader.xml.tree.component;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Grid;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Td;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.ITreeEMComponentParser;

public class GridParser implements ITreeEMComponentParser {

    private static final String TD = "td";

    @Override
    public ITreeEMComponent parse(String modelId, Element container) {
        List<Td> tds = new ArrayList<Td>();
        parseTd(modelId, -1, container, tds);
        if (!tds.isEmpty()) {
            Grid grid = new Grid();
            grid.build(tds);
            return grid;
        } else {
            throw Throw.createRuntimeException("配置文件中id为" + modelId + "的节点没有有效的<" + TD + ">子节点（集）");
        }
    }

    private void parseTd(String modelId, int parentFieldSeqno, Element ele, List<Td> tds) {
        int fieldSeqno = 0;
        if (parentFieldSeqno >= 0) {
            Td td = new Td();
            tds.add(td);
            fieldSeqno = tds.size();
            td.setModelId(modelId);
            td.setFieldSeqno(fieldSeqno);
            td.setSeqno(fieldSeqno);
            td.setParentFieldSeqno(parentFieldSeqno);
            setTdProperties(modelId, ele, td);
        }
        parseSubTds(modelId, ele, tds, fieldSeqno);
    }

    protected void setTdProperties(String modelId, Element ele, Td td) {
        td.setFieldName(ele.getAttribute("text"));//名称
        td.setProperty(ele.getAttribute("property"));//数据属性
        td.setFormula(ele.getAttribute("formula"));//计算公式
        td.setRenderer(ele.getAttribute("renderer"));//渲染函数
        td.setStatRule(ele.getAttribute("stat"));//统计方法
        td.setDesc(ele.getAttribute("desc"));//备注

        td.setDataType(ele.getAttribute("dataType"));//数据类型
        td.setDataFormat(ele.getAttribute("format"));//格式
        td.setAlignType(ele.getAttribute("align"));//对齐方式
        td.setShowType(ele.getAttribute("showType"));//显示类型
        td.setColumnWidth(ele.getAttribute("width"));//宽度
        td.setHeaderCls(ele.getAttribute("headerCls"));//表头样式
        td.setDataCls(ele.getAttribute("dataCls"));//数据样式
    }

    private void parseSubTds(String modelId, Element parent, List<Td> tds, int fieldSeqno) {
        List<Element> subs = XmlHelper.getChildElementsByTagName(parent, TD);
        if (null != subs && !subs.isEmpty()) {
            for (Element sub : subs) {
                parseTd(modelId, fieldSeqno, sub, tds);
            }
        }
    }
}
