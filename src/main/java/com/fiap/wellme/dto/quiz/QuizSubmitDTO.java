package com.fiap.wellme.dto.quiz;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record QuizSubmitDTO(
        @NotBlank(message = "ID do usuário é obrigatório")
        String userId,

        @NotNull(message = "Respostas são obrigatórias. Mapa questionId -> opção escolhida (A/B/C/D)")
        Map<String, String> answers
) {}
