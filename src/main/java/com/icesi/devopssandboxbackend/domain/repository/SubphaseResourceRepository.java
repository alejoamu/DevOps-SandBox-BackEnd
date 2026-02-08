package com.icesi.devopssandboxbackend.domain.repository;

import com.icesi.devopssandboxbackend.domain.model.SubphaseResource;
import com.icesi.devopssandboxbackend.domain.model.SubphaseResourceId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SubphaseResourceRepository extends JpaRepository<SubphaseResource, SubphaseResourceId> {
    List<SubphaseResource> findBySubphaseId(UUID subphaseId);
    List<SubphaseResource> findByResourceId(UUID resourceId);
}
