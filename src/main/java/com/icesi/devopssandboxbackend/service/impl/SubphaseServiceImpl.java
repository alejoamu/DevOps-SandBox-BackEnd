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
			if (incoming.getPhase() == null || incoming.getPhase().getId() == null) {
				throw new IllegalArgumentException("La subfase debe estar asociada a una fase (phase).");
			}
			// Autoasignar al final del orden de la fase. El cliente no decide
			// orderIndex en create: se reordena por DnD.
			UUID phaseId = incoming.getPhase().getId();
			int next = subphaseRepository.findByPhaseId(phaseId).stream()
					.map(Subphase::getOrderIndex)
					.filter(java.util.Objects::nonNull)
					.mapToInt(Integer::intValue)
					.max()
					.orElse(-1) + 1;
			incoming.setOrderIndex(next);
			incoming.setCreatedAt(now);
			incoming.setUpdatedAt(now);
			return subphaseRepository.save(incoming);
		}
		Subphase existing = subphaseRepository.findById(incoming.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Subfase no encontrada"));
		existing.setCode(incoming.getCode());
		existing.setTitle(incoming.getTitle());
		existing.setContent(incoming.getContent());
		// orderIndex NO se actualiza desde el formulario de edición:
		// el reordenamiento es exclusivo del endpoint /reorder.
		// Excepción: si el incoming cambia de fase, mantenemos el actual del
		// destino re-asignándolo al final.
		if (incoming.getPhase() != null && incoming.getPhase().getId() != null) {
			UUID currentPhaseId = existing.getPhase() != null ? existing.getPhase().getId() : null;
			UUID newPhaseId = incoming.getPhase().getId();
			if (currentPhaseId == null || !currentPhaseId.equals(newPhaseId)) {
				int next = subphaseRepository.findByPhaseId(newPhaseId).stream()
						.map(Subphase::getOrderIndex)
						.filter(java.util.Objects::nonNull)
						.mapToInt(Integer::intValue)
						.max()
						.orElse(-1) + 1;
				existing.setOrderIndex(next);
			}
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

	@Override
	@Transactional
	public void reorder(UUID phaseId, List<UUID> orderedIds) {
		if (phaseId == null || orderedIds == null || orderedIds.isEmpty()) {
			return;
		}
		List<Subphase> subphases = subphaseRepository.findByPhaseId(phaseId);
		java.util.Map<UUID, Subphase> byId = new java.util.HashMap<>();
		for (Subphase s : subphases) {
			byId.put(s.getId(), s);
		}
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		int temp = 1000;
		for (UUID id : orderedIds) {
			Subphase s = byId.get(id);
			if (s == null) {
				throw new ResourceNotFoundException("Subfase no encontrada: " + id);
			}
			s.setOrderIndex(temp++);
			s.setUpdatedAt(now);
		}
		subphaseRepository.saveAll(byId.values());
		subphaseRepository.flush();
		for (int i = 0; i < orderedIds.size(); i++) {
			Subphase s = byId.get(orderedIds.get(i));
			s.setOrderIndex(i);
			s.setUpdatedAt(now);
		}
		subphaseRepository.saveAll(byId.values());
		subphaseRepository.flush();
	}
}
