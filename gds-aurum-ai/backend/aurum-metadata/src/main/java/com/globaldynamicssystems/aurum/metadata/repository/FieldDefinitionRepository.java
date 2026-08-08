package com.globaldynamicssystems.aurum.metadata.repository;

import com.globaldynamicssystems.aurum.framework.repository.BaseRepository;
import com.globaldynamicssystems.aurum.model.metadata.FieldDefinition;

import java.util.List;

/**
 * Repositorio Spring Data JPA para la gestión de persistencia de objetos {@link FieldDefinition}.
 */
public interface FieldDefinitionRepository extends BaseRepository<FieldDefinition> {

    /**
     * Obtiene la lista de campos asociados a una entidad según el código de dicha entidad.
     *
     * @param code Código único de la entidad propietaria.
     * @return Lista de objetos {@link FieldDefinition} pertenecientes a la entidad.
     */
    List<FieldDefinition> findByEntityCode(String code);
}