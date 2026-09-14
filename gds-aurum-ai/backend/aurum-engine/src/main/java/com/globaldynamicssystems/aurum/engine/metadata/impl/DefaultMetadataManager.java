package com.globaldynamicssystems.aurum.engine.metadata.impl;

import com.globaldynamicssystems.aurum.engine.metadata.MetadataManager;
import com.globaldynamicssystems.aurum.engine.metadata.MetadataRegistry;
import com.globaldynamicssystems.aurum.model.metadata.EntityDefinition;
import com.globaldynamicssystems.aurum.model.metadata.FieldDefinition;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementación predeterminada de {@link MetadataManager} que provee acceso
 * a los metadatos consultando directamente la abstracción {@link MetadataRegistry}.
 */
@Service
public class DefaultMetadataManager implements MetadataManager {

    private final MetadataRegistry metadataRegistry;

    /**
     * Inyección por constructor del componente de registro de metadatos.
     *
     * @param metadataRegistry Registro central en memoria de metadatos.
     */
    public DefaultMetadataManager(MetadataRegistry metadataRegistry) {
        this.metadataRegistry = metadataRegistry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityDefinition getEntity(String code) {
        return metadataRegistry.findByCode(code)
                .orElseThrow(() -> new NoSuchElementException("EntityDefinition not found for code: " + code));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FieldDefinition> getFields(String entityCode) {
        EntityDefinition entity = getEntity(entityCode);
        return entity.getFields() != null ? entity.getFields() : Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FieldDefinition getField(String entityCode, String fieldCode) {
        return getFields(entityCode).stream()
                .filter(field -> fieldCode != null && fieldCode.equalsIgnoreCase(field.getCode()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "FieldDefinition '" + fieldCode + "' not found in entity '" + entityCode + "'"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(String entityCode) {
        return metadataRegistry.exists(entityCode);
    }
}