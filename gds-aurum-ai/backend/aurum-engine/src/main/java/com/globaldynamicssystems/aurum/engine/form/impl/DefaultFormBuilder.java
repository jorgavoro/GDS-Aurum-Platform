package com.globaldynamicssystems.aurum.engine.form.impl;

import com.globaldynamicssystems.aurum.engine.form.FieldRenderer;
import com.globaldynamicssystems.aurum.engine.form.FormBuilder;
import com.globaldynamicssystems.aurum.engine.form.FormDefinition;
import com.globaldynamicssystems.aurum.engine.form.FormField;
import com.globaldynamicssystems.aurum.model.metadata.EntityDefinition;
import com.globaldynamicssystems.aurum.model.metadata.FieldDefinition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementación predeterminada del constructor de formularios dinámicos.
 */
@Service
public class DefaultFormBuilder implements FormBuilder {

    private final FieldRenderer fieldRenderer;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param fieldRenderer Componente renderizador de campos individuales.
     */
    public DefaultFormBuilder(FieldRenderer fieldRenderer) {
        this.fieldRenderer = fieldRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormDefinition build(EntityDefinition entityDefinition) {
        if (entityDefinition == null) {
            return new FormDefinition();
        }

        List<FormField> formFields = new ArrayList<>();

        if (entityDefinition.getFields() != null) {
            formFields = entityDefinition.getFields().stream()
                    .filter(field -> field != null && Boolean.TRUE.equals(field.getVisible()))
                    .sorted(Comparator.comparing(
                            FieldDefinition::getDisplayOrder,
                            Comparator.nullsLast(Comparator.naturalOrder())
                    ))
                    .map(fieldRenderer::render)
                    .toList();
        }

        return new FormDefinition(
                entityDefinition.getCode(),
                entityDefinition.getName(),
                formFields
        );
    }
}