package com.taxi.taxi.service;

import com.taxi.taxi.dao.*;
import com.taxi.taxi.enumerations.StatutTaxi;
import com.taxi.taxi.enumerations.StatutVoyage;
import com.taxi.taxi.enumerations.MethodePaiement;
import com.taxi.taxi.enumerations.StatutPaiement;
import com.taxi.taxi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CourtierService {

    private final TaxiRepository taxiRepository;
    private final VoyageRepository voyageRepository;
    private final ChauffeurRepository chauffeurRepository;
    private final PassagerRepository passagerRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public CourtierService(TaxiRepository taxiRepository,
                           VoyageRepository voyageRepository,
                           ChauffeurRepository chauffeurRepository,
                           PassagerRepository passagerRepository,
                           TransactionRepository transactionRepository) {
        this.taxiRepository = taxiRepository;
        this.voyageRepository = voyageRepository;
        this.chauffeurRepository = chauffeurRepository;
        this.passagerRepository = passagerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public List<Taxi> getTaxisDisponibles() {
        List<Taxi> taxisDisponibles = taxiRepository.findByStatut(StatutTaxi.DISPONIBLE);
        return taxisDisponibles;
    }

    @Transactional(readOnly = true)
    public List<Passager> Passagers() {
        return passagerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Chauffeur> Chauffeurs() {
        return chauffeurRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Voyage> Voyages() {
        return voyageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Transaction> Transactions() {
        return transactionRepository.findAll();
    }


    public Taxi ajouterTaxi(String numero) {
        Taxi taxi = taxiRepository.findByNumero(numero);
        if (taxi == null) {
            taxi = new Taxi();
            taxi.setNumero(numero);
            taxi.setCodeSecret("0000");
            taxi.setStatut(StatutTaxi.DISPONIBLE);
            taxi.setDateArrivee(LocalDateTime.now());
            return taxiRepository.save(taxi);
        } else {
            taxi.setStatut(StatutTaxi.DISPONIBLE);
            taxi.setDateArrivee(LocalDateTime.now());
            return taxiRepository.save(taxi);
        }
    }


    @Transactional
    public Voyage commencerVoyage(Long taxiId) {
        Taxi taxi = taxiRepository.findById(taxiId)
                .orElseThrow(() -> new IllegalArgumentException("Taxi non trouvé"));


        if (taxi.getStatut() != StatutTaxi.DISPONIBLE) {
            throw new IllegalStateException("Le taxi n'est pas disponible.");
        }

        taxi.setStatut(StatutTaxi.INDISPONIBLE);
        taxiRepository.save(taxi);

        Chauffeur chauffeur = taxi.getChauffeur();
        Courtier courtier = getCourtier();

        Voyage voyage = new Voyage();
        voyage.setTaxi(taxi);
        voyage.setChauffeur(chauffeur);
        voyage.setTarif(15.0);
        voyage.setStatut(StatutVoyage.EN_COURS);
        voyage.setNombrePassagers(0);

        return voyageRepository.save(voyage);
    }

    public void ajouterPassager(Voyage voyage) {
        if (voyage.getNombrePassagers() >= 6) {
            throw new IllegalStateException("Le voyage a atteint la capacité maximale de passagers.");
        }

        voyage.setNombrePassagers(voyage.getNombrePassagers() + 1);

        voyageRepository.save(voyage);

        if (voyage.getNombrePassagers() == 6) {
            terminerVoyage(voyage.getId());
        }
    }

    public void debiterMontant(Passager passager, double montant) {
        if (passager.getSolde() < montant) {
            throw new IllegalStateException("Le passager n'a pas suffisamment de fonds pour payer.");
        }
        passager.setSolde(passager.getSolde() - montant);
        passagerRepository.save(passager);
    }

    public void effectuerPaiement(Voyage voyage, int numeroCompte) {

        Passager passager = passagerRepository.findPassagerByNumeroCompte(numeroCompte);

        if (passager == null) {
            throw new IllegalArgumentException("Le passager avec le numéro de compte donné n'existe pas.");
        }

        if (voyage.getPassagers().contains(passager)) {
            throw new IllegalStateException("Ce passager a déjà payé pour ce voyage.");
        }

        if (passager.getSolde() < 15.0) {
            throw new IllegalStateException("Le passager n'a pas suffisamment de fonds pour payer.");
        }

        debiterMontant(passager, 15.0);

        voyage.getPassagers().add(passager);

        voyage.setNombrePassagers(voyage.getNombrePassagers() + 1);

        // Enregistrer la transaction
        Transaction transaction = new Transaction();
        transaction.setVoyage(voyage);
        transaction.setMethodePaiement(MethodePaiement.CARTE_CREDIT);
        transaction.setStatut(StatutPaiement.VALIDE);
        transaction.setHorodatage(LocalDateTime.now());
        transactionRepository.save(transaction);

        voyageRepository.save(voyage);

        if (voyage.getNombrePassagers() == 6) {
            terminerVoyage(voyage.getId());
        }
    }

    // Terminer un voyage lorsque le taxi atteint 6 passagers
    @Transactional
    public void terminerVoyage(Long idVoyage) {
        Voyage voyage = voyageRepository.findById(idVoyage)
                .orElseThrow(() -> new IllegalArgumentException("Voyage non trouvé"));

        if (voyage.getStatut() != StatutVoyage.EN_COURS) {
            throw new IllegalStateException("Le voyage n'est pas en cours.");
        }

        // Marquer le voyage comme terminé
        voyage.setStatut(StatutVoyage.TERMINE);
        voyage.setHeureDepart(LocalDateTime.now()); // Date et heure de départ
        voyageRepository.save(voyage);
    }

    public void rechargerSoldePassager(int numeroCompte, double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant à recharger doit être positif.");
        }
        Passager passager = passagerRepository.findPassagerByNumeroCompte(numeroCompte);

        if (passager == null) {
            throw new IllegalArgumentException("Le passager avec ce numéro de compte n'existe pas.");
        }

        passager.setSolde(passager.getSolde() + montant);

        passagerRepository.save(passager);
    }

    public void supprimerTaxi(Long taxiId) {
        Taxi taxi = taxiRepository.findById(taxiId)
                .orElseThrow(() -> new IllegalArgumentException("Taxi non trouvé"));

        if (taxi.getStatut() == StatutTaxi.INDISPONIBLE) {
            throw new IllegalStateException("Ce taxi est déjà indisponible.");
        }

        taxi.setStatut(StatutTaxi.INDISPONIBLE);

        taxiRepository.save(taxi);
    }


    // Récupérer le courtier connecté (votre logique ici)
    private Courtier getCourtier() {
        return new Courtier();  // Remplacer par la méthode appropriée pour obtenir l'administrateur actuel
    }
}
