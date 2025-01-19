package ch.heigvd.dai.commentaire;

import ch.heigvd.dai.media.Media;
import ch.heigvd.dai.utilisateur.Utilisateur;

import java.util.Date;

public class Commentaire {

    public Media media;
    public Utilisateur utilisateur;

    public Date date;
    public Integer note;
    public String texte;

    public Commentaire(){}
}
