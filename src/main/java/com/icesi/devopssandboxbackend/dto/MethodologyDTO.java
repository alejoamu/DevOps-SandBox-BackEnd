package com.icesi.devopssandboxbackend.dto;

import com.icesi.devopssandboxbackend.domain.enums.MethodologyStatus;
import java.time.OffsetDateTime;
import java.util.UUID;

public class MethodologyDTO {

    private UUID id;
    private String slug;
    private String name;
    private String introduction;
    private String summary;
    private MethodologyStatus status;
    private Integer version;
    private String languageCode;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public java.util.UUID getId() {
        return id;
    }

    public void setId(java.util.UUID id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public com.icesi.devopssandboxbackend.domain.enums.MethodologyStatus getStatus() {
        return status;
    }

    public void setStatus(com.icesi.devopssandboxbackend.domain.enums.MethodologyStatus status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
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
