package com.icesi.devopssandboxbackend.mapper;

import com.icesi.devopssandboxbackend.domain.model.Subphase;
import com.icesi.devopssandboxbackend.dto.SubphaseDTO;

public class SubphaseMapper {
    public static SubphaseDTO toDTO(Subphase entity) {
        if (entity == null)
            return null;
        SubphaseDTO dto = new SubphaseDTO();
        dto.setId(entity.getId());
        dto.setPhaseId(entity.getPhase() != null ? entity.getPhase().getId() : null);
        dto.setCode(entity.getCode());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setOrderIndex(entity.getOrderIndex());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public static Subphase toEntity(SubphaseDTO dto) {
        if (dto == null)
            return null;
        Subphase entity = new Subphase();
        entity.setId(dto.getId());
        // La relación con Phase debe ser seteada en el servicio si es necesario
        entity.setCode(dto.getCode());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setOrderIndex(dto.getOrderIndex());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }
}
