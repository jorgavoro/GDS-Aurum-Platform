package com.globaldynamicssystems.aurum.engine.rules;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa el contexto de ejecución transportador de datos e información
 * requerida durante la evaluación de reglas de negocio.
 */
public class RuleContext {

    private String entityCode;
    private Map<String, Object> values;
    private Map<String, Object> attributes;

    /**
     * Constructor por defecto.
     */
    public RuleContext() {
        this.values = new HashMap<>();
        this.attributes = new HashMap<>();
    }

    /**
     * Constructor completo.
     *
     * @param entityCode Código identificador de la entidad funcional.
     * @param values     Mapa de valores de campos de la entidad.
     * @param attributes Mapa de atributos o metadatos adicionales del contexto.
     */
    public RuleContext(String entityCode, Map<String, Object> values, Map<String, Object> attributes) {
        this.entityCode = entityCode;
        this.values = values != null ? values : new HashMap<>();
        this.attributes = attributes != null ? attributes : new HashMap<>();
    }

    /**
     * Obtiene el código de la entidad funcional asociada al contexto.
     *
     * @return Código de la entidad.
     */
    public String getEntityCode() {
        return entityCode;
    }

    /**
     * Establece el código de la entidad funcional asociada al contexto.
     *
     * @param entityCode Código de la entidad.
     */
    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    /**
     * Obtiene el mapa de valores asociados a los campos evaluados.
     *
     * @return Mapa de pares clave-valor de la entidad.
     */
    public Map<String, Object> getValues() {
        return values;
    }

    /**
     * Establece el mapa de valores asociados a los campos evaluados.
     *
     * @param values Mapa de pares clave-valor.
     */
    public void setValues(Map<String, Object> values) {
        this.values = values != null ? values : new HashMap<>();
    }

    /**
     * Obtiene el mapa de atributos adicionales del contexto.
     *
     * @return Mapa de atributos contextuales.
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Establece el mapa de atributos adicionales del contexto.
     *
     * @param attributes Mapa de atributos contextuales.
     */
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes != null ? attributes : new HashMap<>();
    }
}