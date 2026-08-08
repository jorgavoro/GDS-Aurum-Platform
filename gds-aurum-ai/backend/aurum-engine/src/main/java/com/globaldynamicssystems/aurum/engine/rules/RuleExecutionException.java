package com.globaldynamicssystems.aurum.engine.rules;

/**
 * Excepción lanzada en tiempo de ejecución cuando ocurre una falla no controlada
 * durante la evaluación de una regla de negocio.
 */
public class RuleExecutionException extends RuntimeException {

    /**
     * Construye una nueva excepción con un mensaje descriptivo.
     *
     * @param message Detalle del error ocurrido.
     */
    public RuleExecutionException(String message) {
        super(message);
    }

    /**
     * Construye una nueva excepción con un mensaje descriptivo y la causa original.
     *
     * @param message Detalle del error ocurrido.
     * @param cause   Causa subyacente del error.
     */
    public RuleExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}