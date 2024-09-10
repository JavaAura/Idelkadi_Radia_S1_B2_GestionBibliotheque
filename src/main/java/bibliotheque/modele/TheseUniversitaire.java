package main.java.bibliotheque.modele;

public class TheseUniversitaire {
    private String universite;
    private String domaine;

    public TheseUniversitaire(String universite, String domaine) {
        this.universite = universite;
        this.domaine = domaine;
    }

    public String getUniversite() {
        return universite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }
}
