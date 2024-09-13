package main.java.bibliotheque.DAO;

import java.sql.SQLException;

public interface EmpruntDAO {

    void ajouterEmprunt(int utilisateurId, int documentId) throws SQLException;
    void retournerEmprunt(int utilisateurId, int documentId) throws SQLException;
}
