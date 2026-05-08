package com.fiap.wellme.repository;

import com.fiap.wellme.model.ProgressStatus;
import com.fiap.wellme.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, String> {
    List<UserProgress> findByUserId(String userId);
    Optional<UserProgress> findByUserIdAndPhaseId(String userId, String phaseId);
    long countByUserIdAndStatus(String userId, ProgressStatus status);

    @Query("SELECT COUNT(DISTINCT p.phase.trail.id) FROM UserProgress p WHERE p.user.id = :userId")
    long countDistinctTrails(@Param("userId") String userId);
}
