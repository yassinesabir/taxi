package com.taxi.taxi.repository;

import com.taxi.taxi.dao.Taxi;
import com.taxi.taxi.enumerations.StatutTaxi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxiRepository extends JpaRepository<Taxi, Long> {
    List<Taxi> findByStatut(StatutTaxi statut);
    Taxi findByNumero(String numero);
}
