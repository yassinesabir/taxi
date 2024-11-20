package com.taxi.taxi.dao;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Voyage voyage;

    @ManyToOne
    private Passager passager;

    @ManyToOne
    private Chauffeur chauffeur;

    private int note;
    private String commentaire;
    private LocalDateTime dateEvaluation;
}
