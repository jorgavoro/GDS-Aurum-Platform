package com.globaldynamicssystems.aurum.engine.query.impl;

import com.globaldynamicssystems.aurum.engine.query.DynamicQueryEngine;
import com.globaldynamicssystems.aurum.engine.query.QueryDefinition;
import com.globaldynamicssystems.aurum.engine.query.QueryResult;
import com.globaldynamicssystems.aurum.engine.query.QueryValidator;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * Implementación desacoplada por defecto del motor {@link DynamicQueryEngine}.
 * Prepara la abstracción sin ejecutar persistencia o consultas físicas a base de datos.
 */
@Component
public class DefaultDynamicQueryEngine implements DynamicQueryEngine {

    private final QueryValidator queryValidator;

    /**
     * Inyección por constructor del componente de validación de consultas.
     *
     * @param queryValidator Componente {@link QueryValidator}.
     */
    public DefaultDynamicQueryEngine(QueryValidator queryValidator) {
        this.queryValidator = queryValidator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QueryResult<Map<String, Object>> execute(QueryDefinition queryDefinition) {
        queryValidator.validate(queryDefinition);

        int page = queryDefinition.getPage() != null ? queryDefinition.getPage() : 0;
        int pageSize = queryDefinition.getPageSize() != null ? queryDefinition.getPageSize() : 10;

        return new QueryResult<>(
                Collections.emptyList(),
                0L,
                page,
                pageSize
        );
    }
}