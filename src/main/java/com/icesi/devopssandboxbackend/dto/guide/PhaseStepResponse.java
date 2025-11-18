package com.icesi.devopssandboxbackend.dto.guide;

import java.util.List;

public class PhaseStepResponse {

	private String title;
	private String description;
	private List<StepResourceResponse> resources;

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

	public List<StepResourceResponse> getResources() {
		return resources;
	}

	public void setResources(List<StepResourceResponse> resources) {
		this.resources = resources;
	}
}

