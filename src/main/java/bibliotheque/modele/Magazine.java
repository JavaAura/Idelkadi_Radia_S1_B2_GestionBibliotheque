package bibliotheque.modele;

import bibliotheque.utilitaire.DateUtils;

import java.time.LocalDate;
import java.util.Optional;

public class Magazine extends Document implements Empruntable, Reservable {
    private String numero;

    public Magazine(String titre, String auteur, LocalDate datePublication, int nombreDePages, String numero) {
        super(titre, auteur, datePublication, nombreDePages);
        this.numero = numero;
    }

    public Magazine(int id, String titre, String auteur, LocalDate datePublication, int nombreDePages, String numero, String statut) {
        super(id, titre, auteur, datePublication, nombreDePages, statut);
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

        return String.format("| %-10d | %-25s | %-15s | %-15s | %-10s | %-10d |", id, titre, auteur, datePublication, numero, nombreDePages);
    }

    public void emprunter() {

        System.out.println("La magazine a été emprunté.");

    }

    @Override
    public void retourner() {
        System.out.println("La magazine a été retourné.");
    }

    @Override
    public void reserver() {
        System.out.println("La magazine a été réservé avec succès.");
    }

    @Override
    public void annuler() {
        System.out.println("La réservation de la magazine a été annulée avec succès.");
    }
}
