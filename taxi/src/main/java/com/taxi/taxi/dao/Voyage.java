package com.taxi.taxi.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.taxi.taxi.enumerations.StatutVoyage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "voyage_passagers",
            joinColumns = @JoinColumn(name = "voyage_id"),
            inverseJoinColumns = @JoinColumn(name = "passager_id")
    )
    private List<Passager> passagers;

    @ManyToOne
    @JsonBackReference
    private Taxi taxi; // Taxi actuellement utilisé pour ce voyage

    @ManyToOne
    @JsonBackReference
    private Chauffeur chauffeur; // Chauffeur actuellement conduisant ce taxi

    @ManyToOne
    @JsonBackReference
    private Courtier courtier; // Chauffeur actuellement conduisant ce taxi

    private LocalDateTime heureDepart;

    @Enumerated(EnumType.STRING)
    private StatutVoyage statut;

    private double tarif;

    @OneToMany(mappedBy = "voyage", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "voyage", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations;

    private int nombrePassagers=0;
}
