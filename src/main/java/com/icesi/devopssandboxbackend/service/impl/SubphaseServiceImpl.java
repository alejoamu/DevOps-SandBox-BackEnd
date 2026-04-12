package com.icesi.devopssandboxbackend.service.impl;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.devopssandboxbackend.domain.model.Subphase;
import com.icesi.devopssandboxbackend.domain.repository.SubphaseRepository;
import com.icesi.devopssandboxbackend.domain.repository.SubphaseResourceRepository;
import com.icesi.devopssandboxbackend.exception.ResourceNotFoundException;
import com.icesi.devopssandboxbackend.service.SubphaseService;

@Service
public class SubphaseServiceImpl implements SubphaseService {
	@Autowired
	private SubphaseRepository subphaseRepository;

	@Autowired
	private SubphaseResourceRepository subphaseResourceRepository;

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
	@Transactional
	public Subphase save(Subphase incoming) {
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		if (incoming.getId() == null) {
			if (incoming.getPhase() == null) {
				throw new IllegalArgumentException("La subfase debe estar asociada a una fase (phase).");
			}
			incoming.setCreatedAt(now);
			incoming.setUpdatedAt(now);
			return subphaseRepository.save(incoming);
		}
		Subphase existing = subphaseRepository.findById(incoming.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Subfase no encontrada"));
		existing.setCode(incoming.getCode());
		existing.setTitle(incoming.getTitle());
		existing.setContent(incoming.getContent());
		existing.setOrderIndex(incoming.getOrderIndex());
		if (incoming.getPhase() != null) {
			existing.setPhase(incoming.getPhase());
		}
		existing.setUpdatedAt(now);
		return subphaseRepository.save(existing);
	}

	@Override
	@Transactional
	public void deleteById(UUID id) {
		subphaseResourceRepository.deleteAll(subphaseResourceRepository.findBySubphaseId(id));
		subphaseRepository.deleteById(id);
	}
}
