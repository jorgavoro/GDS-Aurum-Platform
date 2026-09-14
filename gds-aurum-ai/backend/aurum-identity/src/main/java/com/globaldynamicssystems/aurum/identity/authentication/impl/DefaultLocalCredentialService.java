package com.globaldynamicssystems.aurum.identity.authentication.impl;

import com.globaldynamicssystems.aurum.identity.authentication.InvalidPasswordException;
import com.globaldynamicssystems.aurum.identity.authentication.LocalCredentialService;
import com.globaldynamicssystems.aurum.identity.authentication.PasswordHasher;
import com.globaldynamicssystems.aurum.identity.authentication.PasswordPolicyValidator;
import com.globaldynamicssystems.aurum.identity.exception.UserNotFoundException;
import com.globaldynamicssystems.aurum.identity.model.User;
import com.globaldynamicssystems.aurum.identity.model.UserStatus;
import com.globaldynamicssystems.aurum.identity.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DefaultLocalCredentialService implements LocalCredentialService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final PasswordPolicyValidator passwordPolicyValidator;

    public DefaultLocalCredentialService(UserRepository userRepository,
                                         PasswordHasher passwordHasher,
                                         PasswordPolicyValidator passwordPolicyValidator) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.passwordPolicyValidator = passwordPolicyValidator;
    }

    @Override
    @Transactional
    public void setPassword(UUID userId, String rawPassword) {
        User user = findUser(userId);
        passwordPolicyValidator.validate(rawPassword);
        user.setPasswordHash(passwordHasher.hash(rawPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(UUID userId, String currentPassword, String newPassword) {
        User user = findUser(userId);

        if (user.getStatus() == UserStatus.DELETED
                || user.getStatus() == UserStatus.DISABLED
                || user.getStatus() == UserStatus.LOCKED) {
            throw new IllegalStateException("Cannot change password for user with status: " + user.getStatus());
        }

        if (user.getPasswordHash() == null || !passwordHasher.matches(currentPassword, user.getPasswordHash())) {
            throw new InvalidPasswordException("Current password is incorrect.");
        }

        if (passwordHasher.matches(newPassword, user.getPasswordHash())) {
            throw new InvalidPasswordException("New password must differ from the current password.");
        }

        passwordPolicyValidator.validate(newPassword);
        user.setPasswordHash(passwordHasher.hash(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasLocalCredentials(UUID userId) {
        return userRepository.findById(userId)
                .map(u -> u.getPasswordHash() != null)
                .orElse(false);
    }

    private User findUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }
}
