package com.globaldynamicssystems.aurum.engine.query;

/**
 * Contrato encargado de validar la integridad estructural y coherencia de una {@link QueryDefinition}.
 */
public interface QueryValidator {

    /**
     * Valida que una definición de consulta cumpla con los criterios estructurales mínimos.
     *
     * @param queryDefinition Objeto con la definición de la consulta.
     * @throws IllegalArgumentException si la definición viola alguna regla de validación.
     */
    void validate(QueryDefinition queryDefinition);
}