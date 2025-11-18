package com.icesi.devopssandboxbackend.service;

import java.util.List;

import com.icesi.devopssandboxbackend.dto.guide.GuideResponse;
import com.icesi.devopssandboxbackend.dto.guide.PhaseResponse;

public interface GuideContentService {

	List<GuideResponse> listGuides();

	GuideResponse getGuide(String guideSlug);

	PhaseResponse getPhase(String guideSlug, String phaseSlug);
}

