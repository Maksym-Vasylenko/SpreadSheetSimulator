package com.vasylenko.view;

import com.vasylenko.model.cell.Cell;
import com.vasylenko.model.row.Row;
import com.vasylenko.model.sheet.Sheet;

/**
 * Class that implements interface Viewable.
 * Has method that prints data on screen.
 *
 * @version 1.0 08 DEC 2016
 * @author Makym Vasylenko
 */
public class View implements Viewable {

    /**
     * Prints data on screen
     * @param message - string to print
     */
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printSpreadSheet(Sheet sheet) {
        for (Row row : sheet.getListOfRows()) {
            for (Cell cell: row.getCellListInRow()) {
                System.out.printf("%15S", cell.convertToString());
            }
            System.out.println();
        }
    }


}
