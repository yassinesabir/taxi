package com.taxi.taxi.controller;

import com.taxi.taxi.dao.Chauffeur;
import com.taxi.taxi.service.CourtierService;
import com.taxi.taxi.dao.Taxi;
import com.taxi.taxi.dao.Voyage;
import com.taxi.taxi.dao.Passager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courtier")
public class CourtierController {

    private final CourtierService courtierService;

    @Autowired
    public CourtierController(CourtierService courtierService) {
        this.courtierService = courtierService;
    }

    // Endpoint pour obtenir les taxis disponibles
    @GetMapping("/taxis-disponibles")
    public ResponseEntity<List<Taxi>> getTaxisDisponibles() {
        List<Taxi> taxisDisponibles = courtierService.getTaxisDisponibles();
        return ResponseEntity.ok(taxisDisponibles);
    }

    // Endpoint pour obtenir la liste des passagers
    @GetMapping("/passagers")
    public ResponseEntity<List<Passager>> getPassagers() {
        List<Passager> passagers = courtierService.Passagers();
        return ResponseEntity.ok(passagers);
    }

    // Endpoint pour obtenir la liste des passagers
    @GetMapping("/chauffeurs")
    public ResponseEntity<List<Chauffeur>> getChauffeurs() {
        List<Chauffeur> chauffeurs = courtierService.Chauffeurs();
        return ResponseEntity.ok(chauffeurs);
    }

    // Endpoint pour obtenir la liste des voyages
    @GetMapping("/voyages")
    public ResponseEntity<List<Voyage>> getVoyages() {
        List<Voyage> voyages = courtierService.Voyages();
        return ResponseEntity.ok(voyages);
    }

    // Endpoint pour ajouter un taxi
    @PostMapping("/ajouter-taxi")
    public ResponseEntity<Taxi> ajouterTaxi(@RequestBody String numero) {
        Taxi taxi = courtierService.ajouterTaxi(numero);
        return ResponseEntity.status(HttpStatus.CREATED).body(taxi);
    }

    // Endpoint pour commencer un voyage
    @PostMapping("/commencer-voyage/{taxiId}")
    public ResponseEntity<Voyage> commencerVoyage(@PathVariable Long taxiId) {
        Voyage voyage = courtierService.commencerVoyage(taxiId);
        return ResponseEntity.status(HttpStatus.CREATED).body(voyage);
    }

    // Endpoint pour ajouter un passager à un voyage
    @PostMapping("/ajouter-passager/{voyageId}")
    public ResponseEntity<Void> ajouterPassager(@PathVariable Long voyageId) {
        Voyage voyage = courtierService.Voyages().stream()
                .filter(v -> v.getId().equals(voyageId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Voyage non trouvé"));

        courtierService.ajouterPassager(voyage);
        return ResponseEntity.ok().build();
    }

    // Endpoint pour effectuer un paiement pour un voyage
    @PostMapping("/effectuer-paiement/{voyageId}")
    public ResponseEntity<Void> effectuerPaiement(@PathVariable Long voyageId, @RequestBody int numeroCompte) {
        Voyage voyage = courtierService.Voyages().stream()
                .filter(v -> v.getId().equals(voyageId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Voyage non trouvé"));

        courtierService.effectuerPaiement(voyage, numeroCompte);
        return ResponseEntity.ok().build();
    }

    // Endpoint pour terminer un voyage
    @PostMapping("/terminer-voyage/{voyageId}")
    public ResponseEntity<Void> terminerVoyage(@PathVariable Long voyageId) {
        courtierService.terminerVoyage(voyageId);
        return ResponseEntity.ok().build();
    }

    // Endpoint pour recharger le solde d'un passager
    @PostMapping("/recharger-solde/{numeroCompte}")
    public ResponseEntity<Void> rechargerSolde(@PathVariable int numeroCompte, @RequestParam double montant) {
        courtierService.rechargerSoldePassager(numeroCompte, montant);
        return ResponseEntity.ok().build();
    }

    // Endpoint pour supprimer (rendre indisponible) un taxi
    @PostMapping("/supprimer-taxi/{taxiId}")
    public ResponseEntity<Void> supprimerTaxi(@PathVariable Long taxiId) {
        courtierService.supprimerTaxi(taxiId);
        return ResponseEntity.ok().build();
    }
}
