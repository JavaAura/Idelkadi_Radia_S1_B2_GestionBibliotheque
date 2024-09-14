package bibliotheque.modele;

public enum StatutDocument {
    DISPONIBLE("disponible"),
    EMPRUNTE("emprunte");

    private final String statut;

    StatutDocument(String statut) {
        this.statut = statut;
    }

    public String getStatut() {
        return statut;
    }

    @Override
    public String toString() {
        return statut;
    }

    public static StatutDocument fromString(String statut) {
        for (StatutDocument s : StatutDocument.values()) {
            if (s.statut.equalsIgnoreCase(statut)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Statut non valide: " + statut);
    }
}
