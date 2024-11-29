package com.taxi.taxi.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Passager passager;

    @ManyToOne
    @JsonBackReference
    private Chauffeur chauffeur;

    private int note;
    private String commentaire;
    private LocalDateTime dateEvaluation;
}
