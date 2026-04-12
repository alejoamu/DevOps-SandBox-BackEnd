package com.icesi.devopssandboxbackend.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;

import com.icesi.devopssandboxbackend.config.AdminSecurityProperties;

@Service
public class AdminAuthService {

	private final AdminSecurityProperties props;

	public AdminAuthService(AdminSecurityProperties props) {
		this.props = props;
	}

	public boolean matches(String username, String password) {
		var admin = props.getAdmin();
		return constantTimeEquals(admin.getUsername(), username)
				&& constantTimeEquals(admin.getPassword(), password);
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
