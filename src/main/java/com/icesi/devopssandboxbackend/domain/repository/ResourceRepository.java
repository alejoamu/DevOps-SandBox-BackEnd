package com.icesi.devopssandboxbackend.domain.repository;

import com.icesi.devopssandboxbackend.domain.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ResourceRepository extends JpaRepository<Resource, UUID> {
}
