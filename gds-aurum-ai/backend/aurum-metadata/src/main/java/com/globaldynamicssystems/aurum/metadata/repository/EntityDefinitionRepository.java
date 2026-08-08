package com.globaldynamicssystems.aurum.metadata.repository;

import com.globaldynamicssystems.aurum.framework.repository.BaseRepository;
import com.globaldynamicssystems.aurum.model.metadata.EntityDefinition;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la gestión de persistencia de objetos {@link EntityDefinition}.
 */
public interface EntityDefinitionRepository extends BaseRepository<EntityDefinition> {

    /**
     * Busca una definición de entidad por su código único.
     *
     * @param code Código único de la entidad.
     * @return Un {@link Optional} que contiene la entidad si se encuentra registrada.
     */
    Optional<EntityDefinition> findByCode(String code);

    /**
     * Verifica la existencia de una definición de entidad según su código único.
     *
     * @param code Código único a consultar.
     * @return {@code true} si la entidad existe; {@code false} en caso contrario.
     */
    boolean existsByCode(String code);
}