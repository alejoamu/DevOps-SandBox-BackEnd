package com.icesi.devopssandboxbackend.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SubphaseResourceId implements Serializable {
    @Column(name = "subphase_id")
    private UUID subphaseId;

    @Column(name = "resource_id")
    private UUID resourceId;

    public SubphaseResourceId() {}
    public SubphaseResourceId(UUID subphaseId, UUID resourceId) {
        this.subphaseId = subphaseId;
        this.resourceId = resourceId;
    }
    public UUID getSubphaseId() { return subphaseId; }
    public void setSubphaseId(UUID subphaseId) { this.subphaseId = subphaseId; }
    public UUID getResourceId() { return resourceId; }
    public void setResourceId(UUID resourceId) { this.resourceId = resourceId; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubphaseResourceId that = (SubphaseResourceId) o;
        return Objects.equals(subphaseId, that.subphaseId) && Objects.equals(resourceId, that.resourceId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(subphaseId, resourceId);
    }
}