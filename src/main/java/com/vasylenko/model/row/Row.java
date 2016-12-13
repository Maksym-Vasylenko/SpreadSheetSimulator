package com.vasylenko.model.row;

import com.vasylenko.model.SpreadsheetUnit;
import com.vasylenko.model.cell.Cell;

import java.util.List;

/**
 * Expression interface contains methods that any of Row implementations must realize.
 *
 * @version 1.0 08 DEC 2016
 * @author Maksym Vasylenko
 */
public interface Row extends SpreadsheetUnit {
    void addCellInRow(Cell cell);

    List<Cell> getCellListInRow();

    int getRowNumber();
}
