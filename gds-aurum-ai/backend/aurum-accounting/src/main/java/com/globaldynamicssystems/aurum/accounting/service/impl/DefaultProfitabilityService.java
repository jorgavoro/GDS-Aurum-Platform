package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalReport;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalReportLine;
import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityLine;
import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityReport;
import com.globaldynamicssystems.aurum.accounting.service.AnalyticalReportingService;
import com.globaldynamicssystems.aurum.accounting.service.ProfitabilityCalculator;
import com.globaldynamicssystems.aurum.accounting.service.ProfitabilityService;
import com.globaldynamicssystems.aurum.accounting.service.ProfitabilityValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultProfitabilityService implements ProfitabilityService {

    private final AnalyticalReportingService analyticalReportingService;
    private final ProfitabilityCalculator calculator;
    private final ProfitabilityValidator validator;

    public DefaultProfitabilityService(AnalyticalReportingService analyticalReportingService,
                                       ProfitabilityCalculator calculator,
                                       ProfitabilityValidator validator) {
        this.analyticalReportingService = analyticalReportingService;
        this.calculator = calculator;
        this.validator = validator;
    }

    @Override
    @Transactional(readOnly = true)
    public ProfitabilityReport generate(Long chartOfAccountsId, Long fiscalPeriodId,
                                        AnalyticalDimensionType dimensionType) {
        return generate(chartOfAccountsId, fiscalPeriodId, dimensionType, null);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfitabilityReport generate(Long chartOfAccountsId, Long fiscalPeriodId,
                                        AnalyticalDimensionType dimensionType,
                                        List<Long> dimensionIds) {
        AnalyticalReport analyticalReport = analyticalReportingService.generate(
                chartOfAccountsId, fiscalPeriodId, dimensionType, dimensionIds);

        List<ProfitabilityLine> lines = new ArrayList<>();

        for (AnalyticalReportLine reportLine : analyticalReport.getLines()) {
            BigDecimal revenue = reportLine.getRevenue();
            BigDecimal expense = reportLine.getExpense();
            BigDecimal netResult = calculator.calculateNetResult(revenue, expense);
            BigDecimal marginPercentage = calculator.calculateMarginPercentage(revenue, netResult);

            lines.add(new ProfitabilityLine(
                    reportLine.getDimensionType(),
                    reportLine.getDimensionId(),
                    reportLine.getDimensionCode(),
                    reportLine.getDimensionName(),
                    revenue,
                    expense,
                    netResult,
                    netResult,
                    marginPercentage));
        }

        BigDecimal totalRevenue = analyticalReport.getTotalRevenue();
        BigDecimal totalExpense = analyticalReport.getTotalExpense();
        BigDecimal totalNetResult = calculator.calculateNetResult(totalRevenue, totalExpense);
        BigDecimal totalMarginPercentage = calculator.calculateMarginPercentage(
                totalRevenue, totalNetResult);

        ProfitabilityReport report = new ProfitabilityReport(
                chartOfAccountsId,
                fiscalPeriodId,
                analyticalReport.getFiscalPeriodName(),
                dimensionType,
                lines,
                totalRevenue,
                totalExpense,
                totalNetResult,
                totalNetResult,
                totalMarginPercentage);

        validator.validate(report);

        return report;
    }
}
