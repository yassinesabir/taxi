package com.taxi.taxi.dao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Passager extends Utilisateur{
    private int numeroCompte;

    @ManyToMany(mappedBy = "passagers")
    @JsonManagedReference
    private List<Voyage> voyages;

    @ManyToMany(mappedBy = "passagers")
    private List<NotificationPassager> notifications;


    @OneToMany(mappedBy = "passager")
    private List<Evaluation> evaluationsDonnees;
}
