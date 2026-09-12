package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;
import java.util.List;

public class CashFlowReport {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private String fiscalPeriodName;
    private List<CashFlowLine> operatingLines;
    private List<CashFlowLine> investingLines;
    private List<CashFlowLine> financingLines;
    private BigDecimal operatingCashFlow;
    private BigDecimal investingCashFlow;
    private BigDecimal financingCashFlow;
    private BigDecimal netCashFlow;
    private BigDecimal openingCashBalance;
    private BigDecimal closingCashBalance;

    public CashFlowReport() {
    }

    public CashFlowReport(Long chartOfAccountsId,
                          Long fiscalPeriodId,
                          String fiscalPeriodName,
                          List<CashFlowLine> operatingLines,
                          List<CashFlowLine> investingLines,
                          List<CashFlowLine> financingLines,
                          BigDecimal operatingCashFlow,
                          BigDecimal investingCashFlow,
                          BigDecimal financingCashFlow,
                          BigDecimal netCashFlow,
                          BigDecimal openingCashBalance,
                          BigDecimal closingCashBalance) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.fiscalPeriodName = fiscalPeriodName;
        this.operatingLines = operatingLines;
        this.investingLines = investingLines;
        this.financingLines = financingLines;
        this.operatingCashFlow = operatingCashFlow;
        this.investingCashFlow = investingCashFlow;
        this.financingCashFlow = financingCashFlow;
        this.netCashFlow = netCashFlow;
        this.openingCashBalance = openingCashBalance;
        this.closingCashBalance = closingCashBalance;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public String getFiscalPeriodName() { return fiscalPeriodName; }
    public void setFiscalPeriodName(String fiscalPeriodName) { this.fiscalPeriodName = fiscalPeriodName; }

    public List<CashFlowLine> getOperatingLines() { return operatingLines; }
    public void setOperatingLines(List<CashFlowLine> operatingLines) { this.operatingLines = operatingLines; }

    public List<CashFlowLine> getInvestingLines() { return investingLines; }
    public void setInvestingLines(List<CashFlowLine> investingLines) { this.investingLines = investingLines; }

    public List<CashFlowLine> getFinancingLines() { return financingLines; }
    public void setFinancingLines(List<CashFlowLine> financingLines) { this.financingLines = financingLines; }

    public BigDecimal getOperatingCashFlow() { return operatingCashFlow; }
    public void setOperatingCashFlow(BigDecimal operatingCashFlow) { this.operatingCashFlow = operatingCashFlow; }

    public BigDecimal getInvestingCashFlow() { return investingCashFlow; }
    public void setInvestingCashFlow(BigDecimal investingCashFlow) { this.investingCashFlow = investingCashFlow; }

    public BigDecimal getFinancingCashFlow() { return financingCashFlow; }
    public void setFinancingCashFlow(BigDecimal financingCashFlow) { this.financingCashFlow = financingCashFlow; }

    public BigDecimal getNetCashFlow() { return netCashFlow; }
    public void setNetCashFlow(BigDecimal netCashFlow) { this.netCashFlow = netCashFlow; }

    public BigDecimal getOpeningCashBalance() { return openingCashBalance; }
    public void setOpeningCashBalance(BigDecimal openingCashBalance) { this.openingCashBalance = openingCashBalance; }

    public BigDecimal getClosingCashBalance() { return closingCashBalance; }
    public void setClosingCashBalance(BigDecimal closingCashBalance) { this.closingCashBalance = closingCashBalance; }
}
