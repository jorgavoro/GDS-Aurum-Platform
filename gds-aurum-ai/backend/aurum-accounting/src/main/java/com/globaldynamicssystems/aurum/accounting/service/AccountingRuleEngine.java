package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleContext;
import com.globaldynamicssystems.aurum.accounting.model.AccountingRuleResult;

import java.util.List;

public interface AccountingRuleEngine {

    List<AccountingRuleResult> evaluate(AccountingRuleContext context);

    List<AccountingRuleResult> evaluate(AccountingRuleContext context, List<String> ruleCodes);
}
