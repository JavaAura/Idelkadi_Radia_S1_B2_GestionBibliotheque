package main.java.bibliotheque.modele;

import java.time.LocalDate;

public class JournalScientifique extends Document{

    private String domaineRecherche;

    public JournalScientifique(String titre, String auteur, LocalDate datePublication, int nombreDePages, StatutDocument statut, String domaineRecherche) {
        super(titre, auteur, datePublication, nombreDePages, statut);
        this.domaineRecherche = domaineRecherche;
    }



    public String getDomaineRecherche() {
        return domaineRecherche;
    }

    public void setDomaineRecherche(String domaineRecherche) {
        this.domaineRecherche = domaineRecherche;
    }
}
