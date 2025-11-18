package com.icesi.devopssandboxbackend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.icesi.devopssandboxbackend.domain.model.Project;
import com.icesi.devopssandboxbackend.dto.request.ProjectRequest;
import com.icesi.devopssandboxbackend.dto.response.ProjectResponse;

@Component
public class ProjectMapper {

	public Project toEntity(ProjectRequest request) {
		var project = new Project();
		project.setName(request.getName());
		project.setDescription(request.getDescription());
		project.setOwner(request.getOwner());
		project.setLovableUrl(request.getLovableUrl());
		project.setStatus(request.getStatus());
		return project;
	}

	public void update(Project project, ProjectRequest request) {
		project.setName(request.getName());
		project.setDescription(request.getDescription());
		project.setOwner(request.getOwner());
		project.setLovableUrl(request.getLovableUrl());
		project.setStatus(request.getStatus());
	}

	public ProjectResponse toResponse(Project project) {
		var response = new ProjectResponse();
		response.setId(project.getId());
		response.setName(project.getName());
		response.setDescription(project.getDescription());
		response.setOwner(project.getOwner());
		response.setLovableUrl(project.getLovableUrl());
		response.setStatus(project.getStatus());
		response.setCreatedAt(project.getCreatedAt());
		response.setUpdatedAt(project.getUpdatedAt());
		return response;
	}

	public List<ProjectResponse> toResponseList(List<Project> projects) {
		return projects.stream().map(this::toResponse).toList();
	}
}

