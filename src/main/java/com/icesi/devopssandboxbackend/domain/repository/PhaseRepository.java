package com.icesi.devopssandboxbackend.domain.repository;

import com.icesi.devopssandboxbackend.domain.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PhaseRepository extends JpaRepository<Phase, UUID> {
    List<Phase> findByMethodologyId(UUID methodologyId);
}
