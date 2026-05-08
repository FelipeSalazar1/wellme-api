package com.fiap.wellme.controller;

import com.fiap.wellme.config.OpenApiConfig;
import com.fiap.wellme.dto.trail.*;
import com.fiap.wellme.service.TrailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trails")
@Tag(name = "Trilhas & Fases", description = "Trilhas de aprendizagem e fases educativas")
public class TrailController {

    private final TrailService trailService;

    public TrailController(TrailService trailService) {
        this.trailService = trailService;
    }

    // ---- Trails ----

    @GetMapping
    @Operation(summary = "Listar trilhas ativas (ordenadas)")
    public List<TrailResponseDTO> getActive() {
        return trailService.getAllActive();
    }

    @GetMapping("/all")
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Listar todas as trilhas (incl. inativas) — admin")
    public List<TrailResponseDTO> getAll() {
        return trailService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar trilha por ID")
    public TrailResponseDTO getById(@PathVariable String id) {
        return trailService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Criar trilha (admin)")
    public TrailResponseDTO create(@Valid @RequestBody TrailRequestDTO dto) {
        return trailService.create(dto);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Atualizar trilha (admin)")
    public TrailResponseDTO update(@PathVariable String id, @Valid @RequestBody TrailRequestDTO dto) {
        return trailService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Desativar trilha (exclusão lógica — admin)")
    public TrailResponseDTO deactivate(@PathVariable String id) {
        return trailService.deactivate(id);
    }

    // ---- Phases ----

    @GetMapping("/{trailId}/phases")
    @Operation(summary = "Listar fases de uma trilha")
    public List<PhaseResponseDTO> getPhases(@PathVariable String trailId) {
        return trailService.getPhasesByTrail(trailId);
    }

    @GetMapping("/phases/{id}")
    @Operation(summary = "Buscar fase por ID")
    public PhaseResponseDTO getPhase(@PathVariable String id) {
        return trailService.getPhaseById(id);
    }

    @PostMapping("/phases")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Criar fase (admin)")
    public PhaseResponseDTO createPhase(@Valid @RequestBody PhaseRequestDTO dto) {
        return trailService.createPhase(dto);
    }

    @PutMapping("/phases/{id}")
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Atualizar fase (admin)")
    public PhaseResponseDTO updatePhase(@PathVariable String id, @Valid @RequestBody PhaseRequestDTO dto) {
        return trailService.updatePhase(id, dto);
    }

    @DeleteMapping("/phases/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Remover fase (admin)")
    public void deletePhase(@PathVariable String id) {
        trailService.deletePhase(id);
    }
}
