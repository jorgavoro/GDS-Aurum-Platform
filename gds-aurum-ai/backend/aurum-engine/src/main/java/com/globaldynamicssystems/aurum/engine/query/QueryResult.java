package com.globaldynamicssystems.aurum.engine.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Estructura genérica que encapsula el resultado paginado de una consulta dinámica.
 *
 * @param <T> Tipo de los datos contenidos en el resultado.
 */
public class QueryResult<T> {

    private List<T> content;
    private long totalElements;
    private int page;
    private int pageSize;

    /**
     * Constructor por defecto.
     */
    public QueryResult() {
        this.content = new ArrayList<>();
    }

    /**
     * Constructor completo.
     *
     * @param content       Lista de registros o elementos obtenidos.
     * @param totalElements Total general de elementos que satisfacen la consulta.
     * @param page          Número de página actual.
     * @param pageSize      Tamaño de la página solicitada.
     */
    public QueryResult(List<T> content, long totalElements, int page, int pageSize) {
        this.content = content != null ? content : new ArrayList<>();
        this.totalElements = totalElements;
        this.page = page;
        this.pageSize = pageSize;
    }

    /**
     * Determina si el resultado actual no contiene elementos.
     *
     * @return {@code true} si la lista de contenido es nula o está vacía; {@code false} en caso contrario.
     */
    public boolean isEmpty() {
        return content == null || content.isEmpty();
    }

    /**
     * Obtiene el contenido de la página actual.
     *
     * @return Lista de elementos de tipo {@code T}.
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * Establece el contenido de la página actual.
     *
     * @param content Lista de elementos.
     */
    public void setContent(List<T> content) {
        this.content = content != null ? content : new ArrayList<>();
    }

    /**
     * Obtiene el número total de elementos existentes en la consulta global.
     *
     * @return Cantidad total de elementos.
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Establece el número total de elementos existentes.
     *
     * @param totalElements Cantidad total de elementos.
     */
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * Obtiene el índice de la página actual.
     *
     * @return Número de página.
     */
    public int getPage() {
        return page;
    }

    /**
     * Establece el índice de la página actual.
     *
     * @param page Número de página.
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Obtiene la cantidad de registros por página.
     *
     * @return Tamañode página.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Establece la cantidad de registros por página.
     *
     * @param pageSize Tamaño de página.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}