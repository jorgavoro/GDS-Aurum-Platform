package com.globaldynamicssystems.aurum.accounting.service.rules;

import com.globaldynamicssystems.aurum.accounting.model.AccountingRule;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleContext;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleResult;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleSeverity;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleStatus;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleType;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionValue;
import com.globaldynamicssystems.aurum.accounting.service.AccountingRuleEvaluator;
import org.springframework.stereotype.Component;

@Component
public class ActiveDimensionRule implements AccountingRuleEvaluator {

    private static final AccountingRule RULE = new AccountingRule(
            "RULE-010",
            "Dimension Must Be Active",
            "An inactive analytical dimension should not be used in new operations.",
            AccountingRuleType.DIMENSION,
            AccountingRuleSeverity.WARNING,
            AccountingRuleStatus.ACTIVE,
            100);

    @Override
    public String getRuleCode() { return RULE.getCode(); }

    @Override
    public AccountingRuleType getRuleType() { return RULE.getType(); }

    @Override
    public AccountingRule getRule() { return RULE; }

    @Override
    public AccountingRuleResult evaluate(AccountingRuleContext context) {
        AnalyticalDimensionValue dimension = context.getDimensionValue();
        if (dimension == null) {
            return pass();
        }
        if (!Boolean.TRUE.equals(dimension.getActive())) {
            return warn("Dimension " + dimension.getCode() + " (" + dimension.getDimensionType()
                    + ") is inactive and should not be used in new operations.", "dimensionValue");
        }
        return pass();
    }

    private AccountingRuleResult pass() {
        return new AccountingRuleResult(RULE.getCode(), RULE.getSeverity(), true, null, null);
    }

    private AccountingRuleResult warn(String message, String fieldName) {
        return new AccountingRuleResult(RULE.getCode(), RULE.getSeverity(), false, message, fieldName);
    }
}
