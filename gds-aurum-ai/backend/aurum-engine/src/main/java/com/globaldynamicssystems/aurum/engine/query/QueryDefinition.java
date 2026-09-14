package com.globaldynamicssystems.aurum.engine.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la definición y estructura de una consulta dinámica basada en metadatos.
 */
public class QueryDefinition {

    private String entityCode;
    private List<String> fields;
    private List<QueryFilter> filters;
    private List<QuerySort> sorts;
    private Integer page;
    private Integer pageSize;

    /**
     * Constructor por defecto.
     */
    public QueryDefinition() {
        this.fields = new ArrayList<>();
        this.filters = new ArrayList<>();
        this.sorts = new ArrayList<>();
    }

    /**
     * Constructor completo.
     *
     * @param entityCode Código único de la entidad origen.
     * @param fields     Lista de campos a retornar.
     * @param filters    Lista de filtros o condiciones de búsqueda.
     * @param sorts      Lista de criterios de ordenamiento.
     * @param page       Número de página (basado en índice 0).
     * @param pageSize   Tasa de elementos por página.
     */
    public QueryDefinition(String entityCode, List<String> fields, List<QueryFilter> filters,
                           List<QuerySort> sorts, Integer page, Integer pageSize) {
        this.entityCode = entityCode;
        this.fields = fields != null ? fields : new ArrayList<>();
        this.filters = filters != null ? filters : new ArrayList<>();
        this.sorts = sorts != null ? sorts : new ArrayList<>();
        this.page = page;
        this.pageSize = pageSize;
    }

    /**
     * Obtiene el código de la entidad origen.
     *
     * @return Código de la entidad.
     */
    public String getEntityCode() {
        return entityCode;
    }

    /**
     * Establece el código de la entidad origen.
     *
     * @param entityCode Código de la entidad.
     */
    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    /**
     * Obtiene la lista de campos a proyectar o retornar.
     *
     * @return Lista de nombres de campos.
     */
    public List<String> getFields() {
        return fields;
    }

    /**
     * Establece la lista de campos a proyectar o retornar.
     *
     * @param fields Lista de nombres de campos.
     */
    public void setFields(List<String> fields) {
        this.fields = fields != null ? fields : new ArrayList<>();
    }

    /**
     * Obtiene los filtros aplicados a la consulta.
     *
     * @return Lista de {@link QueryFilter}.
     */
    public List<QueryFilter> getFilters() {
        return filters;
    }

    /**
     * Establece los filtros aplicados a la consulta.
     *
     * @param filters Lista de {@link QueryFilter}.
     */
    public void setFilters(List<QueryFilter> filters) {
        this.filters = filters != null ? filters : new ArrayList<>();
    }

    /**
     * Obtiene los criterios de ordenamiento.
     *
     * @return Lista de {@link QuerySort}.
     */
    public List<QuerySort> getSorts() {
        return sorts;
    }

    /**
     * Establece los criterios de ordenamiento.
     *
     * @param sorts Lista de {@link QuerySort}.
     */
    public void setSorts(List<QuerySort> sorts) {
        this.sorts = sorts != null ? sorts : new ArrayList<>();
    }

    /**
     * Obtiene el número de página solicitado.
     *
     * @return Índice de página.
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Establece el número de página solicitado.
     *
     * @param page Índice de página.
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * Obtiene el tamaño de la página.
     *
     * @return Cantidad de registros por página.
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Establece el tamaño de la página.
     *
     * @param pageSize Cantidad de registros por página.
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}