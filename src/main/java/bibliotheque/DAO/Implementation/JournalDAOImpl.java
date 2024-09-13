package main.java.bibliotheque.DAO.Implementation;

import main.java.bibliotheque.DAO.DocumentDAO;
import main.java.bibliotheque.DAO.DBConnection;
import main.java.bibliotheque.modele.JournalScientifique;
import main.java.bibliotheque.modele.StatutDocument;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JournalDAOImpl implements DocumentDAO<JournalScientifique> {

    private static Connection connection;

    public JournalDAOImpl() {
        try {
            this.connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterDocument(JournalScientifique journal_scientifique) throws SQLException {
        String query = "INSERT INTO journal_scientifique (titre, auteur, date_publication, nombre_de_pages, domaine_recherche) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, journal_scientifique.getTitre());
            preparedStatement.setString(2, journal_scientifique.getAuteur());
            preparedStatement.setDate(3, Date.valueOf(journal_scientifique.getDatePublication()));
            preparedStatement.setInt(4, journal_scientifique.getNombreDePages());
            preparedStatement.setString(5, journal_scientifique.getDomaineRecherche());
            preparedStatement.executeUpdate();
        }catch (Exception ex){
            throw new SQLException(ex.getMessage());
        }
    }

    public void mettreAJourDocument(JournalScientifique journal) throws SQLException {
        String sql = "UPDATE journal_scientifique SET titre = ?, auteur = ?, date_publication = ?, nombre_de_pages = ?, domaine_recherche = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, journal.getTitre());
            stmt.setString(2, journal.getAuteur());
            stmt.setDate(3, Date.valueOf(journal.getDatePublication()));
            stmt.setInt(4, journal.getNombreDePages());
            stmt.setString(5, journal.getDomaineRecherche());
            stmt.setInt(6, journal.getId());
            stmt.executeUpdate();
        }
    }


    @Override
    public void supprimerDocument(int journalID) throws SQLException {
        String query = "DELETE FROM journal_scientifique WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, journalID);
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public List<JournalScientifique> obtenirTousLesDocuments() throws SQLException {
        String query = "SELECT * FROM journal_scientifique";
        List<JournalScientifique> journaux = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                JournalScientifique journal_scientifique = new JournalScientifique(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("auteur"),
                        resultSet.getDate("date_publication").toLocalDate(),
                        resultSet.getInt("nombre_de_pages"),
                        resultSet.getString("domaine_recherche")
                );
                journaux.add(journal_scientifique);
            }
        }
        return journaux;
    }


    public Optional<JournalScientifique> obtenirDocumentParId(int id) throws SQLException {
        String sql = "SELECT * FROM journal_scientifique WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    JournalScientifique journal = new JournalScientifique(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("auteur"),
                            rs.getDate("date_publication").toLocalDate(),
                            rs.getInt("nombre_de_pages"),
                            rs.getString("domaine_recherche")
                    );
                    return Optional.of(journal);
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<JournalScientifique> obtenirDocumentsParTitre(String titre) throws SQLException {
        String query = "SELECT * FROM journal_scientifique WHERE titre ILIKE ?";
        List<JournalScientifique> journaux = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + titre + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                JournalScientifique journal_scientifique = new JournalScientifique(
                        resultSet.getInt("id"),

                        resultSet.getString("titre"),
                        resultSet.getString("auteur"),
                        resultSet.getDate("date_publication").toLocalDate(),
                        resultSet.getInt("nombre_de_pages"),
                        resultSet.getString("domaine_recherche")
                );
                journaux.add(journal_scientifique);
            }
        }
        return journaux;
    }
}
