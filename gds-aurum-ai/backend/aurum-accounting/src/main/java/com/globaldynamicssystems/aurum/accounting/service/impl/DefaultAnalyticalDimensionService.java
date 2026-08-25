package com.globaldynamicssystems.aurum.accounting.service.impl;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionValue;
import com.globaldynamicssystems.aurum.accounting.model.CostCenter;
import com.globaldynamicssystems.aurum.accounting.model.CostCenterStatus;
import com.globaldynamicssystems.aurum.accounting.model.ProfitCenter;
import com.globaldynamicssystems.aurum.accounting.repository.AnalyticalDimensionValueRepository;
import com.globaldynamicssystems.aurum.accounting.repository.CostCenterRepository;
import com.globaldynamicssystems.aurum.accounting.service.AnalyticalDimensionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultAnalyticalDimensionService implements AnalyticalDimensionService {

    private final AnalyticalDimensionValueRepository dimensionValueRepository;
    private final CostCenterRepository costCenterRepository;

    public DefaultAnalyticalDimensionService(AnalyticalDimensionValueRepository dimensionValueRepository,
                                             CostCenterRepository costCenterRepository) {
        this.dimensionValueRepository = dimensionValueRepository;
        this.costCenterRepository = costCenterRepository;
    }

    @Override
    @Transactional
    public AnalyticalDimensionValue registerCostCenter(CostCenter costCenter) {
        if (costCenter == null) {
            throw new IllegalArgumentException("CostCenter cannot be null");
        }
        if (costCenter.getId() == null) {
            throw new IllegalArgumentException("CostCenter must have an id");
        }
        if (!CostCenterStatus.ACTIVE.equals(costCenter.getStatus()) || !Boolean.TRUE.equals(costCenter.getActive())) {
            throw new IllegalArgumentException("CostCenter must be ACTIVE to be registered as a dimension");
        }

        Optional<AnalyticalDimensionValue> existing = dimensionValueRepository
                .findByDimensionTypeAndReferenceId(AnalyticalDimensionType.COST_CENTER, costCenter.getId());

        if (existing.isPresent()) {
            return existing.get();
        }

        AnalyticalDimensionValue value = new AnalyticalDimensionValue();
        value.setDimensionType(AnalyticalDimensionType.COST_CENTER);
        value.setReferenceId(costCenter.getId());
        value.setCode(costCenter.getCode());
        value.setName(costCenter.getName());
        value.setActive(Boolean.TRUE);

        return dimensionValueRepository.save(value);
    }

    @Override
    @Transactional
    public AnalyticalDimensionValue registerProfitCenter(ProfitCenter profitCenter) {
        if (profitCenter == null) {
            throw new IllegalArgumentException("ProfitCenter cannot be null");
        }
        if (profitCenter.getId() == null) {
            throw new IllegalArgumentException("ProfitCenter must have an id");
        }
        if (!CostCenterStatus.ACTIVE.equals(profitCenter.getStatus()) || !Boolean.TRUE.equals(profitCenter.getActive())) {
            throw new IllegalArgumentException("ProfitCenter must be ACTIVE to be registered as a dimension");
        }

        Optional<AnalyticalDimensionValue> existing = dimensionValueRepository
                .findByDimensionTypeAndReferenceId(AnalyticalDimensionType.PROFIT_CENTER, profitCenter.getId());

        if (existing.isPresent()) {
            return existing.get();
        }

        AnalyticalDimensionValue value = new AnalyticalDimensionValue();
        value.setDimensionType(AnalyticalDimensionType.PROFIT_CENTER);
        value.setReferenceId(profitCenter.getId());
        value.setCode(profitCenter.getCode());
        value.setName(profitCenter.getName());
        value.setActive(Boolean.TRUE);

        return dimensionValueRepository.save(value);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnalyticalDimensionValue> find(AnalyticalDimensionType type, Long referenceId) {
        if (type == null) {
            throw new IllegalArgumentException("AnalyticalDimensionType cannot be null");
        }
        if (referenceId == null) {
            throw new IllegalArgumentException("referenceId cannot be null");
        }
        return dimensionValueRepository.findByDimensionTypeAndReferenceId(type, referenceId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isActive(AnalyticalDimensionType type, Long referenceId) {
        return find(type, referenceId)
                .map(v -> Boolean.TRUE.equals(v.getActive()))
                .orElse(false);
    }
}
