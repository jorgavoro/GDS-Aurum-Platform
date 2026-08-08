package com.globaldynamicssystems.aurum.engine.query.impl;

import com.globaldynamicssystems.aurum.engine.query.QueryDefinition;
import com.globaldynamicssystems.aurum.engine.query.QueryValidator;
import org.springframework.stereotype.Component;

/**
 * Implementación por defecto de {@link QueryValidator} que valida
 * las reglas estructurales obligatorias de las consultas dinámicas.
 */
@Component
public class DefaultQueryValidator implements QueryValidator {

    /**
     * Constructor por defecto.
     */
    public DefaultQueryValidator() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(QueryDefinition queryDefinition) {
        if (queryDefinition == null) {
            throw new IllegalArgumentException("QueryDefinition cannot be null.");
        }

        if (queryDefinition.getEntityCode() == null) {
            throw new IllegalArgumentException("entityCode cannot be null.");
        }

        if (queryDefinition.getEntityCode().trim().isEmpty()) {
            throw new IllegalArgumentException("entityCode cannot be empty.");
        }

        if (queryDefinition.getPage() != null && queryDefinition.getPage() < 0) {
            throw new IllegalArgumentException("page cannot be less than 0.");
        }

        if (queryDefinition.getPageSize() != null && queryDefinition.getPageSize() < 1) {
            throw new IllegalArgumentException("pageSize cannot be less than 1.");
        }
    }
}