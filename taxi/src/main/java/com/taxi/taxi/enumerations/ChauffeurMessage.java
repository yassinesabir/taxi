package com.taxi.taxi.enumerations;

public enum ChauffeurMessage {
    BIENVENUE,
    RAPPEL;

    public String getMessage() {
        switch (this) {
            case BIENVENUE:
                return "Bienvenue dans l'application !";
            case RAPPEL:
                return "Merci de rejoindre la station, il n'y a que 6 taxis devant vous.";
            default:
                return "Notification générique.";
        }
    }
}
