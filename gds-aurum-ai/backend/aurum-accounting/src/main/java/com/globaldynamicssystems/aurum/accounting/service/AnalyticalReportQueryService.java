package com.globaldynamicssystems.aurum.accounting.service;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalReport;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalReportFilter;

public interface AnalyticalReportQueryService {

    AnalyticalReport generate(AnalyticalReportFilter filter);
}
