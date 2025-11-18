package com.icesi.devopssandboxbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI backendOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("DevOps Sandbox Backend")
						.description("API para la planeación y seguimiento de proyectos Lovable")
						.version("v1.0.0")
						.contact(new Contact().name("Equipo DevOps Icesi").email("devops@icesi.edu.co"))
						.license(new License().name("Apache 2.0")))
				.externalDocs(new ExternalDocumentation()
						.description("Proyecto Lovable")
						.url("https://lovable.dev/projects/8c93f12a-da90-400c-816c-dffb27a53c6c"));
	}
}

