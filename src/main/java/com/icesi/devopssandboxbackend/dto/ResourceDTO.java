package com.icesi.devopssandboxbackend.dto;

import com.icesi.devopssandboxbackend.domain.enums.ResourceType;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ResourceDTO {

    private UUID id;
    private ResourceType type;
    private String title;
    private String url;
    private String description;
    private String provider;
    private String thumbnailUrl;
    private String metadata;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public java.util.UUID getId() {
        return id;
    }

    public void setId(java.util.UUID id) {
        this.id = id;
    }

    public com.icesi.devopssandboxbackend.domain.enums.ResourceType getType() {
        return type;
    }

    public void setType(com.icesi.devopssandboxbackend.domain.enums.ResourceType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public java.time.OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.time.OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public java.time.OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.time.OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
