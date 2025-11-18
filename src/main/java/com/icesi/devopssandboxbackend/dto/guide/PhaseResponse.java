package com.icesi.devopssandboxbackend.dto.guide;

import java.util.List;

public class PhaseResponse {

	private String id;
	private String title;
	private String description;
	private String duration;
	private List<String> objectives;
	private List<String> deliverables;
	private List<PhaseStepResponse> steps;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<PhaseStepResponse> getSteps() {
		return steps;
	}

	public void setSteps(List<PhaseStepResponse> steps) {
		this.steps = steps;
	}
}

