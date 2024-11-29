package com.taxi.taxi.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.taxi.taxi.enumerations.StatutTaxi;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    @JsonBackReference
    private Chauffeur chauffeur;

    @OneToMany(mappedBy = "taxi")
    @JsonBackReference
    private List<Voyage> voyages;

    @Enumerated(EnumType.STRING)
    private StatutTaxi statut;

    private LocalDateTime dateArrivee;
}
