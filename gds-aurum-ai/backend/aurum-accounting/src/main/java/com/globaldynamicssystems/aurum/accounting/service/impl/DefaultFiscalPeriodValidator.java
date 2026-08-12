package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;
import com.globaldynamicssystems.aurum.accounting.service.FiscalPeriodValidator;
import org.springframework.stereotype.Component;

@Component
public class DefaultFiscalPeriodValidator implements FiscalPeriodValidator {

    public DefaultFiscalPeriodValidator() {
    }

    @Override
    public void validate(FiscalPeriod fiscalPeriod) {
        if (fiscalPeriod == null) {
            throw new IllegalArgumentException("FiscalPeriod instance cannot be null");
        }
        if (fiscalPeriod.getFiscalYear() == null) {
            throw new IllegalArgumentException("Fiscal year cannot be null");
        }
        if (fiscalPeriod.getFiscalYear() <= 0) {
            throw new IllegalArgumentException("Fiscal year must be greater than 0");
        }
        if (fiscalPeriod.getPeriodNumber() == null) {
            throw new IllegalArgumentException("Period number cannot be null");
        }
        if (fiscalPeriod.getPeriodNumber() < 1 || fiscalPeriod.getPeriodNumber() > 12) {
            throw new IllegalArgumentException("Period number must be between 1 and 12");
        }
        if (fiscalPeriod.getName() == null || fiscalPeriod.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Period name cannot be null or empty");
        }
        if (fiscalPeriod.getStartDate() == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (fiscalPeriod.getEndDate() == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }
        if (fiscalPeriod.getStartDate().isAfter(fiscalPeriod.getEndDate())) {
            throw new IllegalArgumentException("Start date (" + fiscalPeriod.getStartDate() 
                    + ") cannot be after end date (" + fiscalPeriod.getEndDate() + ")");
        }
    }
}