package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.exception.AccountingPeriodValidationException;
import com.globaldynamicssystems.aurum.accounting.model.AccountingOperationType;
import com.globaldynamicssystems.aurum.accounting.model.AccountingPeriodOperation;
import com.globaldynamicssystems.aurum.accounting.service.AccountingOperationValidator;
import com.globaldynamicssystems.aurum.accounting.service.AccountingPeriodControlService;
import org.springframework.stereotype.Service;

@Service
public class DefaultAccountingOperationValidator implements AccountingOperationValidator {

    private final AccountingPeriodControlService accountingPeriodControlService;

    public DefaultAccountingOperationValidator(AccountingPeriodControlService accountingPeriodControlService) {
        this.accountingPeriodControlService = accountingPeriodControlService;
    }

    @Override
    public void validate(AccountingPeriodOperation operation) {
        if (operation == null) {
            throw new AccountingPeriodValidationException("Accounting period operation cannot be null.");
        }

        if (operation.getFiscalPeriodId() == null) {
            throw new AccountingPeriodValidationException("Fiscal period ID is mandatory.");
        }

        if (operation.getOperationType() == null) {
            throw new AccountingPeriodValidationException("Accounting operation type is mandatory.");
        }

        AccountingOperationType type = operation.getOperationType();

        switch (type) {
            case JOURNAL_ENTRY_CREATE:
            case JOURNAL_ENTRY_VALIDATE:
            case POSTING:
            case OPENING_BALANCE:
            case YEAR_END_CLOSING:
                if (operation.getAccountingDate() == null) {
                    throw new AccountingPeriodValidationException(
                            "Accounting date is mandatory for operation: " + type
                    );
                }
                accountingPeriodControlService.validateTransactionAllowed(
                        operation.getFiscalPeriodId(),
                        operation.getAccountingDate()
                );
                break;

            case TRIAL_BALANCE:
            case FINANCIAL_REPORT:
                if (operation.getAccountingDate() != null) {
                    accountingPeriodControlService.validateAccountingDate(
                            operation.getFiscalPeriodId(),
                            operation.getAccountingDate()
                    );
                }
                break;

            default:
                throw new AccountingPeriodValidationException("Unsupported operation type: " + type);
        }
    }
}