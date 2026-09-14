package com.globaldynamicssystems.aurum.metadata.service.impl;

import com.globaldynamicssystems.aurum.metadata.repository.EntityDefinitionRepository;
import com.globaldynamicssystems.aurum.metadata.service.MetadataPersistenceService;
import com.globaldynamicssystems.aurum.model.metadata.EntityDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación predeterminada de {@link MetadataPersistenceService} delegando
 * las operaciones de almacenamiento y consulta en {@link EntityDefinitionRepository}.
 */
@Service
@Transactional(readOnly = true)
public class DefaultMetadataPersistenceService implements MetadataPersistenceService {

    private final EntityDefinitionRepository repository;

    /**
     * Inyección por constructor del repositorio de definiciones de entidades.
     *
     * @param repository Repositorio {@link EntityDefinitionRepository}.
     */
    public DefaultMetadataPersistenceService(EntityDefinitionRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public EntityDefinition save(EntityDefinition entity) {
        return repository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<EntityDefinition> findByCode(String code) {
        return repository.findByCode(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EntityDefinition> findAll() {
        return repository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(String code) {
        repository.findByCode(code).ifPresent(repository::delete);
    }
}