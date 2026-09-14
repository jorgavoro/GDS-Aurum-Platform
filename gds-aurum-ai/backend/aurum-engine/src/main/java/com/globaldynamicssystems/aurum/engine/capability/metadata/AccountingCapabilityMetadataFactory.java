package com.globaldynamicssystems.aurum.engine.capability.metadata;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountingCapabilityMetadataFactory {

    public CapabilityMetadata create() {
        CapabilityMetadata metadata = new CapabilityMetadata();
        
        metadata.setCode("ACCOUNTING");
        metadata.setName("Accounting Business Capability");
        metadata.setDescription("Provides financial reporting, profitability analysis, financial ratios, cash flow, financial KPIs, accounting data quality and financial comparison.");
        metadata.setDomain(CapabilityDomain.ACCOUNTING);
        metadata.setEnabled(true);

        metadata.setConcepts(List.of(
                new CapabilityConcept("ACCOUNT", "Account", "Financial Account definition", "Account"),
                new CapabilityConcept("FISCAL_PERIOD", "Fiscal Period", "Accounting time period", "FiscalPeriod"),
                new CapabilityConcept("LEDGER", "Ledger", "Accounting Ledger", "Ledger"),
                new CapabilityConcept("REVENUE", "Revenue", "Revenue generation concept", "Revenue"),
                new CapabilityConcept("EXPENSE", "Expense", "Expense concept", "Expense"),
                new CapabilityConcept("NET_RESULT", "Net Result", "Final financial result", "NetResult"),
                new CapabilityConcept("FINANCIAL_RATIO", "Financial Ratio", "Key financial ratios", "FinancialRatio"),
                new CapabilityConcept("CASH_FLOW", "Cash Flow", "Cash flow movements", "CashFlow"),
                new CapabilityConcept("FINANCIAL_KPI", "Financial KPI", "Key performance indicators", "FinancialKpi"),
                new CapabilityConcept("DATA_QUALITY", "Data Quality", "Accounting validations", "DataQualityFinding")
        ));

        metadata.setInputs(List.of(
                new CapabilityParameterMetadata("chartOfAccountsId", "Chart of Accounts Identifier", CapabilityInputType.IDENTIFIER, true, false, "ChartOfAccounts"),
                new CapabilityParameterMetadata("fiscalPeriodId", "Main Fiscal Period Identifier", CapabilityInputType.PERIOD, false, false, "FiscalPeriod"),
                new CapabilityParameterMetadata("accountIds", "Specific Account Identifiers", CapabilityInputType.ACCOUNT, false, true, "Account"),
                new CapabilityParameterMetadata("kpiTypes", "Selected KPI Types", CapabilityInputType.COLLECTION, false, true, "FinancialKpiType"),
                new CapabilityParameterMetadata("ratioTypes", "Selected Ratio Types", CapabilityInputType.COLLECTION, false, true, "FinancialRatioType"),
                new CapabilityParameterMetadata("dimensionType", "Analytical Dimension Type", CapabilityInputType.DIMENSION, false, false, "AnalyticalDimension"),
                new CapabilityParameterMetadata("dimensionIds", "Analytical Dimension Values", CapabilityInputType.IDENTIFIER, false, true, "AnalyticalDimensionValue"),
                new CapabilityParameterMetadata("currentFiscalPeriodId", "Current Period for Comparisons", CapabilityInputType.PERIOD, false, false, "FiscalPeriod"),
                new CapabilityParameterMetadata("previousFiscalPeriodId", "Previous Period for Comparisons", CapabilityInputType.PERIOD, false, false, "FiscalPeriod")
        ));

        metadata.setOutputs(List.of(
                new CapabilityOutputMetadata("accountData", "Account Entities", CapabilityOutputType.ENTITY, "Account", true),
                new CapabilityOutputMetadata("financialReport", "Aggregated Financial Report", CapabilityOutputType.REPORT, "FinancialReport", false),
                new CapabilityOutputMetadata("financialRatios", "Calculated Financial Ratios", CapabilityOutputType.RATIO, "FinancialRatio", true),
                new CapabilityOutputMetadata("cashFlowStatement", "Cash Flow Details", CapabilityOutputType.CASH_FLOW, "CashFlow", false),
                new CapabilityOutputMetadata("financialKpis", "Key Performance Indicators", CapabilityOutputType.KPI, "FinancialKpi", true),
                new CapabilityOutputMetadata("dataQualityValidations", "Data Quality findings", CapabilityOutputType.VALIDATION, "DataQualityFinding", true),
                new CapabilityOutputMetadata("financialComparison", "Period Comparisons", CapabilityOutputType.COMPARISON, "FinancialComparison", false)
        ));

        // MVP no requiere dependencias obligatorias
        metadata.setDependencies(List.of());

        return metadata;
    }
}