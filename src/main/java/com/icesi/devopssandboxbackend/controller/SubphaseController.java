package com.icesi.devopssandboxbackend.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icesi.devopssandboxbackend.domain.model.Phase;
import com.icesi.devopssandboxbackend.domain.model.Subphase;
import com.icesi.devopssandboxbackend.dto.SubphaseDTO;
import com.icesi.devopssandboxbackend.exception.ResourceNotFoundException;
import com.icesi.devopssandboxbackend.mapper.SubphaseMapper;
import com.icesi.devopssandboxbackend.service.PhaseService;
import com.icesi.devopssandboxbackend.service.SubphaseService;

@RestController
@RequestMapping("/api/subphases")
public class SubphaseController {
	@Autowired
	private SubphaseService subphaseService;

	@Autowired
	private PhaseService phaseService;

	@GetMapping
	public List<SubphaseDTO> getAll() {
		return subphaseService.findAll().stream().map(SubphaseMapper::toDTO).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public SubphaseDTO getById(@PathVariable UUID id) {
		return SubphaseMapper.toDTO(subphaseService.findById(id));
	}

	@GetMapping("/phase/{phaseId}")
	public List<SubphaseDTO> getByPhase(@PathVariable UUID phaseId) {
		return subphaseService.findByPhaseId(phaseId).stream().map(SubphaseMapper::toDTO)
				.collect(Collectors.toList());
	}

	@PostMapping
	public SubphaseDTO create(@RequestBody SubphaseDTO dto) {
		if (dto.getPhaseId() == null) {
			throw new IllegalArgumentException("phaseId es obligatorio para crear una subfase.");
		}
		Phase phase = phaseService.findById(dto.getPhaseId());
		if (phase == null) {
			throw new ResourceNotFoundException("Fase no encontrada.");
		}
		Subphase entity = SubphaseMapper.toEntity(dto);
		entity.setPhase(phase);
		return SubphaseMapper.toDTO(subphaseService.save(entity));
	}

	@PutMapping("/{id}")
	public SubphaseDTO update(@PathVariable UUID id, @RequestBody SubphaseDTO dto) {
		dto.setId(id);
		Subphase entity = SubphaseMapper.toEntity(dto);
		if (dto.getPhaseId() != null) {
			Phase phase = phaseService.findById(dto.getPhaseId());
			if (phase == null) {
				throw new ResourceNotFoundException("Fase no encontrada.");
			}
			entity.setPhase(phase);
		}
		return SubphaseMapper.toDTO(subphaseService.save(entity));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		subphaseService.deleteById(id);
	}

	public static class ReorderRequest {
		private UUID phaseId;
		private List<UUID> orderedIds;

		public UUID getPhaseId() { return phaseId; }
		public void setPhaseId(UUID phaseId) { this.phaseId = phaseId; }
		public List<UUID> getOrderedIds() { return orderedIds; }
		public void setOrderedIds(List<UUID> orderedIds) { this.orderedIds = orderedIds; }
	}

	@PostMapping("/reorder")
	public List<SubphaseDTO> reorder(@RequestBody ReorderRequest body) {
		if (body.getPhaseId() == null) {
			throw new IllegalArgumentException("phaseId es obligatorio.");
		}
		if (body.getOrderedIds() == null || body.getOrderedIds().isEmpty()) {
			throw new IllegalArgumentException("orderedIds es obligatorio.");
		}
		subphaseService.reorder(body.getPhaseId(), body.getOrderedIds());
		return subphaseService.findByPhaseId(body.getPhaseId()).stream()
				.map(SubphaseMapper::toDTO)
				.collect(Collectors.toList());
	}
}
