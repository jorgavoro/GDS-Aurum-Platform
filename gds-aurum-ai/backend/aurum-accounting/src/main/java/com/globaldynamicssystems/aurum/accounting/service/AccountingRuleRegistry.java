package com.globaldynamicssystems.aurum.accounting.service;

import java.util.List;
import java.util.Optional;

public interface AccountingRuleRegistry {

    void register(AccountingRuleEvaluator evaluator);

    Optional<AccountingRuleEvaluator> findByCode(String ruleCode);

    List<AccountingRuleEvaluator> findAll();
}
