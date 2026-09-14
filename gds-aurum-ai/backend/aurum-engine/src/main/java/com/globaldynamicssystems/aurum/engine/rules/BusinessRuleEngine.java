package com.globaldynamicssystems.aurum.engine.rules;

/**
 * Contrato principal del motor de reglas de negocio para evaluar un contexto dado.
 */
public interface BusinessRuleEngine {

    /**
     * Evalúa todas las reglas de negocio aplicables en el contexto suministrado.
     *
     * @param context Contexto de ejecución con la información a evaluar.
     * @return Instancia de {@link BusinessRulesResult} con los resultados globales e individuales.
     */
    BusinessRulesResult evaluate(RuleContext context);
}