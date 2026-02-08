package com.icesi.devopssandboxbackend.service.impl;

import com.icesi.devopssandboxbackend.domain.model.Phase;
import com.icesi.devopssandboxbackend.domain.repository.PhaseRepository;
import com.icesi.devopssandboxbackend.service.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PhaseServiceImpl implements PhaseService {
    @Autowired
    private PhaseRepository phaseRepository;

    @Override
    public Phase findById(UUID id) {
        return phaseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Phase> findAll() {
        return phaseRepository.findAll();
    }

    @Override
    public List<Phase> findByMethodologyId(UUID methodologyId) {
        return phaseRepository.findByMethodologyId(methodologyId);
    }

    @Override
    public Phase save(Phase phase) {
        return phaseRepository.save(phase);
    }

    @Override
    public void deleteById(UUID id) {
        phaseRepository.deleteById(id);
    }
}
