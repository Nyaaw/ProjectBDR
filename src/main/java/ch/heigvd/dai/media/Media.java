package ch.heigvd.dai.media;

import ch.heigvd.dai.commentaire.Commentaire;
import ch.heigvd.dai.createur.Createur;

import java.util.Date;
import java.util.List;

public class Media{

    public Integer id;
    public String nom;
    public Date datesortie;
    public String description;
    public TypeMedia typemedia;
    public Integer note;

    public Integer duree;
    public Boolean couleur;
    public Integer saison;
    public Integer pages;

    public List<Commentaire> commentaires;
    public List<Createur> createurs;
    public List<String> genres;
    public List<String> jeuvideotypes;

    public Media(){}
}
