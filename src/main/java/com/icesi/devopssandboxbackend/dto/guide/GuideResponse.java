package com.icesi.devopssandboxbackend.dto.guide;

import java.util.List;

public class GuideResponse {

	private String id;
	private String name;
	private String description;
	private String icon;
	private List<PhaseResponse> phases;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<PhaseResponse> getPhases() {
		return phases;
	}

	public void setPhases(List<PhaseResponse> phases) {
		this.phases = phases;
	}
}

