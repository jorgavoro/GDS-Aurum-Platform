package com.globaldynamicssystems.aurum.identity.authentication.impl;

import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationFailureReason;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationRequest;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationResult;
import com.globaldynamicssystems.aurum.identity.authentication.AuthenticationService;
import com.globaldynamicssystems.aurum.identity.authentication.PasswordHasher;
import com.globaldynamicssystems.aurum.identity.authorization.SecurityContext;
import com.globaldynamicssystems.aurum.identity.authorization.SecurityContextFactory;
import com.globaldynamicssystems.aurum.identity.model.User;
import com.globaldynamicssystems.aurum.identity.model.UserStatus;
import com.globaldynamicssystems.aurum.identity.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultAuthenticationService implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final SecurityContextFactory securityContextFactory;

    public DefaultAuthenticationService(UserRepository userRepository,
                                        PasswordHasher passwordHasher,
                                        SecurityContextFactory securityContextFactory) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.securityContextFactory = securityContextFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResult authenticate(AuthenticationRequest request) {
        if (request == null) {
            return AuthenticationResult.failure(AuthenticationFailureReason.INVALID_REQUEST, "Invalid request.");
        }

        String username = request.getUsername();
        String password = request.getPassword();

        if (username == null || username.trim().isEmpty()) {
            return AuthenticationResult.failure(AuthenticationFailureReason.INVALID_REQUEST, "Invalid request.");
        }
        if (password == null || password.isEmpty()) {
            return AuthenticationResult.failure(AuthenticationFailureReason.INVALID_REQUEST, "Invalid request.");
        }

        username = username.trim().toLowerCase();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return AuthenticationResult.failure(AuthenticationFailureReason.INVALID_CREDENTIALS, "Invalid credentials.");
        }

        User user = userOpt.get();

        AuthenticationFailureReason statusReason = resolveStatusFailure(user.getStatus());
        if (statusReason != null) {
            return AuthenticationResult.failure(statusReason, "Authentication denied.");
        }

        if (user.getPasswordHash() == null) {
            return AuthenticationResult.failure(AuthenticationFailureReason.LOCAL_AUTHENTICATION_NOT_ENABLED,
                    "Invalid credentials.");
        }

        if (!passwordHasher.matches(password, user.getPasswordHash())) {
            return AuthenticationResult.failure(AuthenticationFailureReason.INVALID_CREDENTIALS, "Invalid credentials.");
        }

        SecurityContext securityContext = securityContextFactory.create(user.getId());

        return AuthenticationResult.success(user.getId(), user.getUsername(), securityContext);
    }

    private AuthenticationFailureReason resolveStatusFailure(UserStatus status) {
        return switch (status) {
            case PENDING -> AuthenticationFailureReason.USER_PENDING;
            case LOCKED -> AuthenticationFailureReason.USER_LOCKED;
            case DISABLED -> AuthenticationFailureReason.USER_DISABLED;
            case DELETED -> AuthenticationFailureReason.USER_DELETED;
            case ACTIVE -> null;
        };
    }
}
