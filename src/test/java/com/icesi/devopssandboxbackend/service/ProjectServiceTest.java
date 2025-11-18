package com.icesi.devopssandboxbackend.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.icesi.devopssandboxbackend.domain.enums.ProjectStatus;
import com.icesi.devopssandboxbackend.dto.request.ProjectRequest;
import com.icesi.devopssandboxbackend.dto.response.PageResponse;
import com.icesi.devopssandboxbackend.dto.response.ProjectResponse;

@SpringBootTest
@Transactional
class ProjectServiceTest {

	@Autowired
	private ProjectService projectService;

	@Test
	void shouldCreateAndListProjects() {
		var request = new ProjectRequest();
		request.setName("Sandbox API");
		request.setDescription("Backend for Lovable integration");
		request.setOwner("Equipo Backend");
		request.setLovableUrl("https://lovable.dev/projects/sample");
		request.setStatus(ProjectStatus.IN_PROGRESS);

		ProjectResponse created = projectService.create(request);
		assertThat(created.getId()).isNotNull();
		assertThat(created.getStatus()).isEqualTo(ProjectStatus.IN_PROGRESS);

		PageResponse<ProjectResponse> page = projectService.list(null, 0, 10);
		assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(1);
		assertThat(page.getItems()).extracting(ProjectResponse::getName).contains("Sandbox API");
	}
}

