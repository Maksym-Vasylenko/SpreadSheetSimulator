package com.vasylenko;

import com.vasylenko.controller.Controller;
import com.vasylenko.model.sheet.Sheet;
import com.vasylenko.model.sheet.SheetImpl;
import com.vasylenko.view.View;

/**
 * Main class. Entry point of the program.
 *
 * @version 1.0 08 DEC 2016
 * @author Maksym Vasylenko
 */
public class Main {
    public static void main(String[] args) {

        /**
         * Instance of model.
         */
        Sheet sheet = new SheetImpl();

        /**
         * Instance of view.
         */
        View view = new View();
        Controller controller = new Controller(sheet, view);

        controller.processStart();
    }
}
