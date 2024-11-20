package com.taxi.taxi.dao;

import com.taxi.taxi.enumerations.ChauffeurMessage;
import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("CHAUFFEUR")
public class NotificationChauffeur extends Notification {
    private ChauffeurMessage message;

    @ManyToMany
    @JoinTable(
            name = "notification_chauffeur",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "chauffeur_id")
    )
    private List<Chauffeur> chauffeurs;
}
