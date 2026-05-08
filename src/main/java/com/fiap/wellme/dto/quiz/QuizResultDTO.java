package com.fiap.wellme.dto.quiz;

public record QuizResultDTO(
        String quizId,
        String userId,
        int totalQuestions,
        int correctAnswers,
        int scorePercent,
        int xpEarned,
        int totalXp,
        int currentLevel,
        String feedback
) {}
