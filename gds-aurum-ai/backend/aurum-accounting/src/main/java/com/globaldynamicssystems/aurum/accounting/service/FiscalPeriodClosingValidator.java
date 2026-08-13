package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FiscalPeriod;

public interface FiscalPeriodClosingValidator {

    void validate(FiscalPeriod fiscalPeriod);
}