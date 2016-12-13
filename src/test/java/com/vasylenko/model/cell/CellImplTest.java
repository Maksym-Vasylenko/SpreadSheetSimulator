package com.vasylenko.model.cell;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by vastl271nko on 13.12.16.
 */
public class CellImplTest {

    private final String testValue = "1*2+3";

    @Test
    public void testSetType() {
        Cell cell = new CellImpl();

        cell.setCellType(CellType.EXPRESSION);

        Assert.assertTrue(cell.getCellType() == CellType.EXPRESSION);
    }


    @Test
    public void testSetValue() {
        Cell cell = new CellImpl();

        cell.setCellValue(testValue);

        Assert.assertTrue(cell.getCellValue().equals(testValue));
    }
}
