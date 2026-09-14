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
@Table(name = "gds_ledger_entry_dimension")
public class LedgerEntryDimension extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledger_entry_id", nullable = false)
    private LedgerEntry ledgerEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dimension_value_id", nullable = false)
    private AnalyticalDimensionValue dimension;

    public LedgerEntryDimension() {
    }

    public LedgerEntryDimension(LedgerEntry ledgerEntry, AnalyticalDimensionValue dimension) {
        this.ledgerEntry = ledgerEntry;
        this.dimension = dimension;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LedgerEntry getLedgerEntry() {
        return ledgerEntry;
    }

    public void setLedgerEntry(LedgerEntry ledgerEntry) {
        this.ledgerEntry = ledgerEntry;
    }

    public AnalyticalDimensionValue getDimension() {
        return dimension;
    }

    public void setDimension(AnalyticalDimensionValue dimension) {
        this.dimension = dimension;
    }
}
