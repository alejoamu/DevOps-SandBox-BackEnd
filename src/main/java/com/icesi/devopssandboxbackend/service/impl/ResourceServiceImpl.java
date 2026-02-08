package com.icesi.devopssandboxbackend.service.impl;

import com.icesi.devopssandboxbackend.domain.model.Resource;
import com.icesi.devopssandboxbackend.domain.repository.ResourceRepository;
import com.icesi.devopssandboxbackend.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Resource findById(UUID id) {
        return resourceRepository.findById(id).orElse(null);
    }

    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    @Override
    public Resource save(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public void deleteById(UUID id) {
        resourceRepository.deleteById(id);
    }
}
