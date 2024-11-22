/* Création de la base de données*/
DROP DATABASE IF EXIST mediateque;
CREATE DATABASE mediateque;

/*Création de tables*/
DROP TABLE IF EXISTS Createur;
DROP TABLE IF EXISTS Groupe;
DROP TABLE IF EXISTS Persone;
DROP TABLE IF EXISTS Genre;
DROP TABLE IF EXISTS Media;
DROP TABLE IF EXISTS Papier;
DROP TABLE IF EXISTS Livre;
DROP TABLE IF EXISTS BD;
DROP TABLE IF EXISTS Numerique;
DROP TABLE IF EXISTS Film;
DROP TABLE IF EXISTS Serie;
DROP TABLE IF EXISTS Type;
DROP TABLE IF EXISTS JeuVideo;
DROP TABLE IF EXISTS Utilisateur;
DROP TABLE IF EXISTS Liste;
DROP TABLE IF EXISTS Commentaire;


CREATE TABLE Createur(
     id serial,
     nom varchar(50),
     CONSTRAINT PK_Createur PRIMARY KEY (id)
);

CREATE TABLE Groupe(
    id serial,
    CONSTRAINT FK_Groupe_id FOREIGN KEY
       (id) REFERENCES Createur(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Personne(
    id serial,
    prenom varchar(50),
    CONSTRAINT FK_Personne_id FOREIGN KEY
     (id) REFERENCES Createur(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Genre(
    nom varchar(50),
    CONSTRAINT PK_Genre PRIMARY KEY (nom)
);

CREATE TABLE Media(
    id serial,
    nom varchar(50),
    dateSortie date,
    description TEXT,
    genre varchar(50),
    CONSTRAINT PK_Media PRIMARY KEY (id),
    CONSTRAINT FK_Media_genre FOREIGN KEY
      (genre) REFERENCES Genre(nom) ON UPDATE CASCADE
);

CREATE TABLE Papier(
    id serial,
    CONSTRAINT FK_Papier_id FOREIGN KEY
       (id) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Livre(
    id serial,
    nbPage int,
    CONSTRAINT FK_Livre_id FOREIGN KEY
      (id) REFERENCES Papier(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE BD(
    id serial,
    couleur bool,
    CONSTRAINT FK_BD_id FOREIGN KEY
       (id) REFERENCES Papier(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Numerique(
    id serial,
    CONSTRAINT FK_Numerique_id FOREIGN KEY
      (id) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Film(
    id serial,
    duree time,
    CONSTRAINT FK_Film_id FOREIGN KEY
     (id) REFERENCES Numerique(id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Serie(
    id serial,
    nbSaison int,
    CONSTRAINT FK_Serie_id FOREIGN KEY
      (id) REFERENCES Numerique(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Type(
    nom varchar(50),
    CONSTRAINT PK_Genre PRIMARY KEY (nom)
);

CREATE TABLE JeuVideo(
    id serial,
    type varchar(50),
    CONSTRAINT FK_JeuVideo_id FOREIGN KEY
     (id) REFERENCES Numerique(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_JeuVideo_type FOREIGN KEY
     (type) REFERENCES Type(nom) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Utilisateur(
    pseudo varchar(50),
    motDePasse varchar(50),
    CONSTRAINT PK_Utilisateur PRIMARY KEY (pseudo)
);

CREATE TABLE Liste(
    pseudo varchar(50),
    nom varchar(50),
    mediaId serial,
    CONSTRAINT PK_Liste PRIMARY KEY (pseudo, nom),
    CONSTRAINT FK_Liste_pseudo FOREIGN KEY
      (pseudo) REFERENCES Utilisateur(pseudo) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_Liste_mediaId FOREIGN KEY
      (mediaId) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE,
);

CREATE TABLE Commentaire(
    pseudo varchar(50),
    id serial,
    date date,
    note int,
    texte TEXT,
    CONSTRAINT PK_Liste PRIMARY KEY (pseudo, id),
    CONSTRAINT FK_Liste_pseudo FOREIGN KEY
        (pseudo) REFERENCES Utilisateur(pseudo) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_Liste_id FOREIGN KEY
        (id) REFERENCES Media(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CHECK ((note >= 0) and (note <= 5))
);

