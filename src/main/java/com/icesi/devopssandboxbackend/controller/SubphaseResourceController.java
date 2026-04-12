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

import com.icesi.devopssandboxbackend.domain.model.Resource;
import com.icesi.devopssandboxbackend.domain.model.Subphase;
import com.icesi.devopssandboxbackend.domain.model.SubphaseResource;
import com.icesi.devopssandboxbackend.dto.SubphaseResourceDTO;
import com.icesi.devopssandboxbackend.exception.ResourceNotFoundException;
import com.icesi.devopssandboxbackend.mapper.SubphaseResourceMapper;
import com.icesi.devopssandboxbackend.service.ResourceService;
import com.icesi.devopssandboxbackend.service.SubphaseResourceService;
import com.icesi.devopssandboxbackend.service.SubphaseService;

@RestController
@RequestMapping("/api/subphase-resources")
public class SubphaseResourceController {
	@Autowired
	private SubphaseResourceService subphaseResourceService;

	@Autowired
	private SubphaseService subphaseService;

	@Autowired
	private ResourceService resourceService;

	@GetMapping
	public List<SubphaseResourceDTO> getAll() {
		return subphaseResourceService.findAll().stream().map(SubphaseResourceMapper::toDTO)
				.collect(Collectors.toList());
	}

	@GetMapping("/subphase/{subphaseId}")
	public List<SubphaseResourceDTO> getBySubphase(@PathVariable UUID subphaseId) {
		return subphaseResourceService.findBySubphaseId(subphaseId).stream().map(SubphaseResourceMapper::toDTO)
				.collect(Collectors.toList());
	}

	@GetMapping("/resource/{resourceId}")
	public List<SubphaseResourceDTO> getByResource(@PathVariable UUID resourceId) {
		return subphaseResourceService.findByResourceId(resourceId).stream().map(SubphaseResourceMapper::toDTO)
				.collect(Collectors.toList());
	}

	@GetMapping("/{subphaseId}/{resourceId}")
	public SubphaseResourceDTO getById(@PathVariable UUID subphaseId, @PathVariable UUID resourceId) {
		return SubphaseResourceMapper.toDTO(subphaseResourceService.findById(subphaseId, resourceId));
	}

	@PostMapping
	public SubphaseResourceDTO create(@RequestBody SubphaseResourceDTO dto) {
		Subphase subphase = subphaseService.findById(dto.getSubphaseId());
		Resource resource = resourceService.findById(dto.getResourceId());
		if (subphase == null) {
			throw new ResourceNotFoundException("Subfase no encontrada.");
		}
		if (resource == null) {
			throw new ResourceNotFoundException("Recurso no encontrado.");
		}
		SubphaseResource entity = SubphaseResourceMapper.toEntity(dto);
		entity.setSubphase(subphase);
		entity.setResource(resource);
		return SubphaseResourceMapper.toDTO(subphaseResourceService.save(entity));
	}

	@PutMapping("/{subphaseId}/{resourceId}")
	public SubphaseResourceDTO update(@PathVariable UUID subphaseId, @PathVariable UUID resourceId,
			@RequestBody SubphaseResourceDTO dto) {
		dto.setSubphaseId(subphaseId);
		dto.setResourceId(resourceId);
		Subphase subphase = subphaseService.findById(subphaseId);
		Resource resource = resourceService.findById(resourceId);
		if (subphase == null || resource == null) {
			throw new ResourceNotFoundException("Subfase o recurso no encontrado.");
		}
		SubphaseResource entity = SubphaseResourceMapper.toEntity(dto);
		entity.setSubphase(subphase);
		entity.setResource(resource);
		return SubphaseResourceMapper.toDTO(subphaseResourceService.save(entity));
	}

	@DeleteMapping("/{subphaseId}/{resourceId}")
	public void delete(@PathVariable UUID subphaseId, @PathVariable UUID resourceId) {
		subphaseResourceService.deleteById(subphaseId, resourceId);
	}
}
