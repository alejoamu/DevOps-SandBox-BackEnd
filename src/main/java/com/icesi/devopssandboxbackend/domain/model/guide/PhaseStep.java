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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "phase_steps")
public class PhaseStep extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "phase_id", nullable = false)
	private Phase phase;

	@Column(nullable = false, length = 160)
	private String title;

	@Column(nullable = false, length = 750)
	private String description;

	@Column(name = "order_index", nullable = false)
	private int orderIndex;

	@OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@OrderBy("orderIndex ASC")
	private List<StepResource> resources = new ArrayList<>();

	public UUID getId() {
		return id;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public List<StepResource> getResources() {
		return resources;
	}

	public void setResources(List<StepResource> resources) {
		this.resources = resources;
	}
}

