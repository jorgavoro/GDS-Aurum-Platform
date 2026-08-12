package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.ChartOfAccounts;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.service.ChartOfAccountsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DefaultChartOfAccountsService implements ChartOfAccountsService {

    private final ChartOfAccountsRepository chartOfAccountsRepository;

    public DefaultChartOfAccountsService(ChartOfAccountsRepository chartOfAccountsRepository) {
        this.chartOfAccountsRepository = chartOfAccountsRepository;
    }

    @Override
    public ChartOfAccounts create(ChartOfAccounts chartOfAccounts) {
        if (chartOfAccounts == null) {
            throw new IllegalArgumentException("ChartOfAccounts instance cannot be null");
        }
        if (chartOfAccounts.getCode() == null || chartOfAccounts.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("ChartOfAccounts code cannot be null or empty");
        }
        if (chartOfAccounts.getName() == null || chartOfAccounts.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("ChartOfAccounts name cannot be null or empty");
        }
        if (chartOfAccountsRepository.existsByCode(chartOfAccounts.getCode())) {
            throw new IllegalArgumentException("ChartOfAccounts with code '" + chartOfAccounts.getCode() + "' already exists");
        }

        return chartOfAccountsRepository.save(chartOfAccounts);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChartOfAccounts> findByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return Optional.empty();
        }
        return chartOfAccountsRepository.findByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChartOfAccounts> findAll() {
        return chartOfAccountsRepository.findAll();
    }
}