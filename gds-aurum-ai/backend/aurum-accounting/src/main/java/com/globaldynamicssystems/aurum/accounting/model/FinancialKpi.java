package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;

public class FinancialKpi {

    private FinancialKpiType type;
    private String code;
    private String name;
    private BigDecimal value;
    private String unit;
    private String category;
    private String description;

    public FinancialKpi() {
    }

    public FinancialKpi(FinancialKpiType type, String code, String name, BigDecimal value,
                        String unit, String category, String description) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.category = category;
        this.description = description;
    }

    public FinancialKpiType getType() {
        return type;
    }

    public void setType(FinancialKpiType type) {
        this.type = type;
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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}