package com.vasylenko.controller.parser;

import com.vasylenko.global.variables.LoggerVariables;
import com.vasylenko.global.variables.RegExp;
import com.vasylenko.global.variables.TextVariables;
import com.vasylenko.model.cell.CellImpl;
import com.vasylenko.model.cell.CellType;
import com.vasylenko.model.row.RowImpl;
import com.vasylenko.model.sheet.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that can parse spreadSheet. Implements interface Parsable.
 *
 * @version 1.0 08 DEC 2016
 * @author Maksym Vasylenko
 */
public class SpreadsheetParser implements Parsable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpreadsheetParser.class);

    /**
     * Parse input string and add cell to spreadsheet.
     * @param textToParse
     * @param sheet
     */
    @Override
    public void parseSpreadsheet(String textToParse, Sheet sheet) {
        String[] valueOfCellsInRow = textToParse.split(RegExp.REGEXP_WHITESPACE);

        sheet.addRowInList(new RowImpl(sheet.getSize()+1));

        String column = "A";
        for (String cellValue: valueOfCellsInRow) {
            LOGGER.debug(LoggerVariables.CELL_VALUE + cellValue);
            addCellValue(sheet, column, cellValue);
            column = String.valueOf((char)(column.charAt(0)+1));
        }
        sheet.incrementSize();
    }

    /**
     * Add cell value to sheet.
     * Creates cell value which depend of what type is it.
     * @param sheet
     * @param column
     * @param cellValue
     */
    private void addCellValue(Sheet sheet, String column, String cellValue) {
        int rowNumber = sheet.getSize() + 1;
        if (cellValue.matches(RegExp.REGEXP_FLOAT)) {
            LOGGER.debug(LoggerVariables.CELL_TYPE_FLOAT);
            sheet.addCellToRow(new CellImpl(cellValue, CellType.NUMERIC_FLOAT,rowNumber,column));
        } else if (cellValue.matches(RegExp.REGEXP_INTEGER)) {
            LOGGER.debug(LoggerVariables.CELL_TYPE_INTEGER);
            sheet.addCellToRow(new CellImpl(cellValue, CellType.NUMERIC_INTEGER,rowNumber,column));
        } else if (cellValue.matches(RegExp.REGEXP_EXPRESSION)) {
            LOGGER.debug(LoggerVariables.CELL_TYPE_EXPRESSION);
            sheet.addCellToRow(new CellImpl(cellValue, CellType.EXPRESSION,rowNumber,column));
        } else if (cellValue.matches(RegExp.REGEXP_STRING)) {
            LOGGER.debug(LoggerVariables.CELL_TYPE_STRING);
            sheet.addCellToRow(new CellImpl(cellValue, CellType.STRING,rowNumber,column));
        } else if (cellValue.matches(RegExp.REGEXP_EMPTY)) {
            LOGGER.debug(LoggerVariables.CELL_TYPE_STRING);
            sheet.addCellToRow(new CellImpl(cellValue, CellType.EMPTY,rowNumber,column));
        } else {
            LOGGER.debug(LoggerVariables.CELL_TYPE_ERROR);
            sheet.addCellToRow(new CellImpl(cellValue, CellType.ERROR,rowNumber,column));
        }
    }

}
