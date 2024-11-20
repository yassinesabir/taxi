package com.taxi.taxi.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Administrateur extends Utilisateur{
    @OneToMany(mappedBy = "admin")
    private List<Voyage> voyages;
}
