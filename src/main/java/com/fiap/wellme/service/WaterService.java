package com.fiap.wellme.service;

import com.fiap.wellme.dto.water.*;
import com.fiap.wellme.exception.ResourceNotFoundException;
import com.fiap.wellme.mapper.WaterMapper;
import com.fiap.wellme.model.User;
import com.fiap.wellme.model.WaterLog;
import com.fiap.wellme.repository.WaterLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WaterService {

    private final WaterLogRepository waterLogRepository;
    private final UserService userService;

    public WaterService(WaterLogRepository waterLogRepository, UserService userService) {
        this.waterLogRepository = waterLogRepository;
        this.userService = userService;
    }

    /** Registra consumo de água (ml) para o usuário no dia informado (padrão: hoje) */
    @Transactional
    public WaterLogResponseDTO log(String userId, WaterLogRequestDTO dto) {
        User user = userService.findEntity(userId);
        LocalDate date = dto.logDate() != null ? dto.logDate() : LocalDate.now();

        WaterLog log = WaterLog.builder()
                .user(user)
                .amountMl(dto.amountMl())
                .logDate(date)
                .build();

        return WaterMapper.toResponseDTO(waterLogRepository.save(log));
    }

    /** Resumo diário: total consumido vs meta, com lista de registros do dia */
    public WaterDailySummaryDTO getDailySummary(String userId, LocalDate date) {
        User user = userService.findEntity(userId);
        LocalDate d = date != null ? date : LocalDate.now();

        List<WaterLogResponseDTO> logs = waterLogRepository
                .findByUserIdAndLogDateOrderByLoggedAt(userId, d).stream()
                .map(WaterMapper::toResponseDTO).toList();

        int total = waterLogRepository.sumAmountByUserAndDate(userId, d);
        boolean goalOk = total >= user.getDailyWaterGoalMl();

        return new WaterDailySummaryDTO(userId, d, total, user.getDailyWaterGoalMl(), goalOk, logs);
    }

    /** Histórico completo de registros do usuário */
    public List<WaterLogResponseDTO> getHistory(String userId) {
        userService.findEntity(userId); // valida existência
        return waterLogRepository.findByUserIdOrderByLoggedAtDesc(userId).stream()
                .map(WaterMapper::toResponseDTO).toList();
    }

    @Transactional
    public void delete(String userId, String logId) {
        WaterLog log = waterLogRepository.findById(logId)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de água não encontrado: " + logId));
        if (!log.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Registro de água não pertence ao usuário informado.");
        }
        waterLogRepository.deleteById(logId);
    }
}
