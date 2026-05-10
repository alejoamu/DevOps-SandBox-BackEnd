package com.icesi.devopssandboxbackend.service.impl;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.devopssandboxbackend.domain.enums.MethodologyStatus;
import com.icesi.devopssandboxbackend.domain.model.Methodology;
import com.icesi.devopssandboxbackend.domain.repository.MethodologyRepository;
import com.icesi.devopssandboxbackend.exception.ResourceNotFoundException;
import com.icesi.devopssandboxbackend.service.MethodologyService;

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
    @Transactional
    public Methodology save(Methodology incoming) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        if (incoming.getId() == null) {
            if (incoming.getStatus() == null) incoming.setStatus(MethodologyStatus.draft);
            if (incoming.getVersion() == null) incoming.setVersion(1);
            if (incoming.getLanguageCode() == null || incoming.getLanguageCode().isBlank()) {
                incoming.setLanguageCode("es");
            }
            if (incoming.getCreatedAt() == null) incoming.setCreatedAt(now);
            incoming.setUpdatedAt(now);
            return methodologyRepository.save(incoming);
        }
        Methodology existing = methodologyRepository.findById(incoming.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Metodología no encontrada."));
        existing.setSlug(incoming.getSlug());
        existing.setName(incoming.getName());
        existing.setIntroduction(incoming.getIntroduction());
        existing.setSummary(incoming.getSummary());
        if (incoming.getStatus() != null) existing.setStatus(incoming.getStatus());
        if (incoming.getVersion() != null) existing.setVersion(incoming.getVersion());
        if (incoming.getLanguageCode() != null && !incoming.getLanguageCode().isBlank()) {
            existing.setLanguageCode(incoming.getLanguageCode());
        }
        existing.setUpdatedAt(now);
        return methodologyRepository.save(existing);
    }

    @Override
    public void deleteById(UUID id) {
        methodologyRepository.deleteById(id);
    }
}
