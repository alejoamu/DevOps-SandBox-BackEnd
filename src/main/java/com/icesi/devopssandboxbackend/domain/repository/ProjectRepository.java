package com.icesi.devopssandboxbackend.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.icesi.devopssandboxbackend.domain.enums.ProjectStatus;
import com.icesi.devopssandboxbackend.domain.model.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

	Page<Project> findByStatus(ProjectStatus status, Pageable pageable);

	Optional<Project> findByLovableUrl(String lovableUrl);
}

