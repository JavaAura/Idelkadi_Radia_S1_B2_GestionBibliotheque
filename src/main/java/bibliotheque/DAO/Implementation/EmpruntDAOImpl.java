package main.java.bibliotheque.DAO.Implementation;

import main.java.bibliotheque.DAO.DBConnection;
import main.java.bibliotheque.DAO.EmpruntDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpruntDAOImpl implements EmpruntDAO {

    private Connection connection;

    public EmpruntDAOImpl() {
        try {
            this.connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void ajouterEmprunt(int utilisateurId, int documentId) throws SQLException {
        String sql = "INSERT INTO emprunt (utilisateur_id, document_id, date_emprunt) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, utilisateurId);
            statement.setInt(2, documentId);
            statement.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de l'ajout de l'emprunt : " + e.getMessage());
        }
    }


    public boolean verifierEmprunt(int documentId, int utilisateurId) throws SQLException {
        String sql = "SELECT 1 FROM emprunt WHERE document_id = ? AND utilisateur_id = ? AND date_retour IS NULL";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, documentId);
            pstmt.setInt(2, utilisateurId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    // Méthode pour mettre à jour la date de retour de l'emprunt
    public void retournerEmprunt(int documentId, int utilisateurId) throws SQLException {
        String sql = "UPDATE emprunt SET date_retour = ? WHERE document_id = ? AND utilisateur_id = ? AND date_retour IS NULL";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            pstmt.setInt(2, documentId);
            pstmt.setInt(3, utilisateurId);
            pstmt.executeUpdate();
        }
    }
}
