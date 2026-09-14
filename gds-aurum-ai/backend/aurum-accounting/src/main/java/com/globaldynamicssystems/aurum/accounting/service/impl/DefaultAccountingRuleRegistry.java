package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.service.AccountingRuleRegistry;
import com.globaldynamicssystems.aurum.accounting.service.rules.ActiveAccountRule;
import com.globaldynamicssystems.aurum.accounting.service.rules.ActiveDimensionRule;
import com.globaldynamicssystems.aurum.accounting.service.rules.AmountLimitWarningRule;
import com.globaldynamicssystems.aurum.accounting.service.rules.BalancedJournalEntryRule;
import com.globaldynamicssystems.aurum.accounting.service.rules.JournalEntryLineAccountRule;
import com.globaldynamicssystems.aurum.accounting.service.rules.JournalEntryLineAmountRule;
import com.globaldynamicssystems.aurum.accounting.service.rules.JournalEntryLineBothSidesRule;
import com.globaldynamicssystems.aurum.accounting.service.rules.OpenFiscalPeriodRule;
import org.springframework.stereotype.Component;

@Component
public class DefaultAccountingRuleRegistry {

    public DefaultAccountingRuleRegistry(AccountingRuleRegistry registry,
                                         ActiveAccountRule activeAccountRule,
                                         OpenFiscalPeriodRule openFiscalPeriodRule,
                                         BalancedJournalEntryRule balancedJournalEntryRule,
                                         JournalEntryLineAccountRule journalEntryLineAccountRule,
                                         JournalEntryLineAmountRule journalEntryLineAmountRule,
                                         JournalEntryLineBothSidesRule journalEntryLineBothSidesRule,
                                         AmountLimitWarningRule amountLimitWarningRule,
                                         ActiveDimensionRule activeDimensionRule) {
        registry.register(activeAccountRule);
        registry.register(openFiscalPeriodRule);
        registry.register(balancedJournalEntryRule);
        registry.register(journalEntryLineAccountRule);
        registry.register(journalEntryLineAmountRule);
        registry.register(journalEntryLineBothSidesRule);
        registry.register(amountLimitWarningRule);
        registry.register(activeDimensionRule);
    }
}
