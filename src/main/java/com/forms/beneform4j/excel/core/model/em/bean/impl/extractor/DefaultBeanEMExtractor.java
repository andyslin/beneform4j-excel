package com.forms.beneform4j.excel.core.model.em.bean.impl.extractor;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.forms.beneform4j.core.util.CoreUtils;
import com.forms.beneform4j.core.util.exception.Throw;
import com.forms.beneform4j.core.util.logger.CommonLogger;
import com.forms.beneform4j.excel.core.ExcelUtils;
import com.forms.beneform4j.excel.core.model.em.bean.BeanEMExtractResult;
import com.forms.beneform4j.excel.core.model.em.bean.BeanEMExtractResult.NextStep;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEM;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMExtractor;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMMatcher;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMProperty;
import com.forms.beneform4j.excel.core.model.em.bean.IBeanEMValidator;

public class DefaultBeanEMExtractor extends AbstractBeanEMExtractor {

    /**
     * 
     */
    private static final long serialVersionUID = 285418589559899611L;

    @Override
    public BeanEMExtractResult extract(IBeanEMProperty property, Workbook workbook, Sheet sheet, Row row, Cell cell, Class<?> type) {
        BeanEMExtractResult result = newExtractResult();
        try {
            if (null == type) {
                extractValue(result, property, workbook, sheet, row, cell, type);
            } else {
                IBeanEM innerBeanEM = property.getInnerBeanEM();
                if (null != innerBeanEM) {
                    extractInnerValue(result, property, workbook, sheet, row, cell, type);
                } else {
                    extractValue(result, property, workbook, sheet, row, cell, type);
                }
            }
        } catch (Exception e) {
            Throw.throwRuntimeException(e);
        }
        return result;
    }

    /**
     * 提取简单值情况
     * 
     * @param result
     * @param property
     * @param workbook
     * @param sheet
     * @param row
     * @param cell
     * @param type
     */
    protected void extractValue(BeanEMExtractResult result, IBeanEMProperty property, Workbook workbook, Sheet sheet, Row row, Cell cell, Class<?> type) {
        setExtractResultValue(result, cell, type);
    }

