package com.icesi.devopssandboxbackend.mapper;

import com.icesi.devopssandboxbackend.domain.model.Methodology;
import com.icesi.devopssandboxbackend.dto.MethodologyDTO;

public class MethodologyMapper {
    public static MethodologyDTO toDTO(Methodology entity) {
        if (entity == null)
            return null;
        MethodologyDTO dto = new MethodologyDTO();
        dto.setId(entity.getId());
        dto.setSlug(entity.getSlug());
        dto.setName(entity.getName());
        dto.setIntroduction(entity.getIntroduction());
        dto.setSummary(entity.getSummary());
        dto.setStatus(entity.getStatus());
        dto.setVersion(entity.getVersion());
        dto.setLanguageCode(entity.getLanguageCode());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public static Methodology toEntity(MethodologyDTO dto) {
        if (dto == null)
            return null;
        Methodology entity = new Methodology();
        entity.setId(dto.getId());
        entity.setSlug(dto.getSlug());
        entity.setName(dto.getName());
        entity.setIntroduction(dto.getIntroduction());
        entity.setSummary(dto.getSummary());
        entity.setStatus(dto.getStatus());
        entity.setVersion(dto.getVersion());
        entity.setLanguageCode(dto.getLanguageCode());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }
}
