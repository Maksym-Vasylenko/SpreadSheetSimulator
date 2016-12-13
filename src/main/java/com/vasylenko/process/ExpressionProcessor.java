package com.vasylenko.process;

import com.vasylenko.model.sheet.Sheet;

/**
 * ExpressionProcessor interface contains methods that any of
 * ExpressionProcessor implementations must realize.
 */
public interface ExpressionProcessor {

    void processExpressions();
    void initializeSheetMap(Sheet sheet);
    void initializeExpressionList();
}
