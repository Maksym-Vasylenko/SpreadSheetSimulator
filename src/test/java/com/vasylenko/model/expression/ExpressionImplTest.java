package com.vasylenko.model.expression;

import com.vasylenko.model.cell.Cell;
import com.vasylenko.model.cell.CellImpl;
import com.vasylenko.model.cell.CellType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vastl271nko on 13.12.16.
 */
public class ExpressionImplTest {

    private static List<Term> terms = new ArrayList<>();
    static {
        terms.add(new Term("A1",TermType.REFERENCE));
        terms.add(new Term("B1",TermType.REFERENCE));
        terms.add(new Term("C1",TermType.REFERENCE));
        terms.add(new Term("5",TermType.NUMERIC));
    }
    private static List<String> operations = new ArrayList<>();
    static {
        operations.add("+");
        operations.add("*");
        operations.add("/");
    }
    private static Cell cell = new CellImpl("=A1+B1*C1/5", CellType.EXPRESSION,2,"A");
    private static Expression expression = new ExpressionImpl(cell,terms,operations,ExpressionState.OK);

    @Test
    public void testCellPosition () {
        String sellPosition = expression.getCellPosition();

        Assert.assertTrue(sellPosition.equals("A2"));
    }

    @Test
    public void testGetFirstTerm() {
        Assert.assertTrue(expression.getFirstTerm().equals("A1"));
    }

    @Test
    public void testOperation() {
        Assert.assertTrue(expression.getOperation(1).equals("*"));
    }

    @Test
    public void testTerm() {
        Assert.assertTrue(expression.getTermValue(3).equals("5"));
    }
}
