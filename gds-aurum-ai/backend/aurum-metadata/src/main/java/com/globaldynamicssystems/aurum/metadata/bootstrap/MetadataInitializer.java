package com.globaldynamicssystems.aurum.metadata.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Inicializa el módulo de metadatos durante el arranque de la aplicación.
 */
@Component
public class MetadataInitializer implements ApplicationRunner {

    private static final Logger logger =
            LoggerFactory.getLogger(MetadataInitializer.class);

    @Override
    public void run(ApplicationArguments args) {
        logger.info("Metadata module initialized.");
    }
}