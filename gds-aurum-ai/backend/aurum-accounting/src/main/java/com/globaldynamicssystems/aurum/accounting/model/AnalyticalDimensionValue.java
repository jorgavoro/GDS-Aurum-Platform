package com.globaldynamicssystems.aurum.accounting.model;

import com.globaldynamicssystems.aurum.framework.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gds_analytical_dimension_value")
public class AnalyticalDimensionValue extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dimension_type", nullable = false)
    private AnalyticalDimensionType dimensionType;

    @Column(name = "reference_id", nullable = false)
    private Long referenceId;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    private Boolean active;

    public AnalyticalDimensionValue() {
    }

    public AnalyticalDimensionValue(Long id, AnalyticalDimensionType dimensionType, Long referenceId,
                                    String code, String name, Boolean active) {
        this.id = id;
        this.dimensionType = dimensionType;
        this.referenceId = referenceId;
        this.code = code;
        this.name = name;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnalyticalDimensionType getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(AnalyticalDimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
