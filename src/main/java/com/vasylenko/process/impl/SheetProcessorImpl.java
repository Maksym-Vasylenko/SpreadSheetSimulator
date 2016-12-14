package com.vasylenko.process.impl;

import com.vasylenko.model.sheet.Sheet;
import com.vasylenko.process.CellErrorTypeProcessor;
import com.vasylenko.process.ExpressionProcessor;
import com.vasylenko.process.SheetProcessor;
import com.vasylenko.process.StringProcessor;

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

    /**
     * Responds for processing strings.
     */
    private StringProcessor stringProcessorImpl;

    /**
     * Responds for processing error after input.
     */
    private CellErrorTypeProcessor cellErrorTypeProcessor;

    public SheetProcessorImpl() {
        expressionProcessorImpl = new ExpressionProcessorImpl();
        stringProcessorImpl = new StringProcessorImpl();
        cellErrorTypeProcessor = new CellErrorTypeProcessorImpl();
    }

    /**
     * Method that start processing data and doing
     * different operation in our spreadsheet
     * @param sheet - parsed spreadsheet
     */
    public void processData(Sheet sheet) {
        cellErrorTypeProcessor.processErrors(sheet);
        stringProcessorImpl.processStrings(sheet);
        expressionProcessorImpl.initializeSheetMap(sheet);
        expressionProcessorImpl.initializeExpressionList();
        expressionProcessorImpl.processExpressions();
    }
}
