/*Triggers*/
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



/*Functions*/




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

--insérer 5 livres génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    id_createur INTEGER;
BEGIN
FOR i IN 1..5 LOOP
    -- Insérer un livre
    INSERT INTO Media (nom, dateSortie, description)
    VALUES ('Livre' || i, CURRENT_DATE, 'Description pour Livre' || i)
    RETURNING id INTO id_media;

            -- Assigner un créateur aléatoire au livre
SELECT id INTO id_createur FROM Createur ORDER BY random() LIMIT 1;
INSERT INTO Media_Createur (mediaId, createurId)
VALUES (id_media, id_createur);

-- Insérer le papier et le livre
INSERT INTO Papier (id)
VALUES (id_media);

INSERT INTO Livre (id, nbPage)
VALUES (id_media, 100 + i);
END LOOP;
END $$;


-- Insérer 5 BD génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    id_createur INTEGER;
BEGIN
FOR i IN 1..5 LOOP
        -- Insérer une BD
        INSERT INTO Media (nom, dateSortie, description)
        VALUES ('BD' || i, CURRENT_DATE, 'Description pour BD' || i)
        RETURNING id INTO id_media;

        -- Assigner un créateur aléatoire à la BD
SELECT id INTO id_createur FROM Createur ORDER BY random() LIMIT 1;
INSERT INTO Media_Createur (mediaId, createurId)
VALUES (id_media, id_createur);

-- Insérer le papier et la BD
INSERT INTO Papier (id)
VALUES (id_media);

INSERT INTO BD (id, couleur)
VALUES (id_media, TRUE);
END LOOP;
END $$;


-- Insérer 5 films génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    id_createur INTEGER;
BEGIN
FOR i IN 1..5 LOOP
        -- Insérer un film
        INSERT INTO Media (nom, dateSortie, description)
        VALUES ('Film' || i, CURRENT_DATE, 'Description pour Film' || i)
        RETURNING id INTO id_media;

        -- Assigner un créateur aléatoire au film
SELECT id INTO id_createur FROM Createur ORDER BY random() LIMIT 1;
INSERT INTO Media_Createur (mediaId, createurId)
VALUES (id_media, id_createur);

-- Insérer le numérique et le film
INSERT INTO Numerique (id)
VALUES (id_media);

INSERT INTO Film (id, duree)
VALUES (id_media, INTERVAL '2 hours' + (i || ' minutes')::INTERVAL);
END LOOP;
END $$;


-- Insérer 5 séries génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    id_createur INTEGER;
BEGIN
FOR i IN 1..5 LOOP
        -- Insérer une série
        INSERT INTO Media (nom, dateSortie, description)
        VALUES ('Serie' || i, CURRENT_DATE, 'Description pour Serie' || i)
        RETURNING id INTO id_media;

        -- Assigner un créateur aléatoire à la série
SELECT id INTO id_createur FROM Createur ORDER BY random() LIMIT 1;
INSERT INTO Media_Createur (mediaId, createurId)
VALUES (id_media, id_createur);

-- Insérer le numérique et la série
INSERT INTO Numerique (id)
VALUES (id_media);

INSERT INTO Serie (id, nbSaison)
VALUES (id_media, i);
END LOOP;
END $$;


-- Insérer 5 jeux vidéo génériques
DO $$
DECLARE
i INTEGER;
    id_media INTEGER;
    id_createur INTEGER;
BEGIN
FOR i IN 1..5 LOOP
        -- Insérer un jeu vidéo
        INSERT INTO Media (nom, dateSortie, description)
        VALUES ('JeuVideo' || i, CURRENT_DATE, 'Description pour JeuVideo' || i)
        RETURNING id INTO id_media;

        -- Assigner un créateur aléatoire au jeu vidéo
SELECT id INTO id_createur FROM Createur ORDER BY random() LIMIT 1;
INSERT INTO Media_Createur (mediaId, createurId)
VALUES (id_media, id_createur);

-- Insérer le numérique et le jeu vidéo
INSERT INTO Numerique (id)
VALUES (id_media);

INSERT INTO JeuVideo (id)
VALUES (id_media);
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

