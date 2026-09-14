package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.service.AccountingRuleEvaluator;
import com.globaldynamicssystems.aurum.accounting.service.AccountingRuleRegistry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryAccountingRuleRegistry implements AccountingRuleRegistry {

    private final ConcurrentHashMap<String, AccountingRuleEvaluator> rules = new ConcurrentHashMap<>();

    @Override
    public void register(AccountingRuleEvaluator evaluator) {
        if (evaluator == null || evaluator.getRuleCode() == null) {
            throw new IllegalArgumentException("Evaluator and its ruleCode cannot be null");
        }
        rules.put(evaluator.getRuleCode(), evaluator);
    }

    @Override
    public Optional<AccountingRuleEvaluator> findByCode(String ruleCode) {
        if (ruleCode == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(rules.get(ruleCode));
    }

    @Override
    public List<AccountingRuleEvaluator> findAll() {
        return new ArrayList<>(rules.values());
    }
}
