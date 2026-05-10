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
			// En create siempre se autoasigna al final del orden de la metodología.
			// El cliente no decide el orderIndex: el reordenamiento se hace por DnD.
			if (incoming.getMethodology() == null || incoming.getMethodology().getId() == null) {
				throw new IllegalArgumentException("La fase debe estar asociada a una metodología.");
			}
			UUID methodologyId = incoming.getMethodology().getId();
			int next = phaseRepository.findByMethodologyId(methodologyId).stream()
					.map(Phase::getOrderIndex)
					.filter(java.util.Objects::nonNull)
					.mapToInt(Integer::intValue)
					.max()
					.orElse(-1) + 1;
			incoming.setOrderIndex(next);
			incoming.setCreatedAt(now);
			incoming.setUpdatedAt(now);
			return phaseRepository.save(incoming);
		}
		Phase existing = phaseRepository.findById(incoming.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Fase no encontrada."));
		existing.setCode(incoming.getCode());
		existing.setTitle(incoming.getTitle());
		existing.setDescription(incoming.getDescription());
		// orderIndex no se modifica desde el formulario de edición:
		// el reordenamiento se hace exclusivamente por el endpoint /reorder.
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

	@Override
	@Transactional
	public void reorder(UUID methodologyId, List<UUID> orderedIds) {
		if (methodologyId == null || orderedIds == null || orderedIds.isEmpty()) {
			return;
		}
		List<Phase> phases = phaseRepository.findByMethodologyId(methodologyId);
		java.util.Map<UUID, Phase> byId = new java.util.HashMap<>();
		for (Phase p : phases) {
			byId.put(p.getId(), p);
		}
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		// Pasada 1: rango temporal alto para liberar los slots finales
		int temp = 1000;
		for (UUID id : orderedIds) {
			Phase p = byId.get(id);
			if (p == null) {
				throw new ResourceNotFoundException("Fase no encontrada: " + id);
			}
			p.setOrderIndex(temp++);
			p.setUpdatedAt(now);
		}
		phaseRepository.saveAll(byId.values());
		phaseRepository.flush();
		// Pasada 2: índices finales 0..n-1
		for (int i = 0; i < orderedIds.size(); i++) {
			Phase p = byId.get(orderedIds.get(i));
			p.setOrderIndex(i);
			p.setUpdatedAt(now);
		}
		phaseRepository.saveAll(byId.values());
		phaseRepository.flush();
	}
}
