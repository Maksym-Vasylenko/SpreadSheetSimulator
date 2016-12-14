package com.vasylenko.process;

import com.vasylenko.model.sheet.Sheet;

/**
 * StringProcessor interface contains methods that any of
 * StringProcessor implementations must realize.
 */
public interface StringProcessor {
    void processStrings (Sheet sheet);

}
