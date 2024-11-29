package com.taxi.taxi.repository;

import com.taxi.taxi.dao.Passager;
import com.taxi.taxi.dao.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassagerRepository extends JpaRepository<Passager, Long> {
    Passager findPassagerByNumeroCompte(int numeroCompte);
}
