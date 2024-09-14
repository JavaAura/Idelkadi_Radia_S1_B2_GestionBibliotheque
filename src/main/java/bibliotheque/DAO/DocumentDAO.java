package bibliotheque.DAO;

import bibliotheque.modele.Document;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface DocumentDAO<T extends Document> {
    void ajouterDocument(T document) throws SQLException;

    Optional<T> obtenirDocumentParId(int id) throws SQLException;

    void supprimerDocument(int id) throws SQLException;

    List<T> obtenirTousLesDocuments() throws SQLException;

    List<T> obtenirDocumentsParTitre(String titre) throws SQLException;

    void mettreAJourDocument(T document) throws SQLException;
}
