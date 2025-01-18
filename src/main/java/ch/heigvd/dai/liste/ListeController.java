package ch.heigvd.dai.liste;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import ch.heigvd.dai.commentaire.Commentaire;
import ch.heigvd.dai.createur.Createur;
import ch.heigvd.dai.createur.TypeCreateur;
import ch.heigvd.dai.media.Media;
import ch.heigvd.dai.media.TypeMedia;
import ch.heigvd.dai.utilisateur.Utilisateur;
import io.javalin.http.*;

public class ListeController {

    static Liste Seen;
    static Liste Favorite;
    static Liste toBeSeen;
    static Liste Watching;
    static Liste exempleListe;

    static Media exampleMedia;

    static{
        exempleListe = new Liste();
        Seen = new Liste();
        Favorite = new Liste();
        toBeSeen = new Liste();
        Watching = new Liste();

        exempleListe.nom = "Liste exemple";
        Seen.nom = "Seen";
        toBeSeen.nom = "To be seen";
        Favorite.nom = "Favorite";
        Watching.nom = "Watching";

        exampleMedia = new Media();

        exampleMedia.id = 2;
        exampleMedia.nom = "Noita";
        exampleMedia.typemedia = TypeMedia.jeuvideo;
        exampleMedia.datesortie = LocalDate.of(2019, 5, 23);
        exampleMedia.description = "A difficult roguelike where every pixel is simulated.";

        exampleMedia.genres = List.of("Adventure", "Horror");

        exampleMedia.jeuvideotypes = List.of("Rogue like", "Sandbox");

        Createur crea1 = new Createur();
        crea1.typecreateur = TypeCreateur.personne;
        crea1.nom = "Hempuli";

        Createur crea2 = new Createur();
        crea2.typecreateur = TypeCreateur.groupe;
        crea2.nom = "Nolla Games";

        exampleMedia.createurs = List.of(crea1, crea2);

        Commentaire com1 = new Commentaire();
        com1.media = exampleMedia;
        com1.date = LocalDate.now();
        com1.note = 2;
        com1.texte = "Trop nul\nce jeu.";

        com1.utilisateur = new Utilisateur();
        com1.utilisateur.nom = "Jean-jacques";

        Commentaire com2 = new Commentaire();
        com2.media = exampleMedia;
        com2.date = LocalDate.parse("2024-01-02");
        com2.note = 2;
        com2.texte = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";

        com2.utilisateur = new Utilisateur();
        com2.utilisateur.nom = "xXxJeanPierredu92xXx";

        exampleMedia.commentaires = List.of(com1, com2);

        Favorite.medias = List.of(exampleMedia, exampleMedia);
        Seen.medias = List.of(exampleMedia, exampleMedia);
        toBeSeen.medias = List.of(exampleMedia, exampleMedia);
        Watching.medias = List.of(exampleMedia, exampleMedia);

    }

    public ListeController() {}

    public static void getOne(Context ctx){


        // get media with id from DB.

        ctx.render("list.html", Map.of("list", Favorite));
    }

    public static void getAll(Context ctx){

        // get medias with id from db

        ctx.render("mylists.html", Map.of("lists", List.of(Favorite, Seen, Watching, toBeSeen)));
    }

    public static void insertList(Context ctx){
        Liste list = new Liste();

        //media.id = ctx.formParamAsClass("id", Integer.class).get();
        list.nom = ctx.formParamAsClass("nom", String.class).get();

        // create media in DB and get id

        ctx.redirect("/list?nom=" + list.nom);
    }
}
