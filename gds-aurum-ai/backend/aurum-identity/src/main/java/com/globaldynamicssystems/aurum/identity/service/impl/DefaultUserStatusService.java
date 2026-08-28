package com.globaldynamicssystems.aurum.identity.service.impl;

import com.globaldynamicssystems.aurum.identity.exception.UserNotFoundException;
import com.globaldynamicssystems.aurum.identity.model.User;
import com.globaldynamicssystems.aurum.identity.model.UserStatus;
import com.globaldynamicssystems.aurum.identity.repository.UserRepository;
import com.globaldynamicssystems.aurum.identity.service.UserStatusService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultUserStatusService implements UserStatusService {

    private final UserRepository userRepository;

    public DefaultUserStatusService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User changeStatus(Long userId, UserStatus newStatus) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (newStatus == null) {
            throw new IllegalArgumentException("New status cannot be null");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        UserStatus currentStatus = user.getStatus();

        if (currentStatus == UserStatus.DELETED) {
            throw new IllegalStateException("Cannot change status of a DELETED user");
        }

        validateTransition(currentStatus, newStatus);

        user.setStatus(newStatus);
        return userRepository.save(user);
    }

    private void validateTransition(UserStatus current, UserStatus target) {
        boolean valid = switch (current) {
            case PENDING -> target == UserStatus.ACTIVE || target == UserStatus.DISABLED || target == UserStatus.DELETED;
            case ACTIVE -> target == UserStatus.LOCKED || target == UserStatus.DISABLED || target == UserStatus.DELETED;
            case LOCKED -> target == UserStatus.ACTIVE || target == UserStatus.DISABLED || target == UserStatus.DELETED;
            case DISABLED -> target == UserStatus.ACTIVE || target == UserStatus.DELETED;
            case DELETED -> false;
        };

        if (!valid) {
            throw new IllegalStateException("Invalid status transition from " + current + " to " + target);
        }
    }
}