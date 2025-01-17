package ch.heigvd.dai.media;

import ch.heigvd.dai.commentaire.Commentaire;
import ch.heigvd.dai.createur.Createur;

import java.time.LocalDate;
import java.util.List;

public class Media{

    public Integer id;
    public String nom;
    public LocalDate datesortie;
    public String description;
    public TypeMedia typemedia;

    public List<Commentaire> commentaires;
    public List<Createur> createurs;
    public List<String> genres;
    public List<String> jeuvideotypes;

    public Media(){}
}