package com.vasylenko.global.variables;

/**
 * Contains strings for regular expressions
 */
public interface RegExp {

    String REGEXP_WHITESPACE = "\\s";
    String REGEXP_EMPTY = "";
    String REGEXP_NUMBER = "(-)?\\d+||(-)?\\d+\\.\\d+";
    String REGEXP_INTEGER = "(-)?\\d+";
    String REGEXP_FLOAT = "(-)?\\d+\\.\\d+";
    String REGEXP_STRING = "^'([A-Za-z0-9]+)";
    String REGEXP_EXPRESSION = "^=(([A-Za-z]{1,})([0-9]{1,})|(\\d+(\\.\\d+)?))" +
            "(([\\+\\-\\*\\/](([A-Za-z]{1,})([0-9]{1,})|(\\d+(\\.\\d+)?)))+)?$";
    String REGEXP_PATTERN_OPERATION = "[\\=\\+\\-\\*\\/]";
    String REGEXP_TERM = "(([A-Za-z]{1})([0-9]{1}))|([0-9]+)";

    String REGEXP_DIV_BY_0 = ".*/0([^.]|$|\\.(0{4,}.*|0{1,4}([^0-9]|$))).*";

}
