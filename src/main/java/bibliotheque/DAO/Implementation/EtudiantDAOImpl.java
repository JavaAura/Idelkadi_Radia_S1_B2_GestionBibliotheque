package main.java.bibliotheque.DAO.Implementation;

import main.java.bibliotheque.DAO.EtudiantDAO;
import main.java.bibliotheque.DAO.UtilisateurDAO;
import main.java.bibliotheque.modele.Etudiant;
import main.java.bibliotheque.modele.Utilisateur;
import main.java.bibliotheque.DAO.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EtudiantDAOImpl implements EtudiantDAO {

    private Connection connection;

    public EtudiantDAOImpl() {
        try {
            this.connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterUtilisateur(Etudiant etudiant) throws SQLException {
        String sql = "INSERT INTO etudiant (nom, age, numero_dadhesion, niveau) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, etudiant.getNom());
            statement.setInt(2, etudiant.getAge());
            statement.setString(3, etudiant.getNumeroDadhesion());
            statement.setString(4, etudiant.getNiveau());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("L'étudiant a été ajouté avec succès !");
            } else {
                System.out.println("L'ajout de l'étudiant a échoué.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'étudiant : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void mettreAJourUtilisateur(Etudiant etudiant) throws SQLException {
        String sql = "UPDATE etudiant SET nom = ?, age = ?, niveau = ? WHERE numero_dadhesion = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, etudiant.getNom());
            statement.setInt(2, etudiant.getAge());
            statement.setString(3, etudiant.getNiveau());
            statement.setString(4, etudiant.getNumeroDadhesion());
            statement.executeUpdate();
        }
    }


    @Override
    public void supprimerUtilisateur(String numeroDadhesion) throws SQLException {
        String sql = "DELETE FROM etudiant WHERE numero_dadhesion = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, numeroDadhesion);
            statement.executeUpdate();
        }
    }

    @Override
    public Etudiant obtenirUtilisateurParId(int id) throws SQLException {
        return null; // Implémentation à ajouter
    }

    @Override
    public List<Etudiant> obtenirTousLesUtilisateurs() throws SQLException {
        List<Etudiant> etudiants = new ArrayList<>();
        String sql = "SELECT * FROM etudiant";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Etudiant etudiant = new Etudiant(
                        resultSet.getString("nom"),
                        resultSet.getInt("age"),
                        resultSet.getString("numero_dadhesion"),
                        resultSet.getString("niveau")
                );
                etudiants.add(etudiant);
            }
        }
        return etudiants;
    }


    public Optional<Etudiant> trouverParNumeroDadhesion(String numeroDadhesion) throws SQLException {
        String sql = "SELECT * FROM etudiant WHERE numero_dadhesion = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, numeroDadhesion);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Etudiant etudiant = new Etudiant();

                etudiant.setNumeroDadhesion(rs.getString("numero_dadhesion"));
                etudiant.setNom(rs.getString("nom"));
                etudiant.setAge(rs.getInt("age"));
                etudiant.setNiveau(rs.getString("niveau"));
                return Optional.of(etudiant);
            }
        }
        return Optional.empty();
    }
}

