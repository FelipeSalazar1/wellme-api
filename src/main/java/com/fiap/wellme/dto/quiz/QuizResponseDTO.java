package com.fiap.wellme.dto.quiz;

import java.util.List;

public record QuizResponseDTO(
        String id,
        String phaseId,
        String phaseTitle,
        String title,
        Integer xpReward,
        List<QuestionResponseDTO> questions
) {}
