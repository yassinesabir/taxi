package com.taxi.taxi.dao;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Passager extends Utilisateur{
    private int numeroCompte;

    @ManyToMany(mappedBy = "passagers")
    private List<Voyage> voyages;

    @ManyToMany(mappedBy = "passagers")
    private List<NotificationPassager> notifications;


    @OneToMany(mappedBy = "passager")
    private List<Evaluation> evaluationsDonnees;
}
