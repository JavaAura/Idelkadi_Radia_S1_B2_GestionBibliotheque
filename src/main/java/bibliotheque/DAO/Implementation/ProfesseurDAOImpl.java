package main.java.bibliotheque.DAO.Implementation;

import main.java.bibliotheque.DAO.UtilisateurDAO;
import main.java.bibliotheque.modele.Utilisateur;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class ProfesseurDAOImpl implements UtilisateurDAO {
    @Override
    public void ajouterUtilisateur(Utilisateur utilisateur) throws SQLException {

    }

    @Override
    public void mettreAJourUtilisateur(Utilisateur utilisateur) throws SQLException {

    }

    @Override
    public void supprimerUtilisateur(int id) throws SQLException {

    }

    @Override
    public Utilisateur obtenirUtilisateurParId(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Utilisateur> obtenirTousLesUtilisateurs() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public Utilisateur obtenirUtilisateurParNumeroDadhesion(String numeroDadhesion) throws SQLException {
        return null;
    }
}
