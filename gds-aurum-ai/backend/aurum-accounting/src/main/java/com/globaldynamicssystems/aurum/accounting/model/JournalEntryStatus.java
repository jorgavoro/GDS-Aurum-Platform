package com.globaldynamicssystems.aurum.accounting.model;

/**
 * Representa el estado del ciclo de vida de un asiento contable.
 */
public enum JournalEntryStatus {
    DRAFT,
    VALIDATED,
    POSTED,
    VOIDED
}