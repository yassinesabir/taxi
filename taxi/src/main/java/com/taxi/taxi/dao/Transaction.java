package com.taxi.taxi.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.taxi.taxi.enumerations.MethodePaiement;
import com.taxi.taxi.enumerations.StatutPaiement;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Voyage voyage;

    @Enumerated(EnumType.STRING)
    private MethodePaiement methodePaiement;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statut;

    private LocalDateTime horodatage;
}
