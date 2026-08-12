package com.globaldynamicssystems.aurum.accounting.model;

import com.globaldynamicssystems.aurum.model.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "gds_ledger_entry")
public class LedgerEntry extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_entry_id", nullable = false)
    private JournalEntry journalEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_entry_line_id", nullable = false)
    private JournalEntryLine journalEntryLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fiscal_period_id", nullable = false)
    private FiscalPeriod fiscalPeriod;

    @Column(name = "accounting_date", nullable = false)
    private LocalDate accountingDate;

    @Column(name = "debit", nullable = false, precision = 19, scale = 4)
    private BigDecimal debit;

    @Column(name = "credit", nullable = false, precision = 19, scale = 4)
    private BigDecimal credit;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "line_number", nullable = false)
    private Integer lineNumber;

    public LedgerEntry() {
    }

    public LedgerEntry(Long id, JournalEntry journalEntry, JournalEntryLine journalEntryLine,
                       Account account, FiscalPeriod fiscalPeriod, LocalDate accountingDate,
                       BigDecimal debit, BigDecimal credit, String description, Integer lineNumber) {
        this.id = id;
        this.journalEntry = journalEntry;
        this.journalEntryLine = journalEntryLine;
        this.account = account;
        this.fiscalPeriod = fiscalPeriod;
        this.accountingDate = accountingDate;
        this.debit = debit;
        this.credit = credit;
        this.description = description;
        this.lineNumber = lineNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JournalEntry getJournalEntry() {
        return journalEntry;
    }

    public void setJournalEntry(JournalEntry journalEntry) {
        this.journalEntry = journalEntry;
    }

    public JournalEntryLine getJournalEntryLine() {
        return journalEntryLine;
    }

    public void setJournalEntryLine(JournalEntryLine journalEntryLine) {
        this.journalEntryLine = journalEntryLine;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public FiscalPeriod getFiscalPeriod() {
        return fiscalPeriod;
    }

    public void setFiscalPeriod(FiscalPeriod fiscalPeriod) {
        this.fiscalPeriod = fiscalPeriod;
    }

    public LocalDate getAccountingDate() {
        return accountingDate;
    }

    public void setAccountingDate(LocalDate accountingDate) {
        this.accountingDate = accountingDate;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }
}