package com.globaldynamicssystems.aurum.engine.form;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la estructura de un formulario dinámico derivado de la definición de una entidad.
 */
public class FormDefinition {

    private String entityCode;
    private String title;
    private List<FormField> fields;

    /**
     * Constructor por defecto.
     */
    public FormDefinition() {
        this.fields = new ArrayList<>();
    }

    /**
     * Constructor completo.
     *
     * @param entityCode Código identificador de la entidad.
     * @param title      Título del formulario.
     * @param fields     Lista de campos visuales que componen el formulario.
     */
    public FormDefinition(String entityCode, String title, List<FormField> fields) {
        this.entityCode = entityCode;
        this.title = title;
        this.fields = fields != null ? fields : new ArrayList<>();
    }

    /**
     * Obtiene el código de la entidad asociada al formulario.
     *
     * @return Código de la entidad.
     */
    public String getEntityCode() {
        return entityCode;
    }

    /**
     * Establece el código de la entidad asociada al formulario.
     *
     * @param entityCode Código de la entidad.
     */
    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    /**
     * Obtiene el título descriptivo del formulario.
     *
     * @return Título del formulario.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Establece el título descriptivo del formulario.
     *
     * @param title Título del formulario.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtiene la lista de controles que integran el formulario.
     *
     * @return Lista de {@link FormField}.
     */
    public List<FormField> getFields() {
        return fields;
    }

    /**
     * Establece la lista de controles que integran el formulario.
     *
     * @param fields Lista de {@link FormField}.
     */
    public void setFields(List<FormField> fields) {
        this.fields = fields != null ? fields : new ArrayList<>();
    }
}