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
public class JournalEntryLineBothSidesRule implements AccountingRuleEvaluator {

    private static final AccountingRule RULE = new AccountingRule(
            "RULE-008",
            "Line Cannot Have Both Debit And Credit",
            "A JournalEntryLine cannot have debit > 0 and credit > 0 simultaneously.",
            AccountingRuleType.JOURNAL_ENTRY_LINE,
            AccountingRuleSeverity.ERROR,
            AccountingRuleStatus.ACTIVE,
            80);

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

        if (debit.compareTo(BigDecimal.ZERO) > 0 && credit.compareTo(BigDecimal.ZERO) > 0) {
            return fail("JournalEntryLine " + line.getId()
                    + " has both Debit=" + debit + " and Credit=" + credit
                    + ". Only one side is allowed.", "debit");
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
