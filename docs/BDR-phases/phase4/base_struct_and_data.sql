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



/*Views*/

-- Types
CREATE VIEW vLivre
AS
SELECT
    Media.id,
    Media.nom,
    Media.dateSortie,
    Media.description,
    Livre.nbPage
FROM
    Livre
        INNER JOIN Media ON
        Livre.id = Media.id;


CREATE VIEW vBD
AS
SELECT
    Media.id,
    Media.nom,
    Media.dateSortie,
    Media.description,
    BD.couleur
FROM
    BD
        INNER JOIN Media ON
        BD.id = Media.id;


CREATE VIEW vFilm
AS
SELECT
    Media.id,
    Media.nom,
    Media.dateSortie,
    Media.description,
    Film.duree
FROM
    Film
        INNER JOIN Media ON
        Film.id = Media.id;

CREATE VIEW vSerie
AS
SELECT
    Media.id,
    Media.nom,
    Media.dateSortie,
    Media.description,
    Serie.nbPage
FROM
    Serie
        INNER JOIN Media ON
        Serie.id = Media.id;


CREATE VIEW vJeuVideo
AS
SELECT
    Media.id,
    Media.nom,
    Media.dateSortie,
    Media.description,
    JeuVideo.nbPage
FROM
    JeuVideo
        INNER JOIN Media ON
        JeuVideo.id = Media.id;


/*add base data*/

-- Insérer 5 créateurs génériques
DO $$
DECLARE
i INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    INSERT INTO Createur (nom)
    VALUES ('Createur' || i);
END LOOP;
END $$;

-- Insérer 5 livres génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    random_genre_id INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    INSERT INTO Media (nom, dateSortie, description)
    VALUES ('Livre' || i, CURRENT_DATE, 'Description pour Livre' || i)
    RETURNING id INTO id_media;

    INSERT INTO Papier (id)
    VALUES (id_media);

    INSERT INTO Livre (id, nbPage)
    VALUES (id_media, 100 + i);

    -- Associer un genre aléatoire
    SELECT id INTO random_genre_id
    FROM Genre
    ORDER BY RANDOM()
        LIMIT 1;

    INSERT INTO MediaGenre (idMedia, idGenre)
    VALUES (id_media, random_genre_id);
END LOOP;
END $$;

-- Insérer 5 BD génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    random_genre_id INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    INSERT INTO Media (nom, dateSortie, description)
    VALUES ('BD' || i, CURRENT_DATE, 'Description pour BD' || i)
    RETURNING id INTO id_media;

    INSERT INTO Papier (id)
    VALUES (id_media);

    INSERT INTO BD (id, couleur)
    VALUES (id_media, TRUE);

    -- Associer un genre aléatoire
    SELECT id INTO random_genre_id
    FROM Genre
    ORDER BY RANDOM()
        LIMIT 1;

    INSERT INTO MediaGenre (idMedia, idGenre)
    VALUES (id_media, random_genre_id);
END LOOP;
END $$;

-- Insérer 5 films génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    random_genre_id INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    INSERT INTO Media (nom, dateSortie, description)
    VALUES ('Film' || i, CURRENT_DATE, 'Description pour Film' || i)
    RETURNING id INTO id_media;

    INSERT INTO Numerique (id)
    VALUES (id_media);

    INSERT INTO Film (id, duree)
    VALUES (id_media, INTERVAL '2 hours' + (i || ' minutes')::INTERVAL);

    -- Associer un genre aléatoire
    SELECT id INTO random_genre_id
    FROM Genre
    ORDER BY RANDOM()
        LIMIT 1;

    INSERT INTO MediaGenre (idMedia, idGenre)
    VALUES (id_media, random_genre_id);
END LOOP;
END $$;

-- Insérer 5 séries génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    random_genre_id INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    INSERT INTO Media (nom, dateSortie, description)
    VALUES ('Serie' || i, CURRENT_DATE, 'Description pour Serie' || i)
    RETURNING id INTO id_media;

    INSERT INTO Numerique (id)
    VALUES (id_media);

    INSERT INTO Serie (id, nbSaison)
    VALUES (id_media, i);

    -- Associer un genre aléatoire
    SELECT id INTO random_genre_id
    FROM Genre
    ORDER BY RANDOM()
        LIMIT 1;

    INSERT INTO MediaGenre (idMedia, idGenre)
    VALUES (id_media, random_genre_id);
END LOOP;
END $$;

-- Insérer 5 jeux vidéo génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    random_type_id INTEGER;
    random_genre_id INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    INSERT INTO Media (nom, dateSortie, description)
    VALUES ('JeuVideo' || i, CURRENT_DATE, 'Description pour JeuVideo' || i)
    RETURNING id INTO id_media;

    INSERT INTO Numerique (id)
    VALUES (id_media);

    INSERT INTO JeuVideo (id)
    VALUES (id_media);

    -- Associer un type aléatoire
    SELECT id INTO random_type_id
            FROM Type
            ORDER BY RANDOM()
                LIMIT 1;

    INSERT INTO JeuVideoType (idJeuVideo, idType)
    VALUES (id_media, random_type_id);

    -- Associer un genre aléatoire
    SELECT id INTO random_genre_id
    FROM Genre
    ORDER BY RANDOM()
        LIMIT 1;

    INSERT INTO MediaGenre (idMedia, idGenre)
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

