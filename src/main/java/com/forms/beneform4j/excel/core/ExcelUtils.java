package com.forms.beneform4j.excel.core;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.forms.beneform4j.core.util.CoreUtils;

/**
 * 版权：深圳四方精创资讯股份有限公司 功能：工具类－Excel数据提取时使用的工具类 作者： LinJisong 版本： 1.0.0 日期： 2015-11-16
 */
public class ExcelUtils {

    private static final int MAX_ROWS = 65535; // 老版本的Excel最大行数

    private static final int MIN_COLUMN_WIDTH = 512 * 10; // Excel的最小列宽,10个中文

    private static final List<String> POS = Arrays.asList( // Excel位置
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ", "BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BQ", "BR", "BS", "BT", "BU", "BV", "BW", "BX", "BY", "BZ", "CA", "CB", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CJ", "CK", "CL", "CM", "CN", "CO", "CP", "CQ", "CR", "CS", "CT", "CU", "CV", "CW", "CX", "CY", "CZ", "DA", "DB", "DC", "DD", "DE", "DF", "DG", "DH", "DI", "DJ", "DK", "DL", "DM", "DN", "DO", "DP", "DQ", "DR", "DS", "DT", "DU", "DV", "DW", "DX", "DY", "DZ", "EA", "EB", "EC", "ED", "EE", "EF", "EG", "EH", "EI", "EJ", "EK", "EL", "EM", "EN", "EO", "EP", "EQ", "ER", "ES", "ET", "EU", "EV", "EW", "EX", "EY", "EZ", "FA", "FB", "FC", "FD", "FE", "FF", "FG", "FH", "FI", "FJ", "FK", "FL", "FM",
            "FN", "FO", "FP", "FQ", "FR", "FS", "FT", "FU", "FV", "FW", "FX", "FY", "FZ", "GA", "GB", "GC", "GD", "GE", "GF", "GG", "GH", "GI", "GJ", "GK", "GL", "GM", "GN", "GO", "GP", "GQ", "GR", "GS", "GT", "GU", "GV", "GW", "GX", "GY", "GZ", "HA", "HB", "HC", "HD", "HE", "HF", "HG", "HH", "HI", "HJ", "HK", "HL", "HM", "HN", "HO", "HP", "HQ", "HR", "HS", "HT", "HU", "HV", "HW", "HX", "HY", "HZ", "IA", "IB", "IC", "ID", "IE", "IF", "IG", "IH", "II", "IJ", "IK", "IL", "IM", "IN", "IO", "IP", "IQ", "IR", "IS", "IT", "IU", "IV");

    // public static void autoSizeColumn(Workbook wb){
    // try{
    // for(int i = wb.getNumberOfSheets(); i >= 0; i--){
    // Sheet sheet = wb.getSheetAt(i);
    // }
    // }catch(Throwable t){}
    // }

    public static Workbook newStreamWorkbook() {
        return newStreamWorkbook(null);
    }

    public static Workbook newStreamWorkbook(XSSFWorkbook workbook) {
        return newStreamWorkbook(workbook, 1000);
    }

    public static Workbook newStreamWorkbook(XSSFWorkbook workbook, int rowAccessWindowSize) {
        return new SXSSFWorkbook(workbook, rowAccessWindowSize);
    }

    public static Workbook newStreamWorkbook(XSSFWorkbook workbook, int rowAccessWindowSize, boolean compressTmpFiles) {
        return new SXSSFWorkbook(workbook, rowAccessWindowSize, compressTmpFiles);
    }

    public static Workbook newStreamWorkbook(XSSFWorkbook workbook, int rowAccessWindowSize, boolean compressTempFiles, boolean useSharedStringsTable) {
        return new SXSSFWorkbook(workbook, rowAccessWindowSize, compressTempFiles, useSharedStringsTable);
    }

    /**
     * 自适应宽度
     * 
     * @param sheet 表单
     * @param cols 列数
     */
    public static void autoSizeColumn(Sheet sheet, int cols) {
        autoSizeColumn(sheet, cols, 0);
    }

    /**
     * 自适应宽度
     * 
     * @param sheet 表单
     * @param cols 列数
     * @param beginCell 起始列
     */
    public static void autoSizeColumn(Sheet sheet, int cols, int beginCell) {
        try {
            for (short k = 0; k < cols; k++) {
                if (0 != sheet.getColumnWidth(beginCell + k)) {
                    sheet.autoSizeColumn(beginCell + k, true);
                }
            }
        } catch (Throwable e) {
        }

        try {
            for (short k = 0; k < cols; k++) {
                if (MIN_COLUMN_WIDTH > sheet.getColumnWidth(beginCell + k)) {
                    sheet.setColumnWidth(beginCell + k, MIN_COLUMN_WIDTH);
                }
            }
        } catch (Throwable e) {
        }
    }

