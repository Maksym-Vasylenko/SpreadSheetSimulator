package com.vasylenko.model.cell;

import com.vasylenko.model.SpreadsheetUnit;

/**
 * Cell interface contains methods that any of Cell implementations must realize.
 *
 * @version 1.0 08 DEC 2016
 * @author Maksym Vasylenko
 */
public interface Cell extends SpreadsheetUnit {
    String getCellValue();

    CellType getCellType();

    void setCellType(CellType cellType);

    int getRowNumber();

    String getColumn();

    String getFullCellName();

    void setCellValue(String value);
}
