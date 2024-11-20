package com.taxi.taxi.enumerations;

public enum PassagerMessage {
    BIENVENUE,
    VALIDATION_PAIEMENT,
    RECHARGE;


    public String getMessage() {
        switch (this) {
            case BIENVENUE:
                return "Bienvenue dans l'application !";
            case VALIDATION_PAIEMENT:
                return "Paiement validé avec succès.";
            case RECHARGE:
                return "Recherche effectué avec succès.";
            default:
                return "Notification générique.";
        }
    }
}
