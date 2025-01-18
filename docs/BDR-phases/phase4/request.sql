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
        LEFT JOIN Media_Genre mg ON m.id = mg.idMedia
        LEFT JOIN Genre g ON mg.idGenre = g.id
        LEFT JOIN Media_Createur mc ON m.id = mc.idMedia
        LEFT JOIN Createur c ON mc.idCreateur = c.id
        LEFT JOIN Papier p ON m.id = p.id
        LEFT JOIN Numerique n ON m.id = n.id
        LEFT JOIN Livre l ON m.id = l.id
        LEFT JOIN BD b ON m.id = b.id
        LEFT JOIN Film f ON m.id = f.id
        LEFT JOIN Serie s ON m.id = s.id
        LEFT JOIN JeuVideo jv ON m.id = jv.id
        LEFT JOIN JeuVideo_Type jvt ON jv.id = jvt.idJeuVideo
        LEFT JOIN Type jt ON jvt.idType = jt.id
WHERE
    m.id = <media_id>;

<<<<<<< HEAD
-- 5 last added medias
SELECT *
FROM Media
ORDER BY id DESC
LIMIT 5;

-- get results
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
        LEFT JOIN MediaGenre mg ON m.id = mg.idMedia
        LEFT JOIN Genre g ON mg.idGenre = g.id
        LEFT JOIN Papier p ON m.id = p.id
        LEFT JOIN Numerique n ON m.id = n.id
        LEFT JOIN Livre l ON m.id = l.id
        LEFT JOIN BD b ON m.id = b.id
        LEFT JOIN Film f ON m.id = f.id
        LEFT JOIN Serie s ON m.id = s.id
        LEFT JOIN JeuVideo jv ON m.id = jv.id
        LEFT JOIN JeuVideoType jvt ON jv.id = jvt.idJeuVideo
        LEFT JOIN Type jt ON jvt.idType = jt.id
WHERE
  -- Condition pour le genre
    (g.nom = '<genre_name>' OR '<genre_name>' IS NULL)
  -- Condition pour un type spécifique de média
  AND (
    ('Livre' = '<media_type>' AND l.id IS NOT NULL)
        OR ('BD' = '<media_type>' AND b.id IS NOT NULL)
        OR ('Film' = '<media_type>' AND f.id IS NOT NULL)
        OR ('Série' = '<media_type>' AND s.id IS NOT NULL)
        OR ('Jeu Vidéo' = '<media_type>' AND jv.id IS NOT NULL)
        OR '<media_type>' IS NULL
    )
  -- Condition pour un jeu vidéo d'un type spécifique
  AND (
    (jv.id IS NOT NULL AND jt.nom = '<game_type>')
        OR '<game_type>' IS NULL
    )
  -- Condition pour une BD en couleur ou non
  AND (
    (b.id IS NOT NULL AND b.couleur = <is_color>)
        OR <is_color> IS NULL
    )
  -- Condition pour un livre avec un nombre de pages minimum
  AND (
    (l.id IS NOT NULL AND l.nbPage >= <min_pages>)
        OR <min_pages> IS NULL
    )
  -- Condition pour une série avec un nombre de saisons minimum
  AND (
    (s.id IS NOT NULL AND s.nbSaison >= <min_seasons>)
        OR <min_seasons> IS NULL
    )
  -- Condition pour un mot spécifique dans le titre ou la description
  AND (
    (m.nom ILIKE '%' || '<search_word>' || '%')
        OR (m.description ILIKE '%' || '<search_word>' || '%')
        OR '<search_word>' IS NULL
    );


-- insert into list
INSERT INTO Media_List (mediaId, listenom, listePseudo)
        VALUES (<id>, <nom>, <pseudo>);

-- add comment
INSERT INTO Commentaire (pseudo, id, date, note, texte)
        VALUES (<pseudo>, <id>, CUREENT_DATE, <note>, <texte>);

-- insert media
DO $$
DECLARE
i INTEGER;
id_media INTEGER;
random_genre_id INTEGER;
BEGIN
INSERT INTO Media (nom, dateSortie, description)
VALUES ( <media.nom> ,  <media.datesortie> , '<media.description>')
RETURNING id INTO id_media;
INSERT INTO  media.typemedia  (id)
VALUES (id_media);
    FOR int i..nbGenres LOOP
        INSERT INTO Media_Genre (idMedia, idGenre)
        VALUES (<id_media>,  <media.genres[i]> );  // à corriger
    END LOOP;
END $$;

 
-- 5 last medias added
SELECT 
    *
FROM 
    Media
ORDER BY id DESC
LIMIT 5;


-- lists of a user
SELECT
    nom
FROM
    Liste
WHERE
    pseudo = <user_pseudo>;

-- medias in list
SELECT
    *
FROM
    Media
        INNER JOIN Media_Liste ON Media_Liste.id = Media.id
        INNER JOIN Liste ON Media_Liste.nom = Liste.nom
WHERE
    Liste.nom = <nom liste>

-- add list
INSERT INTO
        Liste (pseudo, nom)
        VALUES (<pseudo>, <list.nom>);

