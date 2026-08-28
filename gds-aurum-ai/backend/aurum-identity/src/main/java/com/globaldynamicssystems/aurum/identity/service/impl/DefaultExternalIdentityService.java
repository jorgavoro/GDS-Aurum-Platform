package com.globaldynamicssystems.aurum.identity.service.impl;

import com.globaldynamicssystems.aurum.identity.exception.ExternalIdentityAlreadyLinkedException;
import com.globaldynamicssystems.aurum.identity.exception.ExternalIdentityNotFoundException;
import com.globaldynamicssystems.aurum.identity.exception.UserNotFoundException;
import com.globaldynamicssystems.aurum.identity.model.ExternalIdentity;
import com.globaldynamicssystems.aurum.identity.model.User;
import com.globaldynamicssystems.aurum.identity.repository.ExternalIdentityRepository;
import com.globaldynamicssystems.aurum.identity.repository.UserRepository;
import com.globaldynamicssystems.aurum.identity.service.ExternalIdentityService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultExternalIdentityService implements ExternalIdentityService {

    private final ExternalIdentityRepository externalIdentityRepository;
    private final UserRepository userRepository;

    public DefaultExternalIdentityService(ExternalIdentityRepository externalIdentityRepository,
                                          UserRepository userRepository) {
        this.externalIdentityRepository = externalIdentityRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ExternalIdentity link(Long userId, String provider, String subject, String email) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (provider == null || provider.trim().isEmpty()) {
            throw new IllegalArgumentException("Provider cannot be null or empty");
        }
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be null or empty");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        String normalizedProvider = provider.toUpperCase().trim();
        String normalizedSubject = subject.trim();

        if (externalIdentityRepository.existsByProviderAndSubject(normalizedProvider, normalizedSubject)) {
            throw new ExternalIdentityAlreadyLinkedException("External identity already linked for provider " + normalizedProvider + " and subject " + normalizedSubject);
        }

        ExternalIdentity externalIdentity = new ExternalIdentity();
        externalIdentity.setUser(user);
        externalIdentity.setProvider(normalizedProvider);
        externalIdentity.setSubject(normalizedSubject);
        if (email != null && !email.trim().isEmpty()) {
            externalIdentity.setEmail(email.toLowerCase().trim());
        }
        externalIdentity.setActive(true);

        return externalIdentityRepository.save(externalIdentity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByExternalIdentity(String provider, String subject) {
        if (provider == null || subject == null) {
            return Optional.empty();
        }
        return externalIdentityRepository.findByProviderAndSubject(provider.toUpperCase().trim(), subject.trim())
                .map(ExternalIdentity::getUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExternalIdentity> findByUserId(Long userId) {
        if (userId == null) {
            return List.of();
        }
        return externalIdentityRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void unlink(Long userId, String provider, String subject) {
        if (userId == null || provider == null || subject == null) {
            return;
        }
        ExternalIdentity identity = externalIdentityRepository
                .findByProviderAndSubject(provider.toUpperCase().trim(), subject.trim())
                .orElseThrow(() -> new ExternalIdentityNotFoundException("External identity not found"));

        if (!identity.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("External identity does not belong to the specified user");
        }

        externalIdentityRepository.delete(identity);
    }
}