package com.icesi.devopssandboxbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icesi.devopssandboxbackend.dto.guide.GuideResponse;
import com.icesi.devopssandboxbackend.dto.guide.PhaseResponse;
import com.icesi.devopssandboxbackend.service.GuideContentService;

@RestController
@RequestMapping("/api/v1/guides")
public class GuideContentController {

	private final GuideContentService guideContentService;

	public GuideContentController(GuideContentService guideContentService) {
		this.guideContentService = guideContentService;
	}

	@GetMapping
	public ResponseEntity<List<GuideResponse>> listGuides() {
		return ResponseEntity.ok(guideContentService.listGuides());
	}

	@GetMapping("/{guideSlug}")
	public ResponseEntity<GuideResponse> getGuide(@PathVariable String guideSlug) {
		return ResponseEntity.ok(guideContentService.getGuide(guideSlug));
	}

	@GetMapping("/{guideSlug}/phases/{phaseSlug}")
	public ResponseEntity<PhaseResponse> getPhase(
			@PathVariable String guideSlug,
			@PathVariable String phaseSlug) {
		return ResponseEntity.ok(guideContentService.getPhase(guideSlug, phaseSlug));
	}
}

