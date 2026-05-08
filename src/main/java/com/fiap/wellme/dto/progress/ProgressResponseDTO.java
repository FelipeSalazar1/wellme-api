package com.fiap.wellme.dto.progress;

import com.fiap.wellme.model.ProgressStatus;
import java.time.LocalDateTime;

public record ProgressResponseDTO(
        String id,
        String userId,
        String phaseId,
        String phaseTitle,
        String trailId,
        String trailTitle,
        ProgressStatus status,
        Integer quizScore,
        Integer xpEarned,
        LocalDateTime completedAt,
        LocalDateTime createdAt
) {}
