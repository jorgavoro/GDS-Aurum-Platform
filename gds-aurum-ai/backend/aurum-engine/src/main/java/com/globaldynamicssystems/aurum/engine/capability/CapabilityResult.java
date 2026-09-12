package com.globaldynamicssystems.aurum.engine.capability;

public class CapabilityResult {

    private String capabilityCode;
    private Boolean successful;
    private Object data;
    private String message;

    public CapabilityResult() {
    }

    public CapabilityResult(String capabilityCode, Boolean successful, Object data, String message) {
        this.capabilityCode = capabilityCode;
        this.successful = successful;
        this.data = data;
        this.message = message;
    }

    public String getCapabilityCode() {
        return capabilityCode;
    }

    public void setCapabilityCode(String capabilityCode) {
        this.capabilityCode = capabilityCode;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}