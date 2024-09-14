package main.java.bibliotheque.DAO.Implementation;

import main.java.bibliotheque.DAO.DocumentDAO;
import main.java.bibliotheque.DAO.DBConnection;
import main.java.bibliotheque.modele.Magazine;
import main.java.bibliotheque.modele.StatutDocument;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MagazineDAOImpl implements DocumentDAO<Magazine> {

    private static Connection connection;

    public MagazineDAOImpl() {
            this.connection = DBConnection.getInstance().getConnection();

    }

    @Override
    public void ajouterDocument(Magazine magazine) throws SQLException {
        String query = "INSERT INTO magazine (titre, auteur, date_publication, nombre_de_pages, numero) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, magazine.getTitre());
            preparedStatement.setString(2, magazine.getAuteur());
            preparedStatement.setDate(3, Date.valueOf(magazine.getDatePublication()));
            preparedStatement.setInt(4, magazine.getNombreDePages());
            preparedStatement.setString(5, magazine.getNumero());
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public void supprimerDocument(int magazineID) throws SQLException {
        String query = "DELETE FROM magazine WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, magazineID);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Magazine> obtenirTousLesDocuments() throws SQLException {
        String query = "SELECT * FROM magazine";
        List<Magazine> magazines = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Magazine magazine = new Magazine(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("auteur"),
                        resultSet.getDate("date_publication").toLocalDate(),
                        resultSet.getInt("nombre_de_pages"),
                        resultSet.getString("numero"),
                        resultSet.getString("statut")
                );
                magazines.add(magazine);
            }
        }
        return magazines;
    }

    public Optional<Magazine> obtenirDocumentParId(int id) throws SQLException {
        String sql = "SELECT * FROM magazine WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Magazine magazine = new Magazine(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("auteur"),
                            rs.getDate("date_publication").toLocalDate(),
                            rs.getInt("nombre_de_pages"),
                            rs.getString("numero"),
                            rs.getString("statut")

                    );
                    return Optional.of(magazine);
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Magazine> obtenirDocumentsParTitre(String titre) throws SQLException {
        String query = "SELECT * FROM magazine WHERE titre ILIKE ?";
        List<Magazine> magazines = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + titre + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Magazine magazine = new Magazine(
                        resultSet.getInt("id"),

                        resultSet.getString("titre"),
                        resultSet.getString("auteur"),
                        resultSet.getDate("date_publication").toLocalDate(),
                        resultSet.getInt("nombre_de_pages"),
                        resultSet.getString("numero"),
                        resultSet.getString("statut")

                );
                magazines.add(magazine);
            }
        }
        return magazines;
    }

    public void mettreAJourDocument(Magazine magazine) throws SQLException {
        String sql = "UPDATE magazine SET titre = ?, auteur = ?, date_publication = ?, nombre_de_pages = ?, numero = ?  , statut = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, magazine.getTitre());
            stmt.setString(2, magazine.getAuteur());
            stmt.setDate(3, Date.valueOf(magazine.getDatePublication()));
            stmt.setInt(4, magazine.getNombreDePages());
            stmt.setString(5, magazine.getNumero());
            stmt.setString(6, magazine.getStatut().toString().toLowerCase());
            stmt.setInt(7, magazine.getId());
            stmt.executeUpdate();
        }
    }
}
