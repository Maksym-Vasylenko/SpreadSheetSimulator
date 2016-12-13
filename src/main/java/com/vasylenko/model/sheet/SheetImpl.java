package com.vasylenko.model.sheet;

import com.vasylenko.model.cell.Cell;
import com.vasylenko.model.row.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents sheet in spreadsheet simulator.
 * Consists of list of row that can be in sheet.
 * Has size which is equal to number of rows in sheet.
 *
 * @version 1.0 08 DEC 2016
 * @author Maksym Vasylenko
 */
public class SheetImpl implements Sheet {

    /**
     * List of rows in sheet
     */
    private List<Row> listOfRows;

    /**
     * Number of rows in sheet
     */
    private int size;

    public SheetImpl() {
        size = 0;
        listOfRows = new ArrayList<>();
    }

    public List<Row> getListOfRows() {
        return listOfRows;
    }

    public int getSize() {
        return size;
    }

    /**
     * Increment size when row has been added.
     */
    public void incrementSize() {
        size++;
    }

    /**
     * Add row in list of rows.
     * @param row - row to add.
     */
    public void addRowInList(Row row) {
        listOfRows.add(row);
    }

    /**
     * Add cell to one of the rows.
     * @param cell - cell to add.
     */
    public void addCellToRow(Cell cell) {
        listOfRows.get(size).addCellInRow(cell);
    }

    /**
     * Converts all rows into one string.
     * @return string of all rows.
     */
    @Override
    public String convertToString() {

        StringBuilder stringBuilder = new StringBuilder();

        for (Row row : listOfRows) {
            stringBuilder.append(row.convertToString() + "\n");
        }

        return stringBuilder.toString();
    }
}
