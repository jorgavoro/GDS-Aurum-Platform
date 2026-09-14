package com.globaldynamicssystems.aurum.engine.form;

import com.globaldynamicssystems.aurum.model.metadata.FieldDefinition;
import com.globaldynamicssystems.aurum.model.metadata.FieldType;

/**
 * Contrato para la conversión de definiciones de metadatos de campo hacia estructuras de control visual.
 */
public interface FieldRenderer {

    /**
     * Determina si el renderizador soporta el tipo de campo especificado.
     *
     * @param fieldType Tipo de dato del campo.
     * @return Verdadero si el tipo es soportado; falso en caso contrario.
     */
    boolean supports(FieldType fieldType);

    /**
     * Transforma una definición de campo en un control de formulario.
     *
     * @param fieldDefinition Metadatos del campo de origen.
     * @return Instancia generada de {@link FormField}.
     */
    FormField render(FieldDefinition fieldDefinition);
}