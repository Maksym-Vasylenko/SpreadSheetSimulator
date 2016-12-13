package com.vasylenko.model.controller.parser;

import com.vasylenko.controller.parser.Parsable;
import com.vasylenko.controller.parser.SpreadsheetParser;
import com.vasylenko.model.cell.CellType;
import com.vasylenko.model.row.Row;
import com.vasylenko.model.sheet.Sheet;
import com.vasylenko.model.sheet.SheetImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by vastl271nko on 13.12.16.
 */
public class SpreadSheetParserTest {

    private static Sheet sheet = new SheetImpl();
    private static Parsable parser = new SpreadsheetParser();
    private static final String row = "12\t=C2\t3\t'Sample";
    @BeforeClass
    public static void setUp() {
        parser.parseSpreadsheet(row,sheet);
    }

    @Test
    public void testSheetSize() {
        Assert.assertTrue(sheet.getSize() == 1);
    }

    @Test
    public void testNumberOfCellsInSheet() {
        Assert.assertTrue(sheet.getListOfRows().get(0).getCellListInRow().size() == 4);
    }

    @Test
    public void testAddCellType() {
        Row sheetRow = sheet.getListOfRows().get(0);

        Assert.assertTrue(sheetRow.getCellListInRow().get(0).getCellType() == CellType.NUMERIC_INTEGER);
        Assert.assertTrue(sheetRow.getCellListInRow().get(1).getCellType() == CellType.EXPRESSION);
        Assert.assertTrue(sheetRow.getCellListInRow().get(2).getCellType() == CellType.NUMERIC_INTEGER);
        Assert.assertTrue(sheetRow.getCellListInRow().get(3).getCellType() == CellType.STRING);
    }
}
