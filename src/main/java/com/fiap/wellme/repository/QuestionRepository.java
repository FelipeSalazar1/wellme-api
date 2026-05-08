package com.fiap.wellme.repository;

import com.fiap.wellme.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    List<Question> findByQuizIdOrderByOrderIndex(String quizId);
}
