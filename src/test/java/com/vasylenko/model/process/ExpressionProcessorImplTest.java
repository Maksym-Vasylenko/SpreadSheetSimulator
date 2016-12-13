package com.vasylenko.model.process;

import com.vasylenko.controller.parser.Parsable;
import com.vasylenko.controller.parser.SpreadsheetParser;
import com.vasylenko.model.cell.Cell;
import com.vasylenko.model.cell.CellImpl;
import com.vasylenko.model.cell.CellType;
import com.vasylenko.model.expression.*;
import com.vasylenko.model.sheet.Sheet;
import com.vasylenko.model.sheet.SheetImpl;
import com.vasylenko.process.impl.ExpressionProcessorImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vastl271nko on 13.12.16.
 */
public class ExpressionProcessorImplTest {

    private static final String row1 = "12 =C2 3 'Sample";
    private static final String row2 = "=A1+B1*C1/5 =A2*B1 =B3-C3 'Spread";
    private static final String row3 = "'Test =4-3+C3 5 'Sheet";
    private static final String row4 = "=A3 =A4 =A3+C3 =B2#4";
    private static final String row5 = "12Sample =C2/0 =D5 =C5";
    private static final Parsable parser = new SpreadsheetParser();
    private static Sheet sheet = new SheetImpl();
    private static ExpressionProcessorImpl expressionProcessor = new ExpressionProcessorImpl();

    @BeforeClass
    public static void setUp() {
        parser.parseSpreadsheet(row1, sheet);
        parser.parseSpreadsheet(row2, sheet);
        parser.parseSpreadsheet(row3, sheet);
        parser.parseSpreadsheet(row4, sheet);
        parser.parseSpreadsheet(row5, sheet);

        expressionProcessor.initializeSheetMap(sheet);
        expressionProcessor.initializeExpressionList();
    }

    @Test
    public void testExtractTermsFromExpression() {
        String expression = "=A1+B1*C1/5";
        List<Term> termList = expressionProcessor.extractTermsFromExpression(expression);
        Assert.assertTrue(termList.size() == 4);
        Assert.assertTrue(termList.get(0).getTermValue().equals("A1"));
        Assert.assertTrue(termList.get(1).getTermValue().equals("B1"));
        Assert.assertTrue(termList.get(2).getTermValue().equals("C1"));
        Assert.assertTrue(termList.get(3).getTermValue().equals("5"));
    }

    @Test
    public void testExtractOperation() {
        String expression = "A1+B1*C1/5";
        List<String> operation = expressionProcessor.extractExpressionOperation(expression);
        Assert.assertTrue(operation.size() == 3);
        Assert.assertTrue(operation.get(0).equals("+"));
        Assert.assertTrue(operation.get(1).equals("*"));
        Assert.assertTrue(operation.get(2).equals("/"));
    }

    @Test
    public void testFindExpression() {
        String expressionToFind = "=A1+B1*C1/5";
        Expression findExpression = expressionProcessor.findExpression(expressionToFind);

        Assert.assertTrue(findExpression.getFirstTerm().equals("A1"));
        Assert.assertTrue(findExpression.getCellType() == CellType.EXPRESSION);
        Assert.assertTrue(findExpression.getCellPosition().equals("A2"));
    }

    @Test
    public void testExecuteExpression() {
        String stringToExecute = "1+2+4*2";

        String value = expressionProcessor.executeExpression(stringToExecute);

        Assert.assertTrue(value.equals("11"));
    }

    @Test
    public void testFindNumeric() {
        List<Term> terms = new ArrayList<>();

        terms.add(new Term("A1", TermType.REFERENCE));
        terms.add(new Term("B1", TermType.REFERENCE));
        terms.add(new Term("C1", TermType.REFERENCE));
        terms.add(new Term("5", TermType.NUMERIC));

        List<String> operations = new ArrayList<>();

        operations.add("+");
        operations.add("*");
        operations.add("/");

        Cell cell = new CellImpl("=A1+B1*C1/5", CellType.EXPRESSION, 2, "A");
        Expression expression = new ExpressionImpl(cell, terms, operations, ExpressionState.OK);

        Term term = new Term("B1", TermType.REFERENCE);

        String value = expressionProcessor.findNumeric(term,expression);

        Assert.assertTrue(value.equals("1"));
    }

    @Test
    public void testPerformOneTerm() {
        Cell cell = new CellImpl("=C2", CellType.EXPRESSION, 1, "B");
        List<Term> terms = new ArrayList<>();

        terms.add(new Term("C2", TermType.REFERENCE));


        List<String> operations = new ArrayList<>();

        Expression expression = new ExpressionImpl(cell, terms, operations, ExpressionState.OK);

        expressionProcessor.performExpression(expression);

        Assert.assertTrue(expression.getExpressionValue().equals("1"));

    }

    @Test
    public void testConvertReferencesToNumericOrString() {
        Sheet sheet1 = new SheetImpl();
        parser.parseSpreadsheet(row1, sheet1);
        parser.parseSpreadsheet(row2, sheet1);
        parser.parseSpreadsheet(row3, sheet1);
        parser.parseSpreadsheet(row4, sheet1);
        parser.parseSpreadsheet(row5, sheet1);

        ExpressionProcessorImpl exprProcessImpl = new ExpressionProcessorImpl();
        exprProcessImpl.initializeSheetMap(sheet1);
        exprProcessImpl.initializeExpressionList();
        List<Term> terms = new ArrayList<>();

        terms.add(new Term("A1", TermType.REFERENCE));
        terms.add(new Term("B1", TermType.REFERENCE));
        terms.add(new Term("C1", TermType.REFERENCE));
        terms.add(new Term("5", TermType.NUMERIC));

        List<String> operations = new ArrayList<>();

        operations.add("+");
        operations.add("*");
        operations.add("/");

        Cell cell = new CellImpl("=A1+B1*C1/5", CellType.EXPRESSION, 2, "A");
        Expression expression = new ExpressionImpl(cell, terms, operations, ExpressionState.OK);


        exprProcessImpl.convertReferencesToNumericOrString(expression);
        Assert.assertTrue(expression.getTermValue(0).equals("12"));
        Assert.assertTrue(expression.getTermValue(1).equals("1"));
        Assert.assertTrue(expression.getTermValue(2).equals("3"));
    }

    @Test
    public void testModifyExpressionWithNumeric() {
        List<Term> terms = new ArrayList<>();

        terms.add(new Term("A1", TermType.REFERENCE));
        terms.add(new Term("B1", TermType.REFERENCE));
        terms.add(new Term("C1", TermType.REFERENCE));
        terms.add(new Term("5", TermType.NUMERIC));

        List<String> operations = new ArrayList<>();

        operations.add("+");
        operations.add("*");
        operations.add("/");

        Cell cell = new CellImpl("=A1+B1*C1/5", CellType.EXPRESSION, 2, "A");
        Expression expression = new ExpressionImpl(cell, terms, operations, ExpressionState.OK);
        StringBuilder builder = new StringBuilder();
        expressionProcessor.convertReferencesToNumericOrString(expression);
        expressionProcessor.modifyExpressionWithNumeric(expression, builder);
        expression.setExpressionValue(builder.toString());
        Assert.assertTrue(expression.getExpressionValue().equals("12+1*3/5"));
    }

}
