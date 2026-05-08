package com.fiap.wellme.controller;

import com.fiap.wellme.config.OpenApiConfig;
import com.fiap.wellme.dto.water.*;
import com.fiap.wellme.service.WaterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/water")
@Tag(name = "Hidratação", description = "Registro diário de consumo de água e acompanhamento de meta")
public class WaterController {

    private final WaterService waterService;

    public WaterController(WaterService waterService) {
        this.waterService = waterService;
    }

    @PostMapping("/user/{userId}/log")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Registrar consumo de água (ml)",
               description = "logDate é opcional; se omitido usa a data de hoje.")
    public WaterLogResponseDTO log(@PathVariable String userId,
                                   @Valid @RequestBody WaterLogRequestDTO dto) {
        return waterService.log(userId, dto);
    }

    @GetMapping("/user/{userId}/summary")
    @Operation(summary = "Resumo diário — total consumido vs meta (padrão: hoje)")
    public WaterDailySummaryDTO getSummary(
            @PathVariable String userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return waterService.getDailySummary(userId, date);
    }

    @GetMapping("/user/{userId}/history")
    @Operation(summary = "Histórico completo de registros de água do usuário")
    public List<WaterLogResponseDTO> getHistory(@PathVariable String userId) {
        return waterService.getHistory(userId);
    }

    @DeleteMapping("/user/{userId}/log/{logId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Remover um registro de água")
    public void delete(@PathVariable String userId, @PathVariable String logId) {
        waterService.delete(userId, logId);
    }
}
