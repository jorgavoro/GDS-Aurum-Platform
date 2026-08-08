package com.globaldynamicssystems.aurum.model.metadata;

import com.globaldynamicssystems.aurum.framework.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Representa la definición detallada de un campo perteneciente a una entidad
 * del modelo de metadatos del ERP.
 */
@Entity
@Table(name = "gds_field_definition")
public class FieldDefinition extends AuditableEntity {

    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "label", nullable = false, length = 150)
    private String label;

    @Column(name = "database_column", nullable = false, length = 100)
    private String databaseColumn;

    @Enumerated(EnumType.STRING)
    @Column(name = "field_type", nullable = false, length = 50)
    private FieldType fieldType;

    @Column(name = "length")
    private Integer length;

    @Column(name = "precision")
    private Integer precision;

    @Column(name = "scale")
    private Integer scale;

    @Column(name = "is_required", nullable = false)
    private Boolean required;

    @Column(name = "is_unique", nullable = false)
    private Boolean unique;

    @Column(name = "is_searchable", nullable = false)
    private Boolean searchable;

    @Column(name = "is_filterable", nullable = false)
    private Boolean filterable;

    @Column(name = "is_sortable", nullable = false)
    private Boolean sortable;

    @Column(name = "is_visible", nullable = false)
    private Boolean visible;

    @Column(name = "is_editable", nullable = false)
    private Boolean editable;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_definition_id", nullable = false)
    private EntityDefinition entity;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public FieldDefinition() {
    }

    /**
     * Constructor completo.
     */
    public FieldDefinition(
            String code,
            String name,
            String label,
            String databaseColumn,
            FieldType fieldType,
            Integer length,
            Integer precision,
            Integer scale,
            Boolean required,
            Boolean unique,
            Boolean searchable,
            Boolean filterable,
            Boolean sortable,
            Boolean visible,
            Boolean editable,
            Integer displayOrder,
            EntityDefinition entity) {

        this.code = code;
        this.name = name;
        this.label = label;
        this.databaseColumn = databaseColumn;
        this.fieldType = fieldType;
        this.length = length;
        this.precision = precision;
        this.scale = scale;
        this.required = required;
        this.unique = unique;
        this.searchable = searchable;
        this.filterable = filterable;
        this.sortable = sortable;
        this.visible = visible;
        this.editable = editable;
        this.displayOrder = displayOrder;
        this.entity = entity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDatabaseColumn() {
        return databaseColumn;
    }

    public void setDatabaseColumn(String databaseColumn) {
        this.databaseColumn = databaseColumn;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public Boolean getFilterable() {
        return filterable;
    }

    public void setFilterable(Boolean filterable) {
        this.filterable = filterable;
    }

    public Boolean getSortable() {
        return sortable;
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public EntityDefinition getEntity() {
        return entity;
    }

    public void setEntity(EntityDefinition entity) {
        this.entity = entity;
    }

}