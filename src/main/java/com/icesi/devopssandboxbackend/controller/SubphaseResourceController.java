package com.icesi.devopssandboxbackend.controller;

import com.icesi.devopssandboxbackend.dto.SubphaseResourceDTO;
import com.icesi.devopssandboxbackend.mapper.SubphaseResourceMapper;
import com.icesi.devopssandboxbackend.service.SubphaseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subphase-resources")
public class SubphaseResourceController {
    @Autowired
    private SubphaseResourceService subphaseResourceService;

    @GetMapping
    public List<SubphaseResourceDTO> getAll() {
        return subphaseResourceService.findAll().stream().map(SubphaseResourceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/subphase/{subphaseId}")
    public List<SubphaseResourceDTO> getBySubphase(@PathVariable UUID subphaseId) {
        return subphaseResourceService.findBySubphaseId(subphaseId).stream().map(SubphaseResourceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/resource/{resourceId}")
    public List<SubphaseResourceDTO> getByResource(@PathVariable UUID resourceId) {
        return subphaseResourceService.findByResourceId(resourceId).stream().map(SubphaseResourceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{subphaseId}/{resourceId}")
    public SubphaseResourceDTO getById(@PathVariable UUID subphaseId, @PathVariable UUID resourceId) {
        return SubphaseResourceMapper.toDTO(subphaseResourceService.findById(subphaseId, resourceId));
    }

    @PostMapping
    public SubphaseResourceDTO create(@RequestBody SubphaseResourceDTO dto) {
        return SubphaseResourceMapper.toDTO(subphaseResourceService.save(SubphaseResourceMapper.toEntity(dto)));
    }

    @PutMapping("/{subphaseId}/{resourceId}")
    public SubphaseResourceDTO update(@PathVariable UUID subphaseId, @PathVariable UUID resourceId,
            @RequestBody SubphaseResourceDTO dto) {
        dto.setSubphaseId(subphaseId);
        dto.setResourceId(resourceId);
        return SubphaseResourceMapper.toDTO(subphaseResourceService.save(SubphaseResourceMapper.toEntity(dto)));
    }

    @DeleteMapping("/{subphaseId}/{resourceId}")
    public void delete(@PathVariable UUID subphaseId, @PathVariable UUID resourceId) {
        subphaseResourceService.deleteById(subphaseId, resourceId);
    }
}
