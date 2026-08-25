package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import com.globaldynamicssystems.aurum.accounting.model.AccountType;
import com.globaldynamicssystems.aurum.accounting.model.CashFlowActivityType;
import com.globaldynamicssystems.aurum.accounting.service.CashFlowClassificationService;
import org.springframework.stereotype.Service;

@Service
public class DefaultCashFlowClassificationService implements CashFlowClassificationService {

    @Override
    public CashFlowActivityType classify(Account account) {
        if (account == null || account.getAccountType() == null) {
            return CashFlowActivityType.OPERATING;
        }

        AccountType type = account.getAccountType();

        if (AccountType.REVENUE.equals(type) || AccountType.EXPENSE.equals(type)
                || AccountType.OTHER.equals(type)) {
            return CashFlowActivityType.OPERATING;
        }

        if (AccountType.ASSET.equals(type)) {
            return CashFlowActivityType.INVESTING;
        }

        if (AccountType.LIABILITY.equals(type) || AccountType.EQUITY.equals(type)) {
            return CashFlowActivityType.FINANCING;
        }

        return CashFlowActivityType.OPERATING;
    }
}
