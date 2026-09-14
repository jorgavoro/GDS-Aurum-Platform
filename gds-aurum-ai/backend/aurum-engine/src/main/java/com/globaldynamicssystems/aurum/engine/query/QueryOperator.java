package com.globaldynamicssystems.aurum.engine.query;

/**
 * Enum que define la lista de operadores relacionales y lógicos soportados
 * por el motor de consultas dinámicas.
 */
public enum QueryOperator {
    EQUALS,
    NOT_EQUALS,
    GREATER_THAN,
    GREATER_THAN_OR_EQUAL,
    LESS_THAN,
    LESS_THAN_OR_EQUAL,
    LIKE,
    STARTS_WITH,
    ENDS_WITH,
    IN,
    NOT_IN,
    IS_NULL,
    IS_NOT_NULL,
    BETWEEN
}