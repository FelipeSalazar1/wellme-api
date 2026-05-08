package com.fiap.wellme.service;

import com.fiap.wellme.dto.user.*;
import com.fiap.wellme.dto.water.WaterDailySummaryDTO;
import com.fiap.wellme.dto.water.WaterLogResponseDTO;
import com.fiap.wellme.exception.DuplicateEmailException;
import com.fiap.wellme.exception.ResourceNotFoundException;
import com.fiap.wellme.mapper.UserMapper;
import com.fiap.wellme.mapper.WaterMapper;
import com.fiap.wellme.model.*;
import com.fiap.wellme.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProgressRepository progressRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final WaterLogRepository waterLogRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserProgressRepository progressRepository,
                       UserBadgeRepository userBadgeRepository,
                       WaterLogRepository waterLogRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.userBadgeRepository = userBadgeRepository;
        this.waterLogRepository = waterLogRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream().map(UserMapper::toResponseDTO).toList();
    }

    public UserResponseDTO getById(String id) {
        return userRepository.findById(id)
                .map(UserMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));
    }

    /** Cadastro público: valida e-mail único, criptografa senha */
    @Transactional
    public UserResponseDTO register(UserRegisterDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException("E-mail já cadastrado: " + dto.email());
        }
        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .passwordHash(passwordEncoder.encode(dto.password()))
                .build();
        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    /** Edição de perfil: nome, foto, meta de água e configuração de notificações */
    @Transactional
    public UserResponseDTO update(String id, UserUpdateDTO dto) {
        User user = findEntity(id);
        user.setName(dto.name());
        if (dto.photoUrl() != null) user.setPhotoUrl(dto.photoUrl());
        if (dto.dailyWaterGoalMl() != null) user.setDailyWaterGoalMl(dto.dailyWaterGoalMl());
        if (dto.notificationsEnabled() != null) user.setNotificationsEnabled(dto.notificationsEnabled());
        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    @Transactional
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Dashboard do usuário: XP, nível, fases concluídas, badges, água do dia.
     */
    public DashboardDTO getDashboard(String id) {
        User user = findEntity(id);

        long completed  = progressRepository.countByUserIdAndStatus(id, ProgressStatus.COMPLETED);
        long inProgress = progressRepository.countByUserIdAndStatus(id, ProgressStatus.IN_PROGRESS);
        long badges     = userBadgeRepository.findByUserId(id).size();
        long trails     = progressRepository.countDistinctTrails(id);

        int todayMl = waterLogRepository.sumAmountByUserAndDate(id, LocalDate.now());
        boolean goalOk = todayMl >= user.getDailyWaterGoalMl();

        // XP para próximo nível: 100 * level atual
        int xpToNext = (user.getLevel() * 100) - user.getXp();

        return new DashboardDTO(
                user.getId(), user.getName(),
                user.getXp(), user.getLevel(), Math.max(0, xpToNext),
                completed, inProgress, badges, trails,
                todayMl, user.getDailyWaterGoalMl(), goalOk
        );
    }

    // ---- helpers ----

    public User findEntity(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));
    }

    /** Adiciona XP ao usuário e recalcula nível (100 XP por nível) */
    @Transactional
    public void addXp(User user, int xp) {
        user.setXp(user.getXp() + xp);
        int newLevel = Math.max(1, user.getXp() / 100) + 1;
        user.setLevel(newLevel);
        userRepository.save(user);
    }
}
