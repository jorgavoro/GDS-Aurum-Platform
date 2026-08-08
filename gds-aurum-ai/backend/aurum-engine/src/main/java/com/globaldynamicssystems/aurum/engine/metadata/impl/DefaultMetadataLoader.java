package com.globaldynamicssystems.aurum.engine.metadata.impl;

import com.globaldynamicssystems.aurum.engine.metadata.MetadataLoader;
import com.globaldynamicssystems.aurum.engine.metadata.MetadataRegistry;
import org.springframework.stereotype.Service;

/**
 * Implementación de {@link MetadataLoader} que actúa como punto de extensión
 * para la carga de definiciones dentro del {@link MetadataRegistry}.
 */
@Service
public class DefaultMetadataLoader implements MetadataLoader {

    private final MetadataRegistry metadataRegistry;

    /**
     * Inyección por constructor del registro de metadatos.
     *
     * @param metadataRegistry Componente donde se registrarán las definiciones.
     */
    public DefaultMetadataLoader(MetadataRegistry metadataRegistry) {
        this.metadataRegistry = metadataRegistry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() {
        // Punto de extensión para futuras estrategias de hidratación e inicialización de metadatos
    }
}