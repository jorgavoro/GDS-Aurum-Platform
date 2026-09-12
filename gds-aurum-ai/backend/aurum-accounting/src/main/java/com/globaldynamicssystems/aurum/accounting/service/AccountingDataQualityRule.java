package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.DataQualityFinding;
import com.globaldynamicssystems.aurum.accounting.model.DataQualityRuleType;
import com.globaldynamicssystems.aurum.accounting.model.DataQualitySeverity;

import java.util.Optional;

public interface AccountingDataQualityRule {

    String getCode();

    DataQualityRuleType getRuleType();

    DataQualitySeverity getSeverity();

    Optional<DataQualityFinding> evaluate(Object context);
}
