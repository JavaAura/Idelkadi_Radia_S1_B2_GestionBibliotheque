package main.java.bibliotheque.modele;

import main.java.bibliotheque.interfaces.Empruntable;
import main.java.bibliotheque.utilitaire.DateUtils;

import java.time.LocalDate;
import java.util.Optional;

public class JournalScientifique extends Document implements Empruntable {

    private String domaineRecherche;

    public JournalScientifique(String titre, String auteur, LocalDate datePublication, int nombreDePages, String domaineRecherche) {
        super(titre, auteur, datePublication, nombreDePages);
        this.domaineRecherche = domaineRecherche;
    }

    public JournalScientifique(int id,String titre, String auteur, LocalDate datePublication, int nombreDePages, String domaineRecherche) {
        super(id,titre, auteur, datePublication, nombreDePages);
        this.domaineRecherche = domaineRecherche;
    }

    public String getDomaineRecherche() {
        return domaineRecherche;
    }

    public void setDomaineRecherche(String domaineRecherche) {
        this.domaineRecherche = domaineRecherche;
    }

    @Override
    public String toString() {
        int id = getId();
        String titre = Optional.ofNullable(getTitre()).orElse("N/A");
        String auteur = Optional.ofNullable(getAuteur()).orElse("N/A");
        String datePublication = Optional.ofNullable(getDatePublication()).map(DateUtils::formatDate).orElse("N/A");
        String domaineRecherche = Optional.ofNullable(getDomaineRecherche()).orElse("N/A");
        int nombreDePages = getNombreDePages();

        return String.format("| %-10d | %-25s | %-15s | %-15s | %-20s | %-10d |",id,
                titre, auteur, datePublication, domaineRecherche, nombreDePages);
    }

    public void emprunter() {
        if (getStatut() == StatutDocument.DISPONIBLE) {
            setStatut(StatutDocument.EMPRUNTE);
            System.out.println("Le journal a été emprunté.");
        } else {
            System.out.println("Le journal n'est pas disponible pour l'emprunt.");
        }
    }

    @Override
    public void retourner() {
        if (getStatut() == StatutDocument.EMPRUNTE) {
            setStatut(StatutDocument.DISPONIBLE);
            System.out.println("Le journal a été retourné.");
        } else {
            System.out.println("Le journal n'a pas été emprunté.");
        }
    }

}
