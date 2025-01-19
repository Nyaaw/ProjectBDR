package ch.heigvd.dai.liste;

import java.time.LocalDate;
import java.util.*;

import ch.heigvd.dai.DSLGetter;
import ch.heigvd.dai.commentaire.Commentaire;
import ch.heigvd.dai.createur.Createur;
import ch.heigvd.dai.createur.TypeCreateur;
import ch.heigvd.dai.media.Media;
import ch.heigvd.dai.media.TypeMedia;
import ch.heigvd.dai.utilisateur.Utilisateur;
import io.javalin.http.*;
import org.jooq.DSLContext;

public class ListeController {

    public static DSLContext dsl;

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
        exampleMedia.datesortie = new Date();
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

    public static void getOne(Context ctx){
        String nom = ctx.queryParamAsClass("nom", String.class)
                .check(s -> !s.isBlank(), "list name not provided").get();

        String pseudo = "unpseudo";

        // get medias with id from db
        String sql = """
                SELECT
                    m.id AS media_id,
                    m.nom AS media_name,
                    m.dateSortie AS release_date,
                    g.nom AS genre_name,
                    CASE
                WHEN l.id IS NOT NULL THEN 'livre'
                WHEN b.id IS NOT NULL THEN 'bd'
                WHEN f.id IS NOT NULL THEN 'film'
                WHEN s.id IS NOT NULL THEN 'serie'
                WHEN jv.id IS NOT NULL THEN 'jeuvideo'
                END AS media_type
                FROM
                    Media m
                LEFT JOIN Media_Genre mg ON m.id = mg.mediaId
                LEFT JOIN Genre g ON mg.genreNom = g.nom
                LEFT JOIN Livre l ON m.id = l.id
                LEFT JOIN BD b ON m.id = b.id
                LEFT JOIN Film f ON m.id = f.id
                LEFT JOIN Serie s ON m.id = s.id
                LEFT JOIN JeuVideo jv ON m.id = jv.id
                LEFT JOIN JeuVideo_Type jvt ON jv.id = jvt.jeuVideoId
                LEFT JOIN Type jt ON jvt.typenom = jt.nom
                LEFT JOIN Media_Liste ml ON m.id = ml.idMedia
                WHERE\n""" +
                    "ml.listenom = '"+ nom +"' AND " +
                    "ml.listepseudo = '"+ pseudo +"';";

        // Execute the raw SQL with bind parameters
        var result = dsl.fetch(sql);

        List<Media> medias = DSLGetter.getMultipleMedias(result);
        
        Liste list = new Liste();
        list.nom = nom;
        list.medias = medias;

        ctx.render("list.html", Map.of("list", list));
    }

    public static void getAll(Context ctx){
        String pseudo = "unpseudo";

        String sql = "SELECT\n" +
                "    nom, pseudo\n" +
                "FROM\n" +
                "    Liste\n" +
                "WHERE\n" +
                "    pseudo = '"+ pseudo +"';";
        // Execute the raw SQL with bind parameters
        var result = dsl.fetch(sql, pseudo);
        // Process the result
        result.forEach(record -> {
            System.out.println(record);
        });

        List<Liste> lists = DSLGetter.getMultipleLists(result);

        ctx.render("mylists.html", Map.of("lists", lists));
    }

    public static void insertList(Context ctx){
        Liste list = new Liste();

        //media.id = ctx.formParamAsClass("id", Integer.class).get();
        list.nom = ctx.formParamAsClass("nom", String.class)
                .check(s -> !s.isBlank(), "list name not provided").get();

        String pseudo = "unpseudo";

        // create list in DB
        String sql = "INSERT INTO\n" +
                "Liste (pseudo, nom)\n" +
                "VALUES ('"+pseudo+"', '"+list.nom+"');";
        // Execute the raw SQL with bind parameters
        var result = dsl.fetch(sql, pseudo, "exampleName");
        // Process the result
        result.forEach(record -> {
            System.out.println(record);
        });

        ctx.redirect("/list?nom=" + list.nom);
    }
}
