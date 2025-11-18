package com.icesi.devopssandboxbackend.domain.model.content;

import java.util.UUID;

import com.icesi.devopssandboxbackend.domain.enums.ResourceType;
import com.icesi.devopssandboxbackend.domain.model.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "support_resources")
public class SupportResource extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 160)
	private String title;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ResourceType type;

	@Column(nullable = false, length = 40)
	private String category;

	@Column(nullable = false, length = 500)
	private String description;

	@Column(length = 20)
	private String format;

	@Column(length = 20)
	private String duration;

	@Column(name = "order_index", nullable = false)
	private int orderIndex;

	public UUID getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
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
}

