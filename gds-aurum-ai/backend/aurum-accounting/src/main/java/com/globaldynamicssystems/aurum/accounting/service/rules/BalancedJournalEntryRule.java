package com.globaldynamicssystems.aurum.accounting.service.rules;

import com.globaldynamicssystems.aurum.accounting.model.AccountingRule;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleContext;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleResult;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleSeverity;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleStatus;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleType;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntry;
import com.globaldynamicssystems.aurum.accounting.model.JournalEntryLine;
import com.globaldynamicssystems.aurum.accounting.service.AccountingRuleEvaluator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BalancedJournalEntryRule implements AccountingRuleEvaluator {

    private static final AccountingRule RULE = new AccountingRule(
            "RULE-005",
            "Journal Entry Must Be Balanced",
            "Total debit must equal total credit.",
            AccountingRuleType.JOURNAL_ENTRY,
            AccountingRuleSeverity.ERROR,
            AccountingRuleStatus.ACTIVE,
            50);

    @Override
    public String getRuleCode() { return RULE.getCode(); }

    @Override
    public AccountingRuleType getRuleType() { return RULE.getType(); }

    @Override
    public AccountingRule getRule() { return RULE; }

    @Override
    public AccountingRuleResult evaluate(AccountingRuleContext context) {
        JournalEntry journalEntry = context.getJournalEntry();
        if (journalEntry == null || journalEntry.getLines() == null
                || journalEntry.getLines().isEmpty()) {
            return pass();
        }

        List<JournalEntryLine> lines = journalEntry.getLines();
        BigDecimal totalDebit = BigDecimal.ZERO;
        BigDecimal totalCredit = BigDecimal.ZERO;

        for (JournalEntryLine line : lines) {
            totalDebit = totalDebit.add(line.getDebit() != null ? line.getDebit() : BigDecimal.ZERO);
            totalCredit = totalCredit.add(line.getCredit() != null ? line.getCredit() : BigDecimal.ZERO);
        }

        if (totalDebit.compareTo(totalCredit) != 0) {
            return fail("JournalEntry " + journalEntry.getId()
                    + " is not balanced. Debit=" + totalDebit + " Credit=" + totalCredit + ".",
                    "lines");
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
