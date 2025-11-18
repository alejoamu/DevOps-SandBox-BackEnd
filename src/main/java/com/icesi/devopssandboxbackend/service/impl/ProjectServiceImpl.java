package com.icesi.devopssandboxbackend.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.devopssandboxbackend.domain.enums.ProjectStatus;
import com.icesi.devopssandboxbackend.domain.model.Project;
import com.icesi.devopssandboxbackend.domain.repository.ProjectRepository;
import com.icesi.devopssandboxbackend.dto.request.ProjectRequest;
import com.icesi.devopssandboxbackend.dto.response.PageResponse;
import com.icesi.devopssandboxbackend.dto.response.ProjectResponse;
import com.icesi.devopssandboxbackend.exception.ResourceNotFoundException;
import com.icesi.devopssandboxbackend.mapper.ProjectMapper;
import com.icesi.devopssandboxbackend.service.ProjectService;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	private final ProjectMapper projectMapper;

	public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
		this.projectRepository = projectRepository;
		this.projectMapper = projectMapper;
	}

	@Override
	public ProjectResponse create(ProjectRequest request) {
		Project project = projectMapper.toEntity(request);
		project = projectRepository.save(project);
		return projectMapper.toResponse(project);
	}

	@Override
	public ProjectResponse update(UUID id, ProjectRequest request) {
		Project project = findById(id);
		projectMapper.update(project, request);
		return projectMapper.toResponse(project);
	}

	@Override
	public void delete(UUID id) {
		Project project = findById(id);
		projectRepository.delete(project);
	}

	@Override
	@Transactional(readOnly = true)
	public ProjectResponse get(UUID id) {
		return projectMapper.toResponse(findById(id));
	}

	@Override
	@Transactional(readOnly = true)
	public PageResponse<ProjectResponse> list(ProjectStatus status, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
		Page<Project> projects = status == null
				? projectRepository.findAll(pageable)
				: projectRepository.findByStatus(status, pageable);

		return new PageResponse<>(
				projectMapper.toResponseList(projects.getContent()),
				projects.getTotalElements(),
				projects.getTotalPages(),
				projects.getNumber(),
				projects.getSize());
	}

	private Project findById(UUID id) {
		return projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project %s not found".formatted(id)));
	}
}

