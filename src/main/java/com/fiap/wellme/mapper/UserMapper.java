package com.fiap.wellme.mapper;

import com.fiap.wellme.dto.user.UserResponseDTO;
import com.fiap.wellme.model.User;

public final class UserMapper {
    private UserMapper() {}

    public static UserResponseDTO toResponseDTO(User u) {
        if (u == null) return null;
        return new UserResponseDTO(
                u.getId(), u.getName(), u.getEmail(),
                u.getPhotoUrl(), u.getXp(), u.getLevel(),
                u.getDailyWaterGoalMl(), u.getNotificationsEnabled(),
                u.getCreatedAt()
        );
    }
}
