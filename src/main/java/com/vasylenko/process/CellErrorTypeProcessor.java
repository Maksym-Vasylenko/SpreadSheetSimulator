package com.vasylenko.process;

import com.vasylenko.model.sheet.Sheet;

/**
 * CellErrorTypeProcessor interface contains methods that any of
 * CellErrorTypeProcessor implementations must realize.
 */
public interface CellErrorTypeProcessor {
    void processErrors(Sheet sheet);
}
