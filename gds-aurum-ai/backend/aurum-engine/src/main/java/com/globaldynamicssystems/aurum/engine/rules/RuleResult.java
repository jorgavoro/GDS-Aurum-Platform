package com.globaldynamicssystems.aurum.engine.rules;

/**
 * Encapsula el resultado individual proveniente de la evaluación de una regla de negocio.
 */
public class RuleResult {

    private boolean valid;
    private String ruleCode;
    private String message;

    /**
     * Constructor por defecto.
     */
    public RuleResult() {
    }

    /**
     * Constructor completo.
     *
     * @param valid    Indica si la evaluación fue exitosa.
     * @param ruleCode Código de la regla evaluada.
     * @param message  Mensaje descriptivo o de error.
     */
    public RuleResult(boolean valid, String ruleCode, String message) {
        this.valid = valid;
        this.ruleCode = ruleCode;
        this.message = message;
    }

    /**
     * Construye un resultado exitoso para una regla específica.
     *
     * @param ruleCode Código de la regla evaluada.
     * @return Instancia de {@link RuleResult} válida.
     */
    public static RuleResult success(String ruleCode) {
        return new RuleResult(true, ruleCode, null);
    }

    /**
     * Construye un resultado de fallo para una regla específica.
     *
     * @param ruleCode Código de la regla evaluada.
     * @param message  Mensaje explicativo del fallo.
     * @return Instancia de {@link RuleResult} inválida.
     */
    public static RuleResult failure(String ruleCode, String message) {
        return new RuleResult(false, ruleCode, message);
    }

    /**
     * Determina si la regla fue satisfecha correctamente.
     *
     * @return {@code true} si la regla es válida; {@code false} en caso contrario.
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Establece el estado de validez del resultado.
     *
     * @param valid Estado de validez.
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Obtiene el código de la regla evaluada.
     *
     * @return Código de la regla.
     */
    public String getRuleCode() {
        return ruleCode;
    }

    /**
     * Establece el código de la regla evaluada.
     *
     * @param ruleCode Código de la regla.
     */
    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    /**
     * Obtiene el mensaje resultante de la evaluación.
     *
     * @return Mensaje descriptivo o de error.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Establece el mensaje resultante de la evaluación.
     *
     * @param message Mensaje descriptivo o de error.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}