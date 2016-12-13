package com.vasylenko.model.expression;

import com.vasylenko.model.cell.Cell;
import com.vasylenko.model.cell.CellType;

import java.util.List;

/**
 * Expression interface contains methods that any of Expression implementations must realize.
 */
public interface Expression {

    String getCellPosition();

    CellType getCellType();

    void setExpressionValue(String expression);

    void setCellType(CellType cellType);

    String getExpressionValue();

    List<Term> getTermList();

    List<String> getOperationList();

    String getOperation(int index);

    String getTermValue(int index);

    Cell getCell();

    ExpressionState getExpressionState();

    void setExpressionState(ExpressionState expressionState);

    void modifyTermList(List<Term> terms);

    void modifyOperationsList(List<String> operations);

    String getFirstTerm();
}
