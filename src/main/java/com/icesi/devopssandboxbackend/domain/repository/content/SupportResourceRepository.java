package com.icesi.devopssandboxbackend.domain.repository.content;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.icesi.devopssandboxbackend.domain.model.content.SupportResource;

import java.util.List;

public interface SupportResourceRepository extends JpaRepository<SupportResource, UUID> {

	@Query("SELECT sr FROM SupportResource sr ORDER BY sr.orderIndex ASC")
	List<SupportResource> findAllOrdered();
}

