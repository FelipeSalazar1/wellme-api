package com.fiap.wellme.dto.user;

public record DashboardDTO(
        String userId,
        String name,
        Integer xp,
        Integer level,
        Integer xpToNextLevel,
        Long phasesCompleted,
        Long phasesInProgress,
        Long badgesEarned,
        Long trailsStarted,
        Integer todayWaterMl,
        Integer dailyWaterGoalMl,
        Boolean waterGoalAchieved
) {}
