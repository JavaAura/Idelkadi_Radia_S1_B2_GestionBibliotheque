# Application Java Console pour la Gestion de Bibliothèque

## Description du projet

Ce projet est une évolution d'une application Java Console pour la gestion de bibliothèque. L'application a été améliorée pour intégrer la persistance des données avec PostgreSQL et pour offrir des fonctionnalités avancées telles que la gestion des emprunts, des réservations et des documents, ainsi qu'une interface utilisateur améliorée.

## Objectif général de l'application

L'objectif est de développer une application de gestion de bibliothèque qui offre des fonctionnalités complètes pour la gestion des documents, des utilisateurs, des emprunts et des réservations, tout en utilisant des concepts avancés en Java pour optimiser et étendre les fonctionnalités de l'application.

## Technologies utilisées

- **Langage** : Java 8
- **Base de données** : PostgreSQL
- **API Java** : JDBC, Java Time API, API Stream
- **Outils de gestion de projet** : Git, JIRA
- **Diagrammes UML** : Utilisés pour la modélisation et la documentation

## Gestion de projet

- **Dépôt Git** : [https://github.com/JavaAura/Idelkadi_Radia_S1_B2_GestionBibliotheque]
- **Gestion de projet JIRA** : [https://radia-idelkadi.atlassian.net/jira/software/projects/GES/boards/9/backlog?issueParent=10176%2C10177%2C0&selectedIssue=GES-7&atlOrigin=eyJpIjoiOGZiZGIzNmFmM2QxNGEzMTkzYjY5YTk2OWI4ZDc4ZDkiLCJwIjoiaiJ9]


## Structure du projet

### Architecture

L'application est structurée en plusieurs couches pour séparer les responsabilités et faciliter la gestion et l'évolution du code. Voici un aperçu de l'architecture :

1. **Couche de Présentation (`presentation`)** :
    - **ConsoleUI** : Interface console améliorée avec un menu personnalisé pour interagir avec l'utilisateur.

2. **Couche de Métier (`service`)** :
    - **Service** : Contient la logique métier et les règles de gestion, en reliant les données entre les DAO et la couche de présentation.

3. **Couche de Persistance (`dao`)** :
    - **DAO Interface (`dao`)** : Interfaces définissant les méthodes d'accès aux données (CRUD).
    - **DAO Implementation (`dao/implementation`)** : Implémentations concrètes des interfaces DAO, utilisant JDBC pour interagir avec la base de données.
    - **Design Pattern Singleton** : Assure qu'il n'y a qu'une seule instance de chaque DAO.

4. **Couche Modèle (`modele`)** :
    - **Classes de Modèle** : Représentent les entités du système, comme les `Document`, `Utilisateur`, etc. Chaque classe est conçue pour encapsuler les données et les comportements associés.

5. **Couche Utilitaire (`utilitaire`)** :
    - **DateUtils** : Classe pour la manipulation avancée des dates.
    - **InputValidator** : Classe pour la validation des entrées utilisateur.
    - **ScannerUtil** : Classe pour instancier un objet `Scanner` une seule fois, évitant ainsi la création multiple d'instances.

### Architecture des DAO

- **DAO Interface** : Définie dans le package `dao`, elle spécifie les opérations CRUD (Create, Read, Update, Delete) pour chaque entité.
- **DAO Implementation** : Située dans `dao/implementation`, elle fournit l'implémentation concrète des méthodes de l'interface DAO en utilisant JDBC pour effectuer les opérations sur la base de données.

### Modèle de Données

- **Document** : Classe abstraite avec des sous-classes pour chaque type de document (`Livre`, `Magazine`, `JournalScientifique`, `ThèseUniversitaire`).
- **Utilisateur** : Classe abstraite avec des sous-classes pour les différents types d'utilisateurs (`Etudiant`, `Professeur`).

## Instructions d'installation et d'utilisation

### Prérequis

- **Java 8** : Assurez-vous d'avoir Java 8 installé.
- **PostgreSQL** : Une base de données PostgreSQL doit être configurée `sql\schema_sql`.

### Étapes pour configurer la base de données

1. Créez une base de données PostgreSQL.
2. Exécutez les scripts SQL fournis pour créer les tables et initialiser les données.

### Comment lancer l'application

1. Compilez le projet en utilisant votre IDE ou via la ligne de commande.
2. Exécutez le fichier JAR généré avec la commande :
   ```bash
   java -jar nom_du_fichier.jar


## Améliorations futures possibles

- Intégration d'une interface graphique.
- Optimisation des performances de l'application.

## Auteur et contact

- **Auteur** : Idelkadi Radia
- **Contact** : [idelkadiradia@gmail.com]