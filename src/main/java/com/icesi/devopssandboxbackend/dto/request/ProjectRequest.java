package com.icesi.devopssandboxbackend.dto.request;

import com.icesi.devopssandboxbackend.domain.enums.ProjectStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProjectRequest {

	@NotBlank
	@Size(max = 120)
	private String name;

	@Size(max = 500)
	private String description;

	@NotBlank
	@Size(max = 120)
	private String owner;

	@Size(max = 255)
	private String lovableUrl;

	@NotNull
	private ProjectStatus status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLovableUrl() {
		return lovableUrl;
	}

	public void setLovableUrl(String lovableUrl) {
		this.lovableUrl = lovableUrl;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}
}

