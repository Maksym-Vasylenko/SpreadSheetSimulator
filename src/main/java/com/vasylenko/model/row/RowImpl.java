package com.vasylenko.model.row;

import com.vasylenko.model.cell.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents Row in sheet.
 * Consists of list of cell that can be in row.
 * Has number of row in sheet.
 *
 * @version 1.0 08 DEC 2016
 * @author Maksym Vasylenko
 */
public class RowImpl implements Row {

    /**
     * List of cells in one row
     */
    private List<Cell> cellListInRow;

    /**
     * Number of row in spreadsheet
     */
    private int rowNumber;

    public RowImpl(int rowNumber) {
        this.rowNumber = rowNumber;
        cellListInRow = new ArrayList<>();
    }

    /**
     * Add cell in row.
     * @param cell - cell to add.
     */
    public void addCellInRow(Cell cell) {
        cellListInRow.add(cell);
    }

    public List<Cell> getCellListInRow() {
        return cellListInRow;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    @Override
    public String convertToString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Cell cell : cellListInRow) {
            stringBuilder.append(cell.convertToString() + "    ");
        }
        return stringBuilder.toString();
    }
}
