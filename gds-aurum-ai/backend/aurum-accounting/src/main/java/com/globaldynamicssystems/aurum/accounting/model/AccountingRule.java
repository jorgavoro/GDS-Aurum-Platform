package com.globaldynamicssystems.aurum.accounting.model;

public class AccountingRule {

    private String code;
    private String name;
    private String description;
    private AccountingRuleType type;
    private AccountingRuleSeverity severity;
    private AccountingRuleStatus status;
    private Integer priority;

    public AccountingRule() {
        this.priority = 100;
    }

    public AccountingRule(String code,
                          String name,
                          String description,
                          AccountingRuleType type,
                          AccountingRuleSeverity severity,
                          AccountingRuleStatus status,
                          Integer priority) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.type = type;
        this.severity = severity;
        this.status = status;
        this.priority = priority != null ? priority : 100;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public AccountingRuleType getType() { return type; }
    public void setType(AccountingRuleType type) { this.type = type; }

    public AccountingRuleSeverity getSeverity() { return severity; }
    public void setSeverity(AccountingRuleSeverity severity) { this.severity = severity; }

    public AccountingRuleStatus getStatus() { return status; }
    public void setStatus(AccountingRuleStatus status) { this.status = status; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority != null ? priority : 100; }
}
