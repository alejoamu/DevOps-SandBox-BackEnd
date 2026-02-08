package com.icesi.devopssandboxbackend.service.impl;

import com.icesi.devopssandboxbackend.domain.model.Methodology;
import com.icesi.devopssandboxbackend.domain.repository.MethodologyRepository;
import com.icesi.devopssandboxbackend.service.MethodologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MethodologyServiceImpl implements MethodologyService {
    @Autowired
    private MethodologyRepository methodologyRepository;

    @Override
    public Methodology findById(UUID id) {
        return methodologyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Methodology> findAll() {
        return methodologyRepository.findAll();
    }

    @Override
    public Methodology save(Methodology methodology) {
        return methodologyRepository.save(methodology);
    }

    @Override
    public void deleteById(UUID id) {
        methodologyRepository.deleteById(id);
    }
}
