-- Création de la base de données
CREATE DATABASE bibliotheque_db;


-- Création de la table principale pour les documents
CREATE TABLE document (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    auteur VARCHAR(255) NOT NULL,
    date_publication DATE NOT NULL,
	nombre_de_pages INT NOT NULL,
	statut VARCHAR(20) NOT NULL DEFAULT 'disponible' CHECK (statut IN ('disponible', 'emprunté'))
);

-- Sous-classe Livre héritant de Document
CREATE TABLE livre (
    isbn VARCHAR(20) UNIQUE
) INHERITS (document);

-- Sous-classe Magazine héritant de Document
CREATE TABLE magazine (
    numero VARCHAR(50) UNIQUE
) INHERITS (document);

-- Sous-classe JournalScientifique héritant de Document
CREATE TABLE journal_scientifique (
    domaine_recherche VARCHAR(255)
) INHERITS (document);

-- Sous-classe TheseUniversitaire héritant de Document
CREATE TABLE these_universitaire (
    universite VARCHAR(255),
    domaine VARCHAR(255)
) INHERITS (document);

-- Création de la table principale pour les utilisateurs
CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
	age INT NOT NULL,
    numero_dadhesion VARCHAR(50) UNIQUE
);

-- Sous-classe Etudiant héritant de Utilisateur
CREATE TABLE etudiant (
    niveau VARCHAR(255) NOT NULL
) INHERITS (utilisateur);

-- Sous-classe Professeur héritant de Utilisateur
CREATE TABLE professeur (
    departement VARCHAR(255) NOT NULL
) INHERITS (utilisateur);


-- Table pour gérer les emprunts
CREATE TABLE emprunt (
    id SERIAL PRIMARY KEY,
    utilisateur_id INT REFERENCES utilisateur(id),
    document_id INT REFERENCES document(id),
    date_emprunt DATE NOT NULL,
    date_retour DATE
);

-- Table pour gérer les réservations
CREATE TABLE reservation (
    id SERIAL PRIMARY KEY,
    utilisateur_id INT REFERENCES utilisateur(id),
    document_id INT REFERENCES document(id),
    date_reservation DATE NOT NULL,
    date_annulation DATE
);

-- Index pour optimiser la recherche par titre dans les documents
CREATE INDEX idx_document_titre ON document(titre);

-- Index pour optimiser la recherche par identifiant dans les utilisateurs
CREATE INDEX idx_utilisateur_identifiant ON utilisateur(numero_dadhesion);