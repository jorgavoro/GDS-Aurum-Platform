package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.CashFlowActivityType;

public interface CashFlowClassificationService {

    CashFlowActivityType classify(Account account);
}
