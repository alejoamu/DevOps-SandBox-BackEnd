package com.icesi.devopssandboxbackend.domain.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "subphase_resources")
public class SubphaseResource {
    @EmbeddedId
    private SubphaseResourceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subphaseId")
    @JoinColumn(name = "subphase_id", nullable = false)
    private Subphase subphase;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("resourceId")
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex = 1;

    @Column(name = "note")
    private String note;

    // Getters and setters
    public SubphaseResourceId getId() {
        return id;
    }

    public void setId(SubphaseResourceId id) {
        this.id = id;
    }

    public Subphase getSubphase() {
        return subphase;
    }

    public void setSubphase(Subphase subphase) {
        this.subphase = subphase;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
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