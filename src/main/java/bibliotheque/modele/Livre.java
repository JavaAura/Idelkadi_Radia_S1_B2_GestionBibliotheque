package main.java.bibliotheque.modele;

import java.time.LocalDate;

public class Livre extends Document{
    private String isbn;

    public Livre(String titre, String auteur, LocalDate datePublication, int nombreDePages, StatutDocument statut, String isbn) {
        super(titre, auteur, datePublication, nombreDePages, statut);
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
