package com.globaldynamicssystems.aurum.engine.accounting;

import com.globaldynamicssystems.aurum.accounting.query.AccountingQueryResult;

public class AccountingCapabilityResponse {

    private AccountingCapabilityType capabilityType;
    private AccountingQueryResult result;
    private Boolean successful;
    private String message;

    public AccountingCapabilityResponse() {
    }

    public AccountingCapabilityResponse(AccountingCapabilityType capabilityType, AccountingQueryResult result, 
                                        Boolean successful, String message) {
        this.capabilityType = capabilityType;
        this.result = result;
        this.successful = successful;
        this.message = message;
    }

    public AccountingCapabilityType getCapabilityType() {
        return capabilityType;
    }

    public void setCapabilityType(AccountingCapabilityType capabilityType) {
        this.capabilityType = capabilityType;
    }

    public AccountingQueryResult getResult() {
        return result;
    }

    public void setResult(AccountingQueryResult result) {
        this.result = result;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}