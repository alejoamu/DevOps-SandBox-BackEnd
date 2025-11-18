package com.icesi.devopssandboxbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DevopsSandboxBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevopsSandboxBackendApplication.class, args);
	}
}

