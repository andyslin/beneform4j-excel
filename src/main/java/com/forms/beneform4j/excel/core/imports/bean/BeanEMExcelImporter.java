package com.forms.beneform4j.excel.core.imports.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.Resource;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.excel.core.ExcelUtils;
import com.forms.beneform4j.excel.core.imports.base.AbstractResourceExcelImporter;
import com.forms.beneform4j.excel.core.model.em.bean.BeanEMExtractResult;
import com.forms.beneform4j.excel.core.model.em.bean.BeanEMExtractResult.NextStep;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMProperty;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;

public class BeanEMExcelImporter extends AbstractResourceExcelImporter {

    @Override
    protected Object doImports(Resource resource, IBeanEM model) {
        try {
            Workbook workbook = null;
            try {
                workbook = WorkbookFactory.create(resource.getFile());
            } catch (Exception e) {
                workbook = WorkbookFactory.create(resource.getInputStream());
            }
            return extract(workbook, model);
        } catch (Exception e) {
            throw Throw.createRuntimeException(e);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Object extract(Workbook workbook, IBeanEM model) throws Exception {
        Class<?> cls = model.getBeanType();
        boolean isMap = Map.class.isAssignableFrom(cls);
        boolean debug = CommonLogger.isDebugEnabled();
        Object result = cls.newInstance();
        Map<String, IBeanEMProperty> properties = model.getProperties();
        if (null != properties && !properties.isEmpty()) {
            List<String> dealFields = new ArrayList<String>();
            sheetCircle: //表单循环
            for (int iSheet = 0, lSheet = workbook.getNumberOfSheets(); iSheet < lSheet; iSheet++) {
                Sheet sheet = workbook.getSheetAt(iSheet);
                rowCircle: //行循环
                for (int iRow = 0, lRow = sheet.getPhysicalNumberOfRows(); iRow < lRow; iRow++) {
                    Row row = sheet.getRow(iRow);
                    cellCircle: //列循环
                    for (int iCell = 0, lCell = row.getPhysicalNumberOfCells(); iCell < lCell; iCell++) {
                        Cell cell = row.getCell(iCell);
                        if (debug) {
                            CommonLogger.debug("process cell[" + ExcelUtils.getRowPosition(cell.getRowIndex()) + "," + ExcelUtils.getColumnPosition(cell.getColumnIndex()) + "]:" + ExcelUtils.getCellValue(cell));
                        }
                        for (String fieldName : properties.keySet()) {//属性循环
                            IBeanEMProperty property = properties.get(fieldName);
                            if (dealFields.contains(fieldName) || null == property || null == property.getMatcher()) {
                                continue;
                            }
                            if (property.getMatcher().isMatch(workbook, sheet, row, cell)) {
                                dealFields.add(fieldName);
                                Class<?> fieldType = property.getType();
                                IBeanEMValidator validator = property.getValidator();
                                if (null != validator) {
                                    validator.validate(workbook, sheet, row, cell, fieldType);
                                }

                                if (debug) {
                                    CommonLogger.debug("match field: " + fieldName);
                                }

                                IBeanEMExtractor parser = property.getExtractor();
                                BeanEMExtractResult pr = parser.extract(property, workbook, sheet, row, cell, fieldType);
                                Object value = pr.getValue();
                                if (isMap) {
                                    ((Map) result).put(fieldName, value);
                                } else if (null != value) {
                                    Field field = CoreUtils.findField(cls, fieldName);
                                    if (!field.isAccessible()) {
                                        field.setAccessible(true);
                                    }
                                    field.set(result, value);
                                }

                                NextStep cCase = pr.getNextStep();
                                if (NextStep.END.equals(cCase)) {//解析完成，终止最外层循环
                                    break sheetCircle;
                                } else {//修改循环索引
                                    iCell += pr.getOffsetX();
                                    iRow += pr.getOffsetY();
                                    iSheet += pr.getOffsetSheet();
                                    if (NextStep.NORMAL.equals(cCase)) {
                                        //正常情况下，继续循环处理下一个属性
                                    } else if (NextStep.CONTINUE_CELL.equals(cCase)) {
                                        //继续处理下一索引的cell
                                        continue cellCircle;
                                    } else if (NextStep.CONTINUE_ROW.equals(cCase)) {
                                        //继续处理下一索引的row
                                        continue rowCircle;
                                    } else if (NextStep.CONTINUE_SHEET.equals(cCase)) {
                                        //继续处理下一索引的sheet
                                        continue sheetCircle;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
