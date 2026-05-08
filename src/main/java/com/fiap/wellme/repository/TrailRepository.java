package com.fiap.wellme.repository;

import com.fiap.wellme.model.Trail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrailRepository extends JpaRepository<Trail, String> {
    List<Trail> findByActiveTrueOrderByOrderIndex();
}
