package com.icesi.devopssandboxbackend.domain.repository.content;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icesi.devopssandboxbackend.domain.model.content.FaqEntry;

public interface FaqEntryRepository extends JpaRepository<FaqEntry, UUID> {
}

