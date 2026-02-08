package com.icesi.devopssandboxbackend.service;

import com.icesi.devopssandboxbackend.domain.model.Subphase;
import java.util.List;
import java.util.UUID;

public interface SubphaseService {

    Subphase findById(UUID id);

    List<Subphase> findAll();

    List<Subphase> findByPhaseId(UUID phaseId);

    Subphase save(Subphase subphase);

    void deleteById(UUID id);

}
