package bibliotheque.DAO.Implementation;

import bibliotheque.DAO.ProfesseurDAO;
import bibliotheque.modele.Professeur;
import bibliotheque.DAO.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfesseurDAOImpl implements ProfesseurDAO {

    private Connection connection;

    public ProfesseurDAOImpl() {
        this.connection = DBConnection.getInstance().getConnection();

    }

    @Override
    public void ajouterUtilisateur(Professeur professeur) throws SQLException {
        String sql = "INSERT INTO professeur (nom, age, numero_dadhesion, departement) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, professeur.getNom());
            statement.setInt(2, professeur.getAge());
            statement.setString(3, professeur.getNumeroDadhesion());
            statement.setString(4, professeur.getDepartement());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Le professeur a été ajouté avec succès !");
            } else {
                System.out.println("L'ajout du professeur a échoué.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du professeur : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void mettreAJourUtilisateur(Professeur professeur) throws SQLException {
        String sql = "UPDATE professeur SET nom = ?, age = ?, departement = ? WHERE numero_dadhesion = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, professeur.getNom());
            statement.setInt(2, professeur.getAge());
            statement.setString(3, professeur.getDepartement());
            statement.setString(4, professeur.getNumeroDadhesion());
            statement.executeUpdate();
        }
    }


    @Override
    public void supprimerUtilisateur(String numeroDadhesion) throws SQLException {
        String sql = "DELETE FROM professeur WHERE numero_dadhesion = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, numeroDadhesion);
            statement.executeUpdate();
        }
    }


    public Optional<Professeur> trouverParNumeroDadhesion(String numeroDadhesion) throws SQLException {
        String sql = "SELECT * FROM professeur WHERE numero_dadhesion = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, numeroDadhesion);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Professeur professeur = new Professeur();
                professeur.setId(rs.getInt("id"));
                professeur.setNumeroDadhesion(rs.getString("numero_dadhesion"));
                professeur.setNom(rs.getString("nom"));
                professeur.setAge(rs.getInt("age"));
                professeur.setDepartement(rs.getString("departement"));
                return Optional.of(professeur);
            }
        }
        return Optional.empty();
    }

    public List<Professeur> obtenirTousLesUtilisateurs() throws SQLException {
        List<Professeur> professeurs = new ArrayList<>();
        String sql = "SELECT * FROM professeur";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Professeur professeur = new Professeur(
                        resultSet.getString("nom"),
                        resultSet.getInt("age"),
                        resultSet.getString("numero_dadhesion"),
                        resultSet.getString("departement")
                );
                professeurs.add(professeur);
            }
        }
        return professeurs;
    }


}
