package com.forms.beneform4j.excel.exports.tree.painter.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.ExcelUtils;
import com.forms.beneform4j.excel.data.accessor.DataAccessors;
import com.forms.beneform4j.excel.data.accessor.IDataAccessor;
import com.forms.beneform4j.excel.exports.tree.painter.POIExcelContext;
import com.forms.beneform4j.excel.exports.tree.painter.Scope;
import com.forms.beneform4j.excel.model.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.model.tree.component.grid.Grid;
import com.forms.beneform4j.excel.model.tree.component.grid.Td;

public class GridXlsxPainter extends AbstractSingleXlsxPainter<Grid> {

    private static final int TITLE_ROWS = 1; // 标题所占行数

    @Override
    protected Scope doPaint(Grid component, POIExcelContext context, IDataAccessor accessor, Scope offsetScope) {
        GridConfig config = new GridConfig();
        GridXlsxPainterDelegate gc = new GridXlsxPainterDelegate(component, context, accessor, config, offsetScope);
        return gc.paint();
    }

    private class GridConfig {
        private boolean writeTitle = true; // 是否写标题
        private boolean autoFilter = true; // 是否设置为筛选模式
    }

    private class GridXlsxPainterDelegate {
        private final Grid grid;
        //private final POIExcelContext context;
        private final IDataAccessor accessor;
        private final GridConfig config;
        //private final Scope offsetScope;

        private final List<Td> leaf;

        private final int colspan; // 总共列数
        private final int titleRowspan; // 标题所占的行数
        private final int beginRow; // 开始行数
        private final int beginCell;// 开始列数（= 数据开始列数）
        private final int beginDataRow; // 数据开始行数（= 开始行数 + 标题行数 + 表头行数）
        private final String title;

        private final Workbook wb;
        private final Sheet sheet;
        private final CreationHelper helper;
        private final ClientAnchor anchor;
        private final Font font;
        private final Drawing draw;
        private final DataFormat format;

        private final CellStyle titleStyle; // 标题格式
        private final CellStyle headStyle; // 表头格式
        private final CellStyle[] dataStyle; // 数据格式

        private GridXlsxPainterDelegate(Grid grid, POIExcelContext context, IDataAccessor accessor, GridConfig config, Scope offsetScope) {
            this.grid = grid;
            //this.context = context;
            this.accessor = accessor;
            this.config = config;
            //this.offsetScope = offsetScope;

            this.leaf = grid.getLeaf();
            this.colspan = grid.getColspan();
            this.titleRowspan = config.writeTitle ? TITLE_ROWS : 0;// 标题所占的行数

            ITreeEMRegion region = grid.getRegion();
            int row = 0, cell = 0;
            if (null != offsetScope) {
                switch (region.getOffsetPoint()) {
                    case LEFT_TOP://和左上角顶点相同行相同列开始，实际上会覆盖
                        row = offsetScope.getBeginRow();
                        cell = offsetScope.getBeginCell();
                        break;
                    case LEFT_BUTTOM://左下角，在下一行的同一列开始
                        row = offsetScope.getEndRow() + 1;
                        cell = offsetScope.getBeginCell();
                        break;
                    case RIGHT_TOP://右上角，在同一行的下一列开始
                        row = offsetScope.getBeginRow();
                        cell = offsetScope.getEndCell() + 1;
                        break;
                    case RIGHT_BUTTOM://右下角，在下一行的下一列开始
                        row = offsetScope.getEndRow() + 1;
                        cell = offsetScope.getEndCell() + 1;
                        break;
                }
            }

            this.beginCell = cell + region.getOffsetX();
            this.beginRow = row + region.getOffsetY();
            this.beginDataRow = this.beginRow + this.titleRowspan + grid.getRowspan();

            String t = region.getTitle();
            if (CoreUtils.isBlank(t)) {
                t = region.getSheet().getSheetName();
                if (CoreUtils.isBlank(t)) {
                    t = region.getWorkbook().getName();
                }
            }
            this.title = t;

            this.wb = context.getWorkbook();
            this.sheet = context.getSheet();
            this.helper = context.getHelper();
            this.anchor = context.getAnchor();
            this.font = context.getFont();
            this.draw = context.getDrawing();
            this.format = context.getDataFormat();

            this.titleStyle = ExcelUtils.getTitleStyle(wb);
            this.headStyle = ExcelUtils.getHeadStyle(wb);
            this.dataStyle = new CellStyle[leaf.size()];
            for (int i = 0, l = leaf.size(); i < l; i++) {
                Td td = leaf.get(i);
                this.dataStyle[i] = ExcelUtils.getDataStyle(wb, format, td.getAlignType(), td.getDataFormat());
            }

        }

