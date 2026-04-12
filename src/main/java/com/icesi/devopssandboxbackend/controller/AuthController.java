package com.icesi.devopssandboxbackend.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.icesi.devopssandboxbackend.config.AdminSecurityProperties;
import com.icesi.devopssandboxbackend.dto.AdminMeResponse;
import com.icesi.devopssandboxbackend.dto.AuthResponse;
import com.icesi.devopssandboxbackend.dto.LoginRequest;
import com.icesi.devopssandboxbackend.security.JwtService;
import com.icesi.devopssandboxbackend.service.AdminAuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AdminAuthService adminAuthService;
	private final JwtService jwtService;
	private final AdminSecurityProperties securityProperties;

	public AuthController(
			AdminAuthService adminAuthService,
			JwtService jwtService,
			AdminSecurityProperties securityProperties) {
		this.adminAuthService = adminAuthService;
		this.jwtService = jwtService;
		this.securityProperties = securityProperties;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest body) {
		if (!adminAuthService.matches(body.getUsername(), body.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
		}
		String token = jwtService.generateAccessToken(body.getUsername());
		long expiresInSeconds = Math.max(1L, securityProperties.getJwt().getExpirationMs() / 1000L);
		AuthResponse res = new AuthResponse(token, "Bearer", expiresInSeconds, "ADMIN");
		return ResponseEntity.ok(res);
	}

	@GetMapping("/me")
	public ResponseEntity<AdminMeResponse> me(Principal principal) {
		if (principal == null || principal.getName() == null || principal.getName().isBlank()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autenticado");
		}
		return ResponseEntity.ok(new AdminMeResponse(principal.getName(), "ADMIN"));
	}
}
