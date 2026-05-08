package com.fiap.wellme.dto.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 120)
        String name,

        String photoUrl,

        @Min(value = 500, message = "Meta diária de água deve ser de pelo menos 500 ml")
        Integer dailyWaterGoalMl,

        Boolean notificationsEnabled
) {}
