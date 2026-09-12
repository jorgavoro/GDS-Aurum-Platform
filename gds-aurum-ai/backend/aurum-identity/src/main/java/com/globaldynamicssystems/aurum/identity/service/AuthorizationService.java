package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationRequest;
import com.globaldynamicssystems.aurum.identity.authorization.AuthorizationResult;

public interface AuthorizationService {
    AuthorizationResult authorize(AuthorizationRequest request);
}
