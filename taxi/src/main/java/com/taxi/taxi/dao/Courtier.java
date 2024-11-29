package com.taxi.taxi.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Courtier extends Utilisateur{
    @OneToMany(mappedBy = "courtier")
    @JsonBackReference
    private List<Voyage> voyages;
}
