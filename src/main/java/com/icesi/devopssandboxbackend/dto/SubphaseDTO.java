package com.icesi.devopssandboxbackend.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public class SubphaseDTO {

    private UUID id;
    private UUID phaseId;
    private String code;
    private String title;
    private String content;
    private Integer orderIndex;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public java.util.UUID getId() {
        return id;
    }

    public void setId(java.util.UUID id) {
        this.id = id;
    }

    public java.util.UUID getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(java.util.UUID phaseId) {
        this.phaseId = phaseId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
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
