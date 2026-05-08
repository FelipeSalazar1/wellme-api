package com.fiap.wellme.service;

import com.fiap.wellme.dto.badge.BadgeResponseDTO;
import com.fiap.wellme.model.*;
import com.fiap.wellme.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final UserProgressRepository progressRepository;
    private final WaterLogRepository waterLogRepository;

    public BadgeService(BadgeRepository badgeRepository,
                        UserBadgeRepository userBadgeRepository,
                        UserProgressRepository progressRepository,
                        WaterLogRepository waterLogRepository) {
        this.badgeRepository = badgeRepository;
        this.userBadgeRepository = userBadgeRepository;
        this.progressRepository = progressRepository;
        this.waterLogRepository = waterLogRepository;
    }

    public List<BadgeResponseDTO> getAllBadges() {
        return badgeRepository.findAll().stream()
                .map(b -> new BadgeResponseDTO(b.getId(), b.getTitle(), b.getDescription(),
                        b.getIconUrl(), b.getCriteria(), null))
                .toList();
    }

    public List<BadgeResponseDTO> getUserBadges(String userId) {
        return userBadgeRepository.findByUserId(userId).stream()
                .map(ub -> new BadgeResponseDTO(
                        ub.getBadge().getId(), ub.getBadge().getTitle(),
                        ub.getBadge().getDescription(), ub.getBadge().getIconUrl(),
                        ub.getBadge().getCriteria(), ub.getEarnedAt()))
                .toList();
    }

    /**
     * Verifica e concede badges automaticamente após eventos relevantes.
     * Chamado pelo QuizService e ProgressService.
     */
    @Transactional
    public void checkAndAwardBadges(User user) {
        String uid = user.getId();
        long completed = progressRepository.countByUserIdAndStatus(uid, ProgressStatus.COMPLETED);
        long trails    = progressRepository.countDistinctTrails(uid);
        int  today     = waterLogRepository.sumAmountByUserAndDate(uid, LocalDate.now());

        List<Badge> all = badgeRepository.findAll();

        for (Badge badge : all) {
            if (userBadgeRepository.existsByUserIdAndBadgeId(uid, badge.getId())) continue;

            boolean earn = switch (badge.getId()) {
                case "bg-0001" -> completed >= 1;
                case "bg-0003" -> progressRepository.findByUserId(uid).stream()
                        .anyMatch(p -> p.getQuizScore() != null && p.getQuizScore() == 100);
                case "bg-0004" -> user.getLevel() >= 5;
                case "bg-0006" -> completed >= 10;
                case "bg-0008" -> trails >= 3;
                default        -> false;
            };

            if (earn) award(user, badge);
        }
    }

    private void award(User user, Badge badge) {
        UserBadge ub = UserBadge.builder().user(user).badge(badge).build();
        userBadgeRepository.save(ub);
    }
}
