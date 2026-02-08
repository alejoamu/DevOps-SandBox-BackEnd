package com.icesi.devopssandboxbackend.controller;

import com.icesi.devopssandboxbackend.dto.MethodologyDTO;
import com.icesi.devopssandboxbackend.mapper.MethodologyMapper;
import com.icesi.devopssandboxbackend.service.MethodologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/methodologies")
public class MethodologyController {
    @Autowired
    private MethodologyService methodologyService;

    @GetMapping
    public List<MethodologyDTO> getAll() {
        return methodologyService.findAll().stream().map(MethodologyMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MethodologyDTO getById(@PathVariable UUID id) {
        return MethodologyMapper.toDTO(methodologyService.findById(id));
    }

    @PostMapping
    public MethodologyDTO create(@RequestBody MethodologyDTO dto) {
        return MethodologyMapper.toDTO(methodologyService.save(MethodologyMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public MethodologyDTO update(@PathVariable UUID id, @RequestBody MethodologyDTO dto) {
        dto.setId(id);
        return MethodologyMapper.toDTO(methodologyService.save(MethodologyMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        methodologyService.deleteById(id);
    }
}
