package main.java.bibliotheque.presentation;

import main.java.bibliotheque.DAO.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;

public class ConsoleUI {
    public static void main(String[] args) {
        try (Connection connection = DBConnection.getInstance().getConnection()) {
            System.out.println("Connexion à la base de données réussie! Braaaavo!!");
        } catch (SQLException ex) {
            System.err.println("Erreur de connexion à la base de données : " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Erreur lors du chargement des propriétés : " + ex.getMessage());
        }
    }
}
