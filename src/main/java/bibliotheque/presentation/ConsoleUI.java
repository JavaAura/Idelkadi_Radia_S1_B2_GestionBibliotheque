package bibliotheque.presentation;


import bibliotheque.service.BibliothequeService;
import bibliotheque.service.UtilisateurService;
import bibliotheque.DAO.DBConnection;
import bibliotheque.utilitaire.InputValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleUI {

    public static Scanner scanner = new Scanner(System.in);
    public static Connection connection;
    public static BibliothequeService bibliotheque_Service;
    public static UtilisateurService utilisateur_Service;


    public static void main(String[] args) {
        System.out.println("Bienvenue dans le systeme de gestion de la bibliotheque");
        int choix;

        try {
            connection = DBConnection.getInstance().getConnection();
            bibliotheque_Service = new BibliothequeService();
            utilisateur_Service = new UtilisateurService();

            do {
                afficherMenu();
                System.out.print("Veuillez entrer votre choix (1-5) : ");
                choix = InputValidator.lireChoix();
                switch (choix) {
                    case 1:
                        gererDocuments();
                        break;
                    case 2:
                        gererUtilisateurs();
                        break;
                    case 3:
                        gererEmprunts();
                        break;
                    case 4:
                        gererReservation();
                        break;
                    case 5:
                        System.out.println("A bientot !!");
                        break;
                    default:
                        System.out.println("Choix invalide merci de reessayer");
                        break;
                }
            } while (choix != 5);
        } catch (Exception e) {
            System.out.println("error :" + e.getMessage());
        } finally {
            DBConnection.getInstance().closeConnection();
        }

    }

    public static void afficherMenu() {

        System.out.println("==== Menu ====\n" +
                "\n1- Gerer les documents" +
                "\n2- Gerer les utilisateurs" +
                "\n3- Gerer les emprunts" +
                "\n4- Gerer les reservations" +
                "\n5- Quitter le programme");
    }

    public static void gererDocuments() {
        int choix;
        do {
            System.out.println("\n---- Gerer les documents ----\n" +
                    "\n1- Ajouter un document" +
                    "\n2- Modifier un document" +
                    "\n3- Supprimer un document" +
                    "\n4- Afficher les détails d'un document" +
                    "\n5- Afficher tous les documents" +
                    "\n6- Quitter le sous-menu");
            System.out.print("Veuillez entrer votre choix (1-6) : ");
            choix = InputValidator.lireChoix();
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
                    return;
                default:
                    System.out.println("Choix invalide merci de reessayer");
                    break;
            }
        } while (choix != 6);

    }

    public static void gererUtilisateurs() {
        int choix;
        do {
            System.out.println("\n---- Gerer les utilisateurs ----\n" +
                    "\n1- Ajouter un utilisateur" +
                    "\n2- Modifier un utilisateur" +
                    "\n3- Supprimer un utilisateur" +
                    "\n4- Afficher les details d'un utilisateur" +
                    "\n5- Afficher tous les utilisateurs" +
                    "\n6- Quitter le sous-menu");

            System.out.print("Veuillez entrer votre choix (1-6) : ");
            choix = InputValidator.lireChoix();
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
                    return;
                default:
                    System.out.println("Choix invalide merci de réessayer");
                    break;
            }
        } while (choix != 6);

    }

    public static void gererEmprunts() {
        int choix;
        do {
            System.out.println("\n---- Gerer les emprunts ----\n" +
                    "\n1- Emprunter un document" +
                    "\n2- Retourner un document" +
                    "\n3- Quitter le sous-menu");

            System.out.print("Veuillez entrer votre choix (1-3) : ");
            choix = InputValidator.lireChoix();
            scanner.nextLine();


            switch (choix) {
                case 1:
                    bibliotheque_Service.emprunterDocument();
                    break;

                case 2:
                    bibliotheque_Service.retournerDocument();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre entre 1 et 3.");
                    break;
            }
        } while (choix != 3);

    }

    public static void gererReservation() {

        int choix;
        do {
            System.out.println("\n---- Gerer les réservations ----\n" +
                    "\n1- Reserver un document" +
                    "\n2- Annuler une reservation" +
                    "\n3- Quitter le sous-menu");

            System.out.print("Veuillez entrer votre choix (1-3) : ");
            choix = InputValidator.lireChoix();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    bibliotheque_Service.reserverDocument();
                    break;

                case 2:
                    bibliotheque_Service.annulerReservation();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre entre 1 et 3.");
                    break;
            }
        } while (choix != 3);
    }
}