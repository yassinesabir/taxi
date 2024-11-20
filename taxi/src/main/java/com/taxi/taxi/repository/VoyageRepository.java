package com.taxi.taxi.repository;

import com.taxi.taxi.dao.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {
}
