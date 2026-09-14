package com.globaldynamicssystems.aurum.accounting.repository;

import com.globaldynamicssystems.aurum.accounting.model.CostCenterStatus;
import com.globaldynamicssystems.aurum.accounting.model.ProfitCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfitCenterRepository extends JpaRepository<ProfitCenter, Long> {

    Optional<ProfitCenter> findByChartOfAccountsIdAndCode(Long chartOfAccountsId, String code);

    List<ProfitCenter> findByChartOfAccountsId(Long chartOfAccountsId);

    List<ProfitCenter> findByChartOfAccountsIdAndStatus(Long chartOfAccountsId, CostCenterStatus status);

    boolean existsByChartOfAccountsIdAndCode(Long chartOfAccountsId, String code);
}
