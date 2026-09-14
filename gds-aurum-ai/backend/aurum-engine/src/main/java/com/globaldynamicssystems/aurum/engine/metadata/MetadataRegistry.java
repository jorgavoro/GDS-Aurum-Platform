package com.globaldynamicssystems.aurum.engine.metadata;

import com.globaldynamicssystems.aurum.model.metadata.EntityDefinition;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define el contrato para el registro y almacenamiento en memoria
 * de las definiciones de entidades de metadatos del ERP GDS Aurum AI Platform.
 */
public interface MetadataRegistry {

    /**
     * Registra una definición de entidad dentro del catálogo activo.
     *
     * @param entity Objeto {@link EntityDefinition} a registrar.
     */
    void register(EntityDefinition entity);

    /**
     * Busca una definición de entidad mediante su código único.
     *
     * @param code Código único de la entidad.
     * @return Un {@link Optional} con la entidad si se encuentra registrada, o vacío si no existe.
     */
    Optional<EntityDefinition> findByCode(String code);

    /**
     * Recupera todas las definiciones de entidades registradas actualmente en el sistema.
     *
     * @return Lista de objetos {@link EntityDefinition}.
     */
    List<EntityDefinition> findAll();

    /**
     * Verifica la existencia de una entidad registrada mediante su código.
     *
     * @param code Código único de la entidad.
     * @return {@code true} si la entidad existe en el registro; {@code false} en caso contrario.
     */
    boolean exists(String code);

    /**
     * Elimina el registro de una definición de entidad del catálogo mediante su código.
     *
     * @param code Código único de la entidad a remover.
     */
    void unregister(String code);
}