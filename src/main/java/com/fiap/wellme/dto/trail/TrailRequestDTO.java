package com.fiap.wellme.dto.trail;

import com.fiap.wellme.model.TrailCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TrailRequestDTO(

        @NotBlank(message = "Título é obrigatório")
        @Size(max = 120)
        String title,

        @Size(max = 500)
        String description,

        @NotNull(message = "Categoria é obrigatória (HIDRATACAO, ALIMENTACAO, SONO, SAUDE_MENTAL, EXERCICIO, PREVENCAO)")
        TrailCategory category,

        String iconUrl,

        Integer orderIndex
) {}
