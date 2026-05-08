package com.fiap.wellme.controller;

import com.fiap.wellme.config.OpenApiConfig;
import com.fiap.wellme.dto.quiz.*;
import com.fiap.wellme.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quizzes")
@Tag(name = "Quizzes", description = "Quizzes associados às fases das trilhas")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{quizId}")
    @Operation(summary = "Buscar quiz por ID (sem gabaritos)")
    public QuizResponseDTO getById(@PathVariable String quizId) {
        return quizService.getById(quizId);
    }

    @GetMapping("/phase/{phaseId}")
    @Operation(summary = "Buscar quiz da fase (sem gabaritos)")
    public QuizResponseDTO getByPhase(@PathVariable String phaseId) {
        return quizService.getByPhase(phaseId);
    }

    @PostMapping("/{quizId}/submit")
    @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME)
    @Operation(summary = "Submeter respostas do quiz — corrige, concede XP e badges automaticamente",
               description = "Corpo: { userId, answers: { questionId: 'A'|'B'|'C'|'D' } }")
    public QuizResultDTO submit(@PathVariable String quizId,
                                @Valid @RequestBody QuizSubmitDTO dto) {
        return quizService.submit(quizId, dto);
    }
}
