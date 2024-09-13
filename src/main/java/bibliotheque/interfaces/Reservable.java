package main.java.bibliotheque.interfaces;

import main.java.bibliotheque.modele.Utilisateur;

public interface Reservable {
    void emprunter(Utilisateur utilisateur);
    void retourner(Utilisateur utilisateur);
}
