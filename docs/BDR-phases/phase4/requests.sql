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
BEGIN
FOR i IN 1..5 LOOP
        INSERT INTO Media (nom, dateSortie, description)
        VALUES ('Livre' || i, CURRENT_DATE, 'Description pour Livre' || i)
        RETURNING id INTO id_media;

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
BEGIN
FOR i IN 1..5 LOOP
        INSERT INTO Media (nom, dateSortie, description)
        VALUES ('BD' || i, CURRENT_DATE, 'Description pour BD' || i)
        RETURNING id INTO id_media;

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
BEGIN
FOR i IN 1..5 LOOP
        INSERT INTO Media (nom, dateSortie, description)
        VALUES ('Film' || i, CURRENT_DATE, 'Description pour Film' || i)
        RETURNING id INTO id_media;

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
BEGIN
FOR i IN 1..5 LOOP
        INSERT INTO Media (nom, dateSortie, description)
        VALUES ('Serie' || i, CURRENT_DATE, 'Description pour Serie' || i)
        RETURNING id INTO id_media;

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
BEGIN
FOR i IN 1..5 LOOP
        INSERT INTO Media (nom, dateSortie, description)
        VALUES ('JeuVideo' || i, CURRENT_DATE, 'Description pour JeuVideo' || i)
        RETURNING id INTO id_media;

INSERT INTO Numerique (id)
VALUES (id_media);

INSERT INTO JeuVideo (id)
VALUES (id_media);
END LOOP;
END $$;

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

-- Insérer 2 utilisateurs génériques avec email et mot de passe
DO $$
BEGIN
INSERT INTO Utilisateur (pseudo, motDePasse)
VALUES ('User1', 'mdp1'),
       ('User2', 'mdp2');
END $$;
