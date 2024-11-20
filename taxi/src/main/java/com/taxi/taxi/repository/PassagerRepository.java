package com.taxi.taxi.repository;

import com.taxi.taxi.dao.Passager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassagerRepository extends JpaRepository<Passager, Long> {
}
