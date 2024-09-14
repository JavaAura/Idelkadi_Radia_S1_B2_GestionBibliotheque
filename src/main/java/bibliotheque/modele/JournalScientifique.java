package bibliotheque.modele;

import bibliotheque.utilitaire.DateUtils;

import java.time.LocalDate;
import java.util.Optional;

public class JournalScientifique extends Document implements Empruntable, Reservable {

    private String domaineRecherche;

    public JournalScientifique(String titre, String auteur, LocalDate datePublication, int nombreDePages, String domaineRecherche) {
        super(titre, auteur, datePublication, nombreDePages);
        this.domaineRecherche = domaineRecherche;
    }

    public JournalScientifique(int id, String titre, String auteur, LocalDate datePublication, int nombreDePages, String statut, String domaineRecherche) {
        super(id, titre, auteur, datePublication, nombreDePages, statut);
        this.domaineRecherche = domaineRecherche;
    }

    public String getDomaineRecherche() {
        return domaineRecherche;
    }

    public void setDomaineRecherche(String domaineRecherche) {
        this.domaineRecherche = domaineRecherche;
    }

    @Override
    public String toString() {
        int id = getId();
        String titre = Optional.ofNullable(getTitre()).orElse("N/A");
        String auteur = Optional.ofNullable(getAuteur()).orElse("N/A");
        String datePublication = Optional.ofNullable(getDatePublication()).map(DateUtils::formatDate).orElse("N/A");
        String domaineRecherche = Optional.ofNullable(getDomaineRecherche()).orElse("N/A");
        int nombreDePages = getNombreDePages();

        return String.format("| %-10d | %-25s | %-15s | %-15s | %-20s | %-10d |", id,
                titre, auteur, datePublication, domaineRecherche, nombreDePages);
    }

    public void emprunter() {

        System.out.println("Le journal a été emprunté.");

    }

    @Override
    public void retourner() {

        System.out.println("Le journal a été retourné.");

    }

    @Override
    public void reserver() {
        System.out.println("Le journal a été réservé avec succès.");
    }

    @Override
    public void annuler() {
        System.out.println("La réservation de le journal a été annulée avec succès.");
    }
}
