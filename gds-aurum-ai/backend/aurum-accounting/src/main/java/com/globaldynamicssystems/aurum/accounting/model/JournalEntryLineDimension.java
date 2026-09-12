package com.globaldynamicssystems.aurum.accounting.model;

import com.globaldynamicssystems.aurum.framework.entity.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "gds_journal_entry_line_dimension")
public class JournalEntryLineDimension extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journal_entry_line_id", nullable = false)
    private JournalEntryLine journalEntryLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dimension_value_id", nullable = false)
    private AnalyticalDimensionValue dimension;

    public JournalEntryLineDimension() {
    }

    public JournalEntryLineDimension(Long id, JournalEntryLine journalEntryLine, AnalyticalDimensionValue dimension) {
        this.id = id;
        this.journalEntryLine = journalEntryLine;
        this.dimension = dimension;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JournalEntryLine getJournalEntryLine() {
        return journalEntryLine;
    }

    public void setJournalEntryLine(JournalEntryLine journalEntryLine) {
        this.journalEntryLine = journalEntryLine;
    }

    public AnalyticalDimensionValue getDimension() {
        return dimension;
    }

    public void setDimension(AnalyticalDimensionValue dimension) {
        this.dimension = dimension;
    }
}
