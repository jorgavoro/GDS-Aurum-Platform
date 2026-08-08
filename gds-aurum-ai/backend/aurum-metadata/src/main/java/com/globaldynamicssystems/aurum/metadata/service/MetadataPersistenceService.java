package com.globaldynamicssystems.aurum.metadata.service;

import com.globaldynamicssystems.aurum.model.metadata.EntityDefinition;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio encargada de las operaciones de persistencia de metadatos de entidades.
 */
public interface MetadataPersistenceService {

    /**
     * Guarda o actualiza una definición de entidad en la base de datos.
     *
     * @param entity Objeto {@link EntityDefinition} a persistir.
     * @return La entidad almacenada.
     */
    EntityDefinition save(EntityDefinition entity);

    /**
     * Busca una definición de entidad según su código identificador.
     *
     * @param code Código único de la entidad.
     * @return Un {@link Optional} con la entidad si se encuentra almacenada.
     */
    Optional<EntityDefinition> findByCode(String code);

    /**
     * Obtiene todas las definiciones de entidades almacenadas en la base de datos.
     *
     * @return Lista de objetos {@link EntityDefinition}.
     */
    List<EntityDefinition> findAll();

    /**
     * Elimina una definición de entidad a partir de su código identificador.
     *
     * @param code Código único de la entidad a eliminar.
     */
    void delete(String code);
}