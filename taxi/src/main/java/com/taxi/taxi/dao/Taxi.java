package com.taxi.taxi.dao;

import com.taxi.taxi.enumerations.StatutTaxi;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
public class Taxi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codeSecret;

    @Column(unique = true)
    private String matricule;

    @Column(unique = true)
    private String numero;

    @OneToOne(mappedBy = "taxi")
    private Chauffeur chauffeur;

    @OneToMany(mappedBy = "taxi")
    private List<Voyage> voyages;

    @Enumerated(EnumType.STRING)
    private StatutTaxi statut;
}
