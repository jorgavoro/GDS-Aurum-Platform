package com.globaldynamicssystems.aurum.accounting.repository;

import com.globaldynamicssystems.aurum.accounting.model.CostCenter;
import com.globaldynamicssystems.aurum.accounting.model.CostCenterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {

    Optional<CostCenter> findByChartOfAccountsIdAndCode(Long chartOfAccountsId, String code);

    List<CostCenter> findByChartOfAccountsId(Long chartOfAccountsId);

    List<CostCenter> findByChartOfAccountsIdAndStatus(Long chartOfAccountsId, CostCenterStatus status);

    boolean existsByChartOfAccountsIdAndCode(Long chartOfAccountsId, String code);
}