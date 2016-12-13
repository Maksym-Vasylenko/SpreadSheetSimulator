package com.vasylenko.model.row;

import com.vasylenko.model.cell.Cell;
import com.vasylenko.model.cell.CellImpl;
import com.vasylenko.model.cell.CellType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by vastl271nko on 13.12.16.
 */
public class RowImplTest {

    private static Row row = new RowImpl(1);
    private static Cell cell = new CellImpl("123", CellType.NUMERIC_INTEGER,1,"A");

    @BeforeClass
    public static void setUp() {
        row.addCellInRow(cell);
    }

    @Test
    public void testAddInRowSize() {
        Assert.assertTrue(row.getCellListInRow().size() == 1);
    }

    @Test
    public void testAddInRowCellGetValue() {
        Assert.assertTrue(row.getCellListInRow().get(0).getCellValue().equals(cell.getCellValue()));
    }

    @Test
    public void testAddInRowCellGetType() {
        Assert.assertTrue(row.getCellListInRow().get(0).getCellType() == CellType.NUMERIC_INTEGER);
    }

    @Test
    public void testAddInRowGetFullName() {
        Assert.assertTrue(row.getCellListInRow().get(0).getFullCellName().equals("A1"));
    }
}
