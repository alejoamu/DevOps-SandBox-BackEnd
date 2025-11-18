package com.icesi.devopssandboxbackend.service;

import java.util.UUID;

import com.icesi.devopssandboxbackend.domain.enums.ProjectStatus;
import com.icesi.devopssandboxbackend.dto.request.ProjectRequest;
import com.icesi.devopssandboxbackend.dto.response.PageResponse;
import com.icesi.devopssandboxbackend.dto.response.ProjectResponse;

public interface ProjectService {

	ProjectResponse create(ProjectRequest request);

	ProjectResponse update(UUID id, ProjectRequest request);

	void delete(UUID id);

	ProjectResponse get(UUID id);

	PageResponse<ProjectResponse> list(ProjectStatus status, int page, int size);
}

