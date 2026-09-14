package com.globaldynamicssystems.aurum.identity.service.impl;

import com.globaldynamicssystems.aurum.identity.exception.UserAlreadyExistsException;
import com.globaldynamicssystems.aurum.identity.exception.UserNotFoundException;
import com.globaldynamicssystems.aurum.identity.model.User;
import com.globaldynamicssystems.aurum.identity.model.UserStatus;
import com.globaldynamicssystems.aurum.identity.repository.UserRepository;
import com.globaldynamicssystems.aurum.identity.service.UserService;
import com.globaldynamicssystems.aurum.identity.service.UserStatusService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final UserStatusService userStatusService;

    public DefaultUserService(UserRepository userRepository, UserStatusService userStatusService) {
        this.userRepository = userRepository;
        this.userStatusService = userStatusService;
    }

    @Override
    @Transactional
    public User create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        normalizeAndValidate(user);

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + user.getUsername());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + user.getEmail());
        }

        user.setStatus(UserStatus.PENDING);
        user.setEmailVerified(false);

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return Optional.empty();
        }
        return userRepository.findByUsername(username.toLowerCase().trim());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        return userRepository.findByEmail(email.toLowerCase().trim());
    }

    @Override
    @Transactional
    public User updateStatus(UUID id, UserStatus status) {
        return userStatusService.changeStatus(id, status);
    }

    private void normalizeAndValidate(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is mandatory");
        }
        user.setUsername(user.getUsername().toLowerCase().trim());

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is mandatory");
        }
        user.setEmail(user.getEmail().toLowerCase().trim());

        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is mandatory");
        }
        user.setFirstName(user.getFirstName().trim());

        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is mandatory");
        }
        user.setLastName(user.getLastName().trim());
    }
}