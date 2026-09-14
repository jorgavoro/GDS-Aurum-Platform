package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.model.ExternalIdentity;
import com.globaldynamicssystems.aurum.identity.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExternalIdentityService {

    ExternalIdentity link(UUID userId, String provider, String subject, String email);

    Optional<User> findUserByExternalIdentity(String provider, String subject);

    List<ExternalIdentity> findByUserId(UUID userId);

    void unlink(UUID userId, String provider, String subject);
}