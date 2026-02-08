package com.icesi.devopssandboxbackend.controller;

import com.icesi.devopssandboxbackend.dto.PhaseDTO;
import com.icesi.devopssandboxbackend.mapper.PhaseMapper;
import com.icesi.devopssandboxbackend.service.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/phases")
public class PhaseController {
    @Autowired
    private PhaseService phaseService;

    @GetMapping
    public List<PhaseDTO> getAll() {
        return phaseService.findAll().stream().map(PhaseMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PhaseDTO getById(@PathVariable UUID id) {
        return PhaseMapper.toDTO(phaseService.findById(id));
    }

    @GetMapping("/methodology/{methodologyId}")
    public List<PhaseDTO> getByMethodology(@PathVariable UUID methodologyId) {
        return phaseService.findByMethodologyId(methodologyId).stream().map(PhaseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public PhaseDTO create(@RequestBody PhaseDTO dto) {
        return PhaseMapper.toDTO(phaseService.save(PhaseMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public PhaseDTO update(@PathVariable UUID id, @RequestBody PhaseDTO dto) {
        dto.setId(id);
        return PhaseMapper.toDTO(phaseService.save(PhaseMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        phaseService.deleteById(id);
    }
}
