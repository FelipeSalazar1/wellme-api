package com.fiap.wellme.repository;

import com.fiap.wellme.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, String> {
    List<Phase> findByTrailIdOrderByOrderIndex(String trailId);
}
