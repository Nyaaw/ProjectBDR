/* Création de la base de données, à séparer du script principal dans dBeaver*/
DROP DATABASE IF EXIST mediateque;
CREATE DATABASE mediateque;

/*Création de tables*/
/* 
DROP TABLE IF EXISTS Media_Genre;
DROP TABLE IF EXISTS Media_Liste;
DROP TABLE IF EXISTS Media_Createur;
*/
DROP TABLE IF EXISTS Groupe;
DROP TABLE IF EXISTS Personne;
DROP TABLE IF EXISTS Livre;
DROP TABLE IF EXISTS BD;
DROP TABLE IF EXISTS Papier;
DROP TABLE IF EXISTS Film;
DROP TABLE IF EXISTS Serie;
DROP TABLE IF EXISTS JeuVideo;
DROP TABLE IF EXISTS Numerique;
DROP TABLE IF EXISTS Type;
DROP TABLE IF EXISTS Liste;
DROP TABLE IF EXISTS Commentaire;
DROP TABLE IF EXISTS Utilisateur;
DROP TABLE IF EXISTS Createur;
DROP TABLE IF EXISTS Media;
DROP TABLE IF EXISTS Genre;


CREATE TABLE Createur(
     id serial,
     nom varchar(255) NOT NULL,
     CONSTRAINT PK_Createur PRIMARY KEY (id)
);

CREATE TABLE Groupe(
    id serial,
    CONSTRAINT PK_Groupe PRIMARY KEY (id),
    CONSTRAINT FK_Groupe_id FOREIGN KEY
       (id) REFERENCES Createur(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Personne(
    id serial,
    prenom varchar(255) NOT NULL,
    CONSTRAINT PK_Personne PRIMARY KEY (id),
    CONSTRAINT FK_Personne_id FOREIGN KEY
     (id) REFERENCES Createur(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Genre(
    nom varchar(50),
    CONSTRAINT PK_Genre PRIMARY KEY (nom)
);

CREATE TABLE Media(
    id serial,
    nom varchar(255) NOT NULL,
    dateSortie date NOT NULL,
    description TEXT NOT NULL,
    CONSTRAINT PK_Media PRIMARY KEY (id)
);

CREATE TABLE Media_Createur(
    mediaId integer,
    createurId integer,
    CONSTRAINT PK_Media_Createur PRIMARY KEY(mediaId, createurId),
    CONSTRAINT FK_Media_Createur
)

/* CREATE TABLE Media_Createur */

/* CREATE TABLE Media_Genre */

CREATE TABLE Papier(
    id serial,
    CONSTRAINT PK_Papier PRIMARY KEY (id),
    CONSTRAINT FK_Papier_id FOREIGN KEY
       (id) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Livre(
    id serial,
    nbPage int NOT NULL,
    CONSTRAINT PK_Livre PRIMARY KEY (id),
    CONSTRAINT FK_Livre_id FOREIGN KEY
      (id) REFERENCES Papier(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE BD(
    id serial,
    couleur bool NOT NULL,
    CONSTRAINT PK_BD PRIMARY KEY (id),
    CONSTRAINT FK_BD_id FOREIGN KEY
       (id) REFERENCES Papier(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Numerique(
    id serial,
    CONSTRAINT PK_Numerique PRIMARY KEY(id),
    CONSTRAINT FK_Numerique_id FOREIGN KEY
      (id) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Film(
    id serial,
    duree time NOT NULL,
    CONSTRAINT PK_Film PRIMARY KEY(id),
    CONSTRAINT FK_Film_id FOREIGN KEY
     (id) REFERENCES Numerique(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Serie(
    id serial,
    nbSaison int NOT NULL,
    CONSTRAINT PK_Serie PRIMARY KEY(id),
    CONSTRAINT FK_Serie_id FOREIGN KEY
      (id) REFERENCES Numerique(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Type(
    nom varchar(50),
    CONSTRAINT PK_Type PRIMARY KEY (nom)
);

CREATE TABLE JeuVideo(
    id serial,
    type varchar(50) NOT NULL,
    CONSTRAINT PK_JeuVideo PRIMARY KEY(id),
    CONSTRAINT FK_JeuVideo_id FOREIGN KEY
     (id) REFERENCES Numerique(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_JeuVideo_type FOREIGN KEY
     (type) REFERENCES Type(nom) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Utilisateur(
    pseudo varchar(50),
    motDePasse varchar(255) NOT NULL,
    CONSTRAINT PK_Utilisateur PRIMARY KEY (pseudo)
);

CREATE TABLE Liste(
    pseudo varchar(50),
    nom varchar(50),
    mediaId serial NOT NULL,
    CONSTRAINT PK_Liste PRIMARY KEY (pseudo, nom),
    CONSTRAINT FK_Liste_pseudo FOREIGN KEY
      (pseudo) REFERENCES Utilisateur(pseudo) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_Liste_mediaId FOREIGN KEY
      (mediaId) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE
);

/* CREATE TABLE Media_Liste */

CREATE TABLE Commentaire(
    pseudo varchar(50),
    id serial,
    date date NOT NULL,
    note int NOT NULL,
    texte TEXT,
    CONSTRAINT PK_Commentaire PRIMARY KEY (pseudo, id),
    CONSTRAINT FK_Commentaire_pseudo FOREIGN KEY
        (pseudo) REFERENCES Utilisateur(pseudo) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_Commentaire_id FOREIGN KEY
        (id) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CHECK ((note >= 0) and (note <= 5))
);

