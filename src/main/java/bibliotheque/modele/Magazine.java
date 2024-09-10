package main.java.bibliotheque.modele;

import java.time.LocalDate;

public class Magazine extends Document{
    private String numero;

    public Magazine(String titre, String auteur, LocalDate datePublication, int nombreDePages, String numero) {
        super(titre, auteur, datePublication, nombreDePages);
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
