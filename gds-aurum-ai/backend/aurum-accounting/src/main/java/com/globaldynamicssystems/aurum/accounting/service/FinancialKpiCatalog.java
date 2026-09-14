package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiDefinition;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiType;

import java.util.List;
import java.util.Optional;

public interface FinancialKpiCatalog {

    Optional<FinancialKpiDefinition> find(FinancialKpiType type);

    List<FinancialKpiDefinition> findAll();
}