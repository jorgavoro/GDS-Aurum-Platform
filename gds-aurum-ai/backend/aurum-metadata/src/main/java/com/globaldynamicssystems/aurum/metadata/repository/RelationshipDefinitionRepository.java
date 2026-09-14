package com.globaldynamicssystems.aurum.metadata.repository;

import com.globaldynamicssystems.aurum.framework.repository.BaseRepository;
import com.globaldynamicssystems.aurum.model.metadata.RelationshipDefinition;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la gestión de persistencia de objetos {@link RelationshipDefinition}.
 */
public interface RelationshipDefinitionRepository extends BaseRepository<RelationshipDefinition> {

    /**
     * Obtiene la lista de relaciones cuya entidad origen coincide con el código proporcionado.
     *
     * @param code Código único de la entidad origen.
     * @return Lista de objetos {@link RelationshipDefinition} declaradas desde la entidad origen.
     */
    List<RelationshipDefinition> findBySourceEntityCode(String code);
}