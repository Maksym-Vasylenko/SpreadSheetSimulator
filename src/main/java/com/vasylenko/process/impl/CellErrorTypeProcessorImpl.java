package com.vasylenko.process.impl;

import com.vasylenko.global.variables.TextVariables;
import com.vasylenko.model.cell.Cell;
import com.vasylenko.model.cell.CellType;
import com.vasylenko.model.row.Row;
import com.vasylenko.model.sheet.Sheet;
import com.vasylenko.process.CellErrorTypeProcessor;

/**
 * Created by vastl271nko on 09.12.16.
 */
public class CellErrorTypeProcessorImpl implements CellErrorTypeProcessor {

    /**
     * If cell contains error input special String value will be set to this cell.
     * @param sheet
     */
    public void processErrors(Sheet sheet) {
        for (Row row : sheet.getListOfRows()) {
            for (Cell cell : row.getCellListInRow()) {
                if (cell.getCellType() == CellType.ERROR) {
                    cell.setCellValue(TextVariables.ERROR_INPUT);
                }
            }
        }
    }
}
