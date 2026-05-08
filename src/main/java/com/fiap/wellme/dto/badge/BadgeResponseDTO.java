package com.fiap.wellme.dto.badge;

import java.time.LocalDateTime;

public record BadgeResponseDTO(
        String id,
        String title,
        String description,
        String iconUrl,
        String criteria,
        LocalDateTime earnedAt // null se o badge não foi conquistado pelo usuário
) {}
