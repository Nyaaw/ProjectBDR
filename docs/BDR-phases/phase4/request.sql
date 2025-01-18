-- infos of medias
SELECT * FROM Media

-- info of 1 media
SELECT
    m.id AS media_id,
    m.nom AS media_name,
    m.dateSortie AS release_date,
    m.description AS media_description,
    g.nom AS genre_name,
    c.nom AS creator_name,
    CASE
        WHEN p.id IS NOT NULL THEN 'Papier'
        WHEN n.id IS NOT NULL THEN 'Numérique'
        END AS media_format,
    CASE
        WHEN l.id IS NOT NULL THEN 'Livre'
        WHEN b.id IS NOT NULL THEN 'BD'
        WHEN f.id IS NOT NULL THEN 'Film'
        WHEN s.id IS NOT NULL THEN 'Série'
        WHEN jv.id IS NOT NULL THEN 'Jeu Vidéo'
        END AS media_type,
    l.nbPage AS book_pages,
    b.couleur AS bd_color,
    f.duree AS film_duration,
    s.nbSaison AS series_seasons,
    jt.nom AS game_type
FROM
    Media m
        LEFT JOIN Media_Genre mg ON m.id = mg.mediaId
        LEFT JOIN Genre g ON mg.genreNom = g.nom
        LEFT JOIN Media_Createur mc ON m.id = mc.mediaId
        LEFT JOIN Createur c ON mc.createurId = c.id
        LEFT JOIN Papier p ON m.id = p.id
        LEFT JOIN Numerique n ON m.id = n.id
        LEFT JOIN Livre l ON m.id = l.id
        LEFT JOIN BD b ON m.id = b.id
        LEFT JOIN Film f ON m.id = f.id
        LEFT JOIN Serie s ON m.id = s.id
        LEFT JOIN JeuVideo jv ON m.id = jv.id
        LEFT JOIN JeuVideo_Type jvt ON jv.id = jvt.jeuVideoId
        LEFT JOIN Type jt ON jvt.typenom = jt.nom
WHERE
    m.id = <media_id>;

-- 5 last added medias
SELECT *
FROM Media
ORDER BY id DESC
LIMIT 5;

-- get results
-- for optional, put NULL
SELECT
    m.id AS media_id,
    m.nom AS media_name,
    m.dateSortie AS release_date,
    m.description AS media_description,
    g.nom AS genre_name,
    CASE
        WHEN l.id IS NOT NULL THEN 'Livre'
        WHEN b.id IS NOT NULL THEN 'BD'
        WHEN f.id IS NOT NULL THEN 'Film'
        WHEN s.id IS NOT NULL THEN 'Série'
        WHEN jv.id IS NOT NULL THEN 'Jeu Vidéo'
        END AS media_type,
    l.nbPage AS book_pages,
    b.couleur AS bd_color,
    f.duree AS film_duration,
    s.nbSaison AS series_seasons,
    jt.nom AS game_type
FROM
    Media m
        LEFT JOIN Media_Genre mg ON m.id = mg.mediaId
        LEFT JOIN Genre g ON mg.genrenom = g.nom
        LEFT JOIN Papier p ON m.id = p.id
        LEFT JOIN Numerique n ON m.id = n.id
        LEFT JOIN Livre l ON m.id = l.id
        LEFT JOIN BD b ON m.id = b.id
        LEFT JOIN Film f ON m.id = f.id
        LEFT JOIN Serie s ON m.id = s.id
        LEFT JOIN JeuVideo jv ON m.id = jv.id
        LEFT JOIN JeuVideo_Type jvt ON jv.id = jvt.jeuVideoId
        LEFT JOIN Type jt ON jvt.typenom = jt.nom
WHERE
  -- Condition pour le genre
    (g.nom = COALESCE('<genre_name>', g.nom))
  -- Condition pour un type spécifique de média
  AND (
    (COALESCE('<media_type>', '') = 'Livre' AND l.id IS NOT NULL)
        OR (COALESCE('<media_type>', '') = 'BD' AND b.id IS NOT NULL)
        OR (COALESCE('<media_type>', '') = 'Film' AND f.id IS NOT NULL)
        OR (COALESCE('<media_type>', '') = 'Série' AND s.id IS NOT NULL)
        OR (COALESCE('<media_type>', '') = 'Jeu Vidéo' AND jv.id IS NOT NULL)
        OR COALESCE('<media_type>', '') = ''
    )
  -- Condition pour un jeu vidéo d'un type spécifique
  AND (
    (jv.id IS NOT NULL AND jt.nom = COALESCE('<game_type>', jt.nom))
        OR '<game_type>' IS NULL
    )
  -- Condition pour une BD en couleur ou non
  AND (
    (b.id IS NOT NULL AND b.couleur = COALESCE(<is_color>, b.couleur))
        OR <is_color> IS NULL
    )
  -- Condition pour un livre avec un nombre de pages minimum
  AND (
    (l.id IS NOT NULL AND l.nbPage >= COALESCE(<min_pages>, 0))
        OR <min_pages> IS NULL
    )
  -- Condition pour une série avec un nombre de saisons minimum
  AND (
    (s.id IS NOT NULL AND s.nbSaison >= COALESCE(<min_seasons>, 0))
        OR <min_seasons> IS NULL
    )
  -- Condition pour un mot spécifique dans le titre ou la description
  AND (
    (m.nom ILIKE '%' || COALESCE('<search_word>', '') || '%')
        OR (m.description ILIKE '%' || COALESCE('<search_word>', '') || '%')
    );



-- insert into list
INSERT INTO Media_List (mediaId, listenom, listePseudo)
        VALUES (<id>, <nom>, <pseudo>);

-- add comment
INSERT INTO Commentaire (pseudo, id, date, note, texte)
        VALUES (<pseudo>, <id>, CUREENT_DATE, <note>, <texte>);

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


-- lists of a user
SELECT
    nom
FROM
    Liste
WHERE
    pseudo = <user_pseudo>;

-- add list
INSERT INTO
        Liste (pseudo, nom)
        VALUES (<pseudo>, <list.nom>);

