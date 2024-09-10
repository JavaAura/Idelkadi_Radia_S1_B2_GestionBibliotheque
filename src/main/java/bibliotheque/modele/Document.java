package main.java.bibliotheque.modele;

import java.time.LocalDate;

public abstract class Document {
    private Long id;
    private String titre;
    private String auteur;
    private LocalDate datePublication;
    private int nombreDePages;
    private StatutDocument statut;


    public Document(String titre, String auteur, LocalDate datePublication, int nombreDePages , StatutDocument statut) {
        this.titre = titre;
        this.auteur = auteur;
        this.datePublication = datePublication;
        this.nombreDePages = nombreDePages;
        this.statut = statut != null ? statut : StatutDocument.DISPONIBLE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getNombreDePages() {
        return nombreDePages;
    }

    public void setNombreDePages(int nombreDePages) {
        this.nombreDePages = nombreDePages;
    }


}
