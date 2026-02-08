package com.icesi.devopssandboxbackend.domain.repository;

import com.icesi.devopssandboxbackend.domain.model.Subphase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SubphaseRepository extends JpaRepository<Subphase, UUID> {
    List<Subphase> findByPhaseId(UUID phaseId);
}
