package com.icesi.devopssandboxbackend.dto;

import java.util.UUID;

public class SubphaseResourceDTO {

    private UUID subphaseId;
    private UUID resourceId;
    private Integer orderIndex;
    private String note;

    public java.util.UUID getSubphaseId() {
        return subphaseId;
    }

    public void setSubphaseId(java.util.UUID subphaseId) {
        this.subphaseId = subphaseId;
    }

    public java.util.UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(java.util.UUID resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
