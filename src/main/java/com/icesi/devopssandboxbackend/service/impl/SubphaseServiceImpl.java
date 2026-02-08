package com.icesi.devopssandboxbackend.service.impl;

import com.icesi.devopssandboxbackend.domain.model.Subphase;
import com.icesi.devopssandboxbackend.domain.repository.SubphaseRepository;
import com.icesi.devopssandboxbackend.service.SubphaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubphaseServiceImpl implements SubphaseService {
    @Autowired
    private SubphaseRepository subphaseRepository;

    @Override
    public Subphase findById(UUID id) {
        return subphaseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Subphase> findAll() {
        return subphaseRepository.findAll();
    }

    @Override
    public List<Subphase> findByPhaseId(UUID phaseId) {
        return subphaseRepository.findByPhaseId(phaseId);
    }

    @Override
    public Subphase save(Subphase subphase) {
        return subphaseRepository.save(subphase);
    }

    @Override
    public void deleteById(UUID id) {
        subphaseRepository.deleteById(id);
    }
}
