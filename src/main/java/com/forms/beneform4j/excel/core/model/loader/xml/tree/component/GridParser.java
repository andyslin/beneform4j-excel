package com.forms.beneform4j.excel.core.model.loader.xml.tree.component;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.xml.XmlHelper;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMComponent;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Grid;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Td;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConfig;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.ITreeEMComponentParser;
import com.forms.beneform4j.excel.core.model.loader.xml.tree.ITreeEMTdDecorator;
import com.forms.beneform4j.excel.exception.ExcelExceptionCodes;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 表格组件解析器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class GridParser implements ITreeEMComponentParser {

    private static final String TD = "td";

    @Override
    public ITreeEMComponent parse(String modelId, Element container) {
        List<Td> tds = new ArrayList<Td>();
        parseTd(modelId, -1, container, tds);
        if (!tds.isEmpty()) {
            Grid grid = newGrid();
            grid.build(tds);
            return grid;
        } else {
            throw Throw.createRuntimeException(ExcelExceptionCodes.BF0XLS27, modelId);
        }
    }

    protected Grid newGrid() {
        return new Grid();
    }

    protected Td newTd() {
        return new Td();
    }

    protected void setTdProperties(String modelId, Element ele, Td td) {
        td.setFieldName(ele.getAttribute("text"));//名称
        td.setExpression(ele.getAttribute("expression"));//数据属性
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

        doDecorate(ele, td);
    }

    protected void doDecorate(Element ele, Td td) {
        NodeList nl = ele.getChildNodes();
        for (int i = 0, l = nl.getLength(); i < l; i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                ITreeEMTdDecorator decorator = getDecorator((Element) node);
                if (null != decorator) {
                    decorator.decorate(td, ele, (Element) node);
                }
            }
        }
    }

    private void parseTd(String modelId, int parentFieldSeqno, Element ele, List<Td> tds) {
        int fieldSeqno = 0;
        if (parentFieldSeqno >= 0) {
            Td td = newTd();
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

    private void parseSubTds(String modelId, Element parent, List<Td> tds, int fieldSeqno) {
        List<Element> subs = XmlHelper.getChildElementsByTagName(parent, TD);
        if (null != subs && !subs.isEmpty()) {
            for (Element sub : subs) {
                parseTd(modelId, fieldSeqno, sub, tds);
            }
        }
    }

    /**
     * 获取装饰器
     * 
     * @param ele
     * @return
     */
    private ITreeEMTdDecorator getDecorator(Element ele) {
        String namespace = ele.getNamespaceURI();
        if (!XmlEMLoaderConfig.isDefaultNamespace(namespace)) {
            return XmlEMLoaderConfig.getTreeEMTdDecorator(namespace, ele.getLocalName());
        }
        return null;
    }
}
