package com.globaldynamicssystems.aurum.framework.service;

import com.globaldynamicssystems.aurum.framework.entity.BaseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseService<T extends BaseEntity> {

    T save(T entity);

    T update(T entity);

    Optional<T> findById(UUID id);

    List<T> findAll();

    void delete(UUID id);

    boolean exists(UUID id);

    long count();
}