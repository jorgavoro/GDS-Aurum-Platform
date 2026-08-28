package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.model.ExternalIdentity;
import com.globaldynamicssystems.aurum.identity.model.User;

import java.util.List;
import java.util.Optional;

public interface ExternalIdentityService {

    ExternalIdentity link(Long userId, String provider, String subject, String email);

    Optional<User> findUserByExternalIdentity(String provider, String subject);

    List<ExternalIdentity> findByUserId(Long userId);

    void unlink(Long userId, String provider, String subject);
}