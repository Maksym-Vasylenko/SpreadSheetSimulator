package com.vasylenko.process.impl;

import com.vasylenko.model.sheet.Sheet;
import com.vasylenko.process.ExpressionProcessor;
import com.vasylenko.process.SheetProcessor;

/**
 * Class that realize pattern Facade.
 * Process data in spreadsheet.
 *
 * @version 1.0 08 DEC 2016
 * @author Maksym Vasylenko
 */
public class SheetProcessorImpl implements SheetProcessor {

    /**
     * Responds for processing expressions.
     */
    private ExpressionProcessor expressionProcessorImpl;


    public SheetProcessorImpl() {
        expressionProcessorImpl = new ExpressionProcessorImpl();
    }

    /**
     * Method that start processing data and doing
     * different operation in our spreadsheet
     * @param sheet - parsed spreadsheet
     */
    public void processData(Sheet sheet) {
        expressionProcessorImpl.initializeSheetMap(sheet);
        expressionProcessorImpl.initializeExpressionList();
        expressionProcessorImpl.processExpressions();
    }
}
