

/*Supression des tables si elles existent déjà*/
DROP TABLE IF EXISTS Media_Genre;
DROP TABLE IF EXISTS Media_Liste;
DROP TABLE IF EXISTS Media_Createur;
DROP TABLE IF EXISTS JeuVideo_Type;
DROP TABLE IF EXISTS Groupe;
DROP TABLE IF EXISTS Personne;
DROP TABLE IF EXISTS Livre;
DROP TABLE IF EXISTS BD;
DROP TABLE IF EXISTS Papier;
DROP TABLE IF EXISTS Film;
DROP TABLE IF EXISTS Serie;
DROP TABLE IF EXISTS JeuVideo;
DROP TABLE IF EXISTS Numerique;
DROP TABLE IF EXISTS Type;
DROP TABLE IF EXISTS Liste;
DROP TABLE IF EXISTS Commentaire;
DROP TABLE IF EXISTS Utilisateur;
DROP TABLE IF EXISTS Createur;
DROP TABLE IF EXISTS Media;
DROP TABLE IF EXISTS Genre;

/* Création des tables */

CREATE TABLE Createur(
                         id serial,
                         nom varchar(255) NOT NULL,
                         CONSTRAINT PK_Createur PRIMARY KEY (id)
);

CREATE TABLE Groupe(
                       id integer,
                       CONSTRAINT PK_Groupe PRIMARY KEY (id),
                       CONSTRAINT FK_Groupe_id FOREIGN KEY
                           (id) REFERENCES Createur(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Personne(
                         id integer,
                         prenom varchar(255) NOT NULL,
                         CONSTRAINT PK_Personne PRIMARY KEY (id),
                         CONSTRAINT FK_Personne_id FOREIGN KEY
                             (id) REFERENCES Createur(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Genre(
                      nom varchar(50),
                      CONSTRAINT PK_Genre PRIMARY KEY (nom)
);

CREATE TABLE Media(
                      id serial,
                      nom varchar(255) NOT NULL,
                      dateSortie date NOT NULL,
                      description TEXT NOT NULL,
                      CONSTRAINT PK_Media PRIMARY KEY (id)
);

CREATE TABLE Media_Createur(
                               mediaId integer,
                               createurId integer,
                               CONSTRAINT PK_Media_Createur PRIMARY KEY(mediaId, createurId),
                               CONSTRAINT FK_Media_Createur_mediaId FOREIGN KEY
                                   (mediaId) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE,
                               CONSTRAINT FK_Media_Createur_createurId FOREIGN KEY
                                   (createurId) REFERENCES Createur(id) ON UPDATE CASCADE ON DELETE NO ACTION
);


CREATE TABLE Media_Genre(
                            mediaId integer,
                            genreNom varchar(50),
                            CONSTRAINT PK_Media_Genre PRIMARY KEY(mediaId, genreNom),
                            CONSTRAINT FK_Media_Genre_mediaId FOREIGN KEY
                                (mediaId) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE,
                            CONSTRAINT FK_Media_Genre_genreNom FOREIGN KEY
                                (genreNom) REFERENCES Genre(nom) ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE Papier(
                       id integer,
                       CONSTRAINT PK_Papier PRIMARY KEY (id),
                       CONSTRAINT FK_Papier_id FOREIGN KEY
                           (id) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Livre(
                      id integer,
                      nbPage int NOT NULL,
                      CONSTRAINT PK_Livre PRIMARY KEY (id),
                      CONSTRAINT FK_Livre_id FOREIGN KEY
                          (id) REFERENCES Papier(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE BD(
                   id integer,
                   couleur bool NOT NULL,
                   CONSTRAINT PK_BD PRIMARY KEY (id),
                   CONSTRAINT FK_BD_id FOREIGN KEY
                       (id) REFERENCES Papier(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Numerique(
                          id integer,
                          CONSTRAINT PK_Numerique PRIMARY KEY(id),
                          CONSTRAINT FK_Numerique_id FOREIGN KEY
                              (id) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Film(
                     id integer,
                     duree integer NOT NULL,
                     CONSTRAINT PK_Film PRIMARY KEY(id),
                     CONSTRAINT FK_Film_id FOREIGN KEY
                         (id) REFERENCES Numerique(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Serie(
                      id integer,
                      nbSaison int NOT NULL,
                      CONSTRAINT PK_Serie PRIMARY KEY(id),
                      CONSTRAINT FK_Serie_id FOREIGN KEY
                          (id) REFERENCES Numerique(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Type(
                     nom varchar(50),
                     CONSTRAINT PK_Type PRIMARY KEY (nom)
);

CREATE TABLE JeuVideo(
                         id integer,
                         CONSTRAINT PK_JeuVideo PRIMARY KEY(id),
                         CONSTRAINT FK_JeuVideo_id FOREIGN KEY
                             (id) REFERENCES Numerique(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Utilisateur(
                            pseudo varchar(50),
                            motDePasse varchar(255) NOT NULL,
                            CONSTRAINT PK_Utilisateur PRIMARY KEY (pseudo)
);

CREATE TABLE Liste(
                      pseudo varchar(50),
                      nom varchar(50),
                      CONSTRAINT PK_Liste PRIMARY KEY (pseudo, nom),
                      CONSTRAINT FK_Liste_pseudo FOREIGN KEY
                          (pseudo) REFERENCES Utilisateur(pseudo) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Media_Liste(
                            idMedia integer,
                            listeNom varchar(50),
                            listePseudo varchar(50),
                            CONSTRAINT PK_Media_Liste PRIMARY KEY (idMedia, listeNom, listePseudo),
                            CONSTRAINT FK_Media_Liste_idMedia FOREIGN KEY
                                (idMedia) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE,
                            CONSTRAINT FK_Media_Liste_listeNom FOREIGN KEY
                                (listeNom, listePseudo) REFERENCES Liste(nom, pseudo) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE JeuVideo_Type(
                              jeuVideoId integer,
                              typeNom varchar(50),
                              CONSTRAINT PK_JeuVideo_Type PRIMARY KEY(jeuVideoId, typeNom),
                              CONSTRAINT FK_JeuVideo_Type_jeuVideoId FOREIGN KEY
                                  (jeuVideoId) REFERENCES JeuVideo(id) ON UPDATE CASCADE ON DELETE CASCADE,
                              CONSTRAINT FK_JeuVideo_Type_typeNom FOREIGN KEY
                                  (typeNom) REFERENCES Type(nom) ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE Commentaire(
                            pseudo varchar(50),
                            id integer,
                            date date NOT NULL,
                            note int NOT NULL,
                            texte TEXT,
                            CONSTRAINT PK_Commentaire PRIMARY KEY (pseudo, id),
                            CONSTRAINT FK_Commentaire_pseudo FOREIGN KEY
                                (pseudo) REFERENCES Utilisateur(pseudo) ON UPDATE CASCADE ON DELETE CASCADE,
                            CONSTRAINT FK_Commentaire_id FOREIGN KEY
                                (id) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE,
                            CHECK ((note >= 0) and (note <= 5))
);


/* add unmoving table info*/

INSERT INTO Genre (nom) VALUES ('Horror');
INSERT INTO Genre (nom) VALUES ('Comedy');
INSERT INTO Genre (nom) VALUES ('Action');
INSERT INTO Genre (nom) VALUES ('Romance');
INSERT INTO Genre (nom) VALUES ('Adventure');
INSERT INTO Genre (nom) VALUES ('Historical');
INSERT INTO Genre (nom) VALUES ('Sci-Fi');
INSERT INTO Genre (nom) VALUES ('Fantastic');
INSERT INTO Genre (nom) VALUES ('Crime');
INSERT INTO Genre (nom) VALUES ('Documentary');
INSERT INTO Genre (nom) VALUES ('Thriller');
INSERT INTO Genre (nom) VALUES ('Tragedy');
INSERT INTO Genre (nom) VALUES ('Drama');
INSERT INTO Genre (nom) VALUES ('Game show');
INSERT INTO Genre (nom) VALUES ('Mistery');
INSERT INTO Genre (nom) VALUES ('Reality TV');
INSERT INTO Genre (nom) VALUES ('Western');
INSERT INTO Genre (nom) VALUES ('Noir');

INSERT INTO Type (nom) VALUES ('RPG');
INSERT INTO Type (nom) VALUES ('Online');
INSERT INTO Type (nom) VALUES ('VR');
INSERT INTO Type (nom) VALUES ('Multiplayer');
INSERT INTO Type (nom) VALUES ('Simulation');
INSERT INTO Type (nom) VALUES ('Reflexion');
INSERT INTO Type (nom) VALUES ('Souls like');
INSERT INTO Type (nom) VALUES ('Rogue like');
INSERT INTO Type (nom) VALUES ('Metroidvania');
INSERT INTO Type (nom) VALUES ('Gestion');
INSERT INTO Type (nom) VALUES ('Arcade');
INSERT INTO Type (nom) VALUES ('Rythm');
INSERT INTO Type (nom) VALUES ('Combat');
INSERT INTO Type (nom) VALUES ('Hack and Slash');
INSERT INTO Type (nom) VALUES ('Plateformer');
INSERT INTO Type (nom) VALUES ('Shoot them up');
INSERT INTO Type (nom) VALUES ('FPS');
INSERT INTO Type (nom) VALUES ('TPS');
INSERT INTO Type (nom) VALUES ('Sandbox');
INSERT INTO Type (nom) VALUES ('Racing');
INSERT INTO Type (nom) VALUES ('Strategy');
INSERT INTO Type (nom) VALUES ('Tower defense');
INSERT INTO Type (nom) VALUES ('Cards and Board');
INSERT INTO Type (nom) VALUES ('Light Novel');





/*Triggers and functions*/

-- Créer un trigger pour générer automatiquement les listes des utilisateurs
DO $$
BEGIN
    -- Supprimer le trigger existant s'il existe
    IF EXISTS (
        SELECT 1 FROM pg_trigger
        WHERE tgname = 'after_user_insert'
    ) THEN
DROP TRIGGER after_user_insert ON Utilisateur;
END IF;
END $$;

CREATE OR REPLACE FUNCTION create_default_lists()
RETURNS TRIGGER AS $$
BEGIN
INSERT INTO Liste (pseudo, nom)
VALUES (NEW.pseudo, 'Finished'),
       (NEW.pseudo, 'Favorite'),
       (NEW.pseudo, 'Watching'),
       (NEW.pseudo, 'To watch');
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_user_insert
    AFTER INSERT ON Utilisateur
    FOR EACH ROW
    EXECUTE FUNCTION create_default_lists();

-- insert media
DROP FUNCTION IF EXISTS insert_media(TEXT, DATE, TEXT, TEXT, INTEGER, BOOLEAN, INTEGER, INTEGER, TEXT, TEXT, INTEGER);

CREATE OR REPLACE FUNCTION insert_media(
    media_nom TEXT,
    media_dateSortie DATE DEFAULT CURRENT_DATE,
    media_description TEXT DEFAULT NULL,
    media_type TEXT DEFAULT NULL,
    nb_pages INTEGER DEFAULT NULL,
    bd_couleur BOOLEAN DEFAULT NULL,
    film_duree TIME DEFAULT NULL,
    serie_nbSaisons INTEGER DEFAULT NULL,
    jeu_video_type TEXT DEFAULT NULL,
    genre_nom TEXT DEFAULT NULL,
    createur_id INTEGER DEFAULT NULL
) RETURNS VOID AS $$
DECLARE
id_media INTEGER;
BEGIN
    -- Étape 1 : Insérer dans la table Media
INSERT INTO Media (nom, dateSortie, description)
VALUES (media_nom, media_dateSortie, media_description)
    RETURNING id INTO id_media;

-- Étape 2 : Insérer dans la sous-catégorie correspondante
IF media_type = 'Livre' THEN
            INSERT INTO Papier (id) VALUES (id_media);
INSERT INTO Livre (id, nbPage) VALUES (id_media, nb_pages);

ELSIF media_type = 'BD' THEN
            INSERT INTO Papier (id) VALUES (id_media);
INSERT INTO BD (id, couleur) VALUES (id_media, bd_couleur);

ELSIF media_type = 'Film' THEN
            INSERT INTO Numerique (id) VALUES (id_media);
INSERT INTO Film (id, duree) VALUES (id_media, film_duree);

ELSIF media_type = 'Série' THEN
            INSERT INTO Numerique (id) VALUES (id_media);
INSERT INTO Serie (id, nbSaison) VALUES (id_media, serie_nbSaisons);

ELSIF media_type = 'Jeu Vidéo' THEN
            INSERT INTO Numerique (id) VALUES (id_media);
INSERT INTO JeuVideo (id) VALUES (id_media);

-- Associer au type de jeu vidéo (Type.nom est la clé primaire)
IF jeu_video_type IS NOT NULL THEN
                    INSERT INTO JeuVideo_Type (jeuVideoId, typeNom)
                    VALUES (id_media, jeu_video_type);
END IF;
END IF;

    -- Étape 3 : Associer au genre (Genre.nom est la clé primaire)
    IF genre_nom IS NOT NULL THEN
        -- Vérifier si le genre existe déjà dans la table Genre
        PERFORM 1 FROM Genre WHERE nom = genre_nom;
        -- Si le genre n'existe pas, l'ajouter
        IF NOT FOUND THEN
            INSERT INTO Genre (nom) VALUES (genre_nom);
END IF;

        -- Insérer dans Media_Genre
INSERT INTO Media_Genre (mediaId, genreNom)
VALUES (id_media, genre_nom);
END IF;

    -- Étape 4 : Associer au créateur
    IF createur_id IS NOT NULL THEN
        INSERT INTO Media_Createur (mediaId, createurId)
        VALUES (id_media, createur_id);
END IF;
END;
$$ LANGUAGE plpgsql;