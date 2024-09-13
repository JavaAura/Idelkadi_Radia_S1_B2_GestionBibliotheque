package main.java.bibliotheque.modele;

import java.util.Optional;

public class Etudiant extends Utilisateur{
    private String niveau;


    public Etudiant(){}
    public Etudiant(String nom, int age, String numeroDadhesion, String niveau) {
        super(nom, age, numeroDadhesion);
        this.niveau = niveau;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    @Override
    public String toString() {
        String numeroAdhesion = Optional.ofNullable(getNumeroDadhesion()).orElse("N/A");
        String nom = Optional.ofNullable(getNom()).orElse("N/A");
        String age = Optional.of(Integer.toString(getAge())).orElse("N/A");
        String niveau = Optional.ofNullable(getNiveau()).orElse("N/A");
        return String.format("| %-25s | %-15s | %-4s | %-12s | %-10s |",
                numeroAdhesion, nom, age, niveau, "Étudiant");
    }

}
