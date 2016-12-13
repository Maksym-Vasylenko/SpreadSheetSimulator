package com.vasylenko.view;

import com.vasylenko.model.sheet.Sheet;

/**
 * Interface that has method to print data.
 *
 * @version 1.0 07 DEC 2016
 * @author Maksym 8asylenko
 */
public interface Viewable {
    void printMessage(String message);

    void printSpreadSheet(Sheet sheet);
}
