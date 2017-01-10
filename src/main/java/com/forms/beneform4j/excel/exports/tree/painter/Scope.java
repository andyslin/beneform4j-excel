package com.forms.beneform4j.excel.exports.tree.painter;

import java.io.Serializable;

import com.forms.beneform4j.excel.ExcelUtils;

public class Scope implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3098385611330714794L;

    //public static final Scope ZERO_SCOPE = new Scope(0, 0, 0, 0);

    private final int beginRow;

    private final int endRow;

    private final int beginCell;

    private final int endCell;

    public Scope(int beginRow, int beginCell) {
        this(beginRow, beginRow, beginCell, beginCell);
    }

    public Scope(int beginRow, int endRow, int beginCell, int endCell) {
        this.beginRow = beginRow;
        this.endRow = endRow;
        this.beginCell = beginCell;
        this.endCell = endCell;
    }

    public Scope copy() {
        return new Scope(getBeginRow(), getEndRow(), getBeginCell(), getEndCell());
    }

    public Scope merge(Scope scope) {
        if (null == scope) {
            return copy();
        }
        int row1 = Math.min(this.beginRow, scope.getBeginRow());
        int row2 = Math.max(this.endRow, scope.getEndRow());
        int cell1 = Math.min(this.beginCell, scope.getBeginCell());
        int cell2 = Math.max(this.endCell, scope.getEndCell());
        return new Scope(row1, row2, cell1, cell2);
    }

    public int getBeginRow() {
        return beginRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getBeginCell() {
        return beginCell;
    }

    public int getEndCell() {
        return endCell;
    }

    @Override
    public String toString() {
        return new StringBuffer("Scope[")//scope
                .append(beginRow + 1).append("-").append(endRow + 1)//row
                .append(",").append(ExcelUtils.getColumnPosition(beginCell))//start cell
                .append("-").append(ExcelUtils.getColumnPosition(endCell))//end cell
                .append("]").toString();
    }
}