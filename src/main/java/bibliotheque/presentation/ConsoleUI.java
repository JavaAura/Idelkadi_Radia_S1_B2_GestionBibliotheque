package main.java.bibliotheque.presentation;


import main.java.bibliotheque.service.BibliothequeService;
import main.java.bibliotheque.service.UtilisateurService;
import main.java.bibliotheque.DAO.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleUI {

    public static Scanner scanner = new Scanner(System.in);
    public static Connection connection;
    public static BibliothequeService bibliotheque_Service;
    public static UtilisateurService utilisateur_Service;


    public static void main(String[] args) {
        System.out.println("Bienvenue dans le système de gestion de la bibliothèque");
        int choix;

        try {
            connection = DBConnection.getInstance().getConnection();
            bibliotheque_Service = new BibliothequeService();
            utilisateur_Service = new UtilisateurService();

            do {
                afficherMenu();
                System.out.print("Veuillez entrer votre choix (1-6) : ");
                choix = scanner.nextInt();
                switch (choix) {
                    case 1:
                        gererDocuments();
                        break;
                    case 2:
                        gererUtilisateurs();
                        break;
                    case 3:
//                        bibliotheque_Service.rechercherDocument();
                        break;
                    case 4:
                        gererEmprunts();
                        break;
                    case 5:
                        gererReservation();
                        break;
                    case 6:
                        System.out.println("A bientôt !!");
                        System.out.flush();
                        break;
                    default:
                        System.out.println("Choix invalide merci de réessayer");
                        break;
                }
            } while (choix != 6);
        } catch (Exception e) {
            System.out.println("error :" + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("error :" + e.getMessage());
                }
            }
        }

    }

    public static void afficherMenu() {

        System.out.println("==== Menu ====\n" +
                "\n1- Gérer les documents" +
                "\n2- Gérer les utilisateurs" +
                "\n3- Rechercher un document" +
                "\n4- Gérer les emprunts" +
                "\n5- Gérer les réservations" +
                "\n6- Quitter le programme");
    }

    public static void gererDocuments() {
        int choix;
        do {
            System.out.println("\n---- Gérer les documents ----\n" +
                    "\n1- Ajouter un document" +
                    "\n2- Modifier un document" +
                    "\n3- Supprimer un document" +
                    "\n4- Afficher les détails d'un document" +
                    "\n5- Afficher tous les documents" +
                    "\n6- Quitter le sous-menu");
            System.out.print("Veuillez entrer votre choix (1-6) : ");
            choix = scanner.nextInt();
            scanner.nextLine();
            switch (choix) {
                case 1:
                    bibliotheque_Service.ajouterDocument();
                    break;
                case 2:
                    bibliotheque_Service.modifierDocument();
                    break;
                case 3:
                    bibliotheque_Service.supprimerDocument();
                    break;
                case 4:
                    bibliotheque_Service.trouverUnDocument();
                    break;
                case 5:
                    bibliotheque_Service.afficherTousLesDocuments();
                    break;
                case 6:
                    System.out.flush();
                    break;
                default:
                    System.out.println("Choix invalide merci de réessayer");
                    break;
            }
        } while (choix != 6);

    }

    public static void gererUtilisateurs() {
        int choix;
        do {
            System.out.println("\n---- Gérer les utilisateurs ----\n" +
                    "\n1- Ajouter un utilisateur" +
                    "\n2- Modifier un utilisateur" +
                    "\n3- Supprimer un utilisateur" +
                    "\n4- Afficher les détails d'un utilisateur" +
                    "\n5- Afficher tous les utilisateurs" +
                    "\n6- Quitter le sous-menu");

            System.out.print("Veuillez entrer votre choix (1-6) : ");
            choix = scanner.nextInt();
            scanner.nextLine();
            switch (choix) {
                case 1:
                    utilisateur_Service.ajouterUtilisateur();
                    break;
                case 2:
                    utilisateur_Service.mettreAJourUtilisateur();
                    break;
                case 3:
                    utilisateur_Service.supprimerUtilisateur();
                    break;
                case 4:
                    utilisateur_Service.afficherDetailsUtilisateur();
                    break;
                case 5:
                    utilisateur_Service.afficherTousUtilisateurs();
                    break;
                case 6:
                    System.out.flush();
                    break;
                default:
                    System.out.println("Choix invalide merci de réessayer");
                    break;
            }
        } while (choix != 6);

    }

    public static void gererEmprunts() {
        int choix;
        do {
            System.out.println("\n---- Gérer les emprunts ----\n" +
                    "\n1- Emprunter un document" +
                    "\n2- Retourner un document" +
                    "\n3- Quitter le sous-menu");

            System.out.print("Veuillez entrer votre choix (1-3) : ");
            choix = scanner.nextInt();
            scanner.nextLine();


            switch (choix) {
                case 1:
                    bibliotheque_Service.emprunterDocument();
                    break;

                case 2:
                     bibliotheque_Service.retournerDocument();
                    break;

                case 3:
                    System.out.println("Quitter le sous-menu...");
                    return;

                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre entre 1 et 5.");
                    break;
            }
        } while (choix != 6);

    }

    public static void gererReservation() {

        int choix;
        do {
        System.out.println("\n---- Gérer les réservations ----\n" +
                "\n1- Réserver un document" +
                "\n2- Annuler une réservation" +
                "\n5- Quitter le sous-menu");

        System.out.print("Veuillez entrer votre choix (1-3) : ");
        choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1:
                bibliotheque_Service.reserverDocument();
                break;

            case 2:
                bibliotheque_Service.annulerReservation();
                break;

            case 3:
                System.out.println("Quitter le sous-menu...");
                return;

            default:
                System.out.println("Choix invalide. Veuillez entrer un nombre entre 1 et 5.");
                break;
        }
    } while (choix != 6);
    }
}