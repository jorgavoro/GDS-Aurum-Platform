package com.globaldynamicssystems.aurum.engine.query;

import java.util.Map;

/**
 * Contrato principal del motor de consultas dinámicas encargada de procesar
 * la definición de una consulta y retornar un resultado abstracto en mapa de pares clave-valor.
 */
public interface DynamicQueryEngine {

    /**
     * Ejecuta una consulta dinámica descrita por su definición de metadatos.
     *
     * @param queryDefinition Definición estructural de la consulta.
     * @return Un {@link QueryResult} con los datos proyectados en mapas dinámicos.
     */
    QueryResult<Map<String, Object>> execute(QueryDefinition queryDefinition);
}