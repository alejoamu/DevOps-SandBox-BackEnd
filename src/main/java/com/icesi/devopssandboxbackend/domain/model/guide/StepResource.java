package com.icesi.devopssandboxbackend.domain.model.guide;

import java.util.UUID;

import com.icesi.devopssandboxbackend.domain.enums.ResourceType;
import com.icesi.devopssandboxbackend.domain.model.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "step_resources")
public class StepResource extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "step_id", nullable = false)
	private PhaseStep step;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ResourceType type;

	@Column(nullable = false, length = 200)
	private String title;

	@Column(length = 20)
	private String duration;

	@Column(length = 20)
	private String format;

	@Column(name = "order_index", nullable = false)
	private int orderIndex;

	public UUID getId() {
		return id;
	}

	public PhaseStep getStep() {
		return step;
	}

	public void setStep(PhaseStep step) {
		this.step = step;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
}

