package com.taxi.taxi.repository;

import com.taxi.taxi.dao.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
