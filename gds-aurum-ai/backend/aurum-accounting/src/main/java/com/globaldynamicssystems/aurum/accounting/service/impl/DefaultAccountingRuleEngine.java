package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleContext;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleResult;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleStatus;
import com.globaldynamicssystems.aurum.accounting.service.AccountingRuleEngine;
import com.globaldynamicssystems.aurum.accounting.service.AccountingRuleEvaluator;
import com.globaldynamicssystems.aurum.accounting.service.AccountingRuleRegistry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DefaultAccountingRuleEngine implements AccountingRuleEngine {

    private final AccountingRuleRegistry registry;

    public DefaultAccountingRuleEngine(AccountingRuleRegistry registry) {
        this.registry = registry;
    }

    @Override
    public List<AccountingRuleResult> evaluate(AccountingRuleContext context) {
        return evaluate(context, null);
    }

    @Override
    public List<AccountingRuleResult> evaluate(AccountingRuleContext context, List<String> ruleCodes) {
        if (context == null) {
            throw new IllegalArgumentException("AccountingRuleContext cannot be null");
        }

        List<AccountingRuleEvaluator> candidates = registry.findAll().stream()
                .filter(e -> AccountingRuleStatus.ACTIVE.equals(e.getRule().getStatus()))
                .filter(e -> ruleCodes == null || ruleCodes.isEmpty()
                        || ruleCodes.contains(e.getRuleCode()))
                .sorted(Comparator.comparingInt(e -> e.getRule().getPriority()))
                .toList();

        List<AccountingRuleResult> results = new ArrayList<>();
        for (AccountingRuleEvaluator evaluator : candidates) {
            AccountingRuleResult result = evaluator.evaluate(context);
            if (result != null) {
                results.add(result);
            }
        }
        return results;
    }
}
