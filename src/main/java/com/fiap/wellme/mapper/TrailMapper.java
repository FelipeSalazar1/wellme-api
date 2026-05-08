package com.fiap.wellme.mapper;

import com.fiap.wellme.dto.trail.PhaseResponseDTO;
import com.fiap.wellme.dto.trail.TrailResponseDTO;
import com.fiap.wellme.model.Phase;
import com.fiap.wellme.model.Trail;

public final class TrailMapper {
    private TrailMapper() {}

    public static TrailResponseDTO toResponseDTO(Trail t) {
        if (t == null) return null;
        return new TrailResponseDTO(
                t.getId(), t.getTitle(), t.getDescription(),
                t.getCategory(), t.getIconUrl(), t.getOrderIndex(), t.getActive()
        );
    }

    public static PhaseResponseDTO toPhaseResponseDTO(Phase p) {
        if (p == null) return null;
        return new PhaseResponseDTO(
                p.getId(),
                p.getTrail().getId(),
                p.getTrail().getTitle(),
                p.getTitle(),
                p.getContent(),
                p.getXpReward(),
                p.getOrderIndex()
        );
    }
}
