package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.model.User;
import com.globaldynamicssystems.aurum.identity.model.UserStatus;

import java.util.UUID;

public interface UserStatusService {

    User changeStatus(UUID userId, UserStatus status);
}