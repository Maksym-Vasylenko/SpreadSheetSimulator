package com.vasylenko.controller;

import com.vasylenko.controller.parser.Parsable;
import com.vasylenko.controller.parser.SpreadsheetParser;
import com.vasylenko.global.variables.TextVariables;
import com.vasylenko.model.sheet.Sheet;
import com.vasylenko.model.sheet.SheetImpl;
import com.vasylenko.process.impl.SheetProcessorImpl;
import com.vasylenko.view.View;
import com.vasylenko.view.Viewable;

import java.util.Scanner;

/**
 * Class which contains model and view. Communicates with user.
 *
 * @version 1.0 08 DEC 2016
 * @author Maksym Vasylenko
 */
public class Controller {

    /**
     * Sheet to process
     */
    private Sheet sheet;

    /**
     * Parser to parse text
     */
    private Parsable parser;

    /**
     * To start main process. Used as pattern Facade.
     */
    private SheetProcessorImpl sheetProcessorImpl;

    /**
     * View to show user information on screen.
     */
    private Viewable view;

    public Controller(Sheet sheet, View view) {
        this.sheet = sheet;
        this.view = view;
        parser = new SpreadsheetParser();
        sheetProcessorImpl = new SheetProcessorImpl();
    }

    /**
     * Method which start process.
     */
    public void processStart() {
        askUserToInputRows();
        view.printMessage(sheet.convertToString());
        sheetProcessorImpl.processData(sheet);
        //view.printMessage(sheet.convertToString());
        view.printSpreadSheet(sheet);
    }

    /**
     * Method where user input row in spreadsheet
     * @return string that is row in table
     */
    private String inputRow() {
        Scanner scanner = new Scanner(System.in);
        String row;
        do {

            row = scanner.nextLine();

        } while (row.equals(""));

        return row;
    }

    /**
     * Method communicates with user and asks him
     * to input rows in spreadsheet. User can finish
     * input by typing "Exit" and pressing enter.
     */
    private void askUserToInputRows() {
        view.printMessage(TextVariables.GREETING);
        view.printMessage(TextVariables.ENTER_ROW);

        String row = inputRow();
        while (!row.equals(TextVariables.EXIT)) {
            parser.parseSpreadsheet(row, sheet);
            view.printMessage(TextVariables.ENTER_ROW);
            row = inputRow();
        }
    }
}
