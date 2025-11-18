package com.icesi.devopssandboxbackend.dto.response;

import java.time.Instant;
import java.util.UUID;

import com.icesi.devopssandboxbackend.domain.enums.ProjectStatus;

public class ProjectResponse {

	private UUID id;
	private String name;
	private String description;
	private String owner;
	private String lovableUrl;
	private ProjectStatus status;
	private Instant createdAt;
	private Instant updatedAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}

