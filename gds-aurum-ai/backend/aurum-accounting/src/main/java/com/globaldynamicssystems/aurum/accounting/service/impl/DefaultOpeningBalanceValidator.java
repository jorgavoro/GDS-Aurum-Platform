package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.exception.OpeningBalanceException;
import com.globaldynamicssystems.aurum.accounting.model.OpeningBalanceLine;
import com.globaldynamicssystems.aurum.accounting.model.OpeningBalanceResult;
import com.globaldynamicssystems.aurum.accounting.service.OpeningBalanceValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DefaultOpeningBalanceValidator implements OpeningBalanceValidator {

    @Override
    public void validate(OpeningBalanceResult result) {
        if (result == null) {
            throw new OpeningBalanceException("OpeningBalanceResult must not be null.");
        }
        if (result.getSourceFiscalYear() == null) {
            throw new OpeningBalanceException("sourceFiscalYear is mandatory.");
        }
        if (result.getTargetFiscalYear() == null) {
            throw new OpeningBalanceException("targetFiscalYear is mandatory.");
        }
        if (result.getSourceFiscalYear() >= result.getTargetFiscalYear()) {
            throw new OpeningBalanceException("sourceFiscalYear must be strictly less than targetFiscalYear.");
        }
        if (result.getLines() == null) {
            throw new OpeningBalanceException("lines list must not be null.");
        }
        if (result.getTotalDebit() == null) {
            throw new OpeningBalanceException("totalDebit is mandatory.");
        }
        if (result.getTotalCredit() == null) {
            throw new OpeningBalanceException("totalCredit is mandatory.");
        }
        if (result.getTotalDebit().compareTo(BigDecimal.ZERO) < 0) {
            throw new OpeningBalanceException("totalDebit must be greater than or equal to 0.");
        }
        if (result.getTotalCredit().compareTo(BigDecimal.ZERO) < 0) {
            throw new OpeningBalanceException("totalCredit must be greater than or equal to 0.");
        }

        for (OpeningBalanceLine line : result.getLines()) {
            if (line.getAccountId() == null) {
                throw new OpeningBalanceException("Line accountId must not be null.");
            }
            if (line.getDebit() == null || line.getDebit().compareTo(BigDecimal.ZERO) < 0) {
                throw new OpeningBalanceException("Line debit must be non-null and >= 0 for accountId: " + line.getAccountId());
            }
            if (line.getCredit() == null || line.getCredit().compareTo(BigDecimal.ZERO) < 0) {
                throw new OpeningBalanceException("Line credit must be non-null and >= 0 for accountId: " + line.getAccountId());
            }

            boolean hasDebit = line.getDebit().compareTo(BigDecimal.ZERO) > 0;
            boolean hasCredit = line.getCredit().compareTo(BigDecimal.ZERO) > 0;

            if (hasDebit && hasCredit) {
                throw new OpeningBalanceException("Line cannot have both debit > 0 and credit > 0 for accountId: " + line.getAccountId());
            }
            if (!hasDebit && !hasCredit) {
                throw new OpeningBalanceException("Line cannot have both debit = 0 and credit = 0 for accountId: " + line.getAccountId());
            }

            BigDecimal expectedBalance = line.getDebit().subtract(line.getCredit());
            if (line.getBalance() == null || line.getBalance().compareTo(expectedBalance) != 0) {
                throw new OpeningBalanceException("Line balance does not match debit - credit for accountId: " + line.getAccountId());
            }
        }

        if (result.getTotalDebit().compareTo(result.getTotalCredit()) != 0) {
            throw new OpeningBalanceException("totalDebit (" + result.getTotalDebit() + ") must equal totalCredit (" + result.getTotalCredit() + ")");
        }
    }
}