package com.icesi.devopssandboxbackend.service;

import com.icesi.devopssandboxbackend.domain.model.SubphaseResource;
import java.util.List;
import java.util.UUID;

public interface SubphaseResourceService {

    SubphaseResource findById(UUID subphaseId, UUID resourceId);

    List<SubphaseResource> findAll();

    List<SubphaseResource> findBySubphaseId(UUID subphaseId);

    List<SubphaseResource> findByResourceId(UUID resourceId);

    SubphaseResource save(SubphaseResource subphaseResource);

    void deleteById(UUID subphaseId, UUID resourceId);

}
