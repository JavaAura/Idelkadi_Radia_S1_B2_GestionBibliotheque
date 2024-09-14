package bibliotheque.DAO.Implementation;

import bibliotheque.DAO.DocumentDAO;
import bibliotheque.DAO.DBConnection;
import bibliotheque.modele.Livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivreDAOImpl implements DocumentDAO<Livre> {

    private static Connection connection;

    public LivreDAOImpl() {
        this.connection = DBConnection.getInstance().getConnection();

    }

    @Override
    public void ajouterDocument(Livre livre) throws SQLException {
        String query = "INSERT INTO livre (titre, auteur, date_publication, nombre_de_pages, isbn) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setDate(3, Date.valueOf(livre.getDatePublication()));
            preparedStatement.setInt(4, livre.getNombreDePages());
            preparedStatement.setString(5, livre.getIsbn());
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    public void mettreAJourDocument(Livre livre) throws SQLException {
        String sql = "UPDATE livre SET titre = ?, auteur = ?, date_publication = ?, nombre_de_pages = ?, isbn = ?, statut = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setDate(3, java.sql.Date.valueOf(livre.getDatePublication()));
            stmt.setInt(4, livre.getNombreDePages());
            stmt.setString(5, livre.getIsbn());
            stmt.setString(6, livre.getStatut().toString().toLowerCase());
            stmt.setInt(7, livre.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void supprimerDocument(int livreID) throws SQLException {
        String query = "DELETE FROM livre WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, livreID);
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public List<Livre> obtenirTousLesDocuments() throws SQLException {
        String query = "SELECT * FROM livre";
        List<Livre> livres = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Livre livre = new Livre(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("auteur"),
                        resultSet.getDate("date_publication").toLocalDate(),
                        resultSet.getInt("nombre_de_pages"),
                        resultSet.getString("statut"),
                        resultSet.getString("isbn")
                );
                livres.add(livre);
            }
        }
        return livres;
    }


    @Override
    public List<Livre> obtenirDocumentsParTitre(String titre) throws SQLException {
        String query = "SELECT * FROM livre WHERE titre ILIKE ?";
        List<Livre> livres = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + titre + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Livre livre = new Livre(
                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getString("auteur"),
                        resultSet.getDate("date_publication").toLocalDate(),
                        resultSet.getInt("nombre_de_pages"),
                        resultSet.getString("statut"),
                        resultSet.getString("isbn")
                );
                livres.add(livre);
            }
        }
        return livres;
    }

    public Optional<Livre> obtenirDocumentParId(int id) throws SQLException {
        String sql = "SELECT * FROM livre WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Livre livre = new Livre(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("auteur"),
                            rs.getDate("date_publication").toLocalDate(),
                            rs.getInt("nombre_de_pages"),
                            rs.getString("statut"),
                            rs.getString("isbn")
                    );
                    return Optional.of(livre);
                }
                return Optional.empty();
            }
        }
    }


}
