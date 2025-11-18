package com.icesi.devopssandboxbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icesi.devopssandboxbackend.dto.content.SupportResourceResponse;
import com.icesi.devopssandboxbackend.service.SupportResourceService;

@RestController
@RequestMapping("/api/v1/resources")
public class SupportResourceController {

	private final SupportResourceService supportResourceService;

	public SupportResourceController(SupportResourceService supportResourceService) {
		this.supportResourceService = supportResourceService;
	}

	@GetMapping
	public ResponseEntity<List<SupportResourceResponse>> listResources() {
		return ResponseEntity.ok(supportResourceService.list());
	}
}

