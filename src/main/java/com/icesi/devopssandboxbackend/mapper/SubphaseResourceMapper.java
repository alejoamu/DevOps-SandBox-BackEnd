package com.icesi.devopssandboxbackend.mapper;

import com.icesi.devopssandboxbackend.domain.model.SubphaseResource;
import com.icesi.devopssandboxbackend.domain.model.SubphaseResourceId;
import com.icesi.devopssandboxbackend.dto.SubphaseResourceDTO;

public class SubphaseResourceMapper {
    public static SubphaseResourceDTO toDTO(SubphaseResource entity) {
        if (entity == null)
            return null;
        SubphaseResourceDTO dto = new SubphaseResourceDTO();
        dto.setSubphaseId(entity.getId() != null ? entity.getId().getSubphaseId() : null);
        dto.setResourceId(entity.getId() != null ? entity.getId().getResourceId() : null);
        dto.setOrderIndex(entity.getOrderIndex());
        dto.setNote(entity.getNote());
        return dto;
    }

    public static SubphaseResource toEntity(SubphaseResourceDTO dto) {
        if (dto == null)
            return null;
        SubphaseResource entity = new SubphaseResource();
        entity.setId(new SubphaseResourceId(dto.getSubphaseId(), dto.getResourceId()));
        entity.setOrderIndex(dto.getOrderIndex());
        entity.setNote(dto.getNote());
        return entity;
    }
}
