/*Triggers*/




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
