package com.vasylenko.process.impl;

import com.vasylenko.global.variables.RegExp;
import com.vasylenko.global.variables.TextVariables;
import com.vasylenko.model.cell.Cell;
import com.vasylenko.model.cell.CellType;
import com.vasylenko.model.expression.*;
import com.vasylenko.model.row.Row;
import com.vasylenko.model.sheet.Sheet;
import com.vasylenko.process.ExpressionProcessor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

/**
 * Class that contains methods to work with expressions in spreadsheet
 *
 * @author Maksym Vasylenko
 * @version 1.0 08 DEC 2016
 */
public class ExpressionProcessorImpl implements ExpressionProcessor{

    /**
     * Map of cells and their full names.
     */
    private Map<String, Cell> sheetMap;

    /**
     * List which contains expressions that spreadsheet may contains.
     */
    private List<Expression> expressionList;

    /**
     * Manager to get engine.
     */
    private ScriptEngineManager engineManager;

    /**
     * Stack to control references.
     */
    private List<Cell> cellStack;

    /**
     * Engine to use method eval()
     */
    private ScriptEngine engine;

    public ExpressionProcessorImpl() {
        sheetMap = new LinkedHashMap<>();
        expressionList = new LinkedList<>();
        engineManager = new ScriptEngineManager();
        engine = engineManager.getEngineByName(TextVariables.ENGINE_NAME);
        cellStack = new LinkedList<>();
    }

    /**
     * Copy all data from our spreadsheet to map
     *
     * @param sheet - spreadsheet
     */
    public void initializeSheetMap(Sheet sheet) {
        for (Row row : sheet.getListOfRows()) {
            for (Cell cell : row.getCellListInRow()) {
                sheetMap.put(cell.getFullCellName(), cell);
            }
        }
    }

    /**
     * Finds out expressions in our spreadsheet, create instances of them and adds
     * them to expressionList.
     */
    public void initializeExpressionList() {
        List<Term> terms;
        List<String> operations;
        String expression;
        for (Map.Entry<String, Cell> cellEntry : sheetMap.entrySet()) {
            if (cellEntry.getValue().getCellType() == CellType.EXPRESSION) {
                Cell cell = cellEntry.getValue();
                expression = cell.getCellValue();
                terms = extractTermsFromExpression(expression);
                if (checkNonExistentCells(terms, cell)) {
                    operations = extractExpressionOperation(expression);
                    expressionList.add(new ExpressionImpl(cell, terms, operations, ExpressionState.OK));
                } else {
                    operations = null;
                    cell.setCellValue(TextVariables.CELL_ERROR);
                    expressionList.add(new ExpressionImpl(cell, terms, operations, ExpressionState.ERROR));
                }
            }
        }
    }

    /**
     * Extracts from expression all terms, adds them in list and then returns this list.
     *
     * @param expression - string from which terms will be extracted
     * @return list of terms
     */
    public List<Term> extractTermsFromExpression(String expression) {
        List<Term> termList = new ArrayList<>();
        String[] terms = expression.split(RegExp.REGEXP_PATTERN_OPERATION);
        for (int i = 1; i < terms.length; i++) {
            if (terms[i].matches(RegExp.REGEXP_INTEGER) || terms[i].matches(RegExp.REGEXP_FLOAT))
                termList.add(new Term(terms[i], TermType.NUMERIC));
            else {
                termList.add(new Term(terms[i], TermType.REFERENCE));
            }
        }

        return termList;
    }

    /**
     * Extracts from expression all operations, adds them in list and then returns this list.
     *
     * @param expression - string from which operations will be extracted
     * @return list of operations
     */
    public List<String> extractExpressionOperation(String expression) {
        List<String> operationList = new ArrayList<>();

        String[] operations = expression.split(RegExp.REGEXP_TERM);

        operationList.addAll(Arrays.asList(operations).subList(1, operations.length));

        return operationList;
    }

    /**
     * Method that process all expression in spreadsheet.
     * In for loop it goes through all expressions and performs them.
     */
    public void processExpressions() {
        for (Expression expression : expressionList) {
            cellStack.add(expression.getCell());
            performExpression(expression);
            cellStack.clear();
        }
    }

