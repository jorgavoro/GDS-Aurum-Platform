package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AccountingRule;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleContext;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleResult;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleType;

public interface AccountingRuleEvaluator {

    String getRuleCode();

    AccountingRuleType getRuleType();

    AccountingRule getRule();

    AccountingRuleResult evaluate(AccountingRuleContext context);
}
