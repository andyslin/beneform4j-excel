package com.forms.beneform4j.excel.core.exports.tree.painter.impl;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.SpreadsheetVersion;
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
import com.forms.beneform4j.core.util.data.accessor.DataAccessors;
import com.forms.beneform4j.core.util.data.accessor.IDataAccessor;
import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.excel.core.ExcelUtils;
import com.forms.beneform4j.excel.core.exports.tree.painter.POIExcelContext;
import com.forms.beneform4j.excel.core.exports.tree.painter.Scope;
import com.forms.beneform4j.excel.core.model.em.tree.ITreeEMRegion;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Grid;
import com.forms.beneform4j.excel.core.model.em.tree.impl.component.grid.Td;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : Grid组件绘制器<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class GridXlsxPainter extends AbstractSingleXlsxPainter<Grid> {

    private static final int TITLE_ROWS = 1; // 标题所占行数

    private static final Pattern DYNAMIC_PATTERN = Pattern.compile("\\$\\s*\\{\\s*([^}]+?)\\s*\\}");

    @Override
    protected Scope doPaint(Grid component, POIExcelContext context, IDataAccessor accessor, Scope offsetScope) {
        GridConfig config = new GridConfig();
        GridXlsxPainterDelegate gc = new GridXlsxPainterDelegate(component, context, accessor, config, offsetScope);
        return gc.paint();
    }

    /**
     * 解析动态字符串
     * 
     * @param accessor
     * @param src
     * @return
     */
    protected String resolveDynamicString(IDataAccessor accessor, String src) {
        if (CoreUtils.isBlank(src) || null == accessor) {
            return src;
        }

        Matcher matcher = DYNAMIC_PATTERN.matcher(src);
        while (matcher.find()) {
            String context = matcher.group(0);
            String expression = matcher.group(1);
            String replacement = accessor.value(expression, String.class);
            CommonLogger.debug("dynamic parse: " + context + " ===> " + replacement);
            src = matcher.replaceFirst(Matcher.quoteReplacement(replacement));
            matcher = DYNAMIC_PATTERN.matcher(src);
        }
        return src;
    }

    /**
     * 解析单元格的值
     * 
     * @param da
     * @param root
     * @param td
     * @param rows
     * @param index
     * @return
     */
    protected Object resolveCellValue(IDataAccessor da, Object root, Td td, int rows, int index) {
        Object val = da.value(td.getExpression());//暂不支持计算公式
        return val;
    }

    private class GridConfig {
        private boolean writeTitle = true; // 是否写标题
        private boolean autoFilter = true; // 是否设置为筛选模式
    }

    private class GridXlsxPainterDelegate {
        private final Grid grid;
        private final POIExcelContext context;
        private final IDataAccessor accessor;
        private final GridConfig config;
        //private final Scope offsetScope;

        private final List<Td> leaf;
        private final String title;
        private final int colspan; // 总共列数
        private final int titleRowspan; // 标题所占的行数

        private final Workbook wb;
        private final CreationHelper helper;
        private final ClientAnchor anchor;
        private final Font font;
        private final DataFormat format;

        private final CellStyle titleStyle; // 标题格式
        private final CellStyle headStyle; // 表头格式
        private final CellStyle[] dataStyle; // 数据格式

        private Sheet sheet;
        private Drawing draw;
        private int beginRow; // 开始行数
        private int beginCell;// 开始列数（= 数据开始列数）
        private int beginDataRow; // 数据开始行数（= 开始行数 + 标题行数 + 表头行数）
        private int sheetNumbers = 0;
        private final String sheetName;

        private GridXlsxPainterDelegate(Grid grid, POIExcelContext context, IDataAccessor accessor, GridConfig config, Scope offsetScope) {
            this.grid = grid;
            this.context = context;
            this.accessor = accessor;
            this.config = config;
            //this.offsetScope = offsetScope;

            this.leaf = grid.getLeaf();
            this.colspan = grid.getColspan();
            this.titleRowspan = config.writeTitle ? TITLE_ROWS : 0;// 标题所占的行数

            ITreeEMRegion region = grid.getRegion();
            String t = region.getTitle();
            if (CoreUtils.isBlank(t)) {
                t = region.getSheet().getSheetName();
                if (CoreUtils.isBlank(t)) {
                    t = region.getWorkbook().getName();
                }
            }
            this.title = resolveDynamicString(accessor, t);
            this.sheetName = context.getSheet().getSheetName();

            this.wb = context.getWorkbook();
            this.helper = context.getHelper();
            this.anchor = context.getAnchor();
            this.font = context.getFont();
            this.format = context.getDataFormat();

            this.titleStyle = ExcelUtils.getTitleStyle(wb);
            this.headStyle = ExcelUtils.getHeadStyle(wb);
            this.dataStyle = new CellStyle[leaf.size()];
            for (int i = 0, l = leaf.size(); i < l; i++) {
                Td td = leaf.get(i);
                this.dataStyle[i] = ExcelUtils.getDataStyle(wb, format, td.getAlignType(), td.getDataFormat());
            }
            this.newSheet(offsetScope);
        }

        private void newSheet(Scope offsetScope) {
            if (sheetNumbers >= 1) {
                context.createNewSheet(sheetName + sheetNumbers);
            }
            this.sheet = context.getSheet();
            this.draw = context.getDrawing();

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
        }

        private Scope paint() {
            sSetTitle();
            sSetHead();
            Iterator<Object> iterator = accessor.iterator("#root");
            int rows = beginDataRow, index = 0;
            if (null != iterator) {
                for (; iterator.hasNext();) {
                    if (rows == SpreadsheetVersion.EXCEL2007.getMaxRows()) {//创建新的表单
                        if (config.autoFilter) {
                            sheet.setAutoFilter(new CellRangeAddress(beginDataRow - 1, rows - 1, beginCell, beginCell + colspan - 1));
                        }
                        this.sheetNumbers++;
                        this.newSheet(null);
                        sSetTitle();
                        sSetHead();
                        rows = beginDataRow;
                    }

                    //                    if (rows == SpreadsheetVersion.EXCEL2007.getMaxRows() - 1) {
                    //                        ExcelUtils.setMerge(sheet, rows, beginCell, rows, beginCell + colspan - 1, "数据量过大，省略了部分数据，建议使用其它文本格式导出...", titleStyle);
                    //                        break;
                    //                    }

                    Object root = iterator.next();
                    IDataAccessor da = DataAccessors.newDataAccessor(root);
                    this.sSetOneData(da, root, rows++, index++);
                }
            }
            if (config.autoFilter) {
                sheet.setAutoFilter(new CellRangeAddress(beginDataRow - 1, rows - 1, beginCell, beginCell + colspan - 1));
            }
            return new Scope(beginRow, rows - 1, beginCell, beginCell + colspan - 1);
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
                    String header = resolveDynamicString(accessor, td.getFieldName());
                    ExcelUtils.setMerge(sheet, row1, col1, row2, col2, header, headStyle);
                    addCellComment(row1, col1, td.getDesc());
                }
            }
            sSetHiddenAndLocked();
        }

        /**
         * 添加注释
         * 
         * @param row1
         * @param col1
         * @param desc
         */
        private void addCellComment(int row1, int col1, String desc) {
            if (!CoreUtils.isBlank(desc)) {
                desc = resolveDynamicString(accessor, desc);
                Comment comment = draw.createCellComment(anchor);
                RichTextString rts = helper.createRichTextString(desc);
                rts.applyFont(font);
                comment.setString(rts);
                comment.setRow(row1);
                comment.setColumn(col1);
                CellUtil.getCell(CellUtil.getRow(row1, sheet), col1).setCellComment(comment);
            }
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
         * @param rows
         * @param index
         */
        private void sSetOneData(IDataAccessor da, Object data, int rows, int index) {
            for (int j = 0; j < colspan; j++) {
                Td td = leaf.get(j);
                Object val = resolveCellValue(da, data, td, rows, index);
                ExcelUtils.setCell(sheet, rows, beginCell + j, val, dataStyle[j]);
            }
        }
    }
}
