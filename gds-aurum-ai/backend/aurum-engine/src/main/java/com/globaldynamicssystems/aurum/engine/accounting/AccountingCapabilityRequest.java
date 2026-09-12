package com.globaldynamicssystems.aurum.engine.accounting;

import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryRequest;

public class AccountingCapabilityRequest {

    private AccountingCapabilityType capabilityType;
    private AccountingQueryRequest query;

    public AccountingCapabilityRequest() {
    }

    public AccountingCapabilityRequest(AccountingCapabilityType capabilityType, AccountingQueryRequest query) {
        this.capabilityType = capabilityType;
        this.query = query;
    }

    public AccountingCapabilityType getCapabilityType() {
        return capabilityType;
    }

    public void setCapabilityType(AccountingCapabilityType capabilityType) {
        this.capabilityType = capabilityType;
    }

    public AccountingQueryRequest getQuery() {
        return query;
    }

    public void setQuery(AccountingQueryRequest query) {
        this.query = query;
    }
}