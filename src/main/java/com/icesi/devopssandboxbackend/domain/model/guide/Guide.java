package com.icesi.devopssandboxbackend.domain.model.guide;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.icesi.devopssandboxbackend.domain.model.AuditableEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "guides")
public class Guide extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true, length = 60)
	private String slug;

	@Column(nullable = false, length = 120)
	private String name;

	@Column(nullable = false, length = 500)
	private String description;

	@Column(nullable = false, length = 60)
	private String icon;

	@OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@OrderBy("orderIndex ASC")
	private List<Phase> phases = new ArrayList<>();

	public UUID getId() {
		return id;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<Phase> getPhases() {
		return phases;
	}

	public void setPhases(List<Phase> phases) {
		this.phases = phases;
	}
}

