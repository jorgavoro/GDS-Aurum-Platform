package com.globaldynamicssystems.aurum.engine.form.impl;

import com.globaldynamicssystems.aurum.engine.form.FieldRenderer;
import com.globaldynamicssystems.aurum.engine.form.FormField;
import com.globaldynamicssystems.aurum.model.metadata.FieldDefinition;
import com.globaldynamicssystems.aurum.model.metadata.FieldType;
import org.springframework.stereotype.Component;

/**
 * Implementación de renderizado universal para transformar definiciones de metadatos de campos
 * en controles abstractos de formulario para todos los tipos soportados.
 */
@Component
public class DefaultFieldRenderer implements FieldRenderer {

    /**
     * Constructor por defecto.
     */
    public DefaultFieldRenderer() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(FieldType fieldType) {
        return fieldType != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormField render(FieldDefinition fieldDefinition) {
        if (fieldDefinition == null) {
            return new FormField();
        }

        return new FormField(
                fieldDefinition.getCode(),
                fieldDefinition.getLabel(),
                fieldDefinition.getFieldType(),
                fieldDefinition.getRequired(),
                fieldDefinition.getVisible(),
                fieldDefinition.getEditable(),
                fieldDefinition.getDisplayOrder()
        );
    }
}