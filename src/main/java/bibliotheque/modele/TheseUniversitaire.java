package main.java.bibliotheque.modele;

import java.time.LocalDate;

public class TheseUniversitaire extends Document {
    private String universite;
    private String domaine;

    public TheseUniversitaire(String titre, String auteur, LocalDate datePublication, int nombreDePages, StatutDocument statut, String universite, String domaine) {
        super(titre, auteur, datePublication, nombreDePages, statut);
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
}
