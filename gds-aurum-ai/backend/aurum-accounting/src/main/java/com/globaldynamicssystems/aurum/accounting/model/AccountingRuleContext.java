package com.globaldynamicssystems.aurum.accounting.model;

import java.math.BigDecimal;

public class AccountingRuleContext {

    private Long chartOfAccountsId;
    private Long fiscalPeriodId;
    private JournalEntry journalEntry;
    private JournalEntryLine journalEntryLine;
    private Account account;
    private AnalyticalDimensionValue dimensionValue;
    private BigDecimal amount;

    public AccountingRuleContext() {
    }

    public AccountingRuleContext(Long chartOfAccountsId,
                                 Long fiscalPeriodId,
                                 JournalEntry journalEntry,
                                 JournalEntryLine journalEntryLine,
                                 Account account,
                                 AnalyticalDimensionValue dimensionValue,
                                 BigDecimal amount) {
        this.chartOfAccountsId = chartOfAccountsId;
        this.fiscalPeriodId = fiscalPeriodId;
        this.journalEntry = journalEntry;
        this.journalEntryLine = journalEntryLine;
        this.account = account;
        this.dimensionValue = dimensionValue;
        this.amount = amount;
    }

    public Long getChartOfAccountsId() { return chartOfAccountsId; }
    public void setChartOfAccountsId(Long chartOfAccountsId) { this.chartOfAccountsId = chartOfAccountsId; }

    public Long getFiscalPeriodId() { return fiscalPeriodId; }
    public void setFiscalPeriodId(Long fiscalPeriodId) { this.fiscalPeriodId = fiscalPeriodId; }

    public JournalEntry getJournalEntry() { return journalEntry; }
    public void setJournalEntry(JournalEntry journalEntry) { this.journalEntry = journalEntry; }

    public JournalEntryLine getJournalEntryLine() { return journalEntryLine; }
    public void setJournalEntryLine(JournalEntryLine journalEntryLine) { this.journalEntryLine = journalEntryLine; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public AnalyticalDimensionValue getDimensionValue() { return dimensionValue; }
    public void setDimensionValue(AnalyticalDimensionValue dimensionValue) { this.dimensionValue = dimensionValue; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
