package com.globaldynamicssystems.aurum.identity.repository;

import com.globaldynamicssystems.aurum.identity.model.ExternalIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExternalIdentityRepository extends JpaRepository<ExternalIdentity, Long> {

    Optional<ExternalIdentity> findByProviderAndSubject(String provider, String subject);

    List<ExternalIdentity> findByUserId(Long userId);

    boolean existsByProviderAndSubject(String provider, String subject);
}