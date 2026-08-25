package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionValue;
import com.globaldynamicssystems.aurum.accounting.model.CostCenter;
import com.globaldynamicssystems.aurum.accounting.model.ProfitCenter;

import java.util.Optional;

public interface AnalyticalDimensionService {

    AnalyticalDimensionValue registerCostCenter(CostCenter costCenter);

    AnalyticalDimensionValue registerProfitCenter(ProfitCenter profitCenter);

    Optional<AnalyticalDimensionValue> find(AnalyticalDimensionType type, Long referenceId);

    boolean isActive(AnalyticalDimensionType type, Long referenceId);
}
