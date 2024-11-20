package com.taxi.taxi.dao;

import com.taxi.taxi.enumerations.StatutTaxi;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Chauffeur extends Utilisateur{
    @OneToOne
    private Taxi taxi;

    @ManyToMany(mappedBy = "chauffeurs")
    private List<NotificationChauffeur> notifications;

    @OneToMany(mappedBy = "chauffeur")
    private List<Voyage> voyages;

    @OneToMany(mappedBy = "chauffeur")
    private List<Evaluation> evaluationsRecues;

    private double noteMoyenne;
}
