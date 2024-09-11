package main.java.bibliotheque.DAO.Implementation;

import main.java.bibliotheque.DAO.DocumentDAO;
import main.java.bibliotheque.modele.Document;
import main.java.bibliotheque.modele.StatutDocument;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TheseDAOImpl implements DocumentDAO {
    @Override
    public void ajouterDocument(Document document) throws SQLException {

    }

    @Override
    public void mettreAJourDocument(Document document) throws SQLException {

    }

    @Override
    public void supprimerDocument(int id) throws SQLException {

    }

    @Override
    public List<Document> obtenirDocumentsParType(String type) throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public List<Document> obtenirTousLesDocuments() throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public List<Document> obtenirDocumentsParStatut(StatutDocument statut) throws SQLException {
        return Collections.emptyList();
    }

    @Override
    public List<Document> obtenirDocumentsParTitre(String titre) throws SQLException {
        return Collections.emptyList();
    }
}
