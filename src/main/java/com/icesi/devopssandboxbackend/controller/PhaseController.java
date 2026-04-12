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

import com.icesi.devopssandboxbackend.domain.model.Methodology;
import com.icesi.devopssandboxbackend.domain.model.Phase;
import com.icesi.devopssandboxbackend.dto.PhaseDTO;
import com.icesi.devopssandboxbackend.exception.ResourceNotFoundException;
import com.icesi.devopssandboxbackend.mapper.PhaseMapper;
import com.icesi.devopssandboxbackend.service.MethodologyService;
import com.icesi.devopssandboxbackend.service.PhaseService;

@RestController
@RequestMapping("/api/phases")
public class PhaseController {
	@Autowired
	private PhaseService phaseService;

	@Autowired
	private MethodologyService methodologyService;

	@GetMapping
	public List<PhaseDTO> getAll() {
		return phaseService.findAll().stream().map(PhaseMapper::toDTO).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public PhaseDTO getById(@PathVariable UUID id) {
		return PhaseMapper.toDTO(phaseService.findById(id));
	}

	@GetMapping("/methodology/{methodologyId}")
	public List<PhaseDTO> getByMethodology(@PathVariable UUID methodologyId) {
		return phaseService.findByMethodologyId(methodologyId).stream().map(PhaseMapper::toDTO)
				.collect(Collectors.toList());
	}

	@PostMapping
	public PhaseDTO create(@RequestBody PhaseDTO dto) {
		if (dto.getMethodologyId() == null) {
			throw new IllegalArgumentException("methodologyId es obligatorio para crear una fase.");
		}
		Methodology methodology = methodologyService.findById(dto.getMethodologyId());
		if (methodology == null) {
			throw new ResourceNotFoundException("Metodología no encontrada.");
		}
		Phase entity = PhaseMapper.toEntity(dto);
		entity.setMethodology(methodology);
		return PhaseMapper.toDTO(phaseService.save(entity));
	}

	@PutMapping("/{id}")
	public PhaseDTO update(@PathVariable UUID id, @RequestBody PhaseDTO dto) {
		dto.setId(id);
		return PhaseMapper.toDTO(phaseService.save(PhaseMapper.toEntity(dto)));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		phaseService.deleteById(id);
	}
}
