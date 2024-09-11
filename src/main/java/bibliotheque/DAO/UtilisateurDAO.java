package main.java.bibliotheque.DAO;

import main.java.bibliotheque.modele.Utilisateur;

import java.sql.SQLException;
import java.util.List;

public interface UtilisateurDAO {
    void ajouterUtilisateur(Utilisateur utilisateur) throws SQLException;

    void mettreAJourUtilisateur(Utilisateur utilisateur) throws SQLException;

    void supprimerUtilisateur(int id) throws SQLException;

    Utilisateur obtenirUtilisateurParId(int id) throws SQLException;

    List<Utilisateur> obtenirTousLesUtilisateurs() throws SQLException;

    Utilisateur obtenirUtilisateurParNumeroDadhesion(String numeroDadhesion) throws SQLException;

}
