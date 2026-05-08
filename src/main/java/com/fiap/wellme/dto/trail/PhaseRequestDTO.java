package com.fiap.wellme.dto.trail;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PhaseRequestDTO(

        @NotBlank(message = "ID da trilha é obrigatório")
        String trailId,

        @NotBlank(message = "Título é obrigatório")
        @Size(max = 120)
        String title,

        @NotBlank(message = "Conteúdo educativo é obrigatório")
        String content,

        @NotNull(message = "Recompensa XP é obrigatória")
        @Min(value = 1, message = "XP deve ser maior que zero")
        Integer xpReward,

        Integer orderIndex
) {}
