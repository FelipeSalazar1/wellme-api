package com.fiap.wellme.mapper;

import com.fiap.wellme.dto.quiz.QuestionResponseDTO;
import com.fiap.wellme.dto.quiz.QuizResponseDTO;
import com.fiap.wellme.model.Question;
import com.fiap.wellme.model.Quiz;

import java.util.List;

public final class QuizMapper {
    private QuizMapper() {}

    public static QuestionResponseDTO toQuestionDTO(Question q) {
        if (q == null) return null;
        // correctOption NÃO é exposta ao cliente
        return new QuestionResponseDTO(
                q.getId(), q.getStatement(),
                q.getOptionA(), q.getOptionB(), q.getOptionC(), q.getOptionD(),
                q.getOrderIndex()
        );
    }

    public static QuizResponseDTO toQuizDTO(Quiz quiz, List<Question> questions) {
        if (quiz == null) return null;
        return new QuizResponseDTO(
                quiz.getId(),
                quiz.getPhase().getId(),
                quiz.getPhase().getTitle(),
                quiz.getTitle(),
                quiz.getXpReward(),
                questions.stream().map(QuizMapper::toQuestionDTO).toList()
        );
    }
}
