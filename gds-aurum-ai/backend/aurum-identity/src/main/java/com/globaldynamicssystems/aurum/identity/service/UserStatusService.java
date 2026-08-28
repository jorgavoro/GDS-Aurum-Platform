package com.globaldynamicssystems.aurum.identity.service;

import com.globaldynamicssystems.aurum.identity.model.User;
import com.globaldynamicssystems.aurum.identity.model.UserStatus;

public interface UserStatusService {

    User changeStatus(Long userId, UserStatus status);
}