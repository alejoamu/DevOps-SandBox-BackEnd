package com.icesi.devopssandboxbackend.service;

import com.icesi.devopssandboxbackend.domain.model.Methodology;
import java.util.List;
import java.util.UUID;

public interface MethodologyService {

    Methodology findById(UUID id);

    List<Methodology> findAll();

    Methodology save(Methodology methodology);

    void deleteById(UUID id);

}
