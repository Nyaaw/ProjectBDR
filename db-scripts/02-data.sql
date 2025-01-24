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
       ('User2', 'mdp2'),
       ('unpseudo', 'mdp3');
END $$;

-- Insérer des medias dans la liste watched à but demonstratif
INSERT INTO Media_Liste(idMedia, listeNom, listePseudo)
VALUES (1, 'Finished', 'unpseudo'),
       (7, 'Finished', 'unpseudo'),
       (24, 'Finished', 'unpseudo');

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