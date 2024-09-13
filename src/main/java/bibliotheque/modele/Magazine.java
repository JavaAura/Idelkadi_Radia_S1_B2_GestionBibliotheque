package main.java.bibliotheque.modele;

import main.java.bibliotheque.interfaces.Empruntable;
import main.java.bibliotheque.utilitaire.DateUtils;

import java.time.LocalDate;
import java.util.Optional;

public class Magazine extends Document  implements  Empruntable {
    private String numero;

    public Magazine(String titre, String auteur, LocalDate datePublication, int nombreDePages , String numero) {
        super(titre, auteur, datePublication, nombreDePages);
        this.numero = numero;
    }

    public Magazine(int id,String titre, String auteur, LocalDate datePublication, int nombreDePages , String numero) {
        super(id, titre, auteur, datePublication, nombreDePages);
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        int id = getId();
        String titre = Optional.ofNullable(getTitre()).orElse("N/A");
        String auteur = Optional.ofNullable(getAuteur()).orElse("N/A");
        String datePublication = Optional.ofNullable(getDatePublication()).map(DateUtils::formatDate).orElse("N/A");
        String numero = Optional.ofNullable(getNumero()).orElse("N/A");
        int nombreDePages = getNombreDePages();

        return String.format("| %-10d | %-25s | %-15s | %-15s | %-10s | %-10d |",id, titre, auteur, datePublication, numero, nombreDePages);
    }
    public void emprunter() {
        if (getStatut() == StatutDocument.DISPONIBLE) {
            setStatut(StatutDocument.EMPRUNTE);
            System.out.println("La magazine a été emprunté.");
        } else {
            System.out.println("La magazine n'est pas disponible pour l'emprunt.");
        }
    }

    @Override
    public void retourner() {
        if (getStatut() == StatutDocument.EMPRUNTE) {
            setStatut(StatutDocument.DISPONIBLE);
            System.out.println("La magazine a été retourné.");
        } else {
            System.out.println("La magazine n'a pas été emprunté.");
        }
    }

}
