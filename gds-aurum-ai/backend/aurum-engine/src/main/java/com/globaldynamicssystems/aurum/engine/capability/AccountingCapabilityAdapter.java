package com.globaldynamicssystems.aurum.engine.capability;

import com.globaldynamicssystems.aurum.accounting.model.AccountingQueryRequest;
import com.globaldynamicssystems.aurum.accounting.model.AccountingQueryResult;
import com.globaldynamicssystems.aurum.accounting.model.AccountingQueryType;
import com.globaldynamicssystems.aurum.accounting.service.AccountingQueryService;
import com.globaldynamicssystems.aurum.engine.capability.metadata.CapabilityMetadata;
import com.globaldynamicssystems.aurum.engine.capability.metadata.CapabilityMetadataRegistry;
import com.globaldynamicssystems.aurum.engine.exception.CapabilityExecutionException;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AccountingCapabilityAdapter implements Capability {

    private static final String CAPT_CODE = "ACCOUNTING";

    private final AccountingQueryService accountingQueryService;
    private final CapabilityDiscoveryRegistry discoveryRegistry;
    private final CapabilityMetadataRegistry metadataRegistry;

    public AccountingCapabilityAdapter(AccountingQueryService accountingQueryService,
                                       CapabilityDiscoveryRegistry discoveryRegistry,
                                       CapabilityMetadataRegistry metadataRegistry) {
        this.accountingQueryService = accountingQueryService;
        this.discoveryRegistry = discoveryRegistry;
        this.metadataRegistry = metadataRegistry;
    }

    @Override
    public String getCode() {
        return CAPT_CODE;
    }

    @Override
    public CapabilityDescriptor getDescriptor() {
        return discoveryRegistry.find(CAPT_CODE).orElse(null);
    }

    @Override
    public CapabilityMetadata getMetadata() {
        return metadataRegistry.findByCode(CAPT_CODE).orElse(null);
    }

    @Override
    public CapabilityResult execute(CapabilityRequest request) {
        try {
            AccountingQueryRequest queryRequest = mapToAccountingQueryRequest(request.getParameters());
            AccountingQueryResult queryResult = accountingQueryService.executeQuery(queryRequest);

            return new CapabilityResult(CAPT_CODE, true, queryResult, "Execution successful");
        } catch (Exception e) {
            throw new CapabilityExecutionException("Failed to execute accounting capability", e);
        }
    }

    private AccountingQueryRequest mapToAccountingQueryRequest(Map<String, Object> parameters) {
        AccountingQueryRequest queryRequest = new AccountingQueryRequest();

        if (parameters == null) {
            return queryRequest;
        }

        if (parameters.containsKey("chartOfAccountsId")) {
            queryRequest.setChartOfAccountsId(parseLong(parameters.get("chartOfAccountsId"), "chartOfAccountsId"));
        }
        if (parameters.containsKey("fiscalPeriodId")) {
            queryRequest.setFiscalPeriodId(parseLong(parameters.get("fiscalPeriodId"), "fiscalPeriodId"));
        }
        if (parameters.containsKey("currentFiscalPeriodId")) {
            queryRequest.setCurrentFiscalPeriodId(parseLong(parameters.get("currentFiscalPeriodId"), "currentFiscalPeriodId"));
        }
        if (parameters.containsKey("previousFiscalPeriodId")) {
            queryRequest.setPreviousFiscalPeriodId(parseLong(parameters.get("previousFiscalPeriodId"), "previousFiscalPeriodId"));
        }

        if (parameters.containsKey("queryType")) {
            Object qt = parameters.get("queryType");
            if (qt instanceof AccountingQueryType) {
                queryRequest.setQueryType((AccountingQueryType) qt);
            } else if (qt instanceof String) {
                queryRequest.setQueryType(AccountingQueryType.valueOf((String) qt));
            }
        }

        if (parameters.containsKey("accountIds")) {
            Object val = parameters.get("accountIds");
            if (val instanceof List) {
                @SuppressWarnings("unchecked")
                List<Long> ids = (List<Long>) val;
                queryRequest.setAccountIds(ids);
            }
        }

        if (parameters.containsKey("dimensionType")) {
            queryRequest.setDimensionType((String) parameters.get("dimensionType"));
        }

        if (parameters.containsKey("dimensionIds")) {
            Object val = parameters.get("dimensionIds");
            if (val instanceof List) {
                @SuppressWarnings("unchecked")
                List<Long> ids = (List<Long>) val;
                queryRequest.setDimensionIds(ids);
            }
        }

        return queryRequest;
    }

    private Long parseLong(Object value, String fieldName) {
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException e) {
                throw new CapabilityExecutionException("Invalid format for parameter " + fieldName, e);
            }
        }
        throw new CapabilityExecutionException("Unsupported type for parameter " + fieldName);
    }
}