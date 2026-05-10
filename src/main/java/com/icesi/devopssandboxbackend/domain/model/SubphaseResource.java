package com.icesi.devopssandboxbackend.domain.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "subphase_resources",
        uniqueConstraints = @UniqueConstraint(
                name = "subphase_resources_subphase_id_order_index_key",
                columnNames = {"subphase_id", "order_index"}
        )
)
public class SubphaseResource {

    @EmbeddedId
    private SubphaseResourceId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("subphaseId")
    @JoinColumn(name = "subphase_id", nullable = false,
            foreignKey = @ForeignKey(name = "subphase_resources_subphase_id_fkey"))
    private Subphase subphase;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("resourceId")
    @JoinColumn(name = "resource_id", nullable = false,
            foreignKey = @ForeignKey(name = "subphase_resources_resource_id_fkey"))
    private Resource resource;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex = 1;

    @Column(name = "note", length = 255)
    private String note;

    @PrePersist
    void onCreate() {
        if (orderIndex == null) orderIndex = 1;
    }

    public SubphaseResourceId getId() { return id; }
    public void setId(SubphaseResourceId id) { this.id = id; }

    public Subphase getSubphase() { return subphase; }
    public void setSubphase(Subphase subphase) { this.subphase = subphase; }

    public Resource getResource() { return resource; }
    public void setResource(Resource resource) { this.resource = resource; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
