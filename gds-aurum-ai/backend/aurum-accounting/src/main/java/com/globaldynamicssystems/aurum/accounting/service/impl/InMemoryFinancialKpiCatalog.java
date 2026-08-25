package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiDefinition;
import com.globaldynamicssystems.aurum.accounting.model.FinancialKpiType;
import com.globaldynamicssystems.aurum.accounting.service.FinancialKpiCatalog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryFinancialKpiCatalog implements FinancialKpiCatalog {

    private final Map<FinancialKpiType, FinancialKpiDefinition> catalog = new ConcurrentHashMap<>();

    public InMemoryFinancialKpiCatalog() {
        initCatalog();
    }

    private void initCatalog() {
        register(new FinancialKpiDefinition(
                FinancialKpiType.REVENUE, "KPI-001", "Total Revenue", "MONETARY", "PROFITABILITY", "Total gross revenue generated in the fiscal period."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.EXPENSE, "KPI-002", "Total Expense", "MONETARY", "PROFITABILITY", "Total operating and non-operating expenses incurred."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.NET_RESULT, "KPI-003", "Net Result", "MONETARY", "PROFITABILITY", "Net income or loss calculated as total revenue minus expenses."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.NET_MARGIN, "KPI-004", "Net Margin", "PERCENTAGE", "PROFITABILITY", "Percentage of revenue remaining after all expenses are deducted."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.OPERATING_CASH_FLOW, "KPI-005", "Operating Cash Flow", "MONETARY", "CASH_FLOW", "Net cash generated from core business operations."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.INVESTING_CASH_FLOW, "KPI-006", "Investing Cash Flow", "MONETARY", "CASH_FLOW", "Net cash used in or generated from investment activities."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.FINANCING_CASH_FLOW, "KPI-007", "Financing Cash Flow", "MONETARY", "CASH_FLOW", "Net cash flow from debt, equity, and dividend activities."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.NET_CASH_FLOW, "KPI-008", "Net Cash Flow", "MONETARY", "CASH_FLOW", "Total net change in cash position across all cash activities."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.CURRENT_RATIO, "KPI-009", "Current Ratio", "RATIO", "LIQUIDITY", "Measures ability to cover short-term obligations with current assets."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.QUICK_RATIO, "KPI-010", "Quick Ratio", "RATIO", "LIQUIDITY", "Measures ability to cover short-term obligations with liquid assets."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.DEBT_RATIO, "KPI-011", "Debt Ratio", "RATIO", "LEVERAGE", "Proportion of total assets financed through debt."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.DEBT_TO_EQUITY, "KPI-012", "Debt To Equity Ratio", "RATIO", "LEVERAGE", "Relative proportion of shareholder equity and debt used to finance assets."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.RETURN_ON_ASSETS, "KPI-013", "Return On Assets (ROA)", "PERCENTAGE", "PROFITABILITY", "Percentage indicator of profitability relative to total assets."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.RETURN_ON_EQUITY, "KPI-014", "Return On Equity (ROE)", "PERCENTAGE", "PROFITABILITY", "Percentage measure of financial performance relative to equity."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.GROSS_MARGIN, "KPI-015", "Gross Margin", "PERCENTAGE", "PROFITABILITY", "Percentage of revenue remaining after subtracting cost of goods sold."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.DATA_QUALITY_ERROR_COUNT, "KPI-016", "Data Quality Error Count", "COUNT", "DATA_QUALITY", "Total number of accounting data quality violations classified as ERROR."
        ));
        register(new FinancialKpiDefinition(
                FinancialKpiType.DATA_QUALITY_WARNING_COUNT, "KPI-017", "Data Quality Warning Count", "COUNT", "DATA_QUALITY", "Total number of accounting data quality issues classified as WARNING."
        ));
    }

    private void register(FinancialKpiDefinition definition) {
        if (definition != null && definition.getType() != null) {
            catalog.put(definition.getType(), definition);
        }
    }

    @Override
    public Optional<FinancialKpiDefinition> find(FinancialKpiType type) {
        if (type == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(catalog.get(type));
    }

    @Override
    public List<FinancialKpiDefinition> findAll() {
        return new ArrayList<>(catalog.values());
    }
}