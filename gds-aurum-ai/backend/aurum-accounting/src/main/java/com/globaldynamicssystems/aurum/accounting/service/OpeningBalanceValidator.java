package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.OpeningBalanceResult;

public interface OpeningBalanceValidator {

    void validate(OpeningBalanceResult result);
}