package com.taxi.taxi.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.taxi.taxi.enumerations.StatutTaxi;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Chauffeur extends Utilisateur{
    @OneToOne
    @JsonManagedReference
    private Taxi taxi;

    @ManyToMany(mappedBy = "chauffeurs")
    private List<NotificationChauffeur> notifications;

    @OneToMany(mappedBy = "chauffeur")
    @JsonBackReference
    private List<Voyage> voyages;

    @OneToMany(mappedBy = "chauffeur")
    @JsonBackReference
    private List<Evaluation> evaluationsRecues;

    private double noteMoyenne;
}
