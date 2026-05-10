package com.icesi.devopssandboxbackend.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.icesi.devopssandboxbackend.domain.model.User;
import com.icesi.devopssandboxbackend.domain.repository.UserRepository;

@Service
public class AdminAuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AdminAuthService(
			UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Verifica credenciales únicamente contra la tabla {@code users} de la BD.
	 * Soporta hashes BCrypt y, para entornos legacy, comparación en texto plano
	 * (constant-time).
	 */
	public boolean matches(String username, String password) {
		if (username == null || password == null) {
			return false;
		}
		Optional<User> dbUser = userRepository.findByUsername(username);
		if (dbUser.isEmpty()) {
			return false;
		}
		String stored = dbUser.get().getPassword();
		if (stored == null || stored.isBlank()) {
			return false;
		}
		if (looksLikeBcrypt(stored)) {
			return passwordEncoder.matches(password, stored);
		}
		return constantTimeEquals(stored, password);
	}

	private static boolean looksLikeBcrypt(String value) {
		return value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$");
	}

	private static boolean constantTimeEquals(String a, String b) {
		if (a == null || b == null) {
			return false;
		}
		byte[] x = a.getBytes(StandardCharsets.UTF_8);
		byte[] y = b.getBytes(StandardCharsets.UTF_8);
		return MessageDigest.isEqual(x, y);
	}
}
