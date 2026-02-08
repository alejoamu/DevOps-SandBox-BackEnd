package com.icesi.devopssandboxbackend.service;

import com.icesi.devopssandboxbackend.domain.model.Resource;
import java.util.List;
import java.util.UUID;

public interface ResourceService {

    Resource findById(UUID id);

    List<Resource> findAll();

    Resource save(Resource resource);

    void deleteById(UUID id);

}
