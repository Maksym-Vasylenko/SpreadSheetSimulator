package com.vasylenko.model.expression;

/**
 * Term which expression contains.
 *
 * @version 1.0 08 DEC 2016
 * @author Maksym Vasylenko
 */
public class Term {

    private String termValue;
    private TermType termType;


    public Term(String termValue, TermType termType) {
        this.termValue = termValue;
        this.termType = termType;
    }

    public String getTermValue() {
        return termValue;
    }

    public void setTermValue(String termValue) {
        this.termValue = termValue;
    }

    public TermType getTermType() {
        return termType;
    }

    public void setTermType(TermType termType) {
        this.termType = termType;
    }
}
