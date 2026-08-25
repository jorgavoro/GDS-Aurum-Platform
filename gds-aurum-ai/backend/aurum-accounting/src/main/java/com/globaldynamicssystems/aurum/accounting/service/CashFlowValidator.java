package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.CashFlowReport;

public interface CashFlowValidator {

    void validate(CashFlowReport report);
}