    /**
     * 获取RGB颜色
     * 
     * @param palette
     * @param r
     * @param g
     * @param b
     * @return
     */
    @SuppressWarnings("unused")
    private static short getRGBColor(HSSFPalette palette, int r, int g, int b) {
        return palette.findColor((byte) r, (byte) g, (byte) b).getIndex();
    }

    /**
     * 创建名称
     * 
     * @param wb
     * @param name
     * @param formulaText
     * @return
     */
    public static Name createName(Workbook wb, String name, String formulaText) {
        Name rs = wb.createName();
        rs.setNameName(name);
        rs.setRefersToFormula(formulaText);
        return rs;
    }

    /**
     * 获取数据有效性验证
     * 
     * @param name
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @return
     */
    public static DataValidation getDataValidation(String name, int firstRow, int lastRow, int firstCol, int lastCol) {
        DataValidationConstraint dvc = DVConstraint.createFormulaListConstraint(name);
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        DataValidation dv = new HSSFDataValidation(regions, dvc);
        return dv;
    }

    /**
     * 获取格式
     * 
     * @param wb
     * @param color 字体颜色
     * @param bgColor 背景颜色
     * @param align 水平方向
     * @return
     */
    public static CellStyle getStyle(Workbook wb, short color, short bgColor, HorizontalAlignment align) {
        CellStyle style = getStyle(wb);
        Font font = wb.createFont();
        font.setColor(color);
        style.setFont(font);
        style.setFillForegroundColor(bgColor);
        style.setAlignment(align);
        return style;
    }

