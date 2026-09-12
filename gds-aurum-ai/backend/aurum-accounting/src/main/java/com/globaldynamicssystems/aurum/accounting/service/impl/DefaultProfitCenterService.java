package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionValue;
import com.globaldynamicssystems.aurum.accounting.model.ChartOfAccounts;
import com.globaldynamicssystems.aurum.accounting.model.CostCenterStatus;
import com.globaldynamicssystems.aurum.accounting.model.ProfitCenter;
import com.globaldynamicssystems.aurum.accounting.repository.AnalyticalDimensionValueRepository;
import com.globaldynamicssystems.aurum.accounting.repository.ChartOfAccountsRepository;
import com.globaldynamicssystems.aurum.accounting.repository.ProfitCenterRepository;
import com.globaldynamicssystems.aurum.accounting.service.ProfitCenterService;
import com.globaldynamicssystems.aurum.accounting.service.ProfitCenterValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultProfitCenterService implements ProfitCenterService {

    private final ProfitCenterRepository profitCenterRepository;
    private final ChartOfAccountsRepository chartOfAccountsRepository;
    private final AnalyticalDimensionValueRepository dimensionValueRepository;
    private final ProfitCenterValidator profitCenterValidator;

    public DefaultProfitCenterService(ProfitCenterRepository profitCenterRepository,
                                      ChartOfAccountsRepository chartOfAccountsRepository,
                                      AnalyticalDimensionValueRepository dimensionValueRepository,
                                      ProfitCenterValidator profitCenterValidator) {
        this.profitCenterRepository = profitCenterRepository;
        this.chartOfAccountsRepository = chartOfAccountsRepository;
        this.dimensionValueRepository = dimensionValueRepository;
        this.profitCenterValidator = profitCenterValidator;
    }

    @Override
    @Transactional
    public ProfitCenter create(Long chartOfAccountsId, ProfitCenter profitCenter) {
        if (chartOfAccountsId == null) {
            throw new IllegalArgumentException("chartOfAccountsId cannot be null");
        }
        if (profitCenter == null) {
            throw new IllegalArgumentException("ProfitCenter cannot be null");
        }

        ChartOfAccounts chartOfAccounts = chartOfAccountsRepository.findById(chartOfAccountsId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ChartOfAccounts not found with ID: " + chartOfAccountsId));

        profitCenter.setChartOfAccounts(chartOfAccounts);
        profitCenter.setStatus(CostCenterStatus.ACTIVE);
        profitCenter.setActive(Boolean.TRUE);

        profitCenterValidator.validate(profitCenter);

        if (profitCenterRepository.existsByChartOfAccountsIdAndCode(chartOfAccountsId, profitCenter.getCode())) {
            throw new IllegalArgumentException(
                    "ProfitCenter code '" + profitCenter.getCode() + "' already exists in this ChartOfAccounts");
        }

        ProfitCenter saved = profitCenterRepository.save(profitCenter);

        registerDimension(saved);

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProfitCenter> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ProfitCenter ID cannot be null");
        }
        return profitCenterRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfitCenter> findByChartOfAccounts(Long chartOfAccountsId) {
        if (chartOfAccountsId == null) {
            throw new IllegalArgumentException("chartOfAccountsId cannot be null");
        }
        return profitCenterRepository.findByChartOfAccountsId(chartOfAccountsId);
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ProfitCenter ID cannot be null");
        }

        ProfitCenter profitCenter = profitCenterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ProfitCenter not found with ID: " + id));

        profitCenter.setStatus(CostCenterStatus.INACTIVE);
        profitCenter.setActive(Boolean.FALSE);
        profitCenterRepository.save(profitCenter);

        dimensionValueRepository
                .findByDimensionTypeAndReferenceId(AnalyticalDimensionType.PROFIT_CENTER, id)
                .ifPresent(dim -> {
                    dim.setActive(Boolean.FALSE);
                    dimensionValueRepository.save(dim);
                });
    }

    private void registerDimension(ProfitCenter profitCenter) {
        Optional<AnalyticalDimensionValue> existing = dimensionValueRepository
                .findByDimensionTypeAndReferenceId(AnalyticalDimensionType.PROFIT_CENTER, profitCenter.getId());

        if (existing.isPresent()) {
            return;
        }

        AnalyticalDimensionValue value = new AnalyticalDimensionValue();
        value.setDimensionType(AnalyticalDimensionType.PROFIT_CENTER);
        value.setReferenceId(profitCenter.getId());
        value.setCode(profitCenter.getCode());
        value.setName(profitCenter.getName());
        value.setActive(Boolean.TRUE);

        dimensionValueRepository.save(value);
    }
}
