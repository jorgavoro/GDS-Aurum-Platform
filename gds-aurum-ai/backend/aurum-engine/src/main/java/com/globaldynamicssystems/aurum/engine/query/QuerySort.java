package com.globaldynamicssystems.aurum.engine.query;

/**
 * Representa la instrucción de ordenamiento para un campo específico en una consulta dinámica.
 */
public class QuerySort {

    private String field;
    private SortDirection direction;

    /**
     * Constructor por defecto.
     */
    public QuerySort() {
    }

    /**
     * Constructor completo.
     *
     * @param field     Nombre del campo sobre el cual se aplica el orden.
     * @param direction Dirección del ordenamiento (ASC o DESC).
     */
    public QuerySort(String field, SortDirection direction) {
        this.field = field;
        this.direction = direction;
    }

    /**
     * Obtiene el nombre del campo de ordenamiento.
     *
     * @return Nombre del campo.
     */
    public String getField() {
        return field;
    }

    /**
     * Establece el nombre del campo de ordenamiento.
     *
     * @param field Nombre del campo.
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Obtiene la dirección del ordenamiento.
     *
     * @return Dirección {@link SortDirection}.
     */
    public SortDirection getDirection() {
        return direction;
    }

    /**
     * Establece la dirección del ordenamiento.
     *
     * @param direction Dirección {@link SortDirection}.
     */
    public void setDirection(SortDirection direction) {
        this.direction = direction;
    }
}