package com.globaldynamicssystems.aurum.accounting.repository;

import com.globaldynamicssystems.aurum.accounting.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByChartOfAccountsIdAndCode(Long chartOfAccountsId, String code);

    List<Account> findByChartOfAccountsId(Long chartOfAccountsId);

    List<Account> findByParentId(Long parentId);

    boolean existsByChartOfAccountsIdAndCode(Long chartOfAccountsId, String code);
}