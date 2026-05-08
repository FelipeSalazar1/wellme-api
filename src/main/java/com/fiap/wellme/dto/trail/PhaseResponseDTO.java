package com.fiap.wellme.dto.trail;

public record PhaseResponseDTO(
        String id,
        String trailId,
        String trailTitle,
        String title,
        String content,
        Integer xpReward,
        Integer orderIndex
) {}
