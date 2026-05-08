package com.fiap.wellme.mapper;

import com.fiap.wellme.dto.water.WaterLogResponseDTO;
import com.fiap.wellme.model.WaterLog;

public final class WaterMapper {
    private WaterMapper() {}

    public static WaterLogResponseDTO toResponseDTO(WaterLog w) {
        if (w == null) return null;
        return new WaterLogResponseDTO(
                w.getId(),
                w.getUser().getId(),
                w.getAmountMl(),
                w.getLogDate(),
                w.getLoggedAt()
        );
    }
}
