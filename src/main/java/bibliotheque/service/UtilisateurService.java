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
    private static EtudiantDAO etudiantDAO = new EtudiantDAOImpl();
    private static ProfesseurDAO professeurDAO = new ProfesseurDAOImpl();

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


    public void afficherTousUtilisateurs() {
        try {
            List<Etudiant> etudiants = etudiantDAO.obtenirTousLesUtilisateurs();
            List<Professeur> professeurs = professeurDAO.obtenirTousLesUtilisateurs();

            if (etudiants.isEmpty() && professeurs.isEmpty()) {
                System.out.println("Aucun utilisateur trouvé.");
            } else {
                System.out.println("Liste de tous les utilisateurs : ");

                if (!etudiants.isEmpty()) {
                    System.out.println("| Numéro d'adhésion\t\t\t| Nom\t\t\t  | Âge  | Niveau       | Rôle       |");
                    System.out.println("------------------------------------------------------------------------------");
                    etudiants.forEach(etudiant -> System.out.println(etudiant.toString()));
                }

                if (!professeurs.isEmpty()) {
                    System.out.println("\n\t\t ********************************************** \n");
                    System.out.println("| Numéro d'adhésion \t\t\t| Nom \t\t\t\t | Âge  | Département  | Rôle       |");
                    System.out.println("------------------------------------------------------------------------------");
                    professeurs.forEach(professeur -> System.out.println(professeur.toString()));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des utilisateurs : " + e.getMessage());
        }
    }

    public void afficherDetailsUtilisateur() {
        System.out.print("Entrez le numéro d'adhésion de l'utilisateur : ");
        String numeroAdhesion = scanner.nextLine();

        Optional<Utilisateur> utilisateur = trouverUtilisateur(numeroAdhesion);

        if (utilisateur.isPresent()) {
            Utilisateur user = utilisateur.get();

            String numAdhesion = Optional.ofNullable(user.getNumeroDadhesion()).orElse("N/A");
            String nom = Optional.ofNullable(user.getNom()).orElse("N/A");
            String age = Optional.of(Integer.toString(user.getAge())).orElse("N/A");
            String role = user instanceof Etudiant ? "Étudiant" : "Professeur";

            System.out.println("Détails de l'utilisateur :");
            System.out.println("Numéro d'adhésion : " + numAdhesion);
            System.out.println("Nom : " + nom);
            System.out.println("Âge : " + age);
            System.out.println("Rôle : " + role);

            if (user instanceof Etudiant) {
                String niveau = Optional.ofNullable(((Etudiant) user).getNiveau()).orElse("N/A");
                System.out.println("Niveau : " + niveau);
            }
            else if (user instanceof Professeur) {
                String departement = Optional.ofNullable(((Professeur) user).getDepartement()).orElse("N/A");
                System.out.println("Département : " + departement);
            }
        } else {
            System.out.println("Aucun utilisateur trouvé avec ce numéro d'adhésion.");
        }
    }


    public void mettreAJourUtilisateur() {
        try {
            System.out.print("Entrez le numéro d'adhésion de l'utilisateur à mettre à jour : ");
            String numeroAdhesion = scanner.nextLine();

            Optional<Utilisateur> utilisateur = trouverUtilisateur(numeroAdhesion);

            if (utilisateur.isPresent()) {
                Utilisateur utilisateurAMettreAJour = utilisateur.get();
                utilisateurAMettreAJour.toString();

                System.out.print("Entrez le nouveau nom (laissez vide pour conserver " + utilisateurAMettreAJour.getNom() + ") : ");
                String nouveauNom = scanner.nextLine();
                if (!nouveauNom.isEmpty()) {
                    utilisateurAMettreAJour.setNom(nouveauNom);
                }

                System.out.print("Entrez le nouvel âge (laissez vide pour conserver " + utilisateurAMettreAJour.getAge() + ") : ");
                String ageInput = scanner.nextLine();
                if (!ageInput.isEmpty()) {
                    try {
                        int nouvelAge = Integer.parseInt(ageInput);
                        if (InputValidator.isValidAge(nouvelAge)) {
                            utilisateurAMettreAJour.setAge(nouvelAge);
                        } else {
                            System.out.println("Âge invalide. L'âge doit être supérieur à 6 ans.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Âge invalide.");
                        return;
                    }
                }

                if (utilisateurAMettreAJour instanceof Etudiant) {
                    Etudiant etudiant = (Etudiant) utilisateurAMettreAJour;
                    System.out.print("Entrez le nouveau niveau (laissez vide pour conserver " + etudiant.getNiveau() + ") : ");
                    String nouveauNiveau = scanner.nextLine();
                    if (!nouveauNiveau.isEmpty()) {
                        etudiant.setNiveau(nouveauNiveau);
                    }
                    etudiantDAO.mettreAJourUtilisateur(etudiant);
                    System.out.println("L'étudiant a été mis à jour avec succès.");
                } else if (utilisateurAMettreAJour instanceof Professeur) {
                    Professeur professeur = (Professeur) utilisateurAMettreAJour;
                    System.out.print("Entrez le nouveau département (laissez vide pour conserver " + professeur.getDepartement() + ") : ");
                    String nouveauDepartement = scanner.nextLine();
                    if (!nouveauDepartement.isEmpty()) {
                        professeur.setDepartement(nouveauDepartement);
                    }
                    professeurDAO.mettreAJourUtilisateur(professeur);
                    System.out.println("Le professeur a été mis à jour avec succès.");
                }

            } else {
                System.out.println("Aucun utilisateur trouvé avec ce numéro d'adhésion.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        }
    }

    public void supprimerUtilisateur() {
        try {
            System.out.print("Entrez le numéro d'adhésion de l'utilisateur à supprimer : ");
            String numeroAdhesion = scanner.nextLine();

            Optional<Utilisateur> utilisateur = trouverUtilisateur(numeroAdhesion);

            if (utilisateur.isPresent()) {
                if (utilisateur.get() instanceof Etudiant) {
                    etudiantDAO.supprimerUtilisateur(numeroAdhesion);
                    System.out.println("L'étudiant avec le numéro d'adhésion " + numeroAdhesion + " a été supprimé.");
                } else if (utilisateur.get() instanceof Professeur) {
                    professeurDAO.supprimerUtilisateur(numeroAdhesion);
                    System.out.println("Le professeur avec le numéro d'adhésion " + numeroAdhesion + " a été supprimé.");
                }
            } else {
                System.out.println("Aucun utilisateur trouvé avec ce numéro d'adhésion.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }

    public Optional<Utilisateur> trouverUtilisateur(String numeroAdhesion) {
        try {
            etudiantDAO = new EtudiantDAOImpl();
            professeurDAO = new ProfesseurDAOImpl();

            Optional<Etudiant> etudiant = etudiantDAO.trouverParNumeroDadhesion(numeroAdhesion);
            if (etudiant.isPresent()) {
                return Optional.of(etudiant.get());
            }

            Optional<Professeur> professeur = professeurDAO.trouverParNumeroDadhesion(numeroAdhesion);
            if (professeur.isPresent()) {
                return Optional.of(professeur.get());
            }

            System.out.println("Utilisateur non trouvé.");
            return Optional.empty();

        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur : " + e.getMessage());
            return Optional.empty();
        }
    }




}