package com.fiap.wellme.mapper;

import com.fiap.wellme.dto.progress.ProgressResponseDTO;
import com.fiap.wellme.model.UserProgress;

public final class ProgressMapper {
    private ProgressMapper() {}

    public static ProgressResponseDTO toResponseDTO(UserProgress p) {
        if (p == null) return null;
        return new ProgressResponseDTO(
                p.getId(),
                p.getUser().getId(),
                p.getPhase().getId(),
                p.getPhase().getTitle(),
                p.getPhase().getTrail().getId(),
                p.getPhase().getTrail().getTitle(),
                p.getStatus(),
                p.getQuizScore(),
                p.getXpEarned(),
                p.getCompletedAt(),
                p.getCreatedAt()
        );
    }
}
