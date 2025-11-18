package com.icesi.devopssandboxbackend.domain.model;

import java.util.UUID;

import com.icesi.devopssandboxbackend.domain.enums.ProjectStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 120)
	private String name;

	@Column(length = 500)
	private String description;

	@Column(nullable = false, length = 120)
	private String owner;

	@Column(name = "lovable_url", length = 255)
	private String lovableUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private ProjectStatus status = ProjectStatus.DRAFT;

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
}

