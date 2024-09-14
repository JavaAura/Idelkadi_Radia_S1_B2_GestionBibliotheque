package bibliotheque.modele;

import java.util.Optional;

public class Professeur extends Utilisateur {

    private String departement;

    public Professeur() {
    }

    public Professeur(String nom, int age, String numeroDadhesion, String departement) {
        super(nom, age, numeroDadhesion);
        this.departement = departement;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    @Override
    public String toString() {
        String numeroAdhesion = Optional.ofNullable(getNumeroDadhesion()).orElse("N/A");
        String nom = Optional.ofNullable(getNom()).orElse("N/A");
        String age = Optional.of(Integer.toString(getAge())).orElse("N/A");
        String departement = Optional.ofNullable(getDepartement()).orElse("N/A");
        return String.format("| %-29s | %-18s | %-4s | %-12s | %-10s |",
                numeroAdhesion, nom, age, departement, "Professeur");
    }

}
