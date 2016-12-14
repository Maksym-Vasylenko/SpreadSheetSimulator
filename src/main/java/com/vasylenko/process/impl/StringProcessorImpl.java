package com.vasylenko.process.impl;

import com.vasylenko.model.cell.Cell;
import com.vasylenko.model.cell.CellType;
import com.vasylenko.model.row.Row;
import com.vasylenko.model.sheet.Sheet;
import com.vasylenko.process.StringProcessor;

/**
 * Created by vastl271nko on 09.12.16.
 */
public class StringProcessorImpl implements StringProcessor{

    public void processStrings(Sheet sheet) {
        for (Row row : sheet.getListOfRows()) {
            for (Cell cell : row.getCellListInRow()) {
                if (cell.getCellType() == CellType.STRING) {
                    String changedCellValue = cell.getCellValue().substring(1, cell.getCellValue().length());
                    cell.setCellValue(changedCellValue);
                }
            }
        }
    }
}
