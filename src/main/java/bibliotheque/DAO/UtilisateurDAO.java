package bibliotheque.DAO;

import bibliotheque.modele.Utilisateur;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UtilisateurDAO<T extends Utilisateur> {
    void ajouterUtilisateur(T item) throws SQLException;

    void mettreAJourUtilisateur(T item) throws SQLException;

    void supprimerUtilisateur(String numeroDadhesion) throws SQLException;

    List<T> obtenirTousLesUtilisateurs() throws SQLException;

    Optional<T> trouverParNumeroDadhesion(String numeroDadhesion) throws SQLException;
}
