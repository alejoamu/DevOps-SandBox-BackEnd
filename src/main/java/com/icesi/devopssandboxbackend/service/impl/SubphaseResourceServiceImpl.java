package com.icesi.devopssandboxbackend.service.impl;

import com.icesi.devopssandboxbackend.domain.model.SubphaseResource;
import com.icesi.devopssandboxbackend.domain.model.SubphaseResourceId;
import com.icesi.devopssandboxbackend.domain.repository.SubphaseResourceRepository;
import com.icesi.devopssandboxbackend.service.SubphaseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubphaseResourceServiceImpl implements SubphaseResourceService {
    @Autowired
    private SubphaseResourceRepository subphaseResourceRepository;

    @Override
    public SubphaseResource findById(UUID subphaseId, UUID resourceId) {
        return subphaseResourceRepository.findById(new SubphaseResourceId(subphaseId, resourceId)).orElse(null);
    }

    @Override
    public List<SubphaseResource> findAll() {
        return subphaseResourceRepository.findAll();
    }

    @Override
    public List<SubphaseResource> findBySubphaseId(UUID subphaseId) {
        return subphaseResourceRepository.findBySubphaseId(subphaseId);
    }

    @Override
    public List<SubphaseResource> findByResourceId(UUID resourceId) {
        return subphaseResourceRepository.findByResourceId(resourceId);
    }

    @Override
    public SubphaseResource save(SubphaseResource subphaseResource) {
        return subphaseResourceRepository.save(subphaseResource);
    }

    @Override
    public void deleteById(UUID subphaseId, UUID resourceId) {
        subphaseResourceRepository.deleteById(new SubphaseResourceId(subphaseId, resourceId));
    }
}
