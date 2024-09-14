package bibliotheque.modele;

import bibliotheque.utilitaire.DateUtils;

import java.time.LocalDate;
import java.util.Optional;

public class Livre extends Document implements Empruntable, Reservable {
    private String isbn;

    public Livre(String titre, String auteur, LocalDate datePublication, int nombreDePages, String isbn) {
        super(titre, auteur, datePublication, nombreDePages);
        this.isbn = isbn;
    }

    public Livre(int id, String titre, String auteur, LocalDate datePublication, int nombreDePages, String statut, String isbn) {
        super(id, titre, auteur, datePublication, nombreDePages, statut);
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        int id = getId();
        String titre = Optional.ofNullable(getTitre()).orElse("N/A");
        String auteur = Optional.ofNullable(getAuteur()).orElse("N/A");
        String datePublication = Optional.ofNullable(getDatePublication()).map(DateUtils::formatDate).orElse("N/A");
        String isbn = Optional.ofNullable(getIsbn()).orElse("N/A");
        int nombreDePages = getNombreDePages();

        return String.format("| %-10d  | %-25s | %-15s | %-15s | %-12s | %-10d |", id, titre, auteur, datePublication, isbn, nombreDePages);
    }

    public void emprunter() {

        System.out.println("Le livre a été emprunté.");

    }


    public void retourner() {

        System.out.println("Le livre a été retourné.");

    }

    @Override
    public void reserver() {
        System.out.println("Le livre a été réservé avec succès.");
    }

    @Override
    public void annuler() {
        System.out.println("La réservation de le livre a été annulée avec succès.");
    }
}
