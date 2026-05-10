package com.icesi.devopssandboxbackend.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.icesi.devopssandboxbackend.config.AdminSecurityProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final AdminSecurityProperties props;

	public JwtService(AdminSecurityProperties props) {
		this.props = props;
	}

	public String generateAccessToken(String username) {
		var now = new Date();
		var exp = new Date(now.getTime() + props.getJwt().getExpirationMs());
		return Jwts.builder()
				.subject(username)
				.issuedAt(now)
				.expiration(exp)
				.claim("role", "ADMIN")
				.signWith(signingKey())
				.compact();
	}

	public String extractUsername(String token) {
		return parseClaims(token).getSubject();
	}

	public boolean isTokenValid(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<String> extractRoles(String token) {
		Object role = parseClaims(token).get("role");
		if (role instanceof String s) {
			return List.of("ROLE_" + s);
		}
		if (role instanceof List<?> list) {
			return list.stream().map(Object::toString).map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r).toList();
		}
		return List.of();
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
				.verifyWith(signingKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private SecretKey signingKey() {
		String secret = props.getJwt().getSecret().trim();
		if (secret.startsWith("base64:")) {
			byte[] keyBytes = Decoders.BASE64.decode(secret.substring("base64:".length()).trim());
			return Keys.hmacShaKeyFor(keyBytes);
		}
		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
