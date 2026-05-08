package com.fiap.wellme.repository;

import com.fiap.wellme.model.WaterLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface WaterLogRepository extends JpaRepository<WaterLog, String> {
    List<WaterLog> findByUserIdAndLogDateOrderByLoggedAt(String userId, LocalDate logDate);

    @Query("SELECT COALESCE(SUM(w.amountMl), 0) FROM WaterLog w WHERE w.user.id = :userId AND w.logDate = :date")
    int sumAmountByUserAndDate(@Param("userId") String userId, @Param("date") LocalDate date);

    List<WaterLog> findByUserIdOrderByLoggedAtDesc(String userId);
}
