package com.globaldynamicssystems.aurum.accounting.model;

public class DataQualityFinding {

    private String ruleCode;
    private DataQualityRuleType ruleType;
    private DataQualitySeverity severity;
    private String entityType;
    private Long entityId;
    private String fieldName;
    private String message;

    public DataQualityFinding() {
    }

    public DataQualityFinding(String ruleCode,
                               DataQualityRuleType ruleType,
                               DataQualitySeverity severity,
                               String entityType,
                               Long entityId,
                               String fieldName,
                               String message) {
        this.ruleCode = ruleCode;
        this.ruleType = ruleType;
        this.severity = severity;
        this.entityType = entityType;
        this.entityId = entityId;
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getRuleCode() { return ruleCode; }
    public void setRuleCode(String ruleCode) { this.ruleCode = ruleCode; }

    public DataQualityRuleType getRuleType() { return ruleType; }
    public void setRuleType(DataQualityRuleType ruleType) { this.ruleType = ruleType; }

    public DataQualitySeverity getSeverity() { return severity; }
    public void setSeverity(DataQualitySeverity severity) { this.severity = severity; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }

    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
