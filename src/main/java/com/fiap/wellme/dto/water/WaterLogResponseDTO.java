package com.fiap.wellme.dto.water;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record WaterLogResponseDTO(
        String id,
        String userId,
        Integer amountMl,
        LocalDate logDate,
        LocalDateTime loggedAt
) {}
