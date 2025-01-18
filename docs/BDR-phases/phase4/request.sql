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

