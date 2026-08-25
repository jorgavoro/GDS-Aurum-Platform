package com.globaldynamicssystems.aurum.accounting.service.rules;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRule;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleContext;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleResult;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleSeverity;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleStatus;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleType;
import com.globaldynamicssystems.aurum.accounting.service.AccountingRuleEvaluator;
import org.springframework.stereotype.Component;

@Component
public class ActiveAccountRule implements AccountingRuleEvaluator {

    private static final AccountingRule RULE = new AccountingRule(
            "RULE-001",
            "Account Must Be Active",
            "No posting is allowed on an inactive account.",
            AccountingRuleType.ACCOUNT,
            AccountingRuleSeverity.ERROR,
            AccountingRuleStatus.ACTIVE,
            10);

    @Override
    public String getRuleCode() { return RULE.getCode(); }

    @Override
    public AccountingRuleType getRuleType() { return RULE.getType(); }

    @Override
    public AccountingRule getRule() { return RULE; }

    @Override
    public AccountingRuleResult evaluate(AccountingRuleContext context) {
        Account account = context.getAccount();
        if (account == null) {
            return pass();
        }
        if (!Boolean.TRUE.equals(account.getActive())) {
            return fail("Account " + account.getCode()
                    + " is inactive and cannot be used for posting.", "account");
        }
        return pass();
    }

    private AccountingRuleResult pass() {
        return new AccountingRuleResult(RULE.getCode(), RULE.getSeverity(), true, null, null);
    }

    private AccountingRuleResult fail(String message, String fieldName) {
        return new AccountingRuleResult(RULE.getCode(), RULE.getSeverity(), false, message, fieldName);
    }
}