    /**
     * Performs expression. Converts references to numeric. Then modify expressions with converted numerics.
     * Execute expression to get result of it. Set result of expression in instance of expression.
     *
     * @param expression
     */
    public void performExpression(Expression expression) {
        if (!(expression.getExpressionState() == ExpressionState.ERROR)) {
            convertReferencesToNumericOrString(expression);

            if (expression.getExpressionState() != ExpressionState.ERROR) {
                StringBuilder stringBuilder = new StringBuilder();
                modifyExpressionWithNumeric(expression, stringBuilder);
                String modifiedExpressionValue = stringBuilder.toString();
                if (expression.getCellType() != CellType.STRING && expression.getTermList().size() > 1) {
                    if (stringBuilder.toString().matches(RegExp.REGEXP_DIV_BY_0)) {
                        setExpressionError(expression,TextVariables.DIV_BY_0_ERROR);
                    } else {
                        String value = executeExpression(modifiedExpressionValue);
                        expression.setExpressionValue(value);
                    }

                }
            }
        }
    }

    /**
     * Set error value to expression
     *
     * @param expression - expression to set value
     * @param error - error to set
     */
    public void setExpressionError(Expression expression, String error) {
        expression.setExpressionState(ExpressionState.ERROR);
        expression.setExpressionValue(error);
        expression.setCellType(CellType.ERROR);
    }

