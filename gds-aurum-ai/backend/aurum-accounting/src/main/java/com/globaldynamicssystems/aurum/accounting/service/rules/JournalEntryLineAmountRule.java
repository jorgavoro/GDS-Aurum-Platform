package com.globaldynamicssystems.aurum.accounting.service.rules;

import com.globaldynamicssystems.aurum.accounting.model.AccountingRule;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleContext;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleResult;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleSeverity;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleStatus;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleType;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLine;
import com.globaldynamicssystems.aurum.accounting.service.AccountingRuleEvaluator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JournalEntryLineAmountRule implements AccountingRuleEvaluator {

    private static final AccountingRule RULE = new AccountingRule(
            "RULE-007",
            "Line Must Have Amount",
            "JournalEntryLine must have debit > 0 or credit > 0.",
            AccountingRuleType.JOURNAL_ENTRY_LINE,
            AccountingRuleSeverity.ERROR,
            AccountingRuleStatus.ACTIVE,
            70);

    @Override
    public String getRuleCode() { return RULE.getCode(); }

    @Override
    public AccountingRuleType getRuleType() { return RULE.getType(); }

    @Override
    public AccountingRule getRule() { return RULE; }

    @Override
    public AccountingRuleResult evaluate(AccountingRuleContext context) {
        JournalEntryLine line = context.getJournalEntryLine();
        if (line == null) {
            return pass();
        }
        BigDecimal debit = line.getDebit() != null ? line.getDebit() : BigDecimal.ZERO;
        BigDecimal credit = line.getCredit() != null ? line.getCredit() : BigDecimal.ZERO;

        if (debit.compareTo(BigDecimal.ZERO) <= 0 && credit.compareTo(BigDecimal.ZERO) <= 0) {
            return fail("JournalEntryLine " + line.getId()
                    + " has no amount. Debit and Credit are both zero or negative.", "debit");
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
