package com.globaldynamicssystems.aurum.accounting.service.rules;

import com.globaldynamicssystems.aurum.accounting.model.AccountingRule;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleContext;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleResult;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleSeverity;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleStatus;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleType;
import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriodStatus;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;
import com.globaldynamicssystems.aurum.accounting.service.AccountingRuleEvaluator;
import org.springframework.stereotype.Component;

@Component
public class OpenFiscalPeriodRule implements AccountingRuleEvaluator {

    private static final AccountingRule RULE = new AccountingRule(
            "RULE-002",
            "Fiscal Period Must Be Open",
            "Posting requires an OPEN fiscal period.",
            AccountingRuleType.PERIOD,
            AccountingRuleSeverity.ERROR,
            AccountingRuleStatus.ACTIVE,
            20);

    @Override
    public String getRuleCode() { return RULE.getCode(); }

    @Override
    public AccountingRuleType getRuleType() { return RULE.getType(); }

    @Override
    public AccountingRule getRule() { return RULE; }

    @Override
    public AccountingRuleResult evaluate(AccountingRuleContext context) {
        JournalEntry journalEntry = context.getJournalEntry();
        if (journalEntry == null || journalEntry.getFiscalPeriod() == null) {
            return pass();
        }
        if (!FiscalPeriodStatus.OPEN.equals(journalEntry.getFiscalPeriod().getStatus())) {
            return fail("FiscalPeriod is closed and cannot receive postings.", "fiscalPeriod");
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
