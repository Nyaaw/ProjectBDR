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

