package com.vasylenko.model.sheet;

import com.vasylenko.model.cell.Cell;
import com.vasylenko.model.cell.CellImpl;
import com.vasylenko.model.cell.CellType;
import com.vasylenko.model.row.Row;
import com.vasylenko.model.row.RowImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by vastl271nko on 13.12.16.
 */
public class SheetImplTest {

    private static Sheet sheet = new SheetImpl();
    private static Row row = new RowImpl(1);
    private static Cell cell1 = new CellImpl("123", CellType.NUMERIC_INTEGER,1,"A");
    private static Cell cell2 = new CellImpl("=C3", CellType.EXPRESSION,1,"B");

    @BeforeClass
    public static void setUp() {
        sheet.addRowInList(row);
        sheet.addCellToRow(cell1);
        sheet.addCellToRow(cell2);
    }

    @Test
    public void testAddRowInSheet() {
        Assert.assertTrue(sheet.getListOfRows().size()==1);
    }

    @Test
    public void testNumberCellInSheetRow() {
        Assert.assertTrue(sheet.getListOfRows().get(0).getCellListInRow().size() == 2);
    }


}
