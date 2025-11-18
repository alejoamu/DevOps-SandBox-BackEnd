package com.icesi.devopssandboxbackend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.devopssandboxbackend.domain.model.content.SupportResource;
import com.icesi.devopssandboxbackend.domain.repository.content.SupportResourceRepository;
import com.icesi.devopssandboxbackend.dto.content.SupportResourceResponse;
import com.icesi.devopssandboxbackend.service.SupportResourceService;
import com.icesi.devopssandboxbackend.service.mock.MockDataProvider;

@Service
public class SupportResourceServiceImpl implements SupportResourceService {

	private final SupportResourceRepository supportResourceRepository;

	public SupportResourceServiceImpl(SupportResourceRepository supportResourceRepository) {
		this.supportResourceRepository = supportResourceRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<SupportResourceResponse> list() {
		try {
			long count = supportResourceRepository.count();
			if (count > 0) {
				List<SupportResource> resources = supportResourceRepository.findAllOrdered();
				return resources.stream().map(this::toResponse).toList();
			}
		} catch (Exception e) {
			// Si hay error con BD, usar datos mockeados
			return MockDataProvider.getMockResources();
		}
		// Si no hay datos en BD, devolver datos mockeados
		return MockDataProvider.getMockResources();
	}

	private SupportResourceResponse toResponse(SupportResource resource) {
		var response = new SupportResourceResponse();
		response.setTitle(resource.getTitle());
		response.setType(resource.getType().name().toLowerCase());
		response.setCategory(resource.getCategory());
		response.setDescription(resource.getDescription());
		response.setFormat(resource.getFormat());
		response.setDuration(resource.getDuration());
		return response;
	}
}

