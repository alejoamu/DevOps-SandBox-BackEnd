package com.icesi.devopssandboxbackend.mapper;

import com.icesi.devopssandboxbackend.domain.model.Phase;
import com.icesi.devopssandboxbackend.dto.PhaseDTO;

public class PhaseMapper {
    public static PhaseDTO toDTO(Phase entity) {
        if (entity == null)
            return null;
        PhaseDTO dto = new PhaseDTO();
        dto.setId(entity.getId());
        dto.setMethodologyId(entity.getMethodology() != null ? entity.getMethodology().getId() : null);
        dto.setCode(entity.getCode());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setOrderIndex(entity.getOrderIndex());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public static Phase toEntity(PhaseDTO dto) {
        if (dto == null)
            return null;
        Phase entity = new Phase();
        entity.setId(dto.getId());
        // La relación con Methodology debe ser seteada en el servicio si es necesario
        entity.setCode(dto.getCode());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setOrderIndex(dto.getOrderIndex());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }
}
