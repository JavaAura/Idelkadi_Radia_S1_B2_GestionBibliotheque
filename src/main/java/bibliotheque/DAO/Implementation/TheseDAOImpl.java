package main.java.bibliotheque.DAO.Implementation;

import main.java.bibliotheque.DAO.DocumentDAO;
import main.java.bibliotheque.DAO.DBConnection;
import main.java.bibliotheque.modele.TheseUniversitaire;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TheseDAOImpl implements DocumentDAO<TheseUniversitaire> {

    private static Connection connection;

    public TheseDAOImpl() {
            this.connection = DBConnection.getInstance().getConnection();

    }

    @Override
    public void ajouterDocument(TheseUniversitaire these) throws SQLException {
        String query = "INSERT INTO these_universitaire (titre, auteur, date_publication, nombre_de_pages, universite, domaine) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, these.getTitre());
            preparedStatement.setString(2, these.getAuteur());
            preparedStatement.setDate(3, Date.valueOf(these.getDatePublication()));
            preparedStatement.setInt(4, these.getNombreDePages());
            preparedStatement.setString(5, these.getUniversite());
            preparedStatement.setString(6, these.getDomaine());

            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    public Optional<TheseUniversitaire> obtenirDocumentParId(int id) throws SQLException {
        String sql = "SELECT * FROM these_universitaire WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TheseUniversitaire these = new TheseUniversitaire(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("auteur"),
                            rs.getDate("date_publication").toLocalDate(),
                            rs.getInt("nombre_de_pages"),
                            rs.getString("universite"),
                            rs.getString("domaine"),
                            rs.getString("statut")
                    );
                    return Optional.of(these);
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public void supprimerDocument(int theseID) throws SQLException {
        String query = "DELETE FROM these_universitaire WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, theseID);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<TheseUniversitaire> obtenirTousLesDocuments() throws SQLException {
        String query = "SELECT * FROM these_universitaire";
        List<TheseUniversitaire> theses = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                TheseUniversitaire these = new TheseUniversitaire(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("auteur"),
                        resultSet.getDate("date_publication").toLocalDate(),
                        resultSet.getInt("nombre_de_pages"),
                        resultSet.getString("universite"),
                        resultSet.getString("domaine"),
                        resultSet.getString("statut")
                );
                theses.add(these);
            }
        }
        return theses;
    }

    @Override
    public List<TheseUniversitaire> obtenirDocumentsParTitre(String titre) throws SQLException {
        String query = "SELECT * FROM these_universitaire WHERE titre ILIKE ?";
        List<TheseUniversitaire> theses = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + titre + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TheseUniversitaire these = new TheseUniversitaire(
                        resultSet.getInt("id"),

                        resultSet.getString("titre"),
                        resultSet.getString("auteur"),
                        resultSet.getDate("date_publication").toLocalDate(),
                        resultSet.getInt("nombre_de_pages"),
                        resultSet.getString("universite"),
                        resultSet.getString("domaine"),
                        resultSet.getString("statut")

                );
                theses.add(these);
            }
        }
        return theses;
    }

    public void mettreAJourDocument(TheseUniversitaire these) throws SQLException {
        String sql = "UPDATE these_universitaire SET titre = ?, auteur = ?, date_publication = ?, nombre_de_pages = ?, universite = ?, domaine = ? , statut = ?  WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, these.getTitre());
            stmt.setString(2, these.getAuteur());
            stmt.setDate(3, Date.valueOf(these.getDatePublication()));
            stmt.setInt(4, these.getNombreDePages());
            stmt.setString(5, these.getUniversite());
            stmt.setString(6, these.getDomaine());
            stmt.setString(7, these.getStatut().toString().toLowerCase());
            stmt.setInt(8, these.getId()); // Assurez-vous que l'ID est stocké dans l'objet TheseUniversitaire
            stmt.executeUpdate();
        }
    }

}
