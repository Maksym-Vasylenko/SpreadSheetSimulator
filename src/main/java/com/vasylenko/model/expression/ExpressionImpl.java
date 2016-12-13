package com.vasylenko.model.expression;

import com.vasylenko.model.cell.Cell;
import com.vasylenko.model.cell.CellType;

import java.util.List;

/**
 * Represent expression in cell.
 *
 * @version 1.0 08 DEC 2016
 * @author Maksym Vasylenko
 */
public class ExpressionImpl implements Expression {

    private Cell cell;
    private List<Term> termList;
    private List<String> operationList;
    private ExpressionState expressionState;


    public ExpressionImpl(Cell cell, List<Term> termList, List<String> operationList, ExpressionState expressionState) {
        this.cell = cell;
        this.termList = termList;
        this.operationList = operationList;
        this.expressionState = expressionState;
    }

    public Cell getCell() {
        return cell;
    }


    @Override
    public String getCellPosition() {
        return cell.getFullCellName();
    }

    @Override
    public CellType getCellType() {
        return cell.getCellType();
    }

    @Override
    public void setExpressionValue(String expression) {
        cell.setCellValue(expression);
    }

    @Override
    public void setCellType(CellType cellType) {
        getCell().setCellType(cellType);
    }

    @Override
    public String getExpressionValue() {
        return cell.getCellValue();
    }

    @Override
    public List<Term> getTermList() {
        return termList;
    }

    @Override
    public List<String> getOperationList() {
        return operationList;
    }

    @Override
    public String getOperation(int index) {
        return operationList.get(index);
    }

    @Override
    public String getTermValue(int index) {
        return termList.get(index).getTermValue();
    }

    @Override
    public ExpressionState getExpressionState() {
        return expressionState;
    }

    @Override
    public void setExpressionState(ExpressionState expressionState) {
        this.expressionState = expressionState;
    }

    @Override
    public void modifyTermList(List<Term> terms) {
        termList = terms;
    }

    @Override
    public void modifyOperationsList(List<String> operations) {
        operationList = operations;
    }

    @Override
    public String getFirstTerm() {
        return termList.get(0).getTermValue();
    }
}
