package com.icesi.devopssandboxbackend.domain.model.guide;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.icesi.devopssandboxbackend.domain.model.AuditableEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "guide_phases", uniqueConstraints = {
		@UniqueConstraint(name = "uk_phase_slug_guide", columnNames = { "guide_id", "slug" })
})
public class Phase extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 60)
	private String slug;

	@Column(nullable = false, length = 160)
	private String title;

	@Column(nullable = false, length = 1000)
	private String description;

	@Column(nullable = false, length = 60)
	private String duration;

	@Column(name = "order_index", nullable = false)
	private int orderIndex;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "guide_id", nullable = false)
	private Guide guide;

	@ElementCollection
	@CollectionTable(name = "phase_objectives", joinColumns = @JoinColumn(name = "phase_id"))
	@Column(name = "description", length = 500, nullable = false)
	@OrderColumn(name = "objective_order")
	private List<String> objectives = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "phase_deliverables", joinColumns = @JoinColumn(name = "phase_id"))
	@Column(name = "description", length = 500, nullable = false)
	@OrderColumn(name = "deliverable_order")
	private List<String> deliverables = new ArrayList<>();

	@OneToMany(mappedBy = "phase", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy("orderIndex ASC")
	private List<PhaseStep> steps = new ArrayList<>();

	public UUID getId() {
		return id;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Guide getGuide() {
		return guide;
	}

	public void setGuide(Guide guide) {
		this.guide = guide;
	}

	public List<String> getObjectives() {
		return objectives;
	}

	public void setObjectives(List<String> objectives) {
		this.objectives = objectives;
	}

	public List<String> getDeliverables() {
		return deliverables;
	}

	public void setDeliverables(List<String> deliverables) {
		this.deliverables = deliverables;
	}

	public List<PhaseStep> getSteps() {
		return steps;
	}

	public void setSteps(List<PhaseStep> steps) {
		this.steps = steps;
	}
}

