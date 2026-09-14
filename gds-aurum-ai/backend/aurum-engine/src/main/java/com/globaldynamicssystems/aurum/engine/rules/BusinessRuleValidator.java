package com.globaldynamicssystems.aurum.engine.rules;

/**
 * Contrato para la validación de la integridad estructural de un {@link RuleContext}
 * previo a la evaluación de reglas de negocio.
 */
public interface BusinessRuleValidator {

    /**
     * Valida que un contexto de ejecución cumpla con los requisitos estructurales mínimos.
     *
     * @param context Instancia de {@link RuleContext} a evaluar.
     * @throws IllegalArgumentException si el contexto incumple las reglas de validación.
     */
    void validate(RuleContext context);
}