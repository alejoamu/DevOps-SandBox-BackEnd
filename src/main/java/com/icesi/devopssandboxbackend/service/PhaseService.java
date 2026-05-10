package com.icesi.devopssandboxbackend.service;

import com.icesi.devopssandboxbackend.domain.model.Phase;
import java.util.List;
import java.util.UUID;

public interface PhaseService {

    Phase findById(UUID id);

    List<Phase> findAll();

    List<Phase> findByMethodologyId(UUID methodologyId);

    Phase save(Phase phase);

    void deleteById(UUID id);

    /**
     * Reordena atómicamente las fases de una metodología. {@code orderedIds}
     * define el nuevo orden (índice 0..n-1). La operación es resistente al
     * constraint UNIQUE (methodology_id, order_index) gracias a una doble
     * pasada (rango temporal alto -> rango final).
     */
    void reorder(UUID methodologyId, java.util.List<UUID> orderedIds);
}