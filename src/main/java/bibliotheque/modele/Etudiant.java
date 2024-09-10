package main.java.bibliotheque.modele;

public class Etudiant extends Utilisateur{
    private String niveau;

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
}
