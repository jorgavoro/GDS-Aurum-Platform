package com.globaldynamicssystems.aurum.accounting.service.rules;

import com.globaldynamicssystems.aurum.accounting.model.AccountingRule;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleContext;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleResult;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleSeverity;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleStatus;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleType;
import com.globaldynamicssystems.aurum.accounting.service.AccountingRuleEvaluator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AmountLimitWarningRule implements AccountingRuleEvaluator {

    private static final BigDecimal DEFAULT_LIMIT = new BigDecimal("1000000.00");

    private static final AccountingRule RULE = new AccountingRule(
            "RULE-009",
            "Amount Limit Warning",
            "Amount exceeds the configured limit and requires review.",
            AccountingRuleType.AMOUNT,
            AccountingRuleSeverity.WARNING,
            AccountingRuleStatus.ACTIVE,
            90);

    private final BigDecimal amountLimit;

    public AmountLimitWarningRule() {
        this.amountLimit = DEFAULT_LIMIT;
    }

    public AmountLimitWarningRule(BigDecimal amountLimit) {
        this.amountLimit = amountLimit != null ? amountLimit : DEFAULT_LIMIT;
    }

    @Override
    public String getRuleCode() { return RULE.getCode(); }

    @Override
    public AccountingRuleType getRuleType() { return RULE.getType(); }

    @Override
    public AccountingRule getRule() { return RULE; }

    @Override
    public AccountingRuleResult evaluate(AccountingRuleContext context) {
        BigDecimal amount = context.getAmount();
        if (amount == null) {
            return pass();
        }
        if (amount.compareTo(amountLimit) > 0) {
            return warn("Amount " + amount + " exceeds the configured limit of "
                    + amountLimit + " and requires review.", "amount");
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