    /**
     * Check expressions if they contains cells that does not exists
     * Go through each term in term list of expression and check if it exists.
     */
    public boolean checkNonExistentCells(List<Term> listOfTerms, Cell cell) {
        for (Term term : listOfTerms) {
            if (term.getTermType() == TermType.REFERENCE) {
                if (!sheetMap.containsKey(term.getTermValue())) {
                    cell.setCellValue(TextVariables.CELL_ERROR);
                    cell.setCellType(CellType.ERROR);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Set new value to instance of expression based on previous method result.
     *
     * @param expression
     * @param stringBuilder
     */
    public void modifyExpressionWithNumeric(Expression expression, StringBuilder stringBuilder) {
        if (expression.getTermList().size() > 1) {
            int operationIndex = 0;
            for (Term term : expression.getTermList()) {
                stringBuilder.append(term.getTermValue());
                if (operationIndex < expression.getOperationList().size()) {
                    stringBuilder.append(expression.getOperation(operationIndex));
                    operationIndex++;
                }
            }
        } else {
            stringBuilder.append(expression.getTermValue(0));
        }
    }

    /**
     * Converts to number or to string expression.
     * Looks first of expression has more then one term. If expression has ony one term
     * method checks all possible variants and then does right one. Else go throw all terms
     * and convert expression to format, that will be able to evaluate later.
     *
     * @param expression
     */
    public void convertReferencesToNumericOrString(Expression expression) {
        if (expression.getTermList().size() == 1) {
            performWhenOneTerm(expression);
            return;
        }
        for (Term term : expression.getTermList()) {
            if (term.getTermType() == TermType.REFERENCE) {
                if (!sheetMap.containsKey(term.getTermValue())) {
                    setExpressionError(expression, TextVariables.CELL_ERROR);
                }
                String foundString = findNumeric(term, expression);
                if ((foundString.equals(TextVariables.WRONG_TYPE) &&
                        !expression.getExpressionValue().equals(TextVariables.REFERENCE_ERROR))) {
                    setExpressionError(expression, TextVariables.WRONG_TYPE);
                    return;
                } else if (foundString.equals(TextVariables.CELL_ERROR)) {
                    setExpressionError(expression, TextVariables.CELL_ERROR);
                } else if (foundString.equals(TextVariables.REFERENCE_ERROR)) {
                    return;
                } else if (foundString.equals(TextVariables.DIV_BY_0_ERROR)) {
                    setExpressionError(expression, TextVariables.DIV_BY_0_ERROR);
                    return;
                }
                term.setTermValue(foundString);
                term.setTermType(TermType.NUMERIC);
            }
        }
    }

    /**
     * When cell has only one reference it goes there and perform expression,
     * because when it has one reference it can contain string value.
     *
     * @param expression
     */
    private void performWhenOneTerm(Expression expression) {
        Cell cell;
        String term = expression.getFirstTerm();
        String expressionValue;

        cell = sheetMap.get(term);
        if (term.matches(RegExp.REGEXP_NUMBER)) {
            expression.setExpressionValue(term);
            expression.setCellType(CellType.NUMERIC_FLOAT);
            return;
        }
        if (cellStack.contains(cell)) {
            setParamsWhenCellInStack(expression);
        }
        cellStack.add(cell);
        if (cell.getCellType() == CellType.EXPRESSION) {
            String expressionToFind = cell.getCellValue();
            Expression foundExpression = findExpression(expressionToFind);
            performExpression(foundExpression);
            cell = foundExpression.getCell();
        }
        expressionValue = cell.getCellValue();
        expression.setExpressionValue(expressionValue);
        expression.setCellType(cell.getCellType());
        if (cell.getCellType() == CellType.ERROR) {
            expression.setExpressionState(ExpressionState.ERROR);
        }
        cellStack.remove(cell);

    }

    /**
     * Set parameters if there is circular reference in stack of cells.
     * @param expression - expression where parameters will be set.
     */
    private void setParamsWhenCellInStack(Expression expression) {
        expression.setExpressionState(ExpressionState.ERROR);
        expression.setExpressionValue(TextVariables.REFERENCE_ERROR);
        expression.setCellType(CellType.ERROR);
    }

    /**
     * Find number in expression according to term.
     *
     * @param term
     * @return
     */
    public String findNumeric(Term term, Expression expression) {
        Expression foundExpression = null;
        Cell cell = sheetMap.get(term.getTermValue());

        if (cellStack.contains(cell)) {
            setParamsWhenCellInStack(expression);
            return TextVariables.REFERENCE_ERROR;
        }
        cellStack.add(cell);
        if (cell.getCellType() == CellType.NUMERIC_INTEGER || cell.getCellType() == CellType.NUMERIC_FLOAT) {
            cellStack.remove(cell);
            return cell.getCellValue();
        } else if (cell.getCellType() == CellType.STRING || cell.getCellType() == CellType.EMPTY) {
            return TextVariables.WRONG_TYPE;
        } else if (cell.getCellType() == CellType.ERROR) {
            expression.setExpressionState(ExpressionState.ERROR);
            expression.setExpressionValue(cell.getCellValue());
            return  cell.getCellValue();
        } else {
            foundExpression = findExpression(cell.getCellValue());
            if (foundExpression.getExpressionState() == ExpressionState.ERROR) {
                return foundExpression.getExpressionValue();
            }
            performExpression(foundExpression);
        }
        cellStack.remove(cell);
        return foundExpression.getExpressionValue();
    }

    /**
     * Finds expression according string parameter
     *
     * @param string - searchable value of expression
     * @return found expression
     */
    public Expression findExpression(String string) {
        for (Expression expression : expressionList) {
            if (expression.getExpressionValue().equals(string)) {
                return expression;
            }
        }
        return null;
    }

    /**
     * Creates string with result of expression and return it
     *
     * @param expressionValue
     * @return calculated result converted to string
     */
    public String executeExpression(String expressionValue) {
        String value = "";
        if (expressionValue.matches(RegExp.REGEXP_STRING)) {
            return expressionValue;
        }
        try {
            double valueOfExpression = 0;
            valueOfExpression = (Double) engine.eval(expressionValue);
            valueOfExpression = Math.floor(valueOfExpression * 1000) / 1000;
            value = ((Double) valueOfExpression).toString();
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            try {
                int valueOfExpression = 0;
                valueOfExpression = (Integer) engine.eval(expressionValue);
                value = ((Integer) valueOfExpression).toString();
            } catch (ScriptException e1) {
                e1.printStackTrace();
            }
        }
        return value;
    }
}
