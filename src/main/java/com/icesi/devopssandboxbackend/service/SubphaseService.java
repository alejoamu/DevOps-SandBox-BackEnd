package com.icesi.devopssandboxbackend.service;

import com.icesi.devopssandboxbackend.domain.model.Subphase;
import java.util.List;
import java.util.UUID;

public interface SubphaseService {

    Subphase findById(UUID id);

    List<Subphase> findAll();

    List<Subphase> findByPhaseId(UUID phaseId);

    Subphase save(Subphase subphase);

    void deleteById(UUID id);

    /**
     * Reordena atómicamente las subfases de una fase. {@code orderedIds}
     * define el nuevo orden (índice 0..n-1). Doble pasada para evitar
     * choques con el constraint UNIQUE (phase_id, order_index).
     */
    void reorder(UUID phaseId, java.util.List<UUID> orderedIds);
}
