package com.vasylenko.model.cell;

/**
 * Represent one cell in row.
 *
 * @version 1.0 07 DEC 2016
 * @author Maksym Vasylenko
 */
public class CellImpl implements Cell {

    /**
     * Value of cell
     */
    private String value;

    /**
     * Type of cell
     */
    private CellType cellType;

    /**
     * Number of row where cell located
     */
    private int rowNumber;

    /**
     * Column name where cell located
     */
    private String column;

    /**
     * Full name of cell which consists of columnName and rowNumber
     */
    private String fullCellName;

    public CellImpl() {

    }

    public CellImpl(String value, CellType cellType, int rowNumber, String column) {
        this.value = value;
        this.cellType = cellType;
        this.rowNumber = rowNumber;
        this.column = column;
        fullCellName = column + this.rowNumber;
    }

    @Override
    public String getCellValue() {
        return value;
    }

    @Override
    public CellType getCellType() {
        return cellType;
    }

    @Override
    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    @Override
    public int getRowNumber() {
        return rowNumber;
    }

    @Override
    public String getColumn() {
        return column;
    }

    @Override
    public void setCellValue(String value) {
        this.value = value;
    }

    @Override
    public String getFullCellName() {
        return fullCellName;
    }

    @Override
    public String convertToString() {
        return value;
    }


}
