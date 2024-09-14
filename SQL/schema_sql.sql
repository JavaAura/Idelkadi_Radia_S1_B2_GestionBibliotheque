-- Création de la base de données
CREATE DATABASE bibliotheque_db;


-- Création de la table principale pour les documents
CREATE TABLE document (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    auteur VARCHAR(255) NOT NULL,
    date_publication DATE NOT NULL,
	nombre_de_pages INT NOT NULL,
    statut VARCHAR(20) NOT NULL DEFAULT 'disponible' CHECK (statut IN ('disponible', 'emprunte'))
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



CREATE VIEW vue_utilisateur_id AS
SELECT id
FROM etudiant
UNION
SELECT id
FROM professeur;



CREATE VIEW vue_document_id AS
SELECT id
FROM livre
UNION
SELECT id
FROM magazine
UNION
SELECT id
FROM journal_scientifique
UNION
SELECT id
FROM these_universitaire;


CREATE OR REPLACE FUNCTION verifier_utilisateur_id_existe()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM vue_utilisateur_id WHERE id = NEW.utilisateur_id) THEN
        RAISE EXCEPTION 'L''utilisateur avec id % n''existe pas.', NEW.utilisateur_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trigger_verifier_utilisateur_id
BEFORE INSERT ON emprunt
FOR EACH ROW
EXECUTE FUNCTION verifier_utilisateur_id_existe();

CREATE TRIGGER trigger_verifier_utilisateur_id_reservation
BEFORE INSERT ON reservation
FOR EACH ROW
EXECUTE FUNCTION verifier_utilisateur_id_existe();





CREATE OR REPLACE FUNCTION verifier_document_id_existe()
RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM vue_document_id WHERE id = NEW.document_id) THEN
        RAISE EXCEPTION 'Le document avec id % n''existe pas.', NEW.document_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_verifier_document_id_reservation
BEFORE INSERT ON reservation
FOR EACH ROW
EXECUTE FUNCTION verifier_document_id_existe();

CREATE TRIGGER trigger_verifier_document_id
BEFORE INSERT ON emprunt
FOR EACH ROW
EXECUTE FUNCTION verifier_document_id_existe();


ALTER TABLE emprunt DROP CONSTRAINT emprunt_utilisateur_id_fkey;
ALTER TABLE emprunt DROP CONSTRAINT emprunt_document_id_fkey;

ALTER TABLE reservation DROP CONSTRAINT reservation_utilisateur_id_fkey;
ALTER TABLE reservation DROP CONSTRAINT reservation_document_id_fkey;

