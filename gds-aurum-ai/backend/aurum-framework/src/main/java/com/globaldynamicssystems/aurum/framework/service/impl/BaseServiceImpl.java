package com.globaldynamicssystems.aurum.framework.service.impl;

import com.globaldynamicssystems.aurum.framework.entity.BaseEntity;
import com.globaldynamicssystems.aurum.framework.repository.BaseRepository;
import com.globaldynamicssystems.aurum.framework.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    protected final BaseRepository<T> repository;

    public BaseServiceImpl(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public T update(T entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<T> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exists(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }
}