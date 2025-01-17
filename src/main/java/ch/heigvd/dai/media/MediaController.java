package ch.heigvd.dai.media;

import ch.heigvd.dai.commentaire.Commentaire;
import ch.heigvd.dai.createur.Createur;
import ch.heigvd.dai.createur.TypeCreateur;
import ch.heigvd.dai.utilisateur.Utilisateur;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;
import io.javalin.http.*;

public class MediaController {

    static Media exampleMedia;

    static{
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
    }

    public MediaController() {

    }

    public static void getOne(Context ctx) {

        Integer id = ctx.queryParamAsClass("id", Integer.class).get();

        // get media with id from DB.

        ctx.render("media.html", Map.of("media", exampleMedia));
    }

    public static void getAll(Context ctx) {

        // get medias with id from db

        ctx.render("explore.html", Map.of("medias", List.of(exampleMedia, exampleMedia, exampleMedia)));
    }


    public static void getFive(Context ctx) {

        // get medias with id from db

        ctx.render("index.html", Map.of("medias", List.of(exampleMedia, exampleMedia, exampleMedia), "genres", List.of("Genre 1","Genre 2"), "mediatypes", List.of("type 1","type 2"), "jeuvideotypes", List.of("type 1","type 2")));
    }

    public static void insertMedia(Context ctx){
        Media media = new Media();

        //media.id = ctx.formParamAsClass("id", Integer.class).get();
        media.nom = ctx.formParamAsClass("nom", String.class).get();
        media.description = ctx.formParamAsClass("description", String.class).get();

        String type = ctx.formParamAsClass("typemedia", String.class).get();

        try{
            media.typemedia = TypeMedia.valueOf(type);
        } catch (IllegalArgumentException e){
            throw new BadRequestResponse("Media type is not valid");
        }

        media.genres = ctx.formParamsAsClass("genres", String.class).get();

        media.datesortie = ctx.formParamAsClass("datesortie", Instant.class)
                .get().atZone(ZoneId.systemDefault()).toLocalDate();

        List<Integer> createursIds = ctx.formParamsAsClass("createurs", Integer.class).get();

        if(createursIds.isEmpty())
            throw new BadRequestResponse("No creators given");

        // verify that createursIds are valid creators

        media.genres = ctx.formParamsAsClass("genres", String.class).get();

        // verify that genres exists

        if(media.typemedia == TypeMedia.jeuvideo)
            media.jeuvideotypes = ctx.formParamsAsClass("jeuvideotypes", String.class).get();

        // verify that jeuvideotypes exists

        // create media in DB and get id

        ctx.redirect("/media?id=" + media.id);
    }
}
