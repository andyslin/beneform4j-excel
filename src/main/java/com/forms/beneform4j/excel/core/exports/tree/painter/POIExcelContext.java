package com.forms.beneform4j.excel.core.exports.tree.painter;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.excel.core.ExcelUtils;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : 四方精创 Java EE 开发平台 <br>
 * Description : 绘制Xlsx的上下文<br>
 * Author : LinJisong <br>
 * Version : 1.0.0 <br>
 * Since : 1.0.0 <br>
 * Date : 2017-2-16<br>
 */
public class POIExcelContext {

    private final Workbook workbook;

    private final CreationHelper helper;

    private final ClientAnchor anchor;

    private final Font font;

    private final DataFormat dataFormat;

    private Sheet sheet;

    private Drawing drawing;

    private final Map<String, Scope> scopes = new HashMap<String, Scope>();

    private Scope lastScope;

    public POIExcelContext(Workbook workbook, boolean createFirstSheet) {
        this.workbook = workbook;
        this.helper = workbook.getCreationHelper();
        this.anchor = helper.createClientAnchor();
        this.font = workbook.createFont();
        this.dataFormat = workbook.createDataFormat();
        if (createFirstSheet) {
            this.createNewSheet();
        }
    }

    public void createNewSheet() {
        this.createNewSheet(null);
    }

    public void createNewSheet(String sheetName) {
        if (!CoreUtils.isBlank(sheetName)) {
            sheetName = ExcelUtils.getSafeSheetName(sheetName);
            this.sheet = workbook.createSheet(sheetName);
        } else {
            this.sheet = workbook.createSheet();
        }
        this.drawing = sheet.createDrawingPatriarch();
        this.scopes.clear();
        this.lastScope = null;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public CreationHelper getHelper() {
        return helper;
    }

    public ClientAnchor getAnchor() {
        return anchor;
    }

    public Drawing getDrawing() {
        return drawing;
    }

    public Font getFont() {
        return font;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public void addScope(String name, Scope scope) {
        this.scopes.put(name, scope);
    }

    public void setLastScope(Scope lastScope) {
        this.lastScope = lastScope;
    }

    public Scope getBaseScope(String name) {
        Scope scope = null;
        if (!CoreUtils.isBlank(name)) {
            scope = this.scopes.get(name);
        } else {
            scope = this.lastScope;
        }
        return scope;
    }
}