    /**
     * 获取通用格式
     * 
     * @param wb
     * @return
     */
    public static CellStyle getStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN); // 下边框
        style.setBorderLeft(BorderStyle.THIN); // 左边框
        style.setBorderRight(BorderStyle.THIN); // 右边框
        style.setBorderTop(BorderStyle.THIN); // 上边框
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND); // 填充方式
        style.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直居中
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        return style;
    }

    /**
     * 设置单元格的值
     * 
     * @param sheet
     * @param rowIndex
     * @param cellIndex
     * @param value
     * @param style
     */
    public static void setCell(Sheet sheet, int rowIndex, int cellIndex, Object value, CellStyle style) {
        Row row = CellUtil.getRow(rowIndex, sheet);
        Cell cell = CellUtil.getCell(row, cellIndex);
        setCellStyleValue(cell, style, value);
    }

    /**
     * 设置区域
     * 
     * @param sheet 表单
     * @param beginRow 开始行
     * @param beginCol 开始列
     * @param endRow 结束行
     * @param endCol 结束列
     * @param value 值
     * @param style 样式
     */
    public static void setMerge(Sheet sheet, int beginRow, int beginCol, int endRow, int endCol, Object value, CellStyle style) {
        Row row = null;
        Cell cell = null;

        if (beginRow == endRow && beginCol == endCol) {
            row = CellUtil.getRow(beginRow, sheet);
            cell = CellUtil.getCell(row, beginCol);
            setCellStyleValue(cell, style, value);
        } else {
            sheet.addMergedRegion(new CellRangeAddress(beginRow, endRow, beginCol, endCol));
            for (int i = beginRow; i <= endRow; i++) {
                row = CellUtil.getRow(i, sheet);
                for (int j = beginCol; j <= endCol; j++) {
                    cell = CellUtil.getCell(row, j);
                    if (i == beginRow && j == beginCol) {
                        setCellStyleValue(cell, style, value);
                    } else if (null != style) {
                        cell.setCellStyle(style);
                    }
                }
            }
        }
    }

    /**
     * 设置单元格样式
     * 
     * @param cell
     * @param style
     */
    public static void setCellStyleValue(Cell cell, CellStyle style, Object value) {
        if (null != style) {
            cell.setCellStyle(style);
            if (null != value) {
                if (value instanceof Comment || value instanceof Comment || value instanceof CellFormula) {
                    setCellValue(cell, value);
                    return;
                }
                try {
                    switch (style.getDataFormat()) {
                        case 1:// "0"
                        case 2:// "0.00"
                        case 9:// "0%"
                        case 0xa:// "0.00%"
                            setCellValue(cell, "".equals(value.toString().trim()) ? "" : new BigDecimal(value.toString().trim()));
                            break;
                        case 3:// "#,##0"
                        case 4:// "#,##0.00"
                            setCellValue(cell, "".equals(value.toString().replace(",", "").trim()) ? "" : new BigDecimal(value.toString().replace(",", "").trim()));
                            break;
                        case 031:// "@"
                            setCellValue(cell, value.toString());
                            break;
                        default:
                            setCellValue(cell, value);
                    }
                } catch (Exception e) {
                    setCellValue(cell, value);
                }
            }
        } else {
            setCellValue(cell, value);
        }
    }

    /**
     * 设置单元格值
     * 
     * @param cell
     * @param value
     */
    public static void setCellValue(Cell cell, Object value) {
        if (null != value) {
            if (value instanceof String) {
                cell.setCellValue((String) value);
            } else if (value instanceof BigInteger) {
                cell.setCellValue(((BigInteger) value).intValue());
            } else if (value instanceof Number) {
                cell.setCellValue(((Number) value).doubleValue());
            } else if (value instanceof Date) {
                cell.setCellValue((Date) value);
            } else if (value instanceof Boolean) {
                cell.setCellValue(((Boolean) value).booleanValue());
            } else if (value instanceof RichTextString) {
                cell.setCellValue((RichTextString) value);
            } else if (value instanceof Calendar) {
                cell.setCellValue((Calendar) value);
            } else if (value instanceof Comment) {
                cell.setCellComment((Comment) value);
            } else if (value instanceof Hyperlink) {
                cell.setHyperlink((Hyperlink) value);
            } else if (value instanceof CellFormula) {
                cell.setCellFormula(((CellFormula) value).formula);
            } else {
                cell.setCellValue(value.toString().trim());
            }
        }
    }

    /**
     * 获取可安全使用的Sheet名称
     * 
     * @param name
     * @return
     */
    public static String getSafeSheetName(String name) {
        return getSafeSheetName(name, 31);
    }

    /**
     * 获取可安全使用的Sheet名称
     * 
     * @param name
     * @param minLength
     * @return
     */
    public static String getSafeSheetName(String name, int minLength) {
        if (name == null) {
            return "null";
        }
        if (name.length() < 1) {
            return "empty";
        }
        int length = Math.min(minLength, name.length());
        String shortenname = name.substring(0, length);
        StringBuilder result = new StringBuilder(shortenname);
        for (int i = 0; i < length; i++) {
            char ch = result.charAt(i);
            switch (ch) {
                case '/':
                case '\\':
                case '?':
                case '*':
                case ']':
                case '[':
                    result.setCharAt(i, ' ');
                    break;
                case '\'':
                    if (i == 0 || i == length - 1) {
                        result.setCharAt(i, ' ');
                    }
                    break;
                default:
            }
        }
        return result.toString();
    }

    /**
     * 获取标题样式
     * 
     * @param wb
     * @return
     */
    public static CellStyle getTitleStyle(Workbook wb) {
        CellStyle titleStyle = getStyle(wb, HSSFColor.BLACK.index, HSSFColor.WHITE.index, HorizontalAlignment.CENTER);
        Font font = wb.createFont();
        // font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setBold(true);
        // font.setFontHeight((short)300);
        titleStyle.setFont(font);
        return titleStyle;
    }

    /**
     * 获取表头样式
     * 
     * @param wb
     * @return
     */
    public static CellStyle getHeadStyle(Workbook wb) {
        // CellStyle headStyle = getStyle(wb, HSSFColor.BLACK.index, getRGBColor(palette, 204, 255,
        // 204), CellStyle.ALIGN_CENTER);
        CellStyle headStyle = getStyle(wb, HSSFColor.BLACK.index, (short) 42, HorizontalAlignment.CENTER);
        return headStyle;
    }

    /**
     * 获取数据列的格式
     * 
     * @param wb
     * @param palette
     * @param dataFormat
     * @param align
     * @param format
     * @return
     */
    public static CellStyle getDataStyle(Workbook wb, DataFormat dataFormat, String align, String format) {
        // CellStyle style = ExcelUtil.getStyle(wb, HSSFColor.BLACK.index,
        // ExcelUtil.getRGBColor(palette, 153, 204, 255), CellStyle.ALIGN_LEFT);
        CellStyle style = getStyle(wb, HSSFColor.BLACK.index, (short) 44, HorizontalAlignment.LEFT);
        if ("right".equalsIgnoreCase(align)) {
            style.setAlignment(HorizontalAlignment.RIGHT);
        } else if ("center".equalsIgnoreCase(align)) {
            style.setAlignment(HorizontalAlignment.CENTER);
        }
        if (null != format && !"".equals(format.trim())) {
            style.setDataFormat(dataFormat.getFormat(format.trim()));
        }
        return style;
    }

    /**
     * 是否有效列位置
     * 
     * @param columnPosition
     * @return
     */
    public static boolean isValidColumnPosition(String columnPosition) {
        if (null == columnPosition) {
            return false;
        }
        return POS.contains(columnPosition.toUpperCase());
    }

    /**
     * 根据列位置获取列索引
     * 
     * @param columnPosition
     * @return
     */
    public static int getColumnPositionIndex(String columnPosition) {
        if (null == columnPosition) {
            return -1;
        }
        return POS.indexOf(columnPosition.toUpperCase());
    }

    /**
     * 根据列索引获取列位置
     * 
     * @param columnIndex
     * @return
     */
    public static String getColumnPosition(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= POS.size()) {
            throw new RuntimeException("Excel列索引" + columnIndex + "无效");
        }
        String p = POS.get(columnIndex);
        return p;
    }

    /**
     * 是否有效行位置
     * 
     * @param rowPosition
     * @return
     */
    public static boolean isValidRowPosition(int rowPosition) {
        return rowPosition >= 1 && rowPosition <= MAX_ROWS;
    }

    /**
     * 根据行索引获取行位置
     * 
     * @param rowIndex
     * @return
     */
    public static int getRowPosition(int rowIndex) {
        return rowIndex + 1;
    }

    /**
     * 根据行位置获取行索引
     * 
     * @param rowPosition
     * @return
     */
    public static int getRowPositionIndex(int rowPosition) {
        return rowPosition - 1;
    }

    /**
     * 获取相对位置的表格
     * 
     * @param cell
     * @param offsetX
     * @param offsetY
     * @return
     */
    public static Cell getOffsetCell(Cell cell, int offsetX, int offsetY) {
        if (null == cell) {
            return null;
        }
        Sheet sheet = cell.getRow().getSheet();
        Row row = sheet.getRow(cell.getRowIndex() + offsetY);
        if (null == row) {
            return null;
        }
        return row.getCell(cell.getColumnIndex() + offsetX);
    }

    /**
     * 获取单元格的内容
     * 
     * @param cell
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getCellValue(Cell cell) {
        if (null == cell) {
            return null;
        }
        String data = null;
        switch (cell.getCellTypeEnum()) {
            case BLANK:
                data = "";
                break;
            case BOOLEAN:
                data = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                data = String.valueOf(cell.getCellFormula());
                break;
            case NUMERIC:
                NumberFormat defaultFormat = NumberFormat.getNumberInstance();
                defaultFormat.setMaximumFractionDigits(0);
                defaultFormat.setGroupingUsed(false);
                data = String.valueOf(defaultFormat.format(cell.getNumericCellValue()));
                break;
            case STRING:
                data = cell.getStringCellValue();
                break;
            default:
                data = cell.getStringCellValue();
                break;
        }
        return data;
    }

    /**
     * 获取合并单元格
     * 
     * @param sheet
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public static Cell getMergetCell(Sheet sheet, int rowIndex, int columnIndex) {
        int mn = sheet.getNumMergedRegions();
        for (int i = 0; i < mn; i++) {
            CellRangeAddress cra = sheet.getMergedRegion(i);
            if (cra.isInRange(rowIndex, columnIndex)) {
                return sheet.getRow(cra.getFirstRow()).getCell(cra.getFirstColumn());
            }
        }
        return sheet.getRow(rowIndex).getCell(columnIndex);
    }

    /**
     * 根据目标类型，获取单元格的内容 只支持String、int、long、boolean、double几种类型
     * 
     * @param cell
     * @param cls
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <E> E getCellValue(Cell cell, Class<E> cls) {
        @SuppressWarnings("deprecation")
        CellType cType = cell.getCellTypeEnum();
        if (String.class.isAssignableFrom(cls)) {
            return (E) getCellValue(cell);
        } else if (int.class == cls || Integer.class == cls) {
            switch (cType) {
                case BLANK:
                    Integer iv = 0;
                    return (E) iv;
                case BOOLEAN:
                    Integer biv = cell.getBooleanCellValue() ? 1 : 0;
                    return (E) biv;
                case NUMERIC:
                    Integer niv = new BigDecimal(cell.getNumericCellValue()).intValue();
                    return (E) niv;
                default:
                    String sv = getCellValue(cell);
                    Integer siv = Integer.parseInt(sv);
                    return (E) siv;
            }
        } else if (long.class == cls || Long.class == cls) {
            switch (cType) {
                case BLANK:
                    Long lv = 0l;
                    return (E) lv;
                case BOOLEAN:
                    Long blv = cell.getBooleanCellValue() ? 1l : 0l;
                    return (E) blv;
                case NUMERIC:
                    Long nlv = new BigDecimal(cell.getNumericCellValue()).longValue();
                    return (E) nlv;
                default:
                    String sv = getCellValue(cell);
                    Long slv = Long.parseLong(sv);
                    return (E) slv;
            }
        } else if (boolean.class == cls || Boolean.class == cls) {
            switch (cType) {
                case BLANK:
                    return (E) Boolean.FALSE;
                case BOOLEAN:
                    Boolean bv = cell.getBooleanCellValue();
                    return (E) bv;
                case NUMERIC:
                    double nv = cell.getNumericCellValue();
                    Boolean bnv = nv == 0.0;
                    return (E) bnv;
                default:
                    String sv = getCellValue(cell);
                    Boolean bsv = !(null == sv || "".equals(sv.trim()) || "0".equals(sv.trim()) || "0.0".equals(sv.trim()) || "false".equalsIgnoreCase(sv.trim()));
                    return (E) bsv;
            }
        } else if (double.class == cls || Double.class == cls) {
            switch (cType) {
                case BLANK:
                    Double lv = 0.0;
                    return (E) lv;
                case BOOLEAN:
                    Double blv = cell.getBooleanCellValue() ? 1.0 : 0.0;
                    return (E) blv;
                case NUMERIC:
                    Double ndbv = cell.getNumericCellValue();
                    return (E) ndbv;
                default:
                    String sv = getCellValue(cell);
                    Double sdbv = Double.parseDouble(sv);
                    return (E) sdbv;
            }
        } else if (Object.class.equals(cls)) {
            switch (cType) {
                case BLANK:
                    return (E) "";
                case BOOLEAN:
                    Boolean bv = cell.getBooleanCellValue();
                    return (E) bv;
                case FORMULA:
                    return (E) cell.getCellFormula();
                case NUMERIC:
                    Double dv = cell.getNumericCellValue();
                    return (E) dv;
                case STRING:
                    return (E) cell.getStringCellValue();
                default:
                    return (E) cell.getStringCellValue();
            }
        } else {

        }
        return null;
    }

    /**
     * 获取校验信息
     * 
     * @param sheet
     * @param cell
     * @param msg
     * @return
     */
    public static String getValidateMsg(Sheet sheet, Cell cell, String msg) {
        return "表单[" + sheet.getSheetName() + "]第[" + (cell.getRowIndex() + 1) + "]行第[" + (cell.getColumnIndex() + 1) + "]列:" + msg;
    }

    /**
     * 将Excel对象写本地文件
     * 
     * @param workbook
     * @param file
     */
    public static void write(Workbook workbook, String file) {
        OutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(file));
            write(workbook, stream);
        } catch (IOException e) {
            throw new RuntimeException("创建文件流异常", e);
        } finally {
            CoreUtils.closeQuietly(stream);
        }
    }

    /**
     * 将Excel对象写流
     * 
     * @param workbook
     * @param stream
     */
    public static void write(Workbook workbook, OutputStream stream) {
        try {
            workbook.write(stream);
        } catch (IOException e) {
            throw new RuntimeException("将Excel对象写入流异常", e);
        }
    }

    /**
     * 在Excel文件中写日志
     * 
     * @param sheet
     * @param msg
     */
    public static void writeExcelLog(Sheet sheet, String msg) {
        setMerge(sheet, 0, 0, 30, 12, msg, getStyle(sheet.getWorkbook()));
    }

    /**
     * 获取计算公式对象
     * 
     * @param formula
     * @return
     */
    public static CellFormula getCellFormula(String formula) {
        return new CellFormula(formula);
    }

    public static class CellFormula {
        private final String formula;

        private CellFormula(String formula) {
            this.formula = formula;
        }
    }
}
