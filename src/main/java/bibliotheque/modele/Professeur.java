package main.java.bibliotheque.modele;

public class Professeur extends Utilisateur{

    private String departement;
    public Professeur(String nom, int age, String numeroDadhesion , String departement) {
        super(nom, age, numeroDadhesion);
        this.departement = departement;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }
}
