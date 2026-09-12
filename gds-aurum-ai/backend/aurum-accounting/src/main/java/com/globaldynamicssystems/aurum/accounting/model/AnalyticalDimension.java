package com.globaldynamicssystems.aurum.accounting.model;

public class AnalyticalDimension {

    private AnalyticalDimensionType type;
    private Long dimensionId;
    private String code;
    private String name;

    public AnalyticalDimension() {
    }

    public AnalyticalDimension(AnalyticalDimensionType type, Long dimensionId, String code, String name) {
        this.type = type;
        this.dimensionId = dimensionId;
        this.code = code;
        this.name = name;
    }

    public AnalyticalDimensionType getType() {
        return type;
    }

    public void setType(AnalyticalDimensionType type) {
        this.type = type;
    }

    public Long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(Long dimensionId) {
        this.dimensionId = dimensionId;
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
}