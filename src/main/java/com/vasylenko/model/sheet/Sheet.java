package com.vasylenko.model.sheet;

import com.vasylenko.model.SpreadsheetUnit;
import com.vasylenko.model.cell.Cell;
import com.vasylenko.model.row.Row;

import java.util.List;

/**
 * Sheet interface contains methods that any of Sheet implementations must realize.
 *
 * @version 1.0 08 DEC 2016
 * @author Maksym Vasylenko
 */
public interface Sheet extends SpreadsheetUnit {
    void addRowInList(Row row);

    List<Row> getListOfRows();

    int getSize();

    void addCellToRow(Cell cell);

    void incrementSize();

}
