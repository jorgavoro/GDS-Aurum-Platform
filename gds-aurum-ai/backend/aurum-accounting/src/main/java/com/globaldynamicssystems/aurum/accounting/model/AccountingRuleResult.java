package com.globaldynamicssystems.aurum.accounting.model;

public class AccountingRuleResult {

    private String ruleCode;
    private AccountingRuleSeverity severity;
    private Boolean passed;
    private String message;
    private String fieldName;

    public AccountingRuleResult() {
    }

    public AccountingRuleResult(String ruleCode,
                                AccountingRuleSeverity severity,
                                Boolean passed,
                                String message,
                                String fieldName) {
        this.ruleCode = ruleCode;
        this.severity = severity;
        this.passed = passed;
        this.message = message;
        this.fieldName = fieldName;
    }

    public String getRuleCode() { return ruleCode; }
    public void setRuleCode(String ruleCode) { this.ruleCode = ruleCode; }

    public AccountingRuleSeverity getSeverity() { return severity; }
    public void setSeverity(AccountingRuleSeverity severity) { this.severity = severity; }

    public Boolean getPassed() { return passed; }
    public void setPassed(Boolean passed) { this.passed = passed; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }
}
