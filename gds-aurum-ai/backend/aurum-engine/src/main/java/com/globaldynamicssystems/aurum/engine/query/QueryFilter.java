package com.globaldynamicssystems.aurum.engine.query;

/**
 * Representa una condición o filtro de búsqueda dentro de una consulta dinámica.
 */
public class QueryFilter {

    private String field;
    private QueryOperator operator;
    private Object value;

    /**
     * Constructor por defecto.
     */
    public QueryFilter() {
    }

    /**
     * Constructor completo.
     *
     * @param field    Nombre del campo objetivo.
     * @param operator Operador lógico/relacional a aplicar.
     * @param value    Valor o estructura de valores a evaluar.
     */
    public QueryFilter(String field, QueryOperator operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    /**
     * Obtiene el nombre del campo evaluado.
     *
     * @return Nombre del campo.
     */
    public String getField() {
        return field;
    }

    /**
     * Establece el nombre del campo evaluado.
     *
     * @param field Nombre del campo.
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Obtiene el operador de filtrado.
     *
     * @return Operador {@link QueryOperator}.
     */
    public QueryOperator getOperator() {
        return operator;
    }

    /**
     * Establece el operador de filtrado.
     *
     * @param operator Operador {@link QueryOperator}.
     */
    public void setOperator(QueryOperator operator) {
        this.operator = operator;
    }

    /**
     * Obtiene el valor de comparación.
     *
     * @return Objeto con el valor del filtro.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Establece el valor de comparación.
     *
     * @param value Objeto con el valor del filtro.
     */
    public void setValue(Object value) {
        this.value = value;
    }
}