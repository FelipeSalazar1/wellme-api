package com.fiap.wellme.controller;

import com.fiap.wellme.dto.badge.BadgeResponseDTO;
import com.fiap.wellme.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/badges")
@Tag(name = "Badges & Conquistas", description = "Medalhas e conquistas do sistema de gamificação")
public class BadgeController {

    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os badges disponíveis no sistema")
    public List<BadgeResponseDTO> getAllBadges() {
        return badgeService.getAllBadges();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar badges conquistados pelo usuário (com data de conquista)")
    public List<BadgeResponseDTO> getUserBadges(@PathVariable String userId) {
        return badgeService.getUserBadges(userId);
    }
}