        private Scope paint() {
            sSetTitle();
            sSetHead();
            Iterator<Object> iterator = accessor.iterator("#root");
            int index = 0;
            if (null != iterator) {
                for (; iterator.hasNext();) {
                    Object root = iterator.next();
                    IDataAccessor da = DataAccessors.newDataAccessor(root);
                    this.sSetOneData(da, root, index++);
                }
            }
            if (config.autoFilter) {
                sheet.setAutoFilter(new CellRangeAddress(beginDataRow - 1, beginDataRow + index - 1, beginCell, beginCell + colspan - 1));
            }
            return new Scope(beginRow, beginDataRow + index - 1, beginCell, beginCell + colspan - 1);
        }

        /**
         * 设置标题
         */
        private void sSetTitle() {
            if (config.writeTitle) {
                ExcelUtils.setMerge(sheet, beginRow, beginCell, beginRow + titleRowspan - 1, beginCell + colspan - 1, title, titleStyle);
            }
        }

        /**
         * 设置表头
         */
        private void sSetHead() {
            for (Td td : grid.getList()) {
                if (td.isShow()) {
                    int row1 = beginRow + titleRowspan + td.getTop();
                    int col1 = beginCell + td.getLeft();
                    int row2 = beginRow + titleRowspan + td.getTop() + td.getRowspan() - 1;
                    int col2 = beginCell + td.getLeft() + td.getColspan() - 1;
                    ExcelUtils.setMerge(sheet, row1, col1, row2, col2, td.getFieldName(), headStyle);
                    // 添加注释
                    String desc = td.getDesc();
                    if (!CoreUtils.isBlank(desc)) {
                        Comment comment = draw.createCellComment(anchor);
                        comment.setString(getRichTextString(desc.trim()));
                        comment.setRow(row1);
                        comment.setColumn(col1);
                        CellUtil.getCell(CellUtil.getRow(row1, sheet), col1).setCellComment(comment);
                    }
                }
            }
            sSetHiddenAndLocked();
        }

        private RichTextString getRichTextString(String text) {
            RichTextString rts = helper.createRichTextString(text);
            rts.applyFont(font); // 用于解决2007格式中批注显示不出来的问题
            return rts;
        }

        /**
         * 设置隐藏和锁定
         */
        private void sSetHiddenAndLocked() {
            List<Td> leaf = grid.getLeaf();
            for (int i = 0, s = leaf.size(); i < s; i++) {
                if (leaf.get(i).isHidden()) {
                    sheet.setColumnHidden(beginCell + i, true);
                }
            }
            if (grid.getLockedIndex() > 0) {
                sheet.createFreezePane(beginCell + grid.getLockedIndex(), beginDataRow);
            }
            ExcelUtils.autoSizeColumn(sheet, colspan, beginCell);
        }

        /**
         * 设置一行数据
         * 
         * @param da
         * @param data
         * @param index
         */
        private void sSetOneData(IDataAccessor da, Object data, int index) {
            for (int j = 0; j < colspan; j++) {
                Td td = leaf.get(j);
                Object val = resolveCellValue(da, data, td);
                ExcelUtils.setCell(sheet, beginDataRow + index, beginCell + j, val, dataStyle[j]);
            }
        }

        /**
         * 解析单元格的值
         * 
         * @param da
         * @param root
         * @param td
         * @return
         */
        private Object resolveCellValue(IDataAccessor da, Object root, Td td) {
            Object val = da.value(td.getProperty());//暂不支持计算公式
            return val;
        }
    }
}