package com.fiap.wellme.service;

import com.fiap.wellme.dto.trail.*;
import com.fiap.wellme.exception.ResourceNotFoundException;
import com.fiap.wellme.mapper.TrailMapper;
import com.fiap.wellme.model.Phase;
import com.fiap.wellme.model.Trail;
import com.fiap.wellme.repository.PhaseRepository;
import com.fiap.wellme.repository.TrailRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrailService {

    private final TrailRepository trailRepository;
    private final PhaseRepository phaseRepository;

    public TrailService(TrailRepository trailRepository, PhaseRepository phaseRepository) {
        this.trailRepository = trailRepository;
        this.phaseRepository = phaseRepository;
    }

    // ---- Trails ----

    public List<TrailResponseDTO> getAllActive() {
        return trailRepository.findByActiveTrueOrderByOrderIndex().stream()
                .map(TrailMapper::toResponseDTO).toList();
    }

    public List<TrailResponseDTO> getAll() {
        return trailRepository.findAll().stream()
                .map(TrailMapper::toResponseDTO).toList();
    }

    public TrailResponseDTO getById(String id) {
        return trailRepository.findById(id)
                .map(TrailMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Trilha não encontrada: " + id));
    }

    @Transactional
    public TrailResponseDTO create(TrailRequestDTO dto) {
        Trail trail = Trail.builder()
                .title(dto.title())
                .description(dto.description())
                .category(dto.category())
                .iconUrl(dto.iconUrl())
                .orderIndex(dto.orderIndex() != null ? dto.orderIndex() : 0)
                .active(true)
                .build();
        return TrailMapper.toResponseDTO(trailRepository.save(trail));
    }

    @Transactional
    public TrailResponseDTO update(String id, TrailRequestDTO dto) {
        Trail trail = trailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trilha não encontrada: " + id));
        trail.setTitle(dto.title());
        trail.setDescription(dto.description());
        trail.setCategory(dto.category());
        trail.setIconUrl(dto.iconUrl());
        if (dto.orderIndex() != null) trail.setOrderIndex(dto.orderIndex());
        return TrailMapper.toResponseDTO(trailRepository.save(trail));
    }

    @Transactional
    public TrailResponseDTO deactivate(String id) {
        Trail trail = trailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trilha não encontrada: " + id));
        trail.setActive(false);
        return TrailMapper.toResponseDTO(trailRepository.save(trail));
    }

    // ---- Phases ----

    public List<PhaseResponseDTO> getPhasesByTrail(String trailId) {
        if (!trailRepository.existsById(trailId)) {
            throw new ResourceNotFoundException("Trilha não encontrada: " + trailId);
        }
        return phaseRepository.findByTrailIdOrderByOrderIndex(trailId).stream()
                .map(TrailMapper::toPhaseResponseDTO).toList();
    }

    public PhaseResponseDTO getPhaseById(String id) {
        return phaseRepository.findById(id)
                .map(TrailMapper::toPhaseResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Fase não encontrada: " + id));
    }

    @Transactional
    public PhaseResponseDTO createPhase(PhaseRequestDTO dto) {
        Trail trail = trailRepository.findById(dto.trailId())
                .orElseThrow(() -> new ResourceNotFoundException("Trilha não encontrada: " + dto.trailId()));
        Phase phase = Phase.builder()
                .trail(trail)
                .title(dto.title())
                .content(dto.content())
                .xpReward(dto.xpReward())
                .orderIndex(dto.orderIndex() != null ? dto.orderIndex() : 0)
                .build();
        return TrailMapper.toPhaseResponseDTO(phaseRepository.save(phase));
    }

    @Transactional
    public PhaseResponseDTO updatePhase(String id, PhaseRequestDTO dto) {
        Phase phase = phaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fase não encontrada: " + id));
        Trail trail = trailRepository.findById(dto.trailId())
                .orElseThrow(() -> new ResourceNotFoundException("Trilha não encontrada: " + dto.trailId()));
        phase.setTrail(trail);
        phase.setTitle(dto.title());
        phase.setContent(dto.content());
        phase.setXpReward(dto.xpReward());
        if (dto.orderIndex() != null) phase.setOrderIndex(dto.orderIndex());
        return TrailMapper.toPhaseResponseDTO(phaseRepository.save(phase));
    }

    @Transactional
    public void deletePhase(String id) {
        if (!phaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fase não encontrada: " + id);
        }
        phaseRepository.deleteById(id);
    }

    public Phase findPhaseEntity(String id) {
        return phaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fase não encontrada: " + id));
    }
}
