package com.fiap.wellme.dto.water;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record WaterLogRequestDTO(

        @NotNull(message = "Quantidade em ml é obrigatória")
        @Min(value = 1, message = "Quantidade deve ser maior que zero")
        Integer amountMl,

        LocalDate logDate // opcional; se nulo, usa hoje
) {}
