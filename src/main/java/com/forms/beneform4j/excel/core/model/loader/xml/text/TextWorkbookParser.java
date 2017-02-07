package com.forms.beneform4j.excel.core.model.loader.xml.text;

import java.nio.charset.Charset;

import org.w3c.dom.Element;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.excel.core.model.em.EMType;
import com.forms.beneform4j.excel.core.model.em.text.impl.TextEM;
import com.forms.beneform4j.excel.core.model.loader.IResourceEMLoadContext;
import com.forms.beneform4j.excel.core.model.loader.xml.IEMTopElementParser;
import com.forms.beneform4j.excel.core.model.loader.xml.XmlEMLoaderConsts;

public class TextWorkbookParser implements IEMTopElementParser {

    private static final String NAME = "name";
    private static final String DESC = "desc";
    private static final String PRIOR = "prior";

    private static final String CHARSET = "charset";
    private static final String SKIP_ROWS = "skipRows";
    private static final String MIN_CELLS_OF_ROW = "minCellsOfRow";
    private static final String DEFAULT_CELL_VALUE = "defaultCellValue";
    private static final String SEPARATOR = "separator";
    private static final String IGNORE_EMPTY_ROW = "ignoreEmptyRow";
    private static final String BATCH_NO_FORMAT = "batchNoFormat";
    private static final String ADD_BATCH_NO = "addBatchNo";
    private static final String ADD_ROW_INDEX = "addRowIndex";
    private static final String ADD_DATA_INDEX = "addDataIndex";

    @Override
    public void parse(IResourceEMLoadContext context, Element ele) {
        TextEM em = parseTextEM(ele);
        context.register(em);
    }

    public TextEM parseTextEM(Element ele) {
        TextEM em = new TextEM();
        String id = ele.getAttribute(XmlEMLoaderConsts.ID_PROPERTY);
        if (CoreUtils.isBlank(id)) {
            Throw.throwRuntimeException("模型ID不能为空");
        }
        em.setId(id);
        em.setType(EMType.TEXT);

        String name = ele.getAttribute(NAME);
        if (!CoreUtils.isBlank(name)) {
            em.setName(name);
        }

        String desc = ele.getAttribute(DESC);
        if (!CoreUtils.isBlank(desc)) {
            em.setDesc(desc);
        }

        int prior = 0;
        String priorStr = ele.getAttribute(PRIOR);
        if (!CoreUtils.isBlank(priorStr)) {
            prior = Integer.parseInt(priorStr);
        }
        em.setPrior(prior);

        String charset = ele.getAttribute(CHARSET);
        if (!CoreUtils.isBlank(charset)) {
            if (Charset.isSupported(charset)) {
                em.setCharset(charset);
            } else {
                Throw.throwRuntimeException("不支持的字符集" + charset);
            }
        }

        String skipRows = ele.getAttribute(SKIP_ROWS);
        if (!CoreUtils.isBlank(skipRows)) {
            em.setSkipRows(Integer.parseInt(skipRows));
        }

        String minCellsOfRow = ele.getAttribute(MIN_CELLS_OF_ROW);
        if (!CoreUtils.isBlank(minCellsOfRow)) {
            em.setMinCellsOfRow(Integer.parseInt(minCellsOfRow));
        }

        String defaultCellValue = ele.getAttribute(DEFAULT_CELL_VALUE);
        if (!CoreUtils.isBlank(defaultCellValue)) {
            em.setDefaultCellValue(defaultCellValue);
        }

        String separator = ele.getAttribute(SEPARATOR);
        if (!CoreUtils.isBlank(separator)) {
            em.setSeparator(separator);
        }

        String ignoreEmptyRow = ele.getAttribute(IGNORE_EMPTY_ROW);
        if (!CoreUtils.isBlank(ignoreEmptyRow)) {
            em.setIgnoreEmptyRow(CoreUtils.string2Boolean(ignoreEmptyRow));
        }

        String batchNoFormat = ele.getAttribute(BATCH_NO_FORMAT);
        if (!CoreUtils.isBlank(batchNoFormat)) {
            em.setBatchNoFormat(batchNoFormat);
        }

        String addBatchNo = ele.getAttribute(ADD_BATCH_NO);
        if (!CoreUtils.isBlank(addBatchNo)) {
            em.setAddBatchNo(CoreUtils.string2Boolean(addBatchNo));
        }

        String addRowIndex = ele.getAttribute(ADD_ROW_INDEX);
        if (!CoreUtils.isBlank(addRowIndex)) {
            em.setAddRowIndex(CoreUtils.string2Boolean(addRowIndex));
        }

        String addDataIndex = ele.getAttribute(ADD_DATA_INDEX);
        if (!CoreUtils.isBlank(addDataIndex)) {
            em.setAddDataIndex(CoreUtils.string2Boolean(addDataIndex));
        }
        return em;
    }
}
