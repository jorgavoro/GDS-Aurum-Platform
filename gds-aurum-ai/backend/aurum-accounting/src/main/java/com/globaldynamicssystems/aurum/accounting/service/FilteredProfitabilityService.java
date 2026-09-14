package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityFilter;
import com.globaldynamicssystems.aurum.accounting.model.ProfitabilityReport;

public interface FilteredProfitabilityService {

    ProfitabilityReport generate(ProfitabilityFilter filter);
}
