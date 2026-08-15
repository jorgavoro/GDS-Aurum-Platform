package com.globaldynamicssystems.aurum.accounting.model;

import com.globaldynamicssystems.aurum.framework.entity.AuditableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gds_journal_entry")
public class JournalEntry extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_number", nullable = false, unique = true)
    private String documentNumber;

    @Column(name = "accounting_date", nullable = false)
    private LocalDate accountingDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JournalEntryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chart_of_accounts_id", nullable = false)
    private ChartOfAccounts chartOfAccounts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fiscal_period_id", nullable = false)
    private FiscalPeriod fiscalPeriod;

    @OneToMany(mappedBy = "journalEntry", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<JournalEntryLine> lines = new ArrayList<>();

    public JournalEntry() {
    }

    public JournalEntry(Long id, String documentNumber, LocalDate accountingDate, String description,
                        JournalEntryStatus status, ChartOfAccounts chartOfAccounts,
                        FiscalPeriod fiscalPeriod, List<JournalEntryLine> lines) {
        this.id = id;
        this.documentNumber = documentNumber;
        this.accountingDate = accountingDate;
        this.description = description;
        this.status = status;
        this.chartOfAccounts = chartOfAccounts;
        this.fiscalPeriod = fiscalPeriod;
        if (lines != null) {
            this.lines = lines;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public LocalDate getAccountingDate() {
        return accountingDate;
    }

    public void setAccountingDate(LocalDate accountingDate) {
        this.accountingDate = accountingDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JournalEntryStatus getStatus() {
        return status;
    }

    public void setStatus(JournalEntryStatus status) {
        this.status = status;
    }

    public ChartOfAccounts getChartOfAccounts() {
        return chartOfAccounts;
    }

    public void setChartOfAccounts(ChartOfAccounts chartOfAccounts) {
        this.chartOfAccounts = chartOfAccounts;
    }

    public FiscalPeriod getFiscalPeriod() {
        return fiscalPeriod;
    }

    public void setFiscalPeriod(FiscalPeriod fiscalPeriod) {
        this.fiscalPeriod = fiscalPeriod;
    }

    public List<JournalEntryLine> getLines() {
        return lines;
    }

    public void setLines(List<JournalEntryLine> lines) {
        this.lines = lines;
    }

    public void addLine(JournalEntryLine line) {
        if (line != null) {
            lines.add(line);
            line.setJournalEntry(this);
        }
    }

    public void removeLine(JournalEntryLine line) {
        if (line != null) {
            lines.remove(line);
            line.setJournalEntry(null);
        }
    }
}