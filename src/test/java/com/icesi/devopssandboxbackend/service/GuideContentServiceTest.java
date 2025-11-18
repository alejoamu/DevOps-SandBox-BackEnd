package com.icesi.devopssandboxbackend.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.icesi.devopssandboxbackend.dto.guide.GuideResponse;
import com.icesi.devopssandboxbackend.dto.guide.PhaseResponse;

@SpringBootTest
class GuideContentServiceTest {

	@Autowired
	private GuideContentService guideContentService;

	@Test
	void shouldExposeGuidesForFrontend() {
		var guides = guideContentService.listGuides();
		assertThat(guides).isNotEmpty();

		GuideResponse iaGuide = guides.stream()
				.filter(guide -> guide.getId().equals("ia"))
				.findFirst()
				.orElseThrow();

		assertThat(iaGuide.getPhases()).isNotEmpty();
		assertThat(iaGuide.getPhases().get(0).getSteps()).isNotEmpty();
	}

	@Test
	void shouldReturnPhaseBySlug() {
		PhaseResponse phase = guideContentService.getPhase("iot", "fase3");
		assertThat(phase.getObjectives()).hasSizeGreaterThan(0);
		assertThat(phase.getSteps()).hasSizeGreaterThan(0);
	}
}

