package main.java.bibliotheque.DAO.Implementation;

import main.java.bibliotheque.DAO.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationDAOImpl{

    private Connection connection;

    public ReservationDAOImpl() {
        try {
            this.connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public boolean verifierReservation(int documentId, int utilisateurId) throws SQLException {
        String sql = "SELECT 1 FROM reservation WHERE document_id = ? AND utilisateur_id = ? AND date_annulation IS NULL";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, documentId);
            pstmt.setInt(2, utilisateurId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    public void ajouterReservation(int utilisateurId, int documentId) throws SQLException {
        String sql = "INSERT INTO reservation (utilisateur_id, document_id, date_reservation) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, utilisateurId);
            pstmt.setInt(2, documentId);
            pstmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            pstmt.executeUpdate();
        }
    }
    public void annulerReservation(int documentId, int utilisateurId) throws SQLException {
        String sql = "UPDATE reservation SET date_annulation = NOW() WHERE document_id = ? AND utilisateur_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, documentId);
            pstmt.setInt(2, utilisateurId);
            pstmt.executeUpdate();
        }
    }

}
