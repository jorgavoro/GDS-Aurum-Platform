package com.globaldynamicssystems.aurum.engine.form;

import com.globaldynamicssystems.aurum.model.metadata.EntityDefinition;

/**
 * Contrato funcional para la construcción dinámica de formularios a partir de metadatos de entidad.
 */
public interface FormBuilder {

    /**
     * Construye un modelo de formulario basado en la definición funcional de una entidad.
     *
     * @param entityDefinition Definición de la entidad fuente.
     * @return Instancia de {@link FormDefinition}.
     */
    FormDefinition build(EntityDefinition entityDefinition);
}