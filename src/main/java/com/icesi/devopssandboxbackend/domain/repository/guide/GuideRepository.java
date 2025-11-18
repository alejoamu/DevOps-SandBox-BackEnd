package com.icesi.devopssandboxbackend.domain.repository.guide;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icesi.devopssandboxbackend.domain.model.guide.Guide;

public interface GuideRepository extends JpaRepository<Guide, UUID> {

	Optional<Guide> findBySlug(String slug);
}

