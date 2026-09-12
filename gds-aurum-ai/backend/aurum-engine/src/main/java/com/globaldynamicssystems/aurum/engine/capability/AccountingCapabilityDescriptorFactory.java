package com.globaldynamicssystems.aurum.engine.capability;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountingCapabilityDescriptorFactory {

    public CapabilityDescriptor create() {
        CapabilityDescriptor descriptor = new CapabilityDescriptor();
        
        descriptor.setCode("ACCOUNTING");
        descriptor.setName("Accounting Business Capability");
        descriptor.setDescription("Provides financial accounting analysis, reporting, ratios, cash flow, financial KPIs, data quality and accounting comparison capabilities.");
        descriptor.setCapabilityType(CapabilityType.ACCOUNTING);
        descriptor.setEnabled(true);
        
        descriptor.setSupportedOperations(List.of(
                "ACCOUNT",
                "PROFITABILITY",
                "RATIO",
                "CASH_FLOW",
                "KPI",
                "DATA_QUALITY",
                "COMPARISON"
        ));
        
        descriptor.setRequiredParameters(List.of(
                "chartOfAccountsId"
        ));
        
        descriptor.setOptionalParameters(List.of(
                "fiscalPeriodId",
                "accountIds",
                "kpiTypes",
                "ratioTypes",
                "dimensionType",
                "dimensionIds",
                "currentFiscalPeriodId",
                "previousFiscalPeriodId"
        ));
        
        return descriptor;
    }
}