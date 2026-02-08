package com.icesi.devopssandboxbackend.mapper;

import com.icesi.devopssandboxbackend.domain.model.Resource;
import com.icesi.devopssandboxbackend.dto.ResourceDTO;

public class ResourceMapper {
    public static ResourceDTO toDTO(Resource entity) {
        if (entity == null)
            return null;
        ResourceDTO dto = new ResourceDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setTitle(entity.getTitle());
        dto.setUrl(entity.getUrl());
        dto.setDescription(entity.getDescription());
        dto.setProvider(entity.getProvider());
        dto.setThumbnailUrl(entity.getThumbnailUrl());
        dto.setMetadata(entity.getMetadata());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public static Resource toEntity(ResourceDTO dto) {
        if (dto == null)
            return null;
        Resource entity = new Resource();
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        entity.setTitle(dto.getTitle());
        entity.setUrl(dto.getUrl());
        entity.setDescription(dto.getDescription());
        entity.setProvider(dto.getProvider());
        entity.setThumbnailUrl(dto.getThumbnailUrl());
        entity.setMetadata(dto.getMetadata());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }
}
