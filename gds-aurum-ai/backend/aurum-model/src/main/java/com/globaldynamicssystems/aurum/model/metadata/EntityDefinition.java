package com.globaldynamicssystems.aurum.model.metadata;

import com.globaldynamicssystems.aurum.framework.entity.AuditableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

/**
 * Representa la definición estructural de una entidad del ERP dentro del motor de metadatos.
 * Permite modelar dinámicamente los módulos de negocio sin requerir código estático repetitivo.
 */
@Entity
@Table(name = "gds_entity_definition")
public class EntityDefinition extends AuditableEntity {

    /**
     * Código único identificador de la entidad.
     */
    @Column(name = "code", nullable = false, unique = true, length = 100)
    private String code;

    /**
     * Nombre descriptivo funcional.
     */
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    /**
     * Descripción funcional.
     */
    @Column(name = "description", length = 500)
    private String description;

    /**
     * Nombre de la tabla física.
     */
    @Column(name = "table_name", nullable = false, length = 100)
    private String tableName;

    /**
     * Esquema de la base de datos.
     */
    @Column(name = "schema_name", length = 100)
    private String schemaName;

    /**
     * Indica si la entidad es auditable.
     */
    @Column(name = "is_auditable", nullable = false)
    private Boolean auditable;

    /**
     * Indica si la entidad puede cachearse.
     */
    @Column(name = "is_cacheable", nullable = false)
    private Boolean cacheable;

    /**
     * Indica si la entidad es multi-tenant.
     */
    @Column(name = "is_tenant_aware", nullable = false)
    private Boolean tenantAware;

    /**
     * Indica si la entidad es multi-company.
     */
    @Column(name = "is_company_aware", nullable = false)
    private Boolean companyAware;

    /**
     * Campos pertenecientes a la entidad.
     */
    @OneToMany(
            mappedBy = "entity",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<FieldDefinition> fields;

    /**
     * Constructor por defecto.
     */
    public EntityDefinition() {
    }

    /**
     * Constructor completo.
     */
    public EntityDefinition(String code,
                            String name,
                            String description,
                            String tableName,
                            String schemaName,
                            Boolean auditable,
                            Boolean cacheable,
                            Boolean tenantAware,
                            Boolean companyAware,
                            List<FieldDefinition> fields) {

        this.code = code;
        this.name = name;
        this.description = description;
        this.tableName = tableName;
        this.schemaName = schemaName;
        this.auditable = auditable;
        this.cacheable = cacheable;
        this.tenantAware = tenantAware;
        this.companyAware = companyAware;
        this.fields = fields;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public Boolean getAuditable() {
        return auditable;
    }

    public void setAuditable(Boolean auditable) {
        this.auditable = auditable;
    }

    public Boolean getCacheable() {
        return cacheable;
    }

    public void setCacheable(Boolean cacheable) {
        this.cacheable = cacheable;
    }

    public Boolean getTenantAware() {
        return tenantAware;
    }

    public void setTenantAware(Boolean tenantAware) {
        this.tenantAware = tenantAware;
    }

    public Boolean getCompanyAware() {
        return companyAware;
    }

    public void setCompanyAware(Boolean companyAware) {
        this.companyAware = companyAware;
    }

    public List<FieldDefinition> getFields() {
        return fields;
    }

    public void setFields(List<FieldDefinition> fields) {
        this.fields = fields;
    }

}