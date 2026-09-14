package com.globaldynamicssystems.aurum.engine.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsula el resultado global acumulado tras la ejecución de un conjunto de reglas de negocio.
 */
public class BusinessRulesResult {

    private boolean valid;
    private List<RuleResult> results;

    /**
     * Constructor por defecto.
     */
    public BusinessRulesResult() {
        this.valid = true;
        this.results = new ArrayList<>();
    }

    /**
     * Constructor completo.
     *
     * @param valid   Estado global de validez del conjunto de reglas.
     * @param results Lista de resultados individuales acumulados.
     */
    public BusinessRulesResult(boolean valid, List<RuleResult> results) {
        this.valid = valid;
        this.results = results != null ? results : new ArrayList<>();
    }

    /**
     * Incorpora un resultado individual de regla al resultado global,
     * actualizando el estado general si se detecta alguna regla inválida.
     *
     * @param result Resultado individual {@link RuleResult}.
     */
    public void addResult(RuleResult result) {
        if (result != null) {
            this.results.add(result);
            if (!result.isValid()) {
                this.valid = false;
            }
        }
    }

    /**
     * Determina si existen reglas fallidas dentro del conjunto evaluado.
     *
     * @return {@code true} si al menos una regla falló; {@code false} en caso contrario.
     */
    public boolean hasFailures() {
        return !valid;
    }

    /**
     * Obtiene la validez global de la evaluación.
     *
     * @return {@code true} si todas las reglas fueron válidas; {@code false} si hubo fallos.
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Establece la validez global de la evaluación.
     *
     * @param valid Estado de validez global.
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Obtiene el listado de resultados de las reglas evaluadas.
     *
     * @return Lista de {@link RuleResult}.
     */
    public List<RuleResult> getResults() {
        return results;
    }

    /**
     * Establece el listado de resultados e infiere el estado de validez global.
     *
     * @param results Lista de {@link RuleResult}.
     */
    public void setResults(List<RuleResult> results) {
        this.results = results != null ? results : new ArrayList<>();
        this.valid = this.results.stream().allMatch(RuleResult::isValid);
    }
}