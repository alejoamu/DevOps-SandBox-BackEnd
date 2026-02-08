package com.icesi.devopssandboxbackend.controller;

import com.icesi.devopssandboxbackend.dto.ResourceDTO;
import com.icesi.devopssandboxbackend.mapper.ResourceMapper;
import com.icesi.devopssandboxbackend.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @GetMapping
    public List<ResourceDTO> getAll() {
        return resourceService.findAll().stream().map(ResourceMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResourceDTO getById(@PathVariable UUID id) {
        return ResourceMapper.toDTO(resourceService.findById(id));
    }

    @PostMapping
    public ResourceDTO create(@RequestBody ResourceDTO dto) {
        return ResourceMapper.toDTO(resourceService.save(ResourceMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public ResourceDTO update(@PathVariable UUID id, @RequestBody ResourceDTO dto) {
        dto.setId(id);
        return ResourceMapper.toDTO(resourceService.save(ResourceMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        resourceService.deleteById(id);
    }
}
