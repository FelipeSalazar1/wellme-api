package com.fiap.wellme.dto.quiz;

public record QuestionResponseDTO(
        String id,
        String statement,
        String optionA,
        String optionB,
        String optionC,
        String optionD,
        Integer orderIndex
        // correctOption omitida propositalmente — não exposta ao usuário
) {}
