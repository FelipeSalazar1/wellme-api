package com.fiap.wellme.dto.user;

import java.time.LocalDateTime;

public record UserResponseDTO(
        String id,
        String name,
        String email,
        String photoUrl,
        Integer xp,
        Integer level,
        Integer dailyWaterGoalMl,
        Boolean notificationsEnabled,
        LocalDateTime createdAt
) {}
