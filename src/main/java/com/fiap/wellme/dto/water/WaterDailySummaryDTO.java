package com.fiap.wellme.dto.water;

import java.time.LocalDate;
import java.util.List;

public record WaterDailySummaryDTO(
        String userId,
        LocalDate date,
        Integer totalMl,
        Integer goalMl,
        Boolean goalAchieved,
        List<WaterLogResponseDTO> logs
) {}
