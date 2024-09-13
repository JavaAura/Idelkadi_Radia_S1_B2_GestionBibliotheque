package main.java.bibliotheque.modele;

public abstract class Utilisateur {

    private int id;
    private String nom;
    private int age;
    private String numeroDadhesion;


    public Utilisateur() {
    }

    public Utilisateur(String nom, int age, String numeroDadhesion) {
        this.nom = nom;
        this.age = age;
        this.numeroDadhesion = numeroDadhesion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNumeroDadhesion() {
        return numeroDadhesion;
    }

    public void setNumeroDadhesion(String numeroDadhesion) {
        this.numeroDadhesion = numeroDadhesion;
    }
}