    /**
     * 提取内部值
     * 
     * @param result
     * @param property
     * @param workbook
     * @param sheet
     * @param row
     * @param cell
     * @param type
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void extractInnerValue(BeanEMExtractResult result, IBeanEMProperty property, Workbook workbook, Sheet sheet, Row row, Cell cell, Class<?> type) throws Exception {
        boolean isCollection = Collection.class.isAssignableFrom(type);
        IBeanEM elementEM = property.getInnerBeanEM();
        Class<?> cls = elementEM.getBeanType();
        boolean isMap = Map.class.isAssignableFrom(cls);
        Map<String, IBeanEMProperty> properties = elementEM.getProperties();
        boolean debug = CommonLogger.isDebugEnabled();

        Collection list = null;
        if (isCollection) {
            list = instanceCollection(type);
        }
        List<String> dealFields = null;
        Cell lastCell = null;
        Object inner = null;
        rowCircle: //行循环
        for (int iRow = cell.getRowIndex(), lRow = sheet.getPhysicalNumberOfRows(); iRow < lRow; iRow++) {
            Row cRow = sheet.getRow(iRow);
            cellCircle: //列循环
            for (int iCell = 0, lCell = cRow.getPhysicalNumberOfCells(); iCell < lCell; iCell++) {
                Cell cCell = cRow.getCell(iCell);//ExcelUtils.getMergetCell(sheet, iRow, iCell);
                if (null != cCell) {
                    lastCell = cCell;
                } else {
                    continue;
                }
                boolean end = false;
                if (debug) {
                    CommonLogger.debug("process cell[" + ExcelUtils.getRowPosition(cCell.getRowIndex()) + "," + ExcelUtils.getColumnPosition(cCell.getColumnIndex()) + "]:" + ExcelUtils.getCellValue(cCell));
                }
                if (property.getMatcher().isMatch(workbook, sheet, cRow, cCell)) {
                    if (debug) {
                        if (isCollection) {
                            if (null == inner) {
                                CommonLogger.debug("match circle start, the inner type is '" + cls.getName() + "'.");
                            } else {
                                CommonLogger.debug("match circle start and end the previous circle, the inner type is '" + cls.getName() + "'.");
                            }
                        } else {
                            CommonLogger.debug("match inner object start, the inner type is '" + cls.getName() + "'.");
                        }
                    }
                    inner = CoreUtils.newInstance(cls);
                    if (isCollection) {
                        list.add(inner);
                    } else {
                        result.setValue(inner);
                    }
                    dealFields = new ArrayList<String>();
                } else if (null == dealFields) {
                    continue;
                } else if (isEnd(property, workbook, sheet, cRow, cCell)) {
                    if (debug) {
                        if (isCollection) {
                            CommonLogger.debug("match circle end, the inner type is '" + cls.getName() + "'.");
                        } else {
                            CommonLogger.debug("match inner object end, the inner type is '" + cls.getName() + "'.");
                        }
                    }
                    end = true;
                }
                for (String fieldName : properties.keySet()) {
                    IBeanEMProperty fm = properties.get(fieldName);
                    if (dealFields.contains(fieldName) || null == fm || null == fm.getMatcher()) {
                        continue;
                    }
                    if (fm.getMatcher().isMatch(workbook, sheet, cRow, cCell)) {
                        dealFields.add(fieldName);
                        Class<?> innerFieldType = fm.getType();
                        IBeanEMValidator validator = fm.getValidator();
                        if (null != validator) {
                            validator.validate(workbook, sheet, cRow, cCell, innerFieldType);
                        }

                        if (debug) {
                            CommonLogger.debug("match field: " + fieldName);
                        }

                        IBeanEMExtractor extractor = fm.getExtractor();
                        BeanEMExtractResult pr = extractor.extract(fm, workbook, sheet, cRow, cCell, innerFieldType);
                        Object value = pr.getValue();
                        if (isMap) {
                            ((Map) inner).put(fieldName, value);
                        } else if (null != value) {
                            Field field = CoreUtils.findField(cls, fieldName);
                            if (!field.isAccessible()) {
                                field.setAccessible(true);
                            }
                            field.set(inner, value);
                        }

                        if (dealFields.size() == properties.size()) {
                            end = true;
                        }

                        NextStep cCase = pr.getNextStep();
                        if (NextStep.END.equals(cCase) || end) {//解析完成，终止最外层循环
                            result.setNextStep(NextStep.END);
                            break rowCircle;
                        } else {//修改循环索引
                            if (0 != pr.getOffsetSheet() || NextStep.CONTINUE_SHEET.equals(cCase)) {
                                result.setNextStep(NextStep.CONTINUE_SHEET);
                                result.setOffsetSheet(result.getOffsetSheet() + pr.getOffsetSheet());
                                break rowCircle;
                            }

                            iCell += pr.getOffsetX();
                            iRow += pr.getOffsetY();
                            if (NextStep.NORMAL.equals(cCase)) {
                                //正常情况下，继续循环处理下一个属性
                            } else if (NextStep.CONTINUE_CELL.equals(cCase)) {
                                //继续处理下一索引的cell
                                continue cellCircle;
                            } else if (NextStep.CONTINUE_ROW.equals(cCase)) {
                                //继续处理下一索引的row
                                continue rowCircle;
                            }
                        }
                    }
                }

                if (end) {
                    break rowCircle;
                }
            }
        }
        if (null != list && !list.isEmpty()) {
            result.setValue(list);
        }
        if (null != lastCell) {
            int offsetY = lastCell.getRowIndex() - cell.getRowIndex() - 1;
            result.setOffsetY(offsetY);
            result.setNextStep(NextStep.CONTINUE_ROW);
        }
    }

    /**
     * 是否结束列表数据的提取
     * 
     * @param property
     * @param workbook
     * @param sheet
     * @param row
     * @param cell
     * @return
     */
    protected boolean isEnd(IBeanEMProperty property, Workbook workbook, Sheet sheet, Row row, Cell cell) {
        if (null == sheet || null == row || null == cell) {
            return true;
        } else {
            IBeanEMMatcher em = property.getEndMatcher();
            if (null != em) {
                return em.isMatch(workbook, sheet, row, cell);
            }
            return false;
        }
    }

    /**
     * 实例化集合类型
     * 
     * @param collectionType
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected Collection instanceCollection(Class<?> collectionType) {
        if (collectionType.isInterface()) {
            if (Iterable.class.isAssignableFrom(collectionType)) {
                return new ArrayList();
            }

            if (Collection.class.isAssignableFrom(collectionType)) {
                return new ArrayList();
            }

            if (List.class.isAssignableFrom(collectionType)) {
                return new ArrayList();
            }

            if (SortedSet.class.isAssignableFrom(collectionType)) {
                return new TreeSet();
            }

            if (Set.class.isAssignableFrom(collectionType)) {
                return new HashSet();
            }

            if (BlockingDeque.class.isAssignableFrom(collectionType)) {
                return new LinkedBlockingDeque();
            }

            if (Deque.class.isAssignableFrom(collectionType)) {
                return new ArrayDeque();
            }

            if (BlockingQueue.class.isAssignableFrom(collectionType)) {
                return new DelayQueue();
            }

            if (Queue.class.isAssignableFrom(collectionType)) {
                return new ConcurrentLinkedQueue();
            }
        } else {
            try {
                return (Collection) collectionType.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList();
    }
}
