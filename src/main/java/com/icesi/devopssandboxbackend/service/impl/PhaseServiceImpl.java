package com.icesi.devopssandboxbackend.service.impl;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.devopssandboxbackend.domain.model.Phase;
import com.icesi.devopssandboxbackend.domain.model.Subphase;
import com.icesi.devopssandboxbackend.domain.repository.PhaseRepository;
import com.icesi.devopssandboxbackend.exception.ResourceNotFoundException;
import com.icesi.devopssandboxbackend.service.PhaseService;
import com.icesi.devopssandboxbackend.service.SubphaseService;

@Service
public class PhaseServiceImpl implements PhaseService {
	@Autowired
	private PhaseRepository phaseRepository;

	@Autowired
	private SubphaseService subphaseService;

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
	@Transactional
	public Phase save(Phase incoming) {
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		if (incoming.getId() == null) {
			incoming.setCreatedAt(now);
			incoming.setUpdatedAt(now);
			return phaseRepository.save(incoming);
		}
		Phase existing = phaseRepository.findById(incoming.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Fase no encontrada."));
		existing.setCode(incoming.getCode());
		existing.setTitle(incoming.getTitle());
		existing.setDescription(incoming.getDescription());
		existing.setOrderIndex(incoming.getOrderIndex());
		existing.setUpdatedAt(now);
		if (incoming.getMethodology() != null) {
			existing.setMethodology(incoming.getMethodology());
		}
		return phaseRepository.save(existing);
	}

	@Override
	@Transactional
	public void deleteById(UUID id) {
		List<Subphase> subphases = subphaseService.findByPhaseId(id);
		for (Subphase s : subphases) {
			subphaseService.deleteById(s.getId());
		}
		phaseRepository.deleteById(id);
	}
}
