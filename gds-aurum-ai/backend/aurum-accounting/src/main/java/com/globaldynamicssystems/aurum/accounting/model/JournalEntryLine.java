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

@Entity
@Table(name = "gds_journal_entry_line")
public class JournalEntryLine extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "line_number", nullable = false)
    private Integer lineNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "debit", nullable = false, precision = 19, scale = 4)
    private BigDecimal debit;

    @Column(name = "credit", nullable = false, precision = 19, scale = 4)
    private BigDecimal credit;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_entry_id", nullable = false)
    private JournalEntry journalEntry;

    public JournalEntryLine() {
    }

    public JournalEntryLine(Long id, Integer lineNumber, Account account, BigDecimal debit,
                            BigDecimal credit, String description, JournalEntry journalEntry) {
        this.id = id;
        this.lineNumber = lineNumber;
        this.account = account;
        this.debit = debit;
        this.credit = credit;
        this.description = description;
        this.journalEntry = journalEntry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public JournalEntry getJournalEntry() {
        return journalEntry;
    }

    public void setJournalEntry(JournalEntry journalEntry) {
        this.journalEntry = journalEntry;
    }
}