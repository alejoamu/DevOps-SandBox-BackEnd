package com.icesi.devopssandboxbackend.domain.repository;

import com.icesi.devopssandboxbackend.domain.model.Methodology;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MethodologyRepository extends JpaRepository<Methodology, UUID> {
}
