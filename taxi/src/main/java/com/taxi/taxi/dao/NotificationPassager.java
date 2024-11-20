package com.taxi.taxi.dao;

import com.taxi.taxi.enumerations.PassagerMessage;
import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("PASSAGER")
public class NotificationPassager extends Notification {
    private PassagerMessage message;

    @ManyToMany
    @JoinTable(
            name = "notification_passager",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "passager_id")
    )
    private List<Passager> passagers;
}
