package com.globaldynamicssystems.aurum.engine.rules;

/**
 * Interfaz que define el contrato genérico para la evaluación de una regla de negocio.
 */
public interface BusinessRule {

    /**
     * Obtiene el código único identificador de la regla de negocio.
     *
     * @return Código identificador de la regla.
     */
    String getCode();

    /**
     * Determina si la regla es aplicable para el contexto de ejecución proporcionado.
     *
     * @param context Contexto de ejecución de la regla.
     * @return {@code true} si la regla soporta el contexto; {@code false} en caso contrario.
     */
    boolean supports(RuleContext context);

    /**
     * Evalúa la regla de negocio contra el contexto suministrado.
     *
     * @param context Contexto de ejecución con la información a evaluar.
     * @return Objeto {@link RuleResult} con el resultado individual de la evaluación.
     */
    RuleResult evaluate(RuleContext context);
}