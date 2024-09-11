package main.java.bibliotheque.DAO;

import main.java.bibliotheque.modele.Document;
import main.java.bibliotheque.modele.StatutDocument;

import java.sql.SQLException;
import java.util.List;

public interface DocumentDAO {
    void ajouterDocument(Document document) throws SQLException;

    void mettreAJourDocument(Document document) throws SQLException;

    void supprimerDocument(int id) throws SQLException;

    public List<Document> obtenirDocumentsParType(String type) throws SQLException ;

    List<Document> obtenirTousLesDocuments() throws SQLException;

    List<Document> obtenirDocumentsParStatut(StatutDocument statut) throws SQLException;

    List<Document> obtenirDocumentsParTitre(String titre) throws SQLException;
}
