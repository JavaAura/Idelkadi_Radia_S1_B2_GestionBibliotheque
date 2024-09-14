package main.java.bibliotheque.modele;

import main.java.bibliotheque.interfaces.Empruntable;
import main.java.bibliotheque.interfaces.Reservable;

import java.time.LocalDate;

public abstract class Document  {
    private int id;
    private String titre;
    private String auteur;
    private LocalDate datePublication;
    private int nombreDePages;
    private StatutDocument statut= StatutDocument.DISPONIBLE;

    public Document(String titre, String auteur, LocalDate datePublication, int nombreDePages) {
        this.titre = titre;
        this.auteur = auteur;
        this.datePublication = datePublication;
        this.nombreDePages = nombreDePages;
    }
    public Document(int id,String titre, String auteur, LocalDate datePublication, int nombreDePages ,String statut ) {
        this.id = id;

        this.titre = titre;
        this.auteur = auteur;
        this.datePublication = datePublication;
        this.nombreDePages = nombreDePages;
        try {
            this.statut = StatutDocument.valueOf(statut.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Statut invalide fourni. Le statut sera défini par défaut à DISPONIBLE.");
            this.statut = StatutDocument.DISPONIBLE;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public StatutDocument getStatut() {
        return statut;
    }

    public void setStatut(StatutDocument statut) {
        this.statut = statut;
    }

    public int getNombreDePages() {
        return nombreDePages;
    }

    public void setNombreDePages(int nombreDePages) {
        this.nombreDePages = nombreDePages;
    }


}
