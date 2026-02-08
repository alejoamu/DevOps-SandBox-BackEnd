package com.icesi.devopssandboxbackend.controller;

import com.icesi.devopssandboxbackend.dto.SubphaseDTO;
import com.icesi.devopssandboxbackend.mapper.SubphaseMapper;
import com.icesi.devopssandboxbackend.service.SubphaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subphases")
public class SubphaseController {
    @Autowired
    private SubphaseService subphaseService;

    @GetMapping
    public List<SubphaseDTO> getAll() {
        return subphaseService.findAll().stream().map(SubphaseMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SubphaseDTO getById(@PathVariable UUID id) {
        return SubphaseMapper.toDTO(subphaseService.findById(id));
    }

    @GetMapping("/phase/{phaseId}")
    public List<SubphaseDTO> getByPhase(@PathVariable UUID phaseId) {
        return subphaseService.findByPhaseId(phaseId).stream().map(SubphaseMapper::toDTO).collect(Collectors.toList());
    }

    @PostMapping
    public SubphaseDTO create(@RequestBody SubphaseDTO dto) {
        return SubphaseMapper.toDTO(subphaseService.save(SubphaseMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public SubphaseDTO update(@PathVariable UUID id, @RequestBody SubphaseDTO dto) {
        dto.setId(id);
        return SubphaseMapper.toDTO(subphaseService.save(SubphaseMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        subphaseService.deleteById(id);
    }
}
