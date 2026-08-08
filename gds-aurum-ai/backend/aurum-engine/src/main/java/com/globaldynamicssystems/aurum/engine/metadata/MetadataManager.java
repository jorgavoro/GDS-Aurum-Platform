package com.globaldynamicssystems.aurum.engine.metadata;

import com.globaldynamicssystems.aurum.model.metadata.EntityDefinition;
import com.globaldynamicssystems.aurum.model.metadata.FieldDefinition;

import java.util.List;

/**
 * Interfaz de servicio de alto nivel para la consulta e inspección de metadatos
 * estructurales (entidades y campos) en el ERP GDS Aurum AI Platform.
 */
public interface MetadataManager {

    /**
     * Obtiene la definición de una entidad según su código único.
     *
     * @param code Código identificador de la entidad.
     * @return La estructura {@link EntityDefinition} correspondiente.
     */
    EntityDefinition getEntity(String code);

    /**
     * Obtiene la lista completa de definiciones de campos pertenecientes a una entidad.
     *
     * @param entityCode Código identificador de la entidad.
     * @return Lista de objetos {@link FieldDefinition}.
     */
    List<FieldDefinition> getFields(String entityCode);

    /**
     * Obtiene la definición de un campo específico dentro de una entidad dada.
     *
     * @param entityCode Código identificador de la entidad.
     * @param fieldCode Código identificador del campo.
     * @return La estructura {@link FieldDefinition} correspondiente.
     */
    FieldDefinition getField(String entityCode, String fieldCode);

    /**
     * Comprueba si una entidad está registrada en el motor de metadatos.
     *
     * @param entityCode Código identificador de la entidad.
     * @return {@code true} si existe; {@code false} en caso contrario.
     */
    boolean exists(String entityCode);
}