package com.icesi.devopssandboxbackend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.icesi.devopssandboxbackend.domain.model.guide.Guide;
import com.icesi.devopssandboxbackend.domain.model.guide.Phase;
import com.icesi.devopssandboxbackend.domain.model.guide.PhaseStep;
import com.icesi.devopssandboxbackend.domain.model.guide.StepResource;
import com.icesi.devopssandboxbackend.dto.guide.GuideResponse;
import com.icesi.devopssandboxbackend.dto.guide.PhaseResponse;
import com.icesi.devopssandboxbackend.dto.guide.PhaseStepResponse;
import com.icesi.devopssandboxbackend.dto.guide.StepResourceResponse;

@Component
public class GuideMapper {

	public List<GuideResponse> toGuideResponses(List<Guide> guides) {
		return guides.stream().map(this::toGuideResponse).toList();
	}

	public GuideResponse toGuideResponse(Guide guide) {
		var response = new GuideResponse();
		response.setId(guide.getSlug());
		response.setName(guide.getName());
		response.setDescription(guide.getDescription());
		response.setIcon(guide.getIcon());
		response.setPhases(guide.getPhases().stream().map(this::toPhaseResponse).toList());
		return response;
	}

	public PhaseResponse toPhaseResponse(Phase phase) {
		var response = new PhaseResponse();
		response.setId(phase.getSlug());
		response.setTitle(phase.getTitle());
		response.setDescription(phase.getDescription());
		response.setDuration(phase.getDuration());
		response.setObjectives(List.copyOf(phase.getObjectives()));
		response.setDeliverables(List.copyOf(phase.getDeliverables()));
		response.setSteps(phase.getSteps().stream().map(this::toStepResponse).toList());
		return response;
	}

	private PhaseStepResponse toStepResponse(PhaseStep step) {
		var response = new PhaseStepResponse();
		response.setTitle(step.getTitle());
		response.setDescription(step.getDescription());
		response.setResources(step.getResources().stream().map(this::toStepResourceResponse).toList());
		return response;
	}

	private StepResourceResponse toStepResourceResponse(StepResource resource) {
		var response = new StepResourceResponse();
		response.setType(resource.getType().name().toLowerCase());
		response.setTitle(resource.getTitle());
		response.setDuration(resource.getDuration());
		response.setFormat(resource.getFormat());
		return response;
	}
}

