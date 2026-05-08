package com.fiap.wellme.service;

import com.fiap.wellme.dto.progress.ProgressResponseDTO;
import com.fiap.wellme.exception.ResourceNotFoundException;
import com.fiap.wellme.mapper.ProgressMapper;
import com.fiap.wellme.model.*;
import com.fiap.wellme.repository.UserProgressRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    private final UserProgressRepository progressRepository;
    private final UserService userService;
    private final TrailService trailService;

    public ProgressService(UserProgressRepository progressRepository,
                           UserService userService,
                           TrailService trailService) {
        this.progressRepository = progressRepository;
        this.userService = userService;
        this.trailService = trailService;
    }

    public List<ProgressResponseDTO> getByUser(String userId) {
        userService.findEntity(userId); // valida existência
        return progressRepository.findByUserId(userId).stream()
                .map(ProgressMapper::toResponseDTO).toList();
    }

    /**
     * Inicia uma fase: cria UserProgress com status IN_PROGRESS.
     * Se já existe, retorna o registro atual sem alterar.
     */
    @Transactional
    public ProgressResponseDTO startPhase(String userId, String phaseId) {
        User user  = userService.findEntity(userId);
        Phase phase = trailService.findPhaseEntity(phaseId);

        return progressRepository.findByUserIdAndPhaseId(userId, phaseId)
                .map(ProgressMapper::toResponseDTO)
                .orElseGet(() -> {
                    UserProgress progress = UserProgress.builder()
                            .user(user)
                            .phase(phase)
                            .status(ProgressStatus.IN_PROGRESS)
                            .xpEarned(phase.getXpReward()) // XP base pela leitura da fase
                            .build();
                    UserProgress saved = progressRepository.save(progress);
                    // Concede XP base pela leitura da fase
                    userService.addXp(user, phase.getXpReward());
                    return ProgressMapper.toResponseDTO(saved);
                });
    }

    public ProgressResponseDTO getByUserAndPhase(String userId, String phaseId) {
        return progressRepository.findByUserIdAndPhaseId(userId, phaseId)
                .map(ProgressMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Progresso não encontrado para userId=" + userId + " phaseId=" + phaseId));
    }
}
