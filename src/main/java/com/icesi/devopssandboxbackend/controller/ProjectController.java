package com.icesi.devopssandboxbackend.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icesi.devopssandboxbackend.domain.enums.ProjectStatus;
import com.icesi.devopssandboxbackend.dto.request.ProjectRequest;
import com.icesi.devopssandboxbackend.dto.response.PageResponse;
import com.icesi.devopssandboxbackend.dto.response.ProjectResponse;
import com.icesi.devopssandboxbackend.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@PostMapping
	public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(request));
	}

	@PutMapping("/{projectId}")
	public ResponseEntity<ProjectResponse> update(
			@PathVariable UUID projectId,
			@Valid @RequestBody ProjectRequest request) {
		return ResponseEntity.ok(projectService.update(projectId, request));
	}

	@GetMapping("/{projectId}")
	public ResponseEntity<ProjectResponse> get(@PathVariable UUID projectId) {
		return ResponseEntity.ok(projectService.get(projectId));
	}

	@DeleteMapping("/{projectId}")
	public ResponseEntity<Void> delete(@PathVariable UUID projectId) {
		projectService.delete(projectId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<PageResponse<ProjectResponse>> list(
			@RequestParam(required = false) ProjectStatus status,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(projectService.list(status, page, size));
	}
}

