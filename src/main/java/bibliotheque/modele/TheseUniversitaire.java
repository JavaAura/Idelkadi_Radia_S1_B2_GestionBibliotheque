package main.java.bibliotheque.modele;

import main.java.bibliotheque.interfaces.Empruntable;
import main.java.bibliotheque.utilitaire.DateUtils;

import java.time.LocalDate;
import java.util.Optional;

public class TheseUniversitaire extends Document implements Empruntable {
    private String universite;
    private String domaine;

    public TheseUniversitaire(String titre, String auteur, LocalDate datePublication, int nombreDePages,  String universite, String domaine) {
        super(titre, auteur, datePublication, nombreDePages);
        this.universite = universite;
        this.domaine = domaine;
    }

    public TheseUniversitaire(int id, String titre, String auteur, LocalDate datePublication, int nombreDePages,  String universite, String domaine, String statut) {
        super(id, titre, auteur, datePublication, nombreDePages, statut);
        this.universite = universite;
        this.domaine = domaine;
    }

    public String getUniversite() {
        return universite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    @Override
    public String toString() {
        int id = getId();
        String titre = Optional.ofNullable(getTitre()).orElse("N/A");
        String auteur = Optional.ofNullable(getAuteur()).orElse("N/A");
        String datePublication = Optional.ofNullable(getDatePublication()).map(DateUtils::formatDate).orElse("N/A");
        String universite = Optional.ofNullable(getUniversite()).orElse("N/A");
        String domaine = Optional.ofNullable(getDomaine()).orElse("N/A");
        int nombreDePages = getNombreDePages();

        return String.format("| %-10d | %-25s | %-15s | %-15s | %-20s | %-20s | %-10d |",id,
                titre, auteur, datePublication, universite, domaine, nombreDePages);
    }


    public void emprunter() {
        if (getStatut() == StatutDocument.DISPONIBLE) {
            setStatut(StatutDocument.EMPRUNTE);
            System.out.println("Le livre a été emprunté.");
        } else {
            System.out.println("Le livre n'est pas disponible pour l'emprunt.");
        }
    }

    @Override
    public void retourner() {
        if (getStatut() == StatutDocument.EMPRUNTE) {
            setStatut(StatutDocument.DISPONIBLE);
            System.out.println("Le livre a été retourné.");
        } else {
            System.out.println("Le livre n'a pas été emprunté.");
        }
    }

}
