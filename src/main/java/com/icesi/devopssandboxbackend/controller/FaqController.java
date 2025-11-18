package com.icesi.devopssandboxbackend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icesi.devopssandboxbackend.dto.content.FaqResponse;
import com.icesi.devopssandboxbackend.service.FaqService;

@RestController
@RequestMapping("/api/v1/faqs")
public class FaqController {

	private final FaqService faqService;

	public FaqController(FaqService faqService) {
		this.faqService = faqService;
	}

	@GetMapping
	public ResponseEntity<List<FaqResponse>> listFaqs() {
		return ResponseEntity.ok(faqService.list());
	}
}

