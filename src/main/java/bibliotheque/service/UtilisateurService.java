package main.java.bibliotheque.service;

import main.java.bibliotheque.DAO.EtudiantDAO;
import main.java.bibliotheque.DAO.Implementation.EtudiantDAOImpl;
import main.java.bibliotheque.DAO.Implementation.ProfesseurDAOImpl;
import main.java.bibliotheque.DAO.ProfesseurDAO;
import main.java.bibliotheque.modele.Etudiant;
import main.java.bibliotheque.modele.Professeur;
import main.java.bibliotheque.modele.Utilisateur;
import main.java.bibliotheque.utilitaire.InputValidator;
import main.java.bibliotheque.utilitaire.ScannerUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UtilisateurService {


    public UtilisateurService() {

    }

    private final static Scanner scanner = ScannerUtil.getScanner();
    private static EtudiantDAO etudiantDAO;
    private static ProfesseurDAO professeurDAO;

    public  void ajouterUtilisateur() {
        int typeUtilisateur = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.println("Cet utilisateur est-il un étudiant ou un professeur ? (1- Étudiant, 2- Professeur)");
                typeUtilisateur = scanner.nextInt();
                scanner.nextLine();

                if (typeUtilisateur == 1 || typeUtilisateur == 2) {
                    valid = true;
                } else {
                    System.out.println("Choix invalide, veuillez entrer 1 pour Étudiant ou 2 pour Professeur.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
                scanner.nextLine();
            }
        }

        String nom = "";
        int age = 0;

        boolean validNom = false;
        while (!validNom) {
            System.out.print("Entrez le nom de l'utilisateur : ");
            nom = scanner.nextLine();
            if (InputValidator.isValidString(nom)) {
                validNom = true;
            } else {
                System.out.println("Nom invalide. Veuillez réessayer.");
            }
        }

        boolean validAge = false;
        while (!validAge) {
            try {
                System.out.print("Entrez l'âge de l'utilisateur : ");
                age = scanner.nextInt();
                scanner.nextLine();

                if (InputValidator.isValidAge(age)) {
                    validAge = true;
                } else {
                    System.out.println("Âge invalide. L'âge doit être supérieur à 6 ans.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Âge invalide. Veuillez entrer un nombre entier.");
                scanner.nextLine();
            }
        }

        String numeroAdhesion = InputValidator.generateNumeroDadhesion(nom, age, LocalDate.now());

        if (typeUtilisateur == 1) {
            System.out.print("Entrez le niveau de l'étudiant : ");
            String niveau = scanner.nextLine();

            if (!InputValidator.isValidString(niveau)) {
                System.out.println("Niveau invalide. Veuillez réessayer.");
                return;
            }
            try {
                Etudiant etudiant = new Etudiant(nom, age, numeroAdhesion, niveau);
                etudiantDAO = new EtudiantDAOImpl();
                etudiantDAO.ajouterUtilisateur(etudiant);
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de l'étudiant : " + e.getMessage());
            }

        } else if (typeUtilisateur == 2) {
            System.out.print("Entrez le département du professeur : ");
            String departement = scanner.nextLine();

            if (!InputValidator.isValidString(departement)) {
                System.out.println("Département invalide. Veuillez réessayer.");
                return;
            }

            try {
                Professeur professeur = new Professeur(nom, age, numeroAdhesion, departement);
                professeurDAO = new ProfesseurDAOImpl();
                professeurDAO.ajouterUtilisateur(professeur);
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du professeur : " + e.getMessage());
            }
        }
    }

    public  void mettreAJourUtilisateur() {
        try {
            etudiantDAO = new EtudiantDAOImpl();
            professeurDAO = new ProfesseurDAOImpl();

            System.out.print("Entrez le numéro d'adhésion de l'utilisateur à mettre à jour : ");
            String numeroAdhesion = scanner.nextLine();

            Optional<Etudiant> etudiant = etudiantDAO.trouverParNumeroDadhesion(numeroAdhesion);
            Optional<Professeur> professeur = professeurDAO.trouverParNumeroDadhesion(numeroAdhesion);

            if (etudiant.isPresent()) {
                Etudiant etu = etudiant.get();
                afficherDetailsUtilisateur(etu);

                System.out.print("Entrez le nouveau nom (laissez vide pour conserver " + etu.getNom() + ") : ");
                String nouveauNom = scanner.nextLine();
                if (nouveauNom.isEmpty()) {
                    nouveauNom = etu.getNom();
                }

                System.out.print("Entrez le nouvel âge (laissez vide pour conserver " + etu.getAge() + ") : ");
                String ageInput = scanner.nextLine();
                int nouvelAge = etu.getAge();
                if (!ageInput.isEmpty()) {
                    try {
                        nouvelAge = Integer.parseInt(ageInput);
                        if (!InputValidator.isValidAge(nouvelAge)) {
                            System.out.println("Âge invalide. L'âge doit être supérieur à 6 ans.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Âge invalide.");
                        return;
                    }
                }

                System.out.print("Entrez le nouveau niveau (laissez vide pour conserver " + etu.getNiveau() + ") : ");
                String nouveauNiveau = scanner.nextLine();
                if (nouveauNiveau.isEmpty()) {
                    nouveauNiveau = etu.getNiveau();
                }

                Etudiant etudiantMisAJour = new Etudiant(nouveauNom, nouvelAge, numeroAdhesion, nouveauNiveau);
                etudiantDAO.mettreAJourUtilisateur(etudiantMisAJour);

                System.out.println("L'étudiant a été mis à jour avec succès.");

            } else if (professeur.isPresent()) {
                Professeur prof = professeur.get();
                afficherDetailsUtilisateur(prof);

                System.out.print("Entrez le nouveau nom (laissez vide pour conserver " + prof.getNom() + ") : ");
                String nouveauNom = scanner.nextLine();
                if (nouveauNom.isEmpty()) {
                    nouveauNom = prof.getNom();
                }

                System.out.print("Entrez le nouvel âge (laissez vide pour conserver " + prof.getAge() + ") : ");
                String ageInput = scanner.nextLine();
                int nouvelAge = prof.getAge();
                if (!ageInput.isEmpty()) {
                    try {
                        nouvelAge = Integer.parseInt(ageInput);
                        if (!InputValidator.isValidAge(nouvelAge)) {
                            System.out.println("Âge invalide. L'âge doit être supérieur à 6 ans.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Âge invalide.");
                        return;
                    }
                }

                System.out.print("Entrez le nouveau département (laissez vide pour conserver " + prof.getDepartement() + ") : ");
                String nouveauDepartement = scanner.nextLine();
                if (nouveauDepartement.isEmpty()) {
                    nouveauDepartement = prof.getDepartement();
                }

                Professeur professeurMisAJour = new Professeur(nouveauNom, nouvelAge, numeroAdhesion, nouveauDepartement);
                professeurDAO.mettreAJourUtilisateur(professeurMisAJour);

                System.out.println("Le professeur a été mis à jour avec succès.");

            } else {
                System.out.println("Aucun utilisateur trouvé avec ce numéro d'adhésion.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        }
    }

    public  void supprimerUtilisateur() {
        try {
            etudiantDAO = new EtudiantDAOImpl();
            professeurDAO = new ProfesseurDAOImpl();

            System.out.print("Entrez le numéro d'adhésion de l'utilisateur à supprimer : ");
            String numeroAdhesion = scanner.nextLine();

            Optional<Etudiant> etudiant = etudiantDAO.trouverParNumeroDadhesion(numeroAdhesion);
            Optional<Professeur> professeur = professeurDAO.trouverParNumeroDadhesion(numeroAdhesion);

            if (etudiant.isPresent()) {
                etudiantDAO.supprimerUtilisateur(numeroAdhesion);
                System.out.println("L'étudiant avec le numéro d'adhésion " + numeroAdhesion + " a été supprimé.");
            } else if (professeur.isPresent()) {
                professeurDAO.supprimerUtilisateur(numeroAdhesion);
                System.out.println("Le professeur avec le numéro d'adhésion " + numeroAdhesion + " a été supprimé.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec ce numéro d'adhésion.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }

    public void afficherTousUtilisateurs() {
        try {
            etudiantDAO = new EtudiantDAOImpl();
            professeurDAO = new ProfesseurDAOImpl();

            List<Etudiant> etudiants = etudiantDAO.obtenirTousLesUtilisateurs();

            List<Professeur> professeurs = professeurDAO.obtenirTousLesUtilisateurs();

            if (etudiants.isEmpty() && professeurs.isEmpty()) {
                System.out.println("Aucun utilisateur trouvé.");
            } else {
                System.out.println("Liste de tous les utilisateurs : ");

                if (!etudiants.isEmpty()) {
                    System.out.println("| Numéro d'adhésion\t\t\t| Nom\t\t\t  | Âge  | Niveau       | Rôle       |");
                    System.out.println("------------------------------------------------------------------------------");
                    etudiants.forEach(this::afficherDetailsUtilisateur);
                }

                if (!professeurs.isEmpty()) {
                    System.out.println("\n\t\t ********************************************** \n");


                    System.out.println("| Numéro d'adhésion \t\t\t| Nom \t\t\t\t | Âge  | Département  | Rôle       |");
                    System.out.println("------------------------------------------------------------------------------");
                    professeurs.forEach(this::afficherDetailsUtilisateur);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des utilisateurs : " + e.getMessage());
        }
    }

    private void afficherDetailsUtilisateur(Utilisateur utilisateur) {
        String numeroAdhesion = Optional.ofNullable(utilisateur.getNumeroDadhesion()).orElse("N/A");
        String nom = Optional.ofNullable(utilisateur.getNom()).orElse("N/A");
        String age = Optional.of(Integer.toString(utilisateur.getAge())).orElse("N/A");
        String role = utilisateur instanceof Etudiant ? "Étudiant" : "Professeur";

        if (utilisateur instanceof Etudiant) {
            String niveau = Optional.ofNullable(((Etudiant) utilisateur).getNiveau()).orElse("N/A");
            System.out.printf("| %-25s | %-15s | %-4s | %-12s | %-10s |%n",
                    numeroAdhesion, nom, age, niveau, role);
        } else if (utilisateur instanceof Professeur) {
            String departement = Optional.ofNullable(((Professeur) utilisateur).getDepartement()).orElse("N/A");
            System.out.printf("| %-29s | %-18s | %-4s | %-12s | %-10s |%n",
                    numeroAdhesion, nom, age, departement, role);
        }
    }

    public void trouverUtilisateur() {
        try {
            etudiantDAO = new EtudiantDAOImpl();
            professeurDAO = new ProfesseurDAOImpl();

            System.out.print("Veuillez entrer le numéro d'adhésion de l'utilisateur : ");
            String numeroAdhesion = scanner.nextLine();

            Optional<Etudiant> etudiant = etudiantDAO.obtenirTousLesUtilisateurs()
                    .stream()
                    .filter(e -> e.getNumeroDadhesion().equals(numeroAdhesion))
                    .findFirst();

            if (etudiant.isPresent()) {
                afficherDetailsUtilisateur(etudiant.get());
                return;
            }

            Optional<Professeur> professeur = professeurDAO.obtenirTousLesUtilisateurs()
                    .stream()
                    .filter(p -> p.getNumeroDadhesion().equals(numeroAdhesion))
                    .findFirst();

            if (professeur.isPresent()) {
                afficherDetailsUtilisateur(professeur.get());
            } else {
                System.out.println("Utilisateur non trouvé.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur : " + e.getMessage());
        }
    }

}