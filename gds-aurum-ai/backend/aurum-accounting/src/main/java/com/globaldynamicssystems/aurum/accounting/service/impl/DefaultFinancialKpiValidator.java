package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.FinancialKpi;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiReport;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiType;
import com.globaldynamicssystems.aurum.accounting.service.FinancialKpiValidator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DefaultFinancialKpiValidator implements FinancialKpiValidator {

    public DefaultFinancialKpiValidator() {
    }

    @Override
    public void validate(FinancialKpiReport report) {
        if (report == null) {
            throw new IllegalArgumentException("FinancialKpiReport cannot be null.");
        }
        if (report.getChartOfAccountsId() == null) {
            throw new IllegalArgumentException("ChartOfAccountsId cannot be null in FinancialKpiReport.");
        }
        if (report.getFiscalPeriodId() == null) {
            throw new IllegalArgumentException("FiscalPeriodId cannot be null in FinancialKpiReport.");
        }
        if (report.getKpis() == null) {
            throw new IllegalArgumentException("KPIs list cannot be null in FinancialKpiReport.");
        }

        Set<FinancialKpiType> seenTypes = new HashSet<>();

        for (FinancialKpi kpi : report.getKpis()) {
            if (kpi == null) {
                throw new IllegalArgumentException("KPI item cannot be null in FinancialKpiReport.");
            }
            if (kpi.getType() == null) {
                throw new IllegalArgumentException("KPI type cannot be null.");
            }
            if (kpi.getCode() == null || kpi.getCode().trim().isEmpty()) {
                throw new IllegalArgumentException("KPI code cannot be null or empty for type " + kpi.getType());
            }
            if (kpi.getName() == null || kpi.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("KPI name cannot be null or empty for type " + kpi.getType());
            }
            if (kpi.getValue() == null) {
                throw new IllegalArgumentException("KPI value cannot be null for type " + kpi.getType());
            }
            if (kpi.getUnit() == null || kpi.getUnit().trim().isEmpty()) {
                throw new IllegalArgumentException("KPI unit cannot be null or empty for type " + kpi.getType());
            }

            if (!seenTypes.add(kpi.getType())) {
                throw new IllegalArgumentException("Duplicate KPI type found in report: " + kpi.getType());
            }
        }
    }
}