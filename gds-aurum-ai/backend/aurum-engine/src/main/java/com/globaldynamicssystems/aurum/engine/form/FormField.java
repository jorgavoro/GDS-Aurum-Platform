package com.globaldynamicssystems.aurum.engine.form;

import com.globaldynamicssystems.aurum.model.metadata.FieldType;

/**
 * Representa la abstracción de un control o campo de entrada dentro de un formulario dinámico.
 */
public class FormField {

    private String code;
    private String label;
    private FieldType fieldType;
    private Boolean required;
    private Boolean visible;
    private Boolean editable;
    private Integer order;

    /**
     * Constructor por defecto.
     */
    public FormField() {
    }

    /**
     * Constructor completo.
     *
     * @param code      Código programático del campo.
     * @param label     Etiqueta de despliegue en la interfaz.
     * @param fieldType Tipo de dato y comportamiento del campo.
     * @param required  Indica si la entrada es obligatoria.
     * @param visible   Indica si el control debe visualizarse.
     * @param editable  Indica si el valor es modificable.
     * @param order     Secuencia de ordenamiento para despliegue.
     */
    public FormField(String code, String label, FieldType fieldType, Boolean required, Boolean visible, Boolean editable, Integer order) {
        this.code = code;
        this.label = label;
        this.fieldType = fieldType;
        this.required = required;
        this.visible = visible;
        this.editable = editable;
        this.order = order;
    }

    /**
     * Obtiene el código del campo.
     *
     * @return Código del campo.
     */
    public String getCode() {
        return code;
    }

    /**
     * Establece el código del campo.
     *
     * @param code Código del campo.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Obtiene la etiqueta del campo.
     *
     * @return Etiqueta del campo.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Establece la etiqueta del campo.
     *
     * @param label Etiqueta del campo.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Obtiene el tipo de campo.
     *
     * @return Tipo de campo {@link FieldType}.
     */
    public FieldType getFieldType() {
        return fieldType;
    }

    /**
     * Establece el tipo de campo.
     *
     * @param fieldType Tipo de campo {@link FieldType}.
     */
    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * Obtiene si el campo es requerido.
     *
     * @return Verdadero si es requerido, falso en caso contrario.
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * Establece si el campo es requerido.
     *
     * @param required Estado de obligatoriedad.
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * Obtiene la visibilidad del campo.
     *
     * @return Verdadero si es visible, falso en caso contrario.
     */
    public Boolean getVisible() {
        return visible;
    }

    /**
     * Establece la visibilidad del campo.
     *
     * @param visible Estado de visibilidad.
     */
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    /**
     * Obtiene la editabilidad del campo.
     *
     * @return Verdadero si es editable, falso en caso contrario.
     */
    public Boolean getEditable() {
        return editable;
    }

    /**
     * Establece la editabilidad del campo.
     *
     * @param editable Estado de editabilidad.
     */
    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    /**
     * Obtiene la secuencia de ordenamiento del campo.
     *
     * @return Orden del campo.
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * Establece la secuencia de ordenamiento del campo.
     *
     * @param order Orden del campo.
     */
    public void setOrder(Integer order) {
        this.order = order;
    }
}