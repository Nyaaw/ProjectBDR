/* Création de la base de données*/
DROP DATABASE IF EXIST mediateque;
CREATE DATABASE mediateque;

/*Création de tables*/
CREATE TABLE Createur(
    id serial,
    nom varchar(50),
    CONSTRAINT PK_Createur PRIMARY KEY (id)
);

CREATE TABLE Groupe(
    id serial,

);

CREATE TABLE Personne(

);

CREATE TABLE Genre(

);

CREATE TABLE Type(

);

CREATE TABLE Media(

);

CREATE TABLE Papier(

);


CREATE TABLE Livre(

);

CREATE TABLE BD(

);


CREATE TABLE Numerique(

);


CREATE TABLE Film(

);


CREATE TABLE Serie(

);

CREATE TABLE JeuVideo(

);

CREATE TABLE Utilisateur(

);

CREATE TABLE Liste(

);

CREATE TABLE MediaUtilisateur(

);

