package com.fiap.wellme.service;

import com.fiap.wellme.dto.quiz.*;
import com.fiap.wellme.exception.InvalidAnswerException;
import com.fiap.wellme.exception.QuizAlreadySubmittedException;
import com.fiap.wellme.exception.ResourceNotFoundException;
import com.fiap.wellme.mapper.QuizMapper;
import com.fiap.wellme.model.*;
import com.fiap.wellme.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final UserProgressRepository progressRepository;
    private final UserService userService;
    private final BadgeService badgeService;

    public QuizService(QuizRepository quizRepository,
                       QuestionRepository questionRepository,
                       UserProgressRepository progressRepository,
                       UserService userService,
                       BadgeService badgeService) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.progressRepository = progressRepository;
        this.userService = userService;
        this.badgeService = badgeService;
    }

    /** Retorna quiz com perguntas (sem revelar gabaritos) */
    public QuizResponseDTO getByPhase(String phaseId) {
        Quiz quiz = quizRepository.findByPhaseId(phaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz não encontrado para a fase: " + phaseId));
        List<Question> questions = questionRepository.findByQuizIdOrderByOrderIndex(quiz.getId());
        return QuizMapper.toQuizDTO(quiz, questions);
    }

    public QuizResponseDTO getById(String quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz não encontrado: " + quizId));
        List<Question> questions = questionRepository.findByQuizIdOrderByOrderIndex(quizId);
        return QuizMapper.toQuizDTO(quiz, questions);
    }

    /**
     * Submissão e correção do quiz.
     * - Valida que cada resposta é A/B/C/D
     * - Corrige automaticamente
     * - Atualiza UserProgress com score e COMPLETED
     * - Concede XP proporcional ao score
     * - Verifica e concede badges
     */
    @Transactional
    public QuizResultDTO submit(String quizId, QuizSubmitDTO dto) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz não encontrado: " + quizId));

        User user = userService.findEntity(dto.userId());
        String phaseId = quiz.getPhase().getId();

        // Garante que a fase já foi iniciada (UserProgress existe)
        UserProgress progress = progressRepository.findByUserIdAndPhaseId(dto.userId(), phaseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inicie a fase antes de responder o quiz. " +
                        "Use POST /api/v1/progress/start com userId e phaseId."));

        // Bloqueia resubmissão
        if (progress.getStatus() == ProgressStatus.COMPLETED) {
            throw new QuizAlreadySubmittedException(
                    "Quiz desta fase já foi respondido. Score anterior: " + progress.getQuizScore() + "%");
        }

        // Busca perguntas
        List<Question> questions = questionRepository.findByQuizIdOrderByOrderIndex(quizId);
        Map<String, String> answers = dto.answers();

        // Valida opções enviadas
        for (Map.Entry<String, String> entry : answers.entrySet()) {
            String opt = entry.getValue().toUpperCase();
            if (!opt.matches("[ABCD]")) {
                throw new InvalidAnswerException(
                        "Opção inválida '" + entry.getValue() + "' para questão " + entry.getKey() +
                        ". Use A, B, C ou D.");
            }
        }

        // Corrige
        int correct = 0;
        for (Question q : questions) {
            String given = answers.getOrDefault(q.getId(), "");
            if (given.equalsIgnoreCase(q.getCorrectOption().name())) {
                correct++;
            }
        }

        int total = questions.size();
        int scorePercent = total > 0 ? (correct * 100 / total) : 0;

        // XP proporcional: xpReward do quiz × (score / 100), mínimo 0
        int xpEarned = (int) Math.round(quiz.getXpReward() * (scorePercent / 100.0));

        // Atualiza progresso → COMPLETED
        progress.setStatus(ProgressStatus.COMPLETED);
        progress.setQuizScore(scorePercent);
        progress.setXpEarned(progress.getXpEarned() + xpEarned);
        progress.setCompletedAt(LocalDateTime.now());
        progressRepository.save(progress);

        // Concede XP ao usuário
        userService.addXp(user, xpEarned);

        // Verifica badges
        badgeService.checkAndAwardBadges(user);

        String feedback = buildFeedback(scorePercent);

        return new QuizResultDTO(
                quizId, dto.userId(),
                total, correct, scorePercent,
                xpEarned, user.getXp(), user.getLevel(),
                feedback
        );
    }

    private String buildFeedback(int score) {
        if (score == 100) return "Perfeito! Você acertou todas as questões!";
        if (score >= 70)  return "Muito bem! Você demonstrou bom conhecimento.";
        if (score >= 50)  return "Bom esforço! Continue estudando para melhorar.";
        return "Continue praticando! Revise o conteúdo da fase e tente novamente mais tarde.";
    }
}
