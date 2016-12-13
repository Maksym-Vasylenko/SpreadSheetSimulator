package com.vasylenko.controller.parser;

import com.vasylenko.model.sheet.Sheet;

/**
 * Contains method that any class that want to implement Parsable must realize.
 *
 * @version 1.0 08 DEC 2016
 * @author Maksym Vasylenko
 */
public interface Parsable {
     void parseSpreadsheet(String textToParse, Sheet sheet);
}
