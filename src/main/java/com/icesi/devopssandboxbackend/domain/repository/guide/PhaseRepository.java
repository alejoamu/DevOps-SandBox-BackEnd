package com.icesi.devopssandboxbackend.domain.repository.guide;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.icesi.devopssandboxbackend.domain.model.guide.Phase;

public interface PhaseRepository extends JpaRepository<Phase, UUID> {

	@Query("SELECT p FROM Phase p JOIN FETCH p.guide g WHERE g.slug = :guideSlug AND p.slug = :phaseSlug")
	Optional<Phase> findDetailByGuideSlugAndSlug(@Param("guideSlug") String guideSlug, @Param("phaseSlug") String phaseSlug);
}

