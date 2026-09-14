package com.globaldynamicssystems.aurum.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the GDS Aurum AI Platform.
 *
 * <p>This module is responsible for bootstrapping the entire backend
 * platform and loading all enabled business modules.</p>
 *
 * @author Global Dynamics Systems
 * @version 1.0.0
 */
@SpringBootApplication
public class AurumBootstrapApplication {

    public static void main(String[] args) {
        SpringApplication.run(AurumBootstrapApplication.class, args);
    }

}