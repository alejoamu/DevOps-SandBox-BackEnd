package com.icesi.devopssandboxbackend.service.impl;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.devopssandboxbackend.domain.model.Resource;
import com.icesi.devopssandboxbackend.domain.repository.ResourceRepository;
import com.icesi.devopssandboxbackend.exception.ResourceNotFoundException;
import com.icesi.devopssandboxbackend.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public Resource findById(UUID id) {
		return resourceRepository.findById(id).orElse(null);
	}

	@Override
	public List<Resource> findAll() {
		return resourceRepository.findAll();
	}

	@Override
	@Transactional
	public Resource save(Resource incoming) {
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		if (incoming.getId() == null) {
			if (incoming.getMetadata() == null || incoming.getMetadata().isBlank()) {
				incoming.setMetadata("{}");
			}
			incoming.setCreatedAt(now);
			incoming.setUpdatedAt(now);
			return resourceRepository.save(incoming);
		}
		Resource existing = resourceRepository.findById(incoming.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado."));
		existing.setType(incoming.getType());
		existing.setTitle(incoming.getTitle());
		existing.setUrl(incoming.getUrl());
		existing.setDescription(incoming.getDescription());
		existing.setProvider(incoming.getProvider());
		existing.setThumbnailUrl(incoming.getThumbnailUrl());
		if (incoming.getMetadata() != null && !incoming.getMetadata().isBlank()) {
			existing.setMetadata(incoming.getMetadata());
		}
		existing.setUpdatedAt(now);
		return resourceRepository.save(existing);
	}

	@Override
	public void deleteById(UUID id) {
		resourceRepository.deleteById(id);
	}
}
