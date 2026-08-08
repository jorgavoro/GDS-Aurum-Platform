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
 * Representa la definición de una relación entre dos entidades del
 * modelo de metadatos del ERP.
 */
@Entity
@Table(name = "gds_relationship_definition")
public class RelationshipDefinition extends AuditableEntity {

    /**
     * Nombre de la relación.
     */
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    /**
     * Tipo de relación.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "relationship_type", nullable = false, length = 50)
    private RelationshipType relationshipType;

    /**
     * Nombre del atributo propietario cuando la relación es bidireccional.
     */
    @Column(name = "mapped_by", length = 100)
    private String mappedBy;

    /**
     * Indica si la persistencia se propaga en cascada.
     */
    @Column(name = "is_cascade", nullable = false)
    private Boolean cascade;

    /**
     * Indica si se eliminan automáticamente los registros huérfanos.
     */
    @Column(name = "is_orphan_removal", nullable = false)
    private Boolean orphanRemoval;

    /**
     * Estrategia de carga de la relación.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "fetch_type", nullable = false, length = 20)
    private FetchType fetchType;

    /**
     * Entidad origen.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_entity_id", nullable = false)
    private EntityDefinition sourceEntity;

    /**
     * Entidad destino.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_entity_id", nullable = false)
    private EntityDefinition targetEntity;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public RelationshipDefinition() {
    }

    /**
     * Constructor completo.
     */
    public RelationshipDefinition(
            String name,
            RelationshipType relationshipType,
            String mappedBy,
            Boolean cascade,
            Boolean orphanRemoval,
            FetchType fetchType,
            EntityDefinition sourceEntity,
            EntityDefinition targetEntity) {

        this.name = name;
        this.relationshipType = relationshipType;
        this.mappedBy = mappedBy;
        this.cascade = cascade;
        this.orphanRemoval = orphanRemoval;
        this.fetchType = fetchType;
        this.sourceEntity = sourceEntity;
        this.targetEntity = targetEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getMappedBy() {
        return mappedBy;
    }

    public void setMappedBy(String mappedBy) {
        this.mappedBy = mappedBy;
    }

    public Boolean getCascade() {
        return cascade;
    }

    public void setCascade(Boolean cascade) {
        this.cascade = cascade;
    }

    public Boolean getOrphanRemoval() {
        return orphanRemoval;
    }

    public void setOrphanRemoval(Boolean orphanRemoval) {
        this.orphanRemoval = orphanRemoval;
    }

    public FetchType getFetchType() {
        return fetchType;
    }

    public void setFetchType(FetchType fetchType) {
        this.fetchType = fetchType;
    }

    public EntityDefinition getSourceEntity() {
        return sourceEntity;
    }

    public void setSourceEntity(EntityDefinition sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

    public EntityDefinition getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(EntityDefinition targetEntity) {
        this.targetEntity = targetEntity;
    }

}