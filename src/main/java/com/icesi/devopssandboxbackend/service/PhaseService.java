package com.icesi.devopssandboxbackend.service;

import com.icesi.devopssandboxbackend.domain.model.Phase;
import java.util.List;
import java.util.UUID;

public interface PhaseService {

    Phase findById(UUID id);

    List<Phase> findAll();

    List<Phase> findByMethodologyId(UUID methodologyId);

    Phase save(Phase phase);

    void deleteById(UUID id);

}