package com.fiap.wellme.dto.trail;

import com.fiap.wellme.model.TrailCategory;

public record TrailResponseDTO(
        String id,
        String title,
        String description,
        TrailCategory category,
        String iconUrl,
        Integer orderIndex,
        Boolean active
) {}
