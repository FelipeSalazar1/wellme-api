package com.fiap.wellme.controller;

import com.fiap.wellme.config.OpenApiConfig;
import com.fiap.wellme.dto.progress.ProgressResponseDTO;
import com.fiap.wellme.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/progress")
@Tag(name = "Progresso", description = "Início de fases e acompanhamento do progresso do usuário")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar todo o progresso do usuário")
    public List<ProgressResponseDTO> getByUser(@PathVariable String userId) {
        return progressService.getByUser(userId);
    }

    @GetMapping("/user/{userId}/phase/{phaseId}")
    @Operation(summary = "Progresso do usuário em uma fase específica")
    public ProgressResponseDTO getByUserAndPhase(@PathVariable String userId,
                                                 @PathVariable String phaseId) {
        return progressService.getByUserAndPhase(userId, phaseId);
    }

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Iniciar fase — cria registro IN_PROGRESS e concede XP da fase",
               description = "Params: userId (query) + phaseId (query)")
    public ProgressResponseDTO startPhase(@RequestParam String userId,
                                          @RequestParam String phaseId) {
        return progressService.startPhase(userId, phaseId);
    }
}
