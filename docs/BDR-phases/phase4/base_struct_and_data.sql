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


/*add base data*/

-- Insérer 5 personnes génériques
DO $$
DECLARE
i INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    WITH inserted_createur AS (
	INSERT INTO Createur (nom)
    VALUES ('Createur' || i) RETURNING id )
	INSERT INTO Personne(id, prenom)
    SELECT id, 'Prenom'
    FROM inserted_createur;
END LOOP;
END $$;

-- Insérer 5 livres génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    random_genre_id VARCHAR;
    id_createur INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    INSERT INTO Media (nom, dateSortie, description)
    VALUES ('Livre' || i, CURRENT_DATE, 'Description pour Livre' || i)
    RETURNING id INTO id_media;

INSERT INTO Papier (id)
VALUES (id_media);

INSERT INTO Livre (id, nbPage)
VALUES (id_media, 100 + i);

-- Assigner un créateur aléatoire au livre
SELECT id INTO id_createur FROM Createur ORDER BY random() LIMIT 1;
INSERT INTO Media_Createur (mediaId, createurId)
VALUES (id_media, id_createur);

-- Associer un genre aléatoire
SELECT nom INTO random_genre_id
FROM Genre
ORDER BY RANDOM()
    LIMIT 1;

INSERT INTO media_genre (mediaid, genrenom)
VALUES (id_media, random_genre_id);
END LOOP;
END $$;

-- Insérer 5 BD génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    random_genre_id VARCHAR;
    id_createur INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    INSERT INTO Media (nom, dateSortie, description)
    VALUES ('BD' || i, CURRENT_DATE, 'Description pour BD' || i)
    RETURNING id INTO id_media;

INSERT INTO Papier (id)
VALUES (id_media);

INSERT INTO BD (id, couleur)
VALUES (id_media, TRUE);

-- Assigner un créateur aléatoire à la BD
SELECT id INTO id_createur FROM Createur ORDER BY random() LIMIT 1;
INSERT INTO Media_Createur (mediaId, createurId)
VALUES (id_media, id_createur);

-- Associer un genre aléatoire
SELECT nom INTO random_genre_id
FROM Genre
ORDER BY RANDOM()
    LIMIT 1;

INSERT INTO media_genre (mediaid, genrenom)
VALUES (id_media, random_genre_id);
END LOOP;
END $$;

-- Insérer 5 films génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    random_genre_id VARCHAR;
    id_createur INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    INSERT INTO Media (nom, dateSortie, description)
    VALUES ('Film' || i, CURRENT_DATE, 'Description pour Film' || i)
    RETURNING id INTO id_media;

INSERT INTO Numerique (id)
VALUES (id_media);

INSERT INTO Film (id, duree)
VALUES (id_media, 100+i);

-- Assigner un créateur aléatoire au film
SELECT id INTO id_createur FROM Createur ORDER BY random() LIMIT 1;
INSERT INTO Media_Createur (mediaId, createurId)
VALUES (id_media, id_createur);

-- Associer un genre aléatoire
SELECT nom INTO random_genre_id
FROM Genre
ORDER BY RANDOM()
    LIMIT 1;

INSERT INTO media_genre (mediaid, genrenom)
VALUES (id_media, random_genre_id);
END LOOP;
END $$;

-- Insérer 5 séries génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    random_genre_id VARCHAR;
    id_createur INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    INSERT INTO Media (nom, dateSortie, description)
    VALUES ('Serie' || i, CURRENT_DATE, 'Description pour Serie' || i)
    RETURNING id INTO id_media;

INSERT INTO Numerique (id)
VALUES (id_media);

INSERT INTO Serie (id, nbSaison)
VALUES (id_media, i);

-- Assigner un créateur aléatoire à la série
SELECT id INTO id_createur FROM Createur ORDER BY random() LIMIT 1;
INSERT INTO Media_Createur (mediaId, createurId)
VALUES (id_media, id_createur);

-- Associer un genre aléatoire
SELECT nom INTO random_genre_id
FROM Genre
ORDER BY RANDOM()
    LIMIT 1;

INSERT INTO media_genre (mediaid, genrenom)
VALUES (id_media, random_genre_id);
END LOOP;
END $$;

-- Insérer 5 jeux vidéo génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    random_type_id VARCHAR;
    random_genre_id VARCHAR;
    id_createur INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    INSERT INTO Media (nom, dateSortie, description)
    VALUES ('JeuVideo' || i, CURRENT_DATE, 'Description pour JeuVideo' || i)
    RETURNING id INTO id_media;

INSERT INTO Numerique (id)
VALUES (id_media);

INSERT INTO JeuVideo (id)
VALUES (id_media);

-- Assigner un créateur aléatoire au jeu vidéo
SELECT id INTO id_createur FROM Createur ORDER BY random() LIMIT 1;
INSERT INTO Media_Createur (mediaId, createurId)
VALUES (id_media, id_createur);

-- Associer un type aléatoire
SELECT nom INTO random_type_id
FROM Type
ORDER BY RANDOM()
    LIMIT 1;

INSERT INTO jeuvideo_type (jeuvideoid, typenom)
VALUES (id_media, random_type_id);

-- Associer un genre aléatoire
SELECT nom INTO random_genre_id
FROM Genre
ORDER BY RANDOM()
    LIMIT 1;

INSERT INTO media_genre (mediaid, genrenom)
VALUES (id_media, random_genre_id);
END LOOP;
END $$;

-- Insérer 2 utilisateurs génériques avec email et mot de passe
DO $$
BEGIN
INSERT INTO Utilisateur (pseudo, motDePasse)
VALUES ('User1', 'mdp1'),
       ('User2', 'mdp2');
END $$;

-- insérer 10 commentaires au hasard
DO $$
DECLARE
i INTEGER;
    pseudo_utilisateur VARCHAR(50);
    id_media INTEGER;
    note INTEGER;
    commentaire_texte TEXT;
BEGIN
FOR i IN 1..10 LOOP
        -- Sélectionner un utilisateur au hasard
SELECT pseudo INTO pseudo_utilisateur
FROM Utilisateur
ORDER BY random()
    LIMIT 1;

-- Sélectionner un média au hasard
SELECT id INTO id_media
FROM Media
ORDER BY random()
    LIMIT 1;

-- Vérifier si le commentaire pour cet utilisateur et ce média existe déjà
IF NOT EXISTS (SELECT 1 FROM Commentaire WHERE pseudo = pseudo_utilisateur AND id = id_media) THEN
                -- Générer une note au hasard entre 1 et 5
                note := (floor(random() * 5) + 1)::int;

                -- Générer un texte de commentaire générique
                commentaire_texte := 'Commentaire générique pour le média ' || id_media || ' - Note: ' || note;

                -- Insérer le commentaire dans la table
INSERT INTO Commentaire (pseudo, id, date, note, texte)
VALUES (pseudo_utilisateur, id_media, CURRENT_DATE, note, commentaire_texte);
END IF;
END LOOP;
END $$;