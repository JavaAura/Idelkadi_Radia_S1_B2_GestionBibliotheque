package main.java.bibliotheque.service;

import main.java.bibliotheque.DAO.Implementation.*;
import main.java.bibliotheque.interfaces.Empruntable;
import main.java.bibliotheque.modele.*;
import main.java.bibliotheque.utilitaire.InputValidator;
import main.java.bibliotheque.utilitaire.ScannerUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class BibliothequeService {

    private static final Scanner scanner = ScannerUtil.getScanner();
    private static final LivreDAOImpl livreDAOImpl = new LivreDAOImpl();
    private static final MagazineDAOImpl magazineDAOImpl = new MagazineDAOImpl();
    private static final JournalDAOImpl journalDAOImpl = new JournalDAOImpl();
    private static final TheseDAOImpl theseDAOImpl = new TheseDAOImpl();
    private EmpruntDAOImpl empruntDAOImpl = new EmpruntDAOImpl();

    public void ajouterDocument() {
        int typeDocument = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.println("Quel type de document voulez-vous ajouter ? (1- Livre, 2- Magazine, 3- Journal Scientifique, 4- Thèse Universitaire)");
                typeDocument = scanner.nextInt();
                scanner.nextLine();

                if (typeDocument >= 1 && typeDocument <= 4) {
                    valid = true;
                } else {
                    System.out.println("Choix invalide. Veuillez entrer un nombre entre 1 et 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
                scanner.nextLine();
            }
        }

        String titre = "";
        String auteur = "";
        LocalDate datePublication = null;
        int nombreDePages = 0;

        boolean validTitre = false;
        while (!validTitre) {
            System.out.print("Entrez le titre du document : ");
            titre = scanner.nextLine();
            if (InputValidator.isValidString(titre)) {
                validTitre = true;
            } else {
                System.out.println("Titre invalide. Veuillez réessayer.");
            }
        }

        boolean validAuteur = false;
        while (!validAuteur) {
            System.out.print("Entrez l'auteur du document : ");
            auteur = scanner.nextLine();
            if (InputValidator.isValidString(auteur)) {
                validAuteur = true;
            } else {
                System.out.println("Auteur invalide. Veuillez réessayer.");
            }
        }

        boolean validDate = false;
        while (!validDate) {
            System.out.print("Entrez la date de publication (format YYYY-MM-DD) : ");
            String dateStr = scanner.nextLine();
            if (InputValidator.isValidDate(dateStr)) {
                datePublication = LocalDate.parse(dateStr);
                validDate = true;
            } else {
                System.out.println("Date invalide. Veuillez entrer la date au format YYYY-MM-DD.");
            }
        }

        boolean validNombreDePages = false;
        while (!validNombreDePages) {
            try {
                System.out.print("Entrez le nombre de pages : ");
                nombreDePages = scanner.nextInt();
                scanner.nextLine();
                if (InputValidator.isPositiveNumber(nombreDePages)) {
                    validNombreDePages = true;
                } else {
                    System.out.println("Nombre de pages invalide. Le nombre de pages doit être positif.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
                scanner.nextLine();
            }
        }

        try {
            if (typeDocument == 1) {
                String isbn = "";
                boolean validISBN = false;
                while (!validISBN) {
                    System.out.print("Entrez le numéro ISBN du livre : ");
                    isbn = scanner.nextLine();
                    if (InputValidator.isValidISBN(isbn)) {
                        validISBN = true;
                    } else {
                        System.out.println("ISBN invalide. Veuillez entrer un ISBN valide (10 ou 13 chiffres).");
                    }
                }

                Livre livre = new Livre(titre, auteur, datePublication, nombreDePages, isbn);
                livreDAOImpl.ajouterDocument(livre);

            } else if (typeDocument == 2) {
                String numero = "";
                boolean validNumero = false;
                while (!validNumero) {
                    System.out.print("Entrez le numéro du magazine : ");
                    numero = scanner.nextLine();
                    if (InputValidator.isValidString(numero)) {
                        validNumero = true;
                    } else {
                        System.out.println("Numéro invalide. Veuillez entrer un numéro valide.");
                    }
                }

                Magazine magazine = new Magazine(titre, auteur, datePublication, nombreDePages, numero);
                magazineDAOImpl.ajouterDocument(magazine);

            } else if (typeDocument == 3) {
                String domaineRecherche = "";
                boolean validDomaineRecherche = false;
                while (!validDomaineRecherche) {
                    System.out.print("Entrez le domaine de recherche : ");
                    domaineRecherche = scanner.nextLine();
                    if (InputValidator.isValidString(domaineRecherche)) {
                        validDomaineRecherche = true;
                    } else {
                        System.out.println("Domaine de recherche invalide. Veuillez entrer un domaine valide.");
                    }
                }

                JournalScientifique journal = new JournalScientifique(titre, auteur, datePublication, nombreDePages, domaineRecherche);
                journalDAOImpl.ajouterDocument(journal);

            } else if (typeDocument == 4) {
                String universite = "";
                String domaine = "";

                boolean validUniversite = false;
                while (!validUniversite) {
                    System.out.print("Entrez l'université : ");
                    universite = scanner.nextLine();
                    if (InputValidator.isValidString(universite)) {
                        validUniversite = true;
                    } else {
                        System.out.println("Université invalide. Veuillez entrer un nom d'université valide.");
                    }
                }

                boolean validDomaine = false;
                while (!validDomaine) {
                    System.out.print("Entrez le domaine de la thèse : ");
                    domaine = scanner.nextLine();
                    if (InputValidator.isValidString(domaine)) {
                        validDomaine = true;
                    } else {
                        System.out.println("Domaine invalide. Veuillez entrer un domaine valide.");
                    }
                }

                TheseUniversitaire these = new TheseUniversitaire(titre, auteur, datePublication, nombreDePages, universite, domaine);
                theseDAOImpl.ajouterDocument(these);
            }

            System.out.println("Document ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du document : " + e.getMessage());
        }
    }

    public void trouverUnDocument() {
        try {
            System.out.print("Veuillez entrer le titre du document : ");
            String titre = scanner.nextLine();

            List<Document> documents = new ArrayList<>();

            documents.addAll(livreDAOImpl.obtenirDocumentsParTitre(titre));
            documents.addAll(magazineDAOImpl.obtenirDocumentsParTitre(titre));
            documents.addAll(journalDAOImpl.obtenirDocumentsParTitre(titre));
            documents.addAll(theseDAOImpl.obtenirDocumentsParTitre(titre));

            if (!documents.isEmpty()) {
                afficherDetailsDocuments(documents);
            } else {
                System.out.println("Aucun document trouvé avec le titre spécifié.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du document : " + e.getMessage());
        }
    }

    private void afficherDetailsDocuments(List<Document> documents) {
        if (documents.isEmpty()) {
            System.out.println("Aucun document à afficher.");
            return;
        }

        List<Livre> livres = new ArrayList<>();
        List<Magazine> magazines = new ArrayList<>();
        List<JournalScientifique> journaux = new ArrayList<>();
        List<TheseUniversitaire> theses = new ArrayList<>();

        for (Document document : documents) {
            if (document instanceof Livre) {
                livres.add((Livre) document);
            } else if (document instanceof Magazine) {
                magazines.add((Magazine) document);
            } else if (document instanceof JournalScientifique) {
                journaux.add((JournalScientifique) document);
            } else if (document instanceof TheseUniversitaire) {
                theses.add((TheseUniversitaire) document);
            }
        }

        if (!livres.isEmpty()) {
            System.out.println("\n*** LIVRES ***");
            System.out.println("| id   | Titre                      | Auteur         | Date Publication | ISBN          | Nombre de Pages |");
            System.out.println("----------------------------------------------------------------------------------------------------");
            for (Livre livre : livres) {
                System.out.println(livre.toString());
            }
        }

        if (!magazines.isEmpty()) {
            System.out.println("\n*** MAGAZINES ***");
            System.out.println("| id   | Titre                      | Auteur         | Date Publication | Numéro         | Nombre de Pages |");
            System.out.println("----------------------------------------------------------------------------------------------------");
            for (Magazine magazine : magazines) {
                System.out.println(magazine.toString());
            }
        }

        if (!journaux.isEmpty()) {
            System.out.println("\n*** JOURNAUX SCIENTIFIQUES ***");
            System.out.println("| Titre                      | Auteur         | Date Publication | Domaine Recherche      | Nombre de Pages |");
            System.out.println("----------------------------------------------------------------------------------------------------------");
            for (JournalScientifique journal : journaux) {
                System.out.println(journal.toString());
            }
        }

        if (!theses.isEmpty()) {
            System.out.println("\n*** THÈSES UNIVERSITAIRES ***");
            System.out.println("| id   | Titre                      | Auteur         | Date Publication | Université             | Domaine               | Nombre de Pages |");
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            for (TheseUniversitaire these : theses) {
                System.out.println(these.toString());
            }
        }
    }

    public void afficherTousLesDocuments() {
        try {

            List<Document> tousLesDocuments = new ArrayList<>();
            tousLesDocuments.addAll(livreDAOImpl.obtenirTousLesDocuments());
            tousLesDocuments.addAll(magazineDAOImpl.obtenirTousLesDocuments());
            tousLesDocuments.addAll(journalDAOImpl.obtenirTousLesDocuments());
            tousLesDocuments.addAll(theseDAOImpl.obtenirTousLesDocuments());

            afficherDetailsDocuments(tousLesDocuments);

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des documents : " + e.getMessage());
        }
    }

    public Optional<Document> recupererDocumentParId() {
        try {
            System.out.print("Veuillez entrer l'ID du document : ");
            int idDocument = scanner.nextInt();
            scanner.nextLine();

            List<Document> documents = new ArrayList<>();

            livreDAOImpl.obtenirDocumentParId(idDocument).ifPresent(documents::add);
            magazineDAOImpl.obtenirDocumentParId(idDocument).ifPresent(documents::add);
            journalDAOImpl.obtenirDocumentParId(idDocument).ifPresent(documents::add);
            theseDAOImpl.obtenirDocumentParId(idDocument).ifPresent(documents::add);

            if (!documents.isEmpty()) {
                Document documentTrouve = documents.get(0); // Il devrait y avoir un seul document avec cet ID
                System.out.println("Document trouvé : " + documentTrouve.toString());
                return Optional.of(documentTrouve);
            } else {
                System.out.println("Aucun document trouvé avec cet ID.");
                return Optional.empty();
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du document : " + e.getMessage());
            return Optional.empty();
        } catch (InputMismatchException e) {
            System.out.println("Erreur : L'ID doit être un nombre entier.");
            scanner.nextLine();
            return Optional.empty();
        }
    }

    public void supprimerDocument() {
        Optional<Document> documentOptional = recupererDocumentParId();

        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Êtes-vous sûr de vouloir supprimer ce document (oui/non) ? ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if ("oui".equals(confirmation)) {
                try {
                    if (document instanceof Livre) {
                        livreDAOImpl.supprimerDocument(document.getId());
                        System.out.println("Livre supprimé avec succès.");
                    } else if (document instanceof Magazine) {
                        magazineDAOImpl.supprimerDocument(document.getId());
                        System.out.println("Magazine supprimé avec succès.");
                    } else if (document instanceof JournalScientifique) {
                        journalDAOImpl.supprimerDocument(document.getId());
                        System.out.println("Journal scientifique supprimé avec succès.");
                    } else if (document instanceof TheseUniversitaire) {
                        theseDAOImpl.supprimerDocument(document.getId());
                        System.out.println("Thèse universitaire supprimée avec succès.");
                    } else {
                        System.out.println("Type de document non reconnu pour la suppression.");
                    }
                } catch (SQLException e) {
                    System.out.println("Erreur lors de la suppression du document : " + e.getMessage());
                }
            } else {
                System.out.println("Suppression annulée.");
            }
        } else {
            System.out.println("Aucun document à supprimer.");
        }
    }

    public void modifierDocument() {
        Optional<Document> documentOptional = recupererDocumentParId();

        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();

            try {
                System.out.print("Entrez le nouveau titre (laissez vide pour conserver \"" + document.getTitre() + "\") : ");
                String nouveauTitre = scanner.nextLine();
                if (!nouveauTitre.isEmpty() && InputValidator.isValidString(nouveauTitre)) {
                    document.setTitre(nouveauTitre);
                }

                System.out.print("Entrez le nouvel auteur (laissez vide pour conserver \"" + document.getAuteur() + "\") : ");
                String nouvelAuteur = scanner.nextLine();
                if (!nouvelAuteur.isEmpty() && InputValidator.isValidString(nouvelAuteur)) {
                    document.setAuteur(nouvelAuteur);
                }

                System.out.print("Entrez la nouvelle date de publication (format yyyy-MM-dd, laissez vide pour conserver \"" + document.getDatePublication() + "\") : ");
                String nouvelleDatePublication = scanner.nextLine();
                if (!nouvelleDatePublication.isEmpty() && InputValidator.isValidDate(nouvelleDatePublication)) {
                    document.setDatePublication(LocalDate.parse(nouvelleDatePublication));
                }

                System.out.print("Entrez le nouveau nombre de pages (laisser vide pour conserver \"" + document.getNombreDePages() + "\") : ");
                String nombreDePagesInput = scanner.nextLine();
                if (!nombreDePagesInput.isEmpty()) {
                    try {
                        int nombreDePages = Integer.parseInt(nombreDePagesInput);
                        if (InputValidator.isPositiveNumber(nombreDePages)) {
                            document.setNombreDePages(nombreDePages);
                        } else {
                            System.out.println("Le nombre de pages doit être positif.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Nombre de pages invalide.");
                        return;
                    }
                }

                if (document instanceof Livre) {
                    Livre livre = (Livre) document;

                    System.out.print("Entrez le nouvel ISBN (laissez vide pour conserver \"" + livre.getIsbn() + "\") : ");
                    String nouvelIsbn = scanner.nextLine();
                    if (!nouvelIsbn.isEmpty() && InputValidator.isValidISBN(nouvelIsbn)) {
                        livre.setIsbn(nouvelIsbn);
                    }

                    livreDAOImpl.mettreAJourDocument(livre);
                    System.out.println("Livre mis à jour avec succès.");

                } else if (document instanceof Magazine) {
                    Magazine magazine = (Magazine) document;

                    System.out.print("Entrez le nouveau numéro (laissez vide pour conserver \"" + magazine.getNumero() + "\") : ");
                    String nouveauNumero = scanner.nextLine();
                    if (!nouveauNumero.isEmpty() && InputValidator.isValidString(nouveauNumero)) {
                        magazine.setNumero(nouveauNumero);
                    }

                    magazineDAOImpl.mettreAJourDocument(magazine);
                    System.out.println("Magazine mis à jour avec succès.");

                } else if (document instanceof JournalScientifique) {
                    JournalScientifique journal = (JournalScientifique) document;

                    System.out.print("Entrez le nouveau domaine de recherche (laissez vide pour conserver \"" + journal.getDomaineRecherche() + "\") : ");
                    String nouveauDomaineRecherche = scanner.nextLine();
                    if (!nouveauDomaineRecherche.isEmpty() && InputValidator.isValidString(nouveauDomaineRecherche)) {
                        journal.setDomaineRecherche(nouveauDomaineRecherche);
                    }

                    journalDAOImpl.mettreAJourDocument(journal);
                    System.out.println("Journal scientifique mis à jour avec succès.");

                } else if (document instanceof TheseUniversitaire) {
                    TheseUniversitaire these = (TheseUniversitaire) document;

                    System.out.print("Entrez la nouvelle université (laissez vide pour conserver \"" + these.getUniversite() + "\") : ");
                    String nouvelleUniversite = scanner.nextLine();
                    if (!nouvelleUniversite.isEmpty() && InputValidator.isValidString(nouvelleUniversite)) {
                        these.setUniversite(nouvelleUniversite);
                    }

                    System.out.print("Entrez le nouveau domaine (laissez vide pour conserver \"" + these.getDomaine() + "\") : ");
                    String nouveauDomaine = scanner.nextLine();
                    if (!nouveauDomaine.isEmpty() && InputValidator.isValidString(nouveauDomaine)) {
                        these.setDomaine(nouveauDomaine);
                    }

                    theseDAOImpl.mettreAJourDocument(these);
                    System.out.println("Thèse universitaire mise à jour avec succès.");

                } else {
                    System.out.println("Type de document non reconnu pour la mise à jour.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la mise à jour du document : " + e.getMessage());
            }
        }
    }


    public void emprunterDocument() {
        Optional<Document> documentOptional = recupererDocumentParId();

        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();

            if (document.getStatut() == StatutDocument.DISPONIBLE) {
                System.out.print("Veuillez entrer votre numéro d'adhésion : ");
                String numeroAdhesion = scanner.nextLine();

                UtilisateurService utilisateurService = new UtilisateurService();
                Optional<Utilisateur> utilisateurOptional = utilisateurService.trouverUtilisateur(numeroAdhesion);

                if (utilisateurOptional.isPresent()) {
                    Utilisateur utilisateur = utilisateurOptional.get();

                    boolean peutEmprunter = (utilisateur instanceof Etudiant && !(document instanceof TheseUniversitaire)) ||
                            (utilisateur instanceof Professeur);

                    if (peutEmprunter) {
                        if (document instanceof Empruntable) {
                            ((Empruntable) document).emprunter();

                            document.setStatut(StatutDocument.EMPRUNTE);

                            try {
                                empruntDAOImpl.ajouterEmprunt(utilisateur.getId(), document.getId());
                            } catch (SQLException e) {
                                System.out.println("Erreur lors de l'ajout de l'emprunt : " + e.getMessage());
                            }

                            try {
                                if (document instanceof Livre) {
                                    livreDAOImpl.mettreAJourDocument((Livre) document);
                                } else if (document instanceof Magazine) {
                                    magazineDAOImpl.mettreAJourDocument((Magazine) document);
                                } else if (document instanceof JournalScientifique) {
                                    journalDAOImpl.mettreAJourDocument((JournalScientifique) document);
                                } else if (document instanceof TheseUniversitaire) {
                                    theseDAOImpl.mettreAJourDocument((TheseUniversitaire) document);
                                }
                                ((Empruntable) document).emprunter();
                            } catch (SQLException e) {
                                System.out.println("Erreur lors de la mise à jour du document : " + e.getMessage());
                            }

                        } else {
                            System.out.println("Ce document ne peut pas être emprunté.");
                        }
                    } else {
                        System.out.println("Vous n'avez pas le droit d'emprunter ce document.");
                    }
                } else {
                    System.out.println("Utilisateur non trouvé.");
                }
            } else {
                System.out.println("Le document est déjà emprunté.");
            }
        } else {
            System.out.println("Document non trouvé.");
        }
    }


    public void retournerDocument() {

        System.out.print("Veuillez entrer votre numéro d'adhésion : ");
        String numeroAdhesion = scanner.nextLine();

        Optional<Document> documentOptional = recupererDocumentParId();
        Document document = documentOptional.get();

        UtilisateurService utilisateurService = new UtilisateurService();
        Optional<Utilisateur> utilisateurOptional = utilisateurService.trouverUtilisateur(numeroAdhesion);
        if (!utilisateurOptional.isPresent()) {
            System.out.println("Utilisateur non trouvé.");
            return;
        }

        Utilisateur utilisateur = utilisateurOptional.get();

        try {
            boolean empruntExistant = empruntDAOImpl.verifierEmprunt(document.getId(), utilisateur.getId());

            if (empruntExistant) {
                empruntDAOImpl.retournerEmprunt(document.getId(), utilisateur.getId());

                document.setStatut(StatutDocument.DISPONIBLE);
                if (document instanceof Livre) {
                    livreDAOImpl.mettreAJourDocument((Livre) document);
                } else if (document instanceof Magazine) {
                    magazineDAOImpl.mettreAJourDocument((Magazine) document);
                } else if (document instanceof JournalScientifique) {
                    journalDAOImpl.mettreAJourDocument((JournalScientifique) document);
                } else if (document instanceof TheseUniversitaire) {
                    theseDAOImpl.mettreAJourDocument((TheseUniversitaire) document);
                }

                System.out.println("Le document a été retourné avec succès.");
            } else {
                System.out.println("Aucun emprunt trouvé pour cet utilisateur et ce document.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la gestion du retour du document : " + e.getMessage());
        }
    }

}
