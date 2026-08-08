package com.globaldynamicssystems.aurum.engine.metadata.impl;

import com.globaldynamicssystems.aurum.engine.metadata.MetadataRegistry;
import com.globaldynamicssystems.aurum.model.metadata.EntityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación en memoria de {@link MetadataRegistry} basada en thread-safety
 * mediante {@link ConcurrentHashMap} para la gestión de metadatos del ERP.
 */
@Component
public class InMemoryMetadataRegistry implements MetadataRegistry {

    private final Map<String, EntityDefinition> registry = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void register(EntityDefinition entity) {
        if (entity != null && entity.getCode() != null) {
            registry.put(entity.getCode(), entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<EntityDefinition> findByCode(String code) {
        if (code == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(registry.get(code));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EntityDefinition> findAll() {
        return new ArrayList<>(registry.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(String code) {
        return code != null && registry.containsKey(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unregister(String code) {
        if (code != null) {
            registry.remove(code);
        }
    }
}