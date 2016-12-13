package com.vasylenko.process;

import com.vasylenko.model.sheet.Sheet;

/**
 * SheetProcessor interface contains methods that any of
 * SheetProcessor implementations must realize.
 */
public interface SheetProcessor {
    void processData(Sheet sheet);
}
