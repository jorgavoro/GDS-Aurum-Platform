package com.globaldynamicssystems.aurum.accounting.repository;

import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionType;
import com.globaldynamicssystems.aurum.accounting.model.AnalyticalDimensionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnalyticalDimensionValueRepository extends JpaRepository<AnalyticalDimensionValue, Long> {

    Optional<AnalyticalDimensionValue> findByDimensionTypeAndReferenceId(
            AnalyticalDimensionType dimensionType,
            Long referenceId
    );

    List<AnalyticalDimensionValue> findByDimensionType(
            AnalyticalDimensionType dimensionType
    );

    List<AnalyticalDimensionValue> findByDimensionTypeAndActive(
            AnalyticalDimensionType dimensionType,
            Boolean active
    );
}
