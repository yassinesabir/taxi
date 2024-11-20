package com.taxi.taxi.dao;


import com.taxi.taxi.enumerations.RoleUtilisateur;
import com.taxi.taxi.enumerations.StatutCompte;
import com.taxi.taxi.enumerations.StatutTaxi;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NonNull;

import java.util.List;


@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nom;

    @Email
    @Column(unique = true)
    private String email;

    @NonNull
    private String motDePasse;

    @NonNull
    private String numeroTelephone;

    @Enumerated(EnumType.STRING)
    private RoleUtilisateur role;

    @Column(nullable = false)
    private double solde;

    @Enumerated(EnumType.STRING)
    private StatutCompte statut;

    public Utilisateur() {};
}
