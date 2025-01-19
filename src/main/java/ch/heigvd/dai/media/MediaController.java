package ch.heigvd.dai.media;

import ch.heigvd.dai.commentaire.Commentaire;
import ch.heigvd.dai.createur.Createur;
import ch.heigvd.dai.createur.TypeCreateur;
import ch.heigvd.dai.liste.Liste;
import ch.heigvd.dai.utilisateur.Utilisateur;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;
import io.javalin.http.*;

import org.jooq.DSLContext;

import javax.swing.text.StyledEditorKit;

public class MediaController {

    public static DSLContext dsl;

    static Media exampleMedia;

    static Liste Seen;
    static Liste Favorite;
    static Liste toBeSeen;
    static Liste Watching;
    static Liste exempleListe;

    static{
        exampleMedia = new Media();

        exampleMedia.id = 2;
        exampleMedia.nom = "Noita";
        exampleMedia.typemedia = TypeMedia.jeuvideo;
        exampleMedia.datesortie = new Date(2020,2,3);
        exampleMedia.description = "A difficult roguelike where every pixel is simulated.";
        exampleMedia.note = 2;

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

        exempleListe = new Liste();
        Seen = new Liste();
        Favorite = new Liste();
        toBeSeen = new Liste();
        Watching = new Liste();

        exempleListe.nom = "Liste example";
        Seen.nom = "Seen";
        toBeSeen.nom = "To be seen";
        Favorite.nom = "Favorite";
        Watching.nom = "Watching";

    }

    public MediaController() {

    }

    public static void getOne(Context ctx) {

        Integer id = ctx.queryParamAsClass("id", Integer.class).get();

        // get media with id from DB.

        // Define your raw SQL statement with placeholders
        String sql = "SELECT\n" +
                "    m.id AS media_id,\n" +
                "    m.nom AS media_name,\n" +
                "    m.dateSortie AS release_date,\n" +
                "    m.description AS media_description,\n" +
                "    g.nom AS genre_name,\n" +
                "    c.id AS creator_id,\n" +
                "    c.nom AS creator_name,\n" +
                "    jt.nom AS jeuvideotype_name,\n" +
                "    CASE\n" +
                "WHEN p.id IS NOT NULL THEN 'Papier'\n" +
                "WHEN n.id IS NOT NULL THEN 'Numérique'\n" +
                "END AS media_format,\n" +
                "    CASE\n" +
                "WHEN l.id IS NOT NULL THEN 'livre'\n" +
                "WHEN b.id IS NOT NULL THEN 'bd'\n" +
                "WHEN f.id IS NOT NULL THEN 'film'\n" +
                "WHEN s.id IS NOT NULL THEN 'serie'\n" +
                "WHEN jv.id IS NOT NULL THEN 'jeuvideo'\n" +
                "END AS media_type,\n" +
                "    l.nbPage AS book_pages,\n" +
                "    b.couleur AS bd_color,\n" +
                "    f.duree AS film_duration,\n" +
                "    s.nbSaison AS series_seasons,\n" +
                "    jt.nom AS game_type\n" +
                "FROM\n" +
                "    Media m\n" +
                "LEFT JOIN Media_Genre mg ON m.id = mg.mediaId\n" +
                "LEFT JOIN Commentaire com ON m.id = com.id\n" +
                "LEFT JOIN Genre g ON mg.genreNom = g.nom\n" +
                "LEFT JOIN Media_Createur mc ON m.id = mc.mediaId\n" +
                "LEFT JOIN Createur c ON mc.createurId = c.id\n" +
                "LEFT JOIN Papier p ON m.id = p.id\n" +
                "LEFT JOIN Numerique n ON m.id = n.id\n" +
                "LEFT JOIN Livre l ON m.id = l.id\n" +
                "LEFT JOIN BD b ON m.id = b.id\n" +
                "LEFT JOIN Film f ON m.id = f.id\n" +
                "LEFT JOIN Serie s ON m.id = s.id\n" +
                "LEFT JOIN JeuVideo jv ON m.id = jv.id\n" +
                "LEFT JOIN JeuVideo_Type jvt ON jv.id = jvt.jeuVideoId\n" +
                "LEFT JOIN Type jt ON jvt.typenom = jt.nom\n" +
                "WHERE\n" +
                "    m.id = " + id + ";";


        // Execute the raw SQL with bind parameters
        var raw = dsl.fetch(sql, id);

        for(var x : raw){
            System.out.println(x);
        }

        // Process the result into a list of media objects, grouped by media id
        Media m = null;

        for (var record : raw) {
            Integer mediaId = (Integer) record.get("media_id");

            // If the media doesn't exist in the map, create a new media entry
            if (m == null) {

                m = new Media();
                m.id = mediaId;
                m.nom = (String) record.get("media_name");
                m.datesortie = (Date) record.get("release_date");
                m.description = (String) record.get("media_description");
                m.typemedia = TypeMedia.valueOf((String) record.get("media_type"));

                m.genres = new ArrayList<>();
                m.createurs = new ArrayList<>();
                if(m.typemedia == TypeMedia.jeuvideo)
                    m.jeuvideotypes = new ArrayList<>();
            }

            // Add genre to the media's genre list
            String genreNom = (String) record.get("genre_name");
            if (genreNom != null) {
                // Add the genre only if it isn't already in the list
                if (!m.genres.contains(genreNom)) {
                    m.genres.add(genreNom);
                }
            }

            String jeuVideoType = (String) record.get("jeuvideotype_name");
            if (jeuVideoType != null) {
                if (!m.jeuvideotypes.contains(jeuVideoType)) {
                    m.jeuvideotypes.add(jeuVideoType);
                }
            }

            String createurNom = (String) record.get("creator_name");
            Integer createurId = (Integer) record.get("creator_id");
            if (createurId != null) {
                if (m.createurs.stream().noneMatch(c -> c.id == createurId)) {
                    Createur c = new Createur();
                    c.id = createurId;
                    c.nom = createurNom;
                    m.createurs.add(c);
                }
            }

            //TODO videogametype
            //TODO specific fields like Livre.nbPages
        }

        ctx.render("media.html", Map.of("media", m, "lists", List.of(Favorite, Seen, toBeSeen, Watching, exempleListe)));
    }


    public static void getAll(Context ctx) {

        // get medias with id from db
        String sql = "SELECT * FROM Media;";

        // Execute the raw SQL with bind parameters
        var result = dsl.fetch(sql);

        // Process the result
        result.forEach(record -> {
            System.out.println(record);
        });

        ctx.render("explore.html", Map.of("medias", List.of(exampleMedia, exampleMedia, exampleMedia)));
    }


    public static void getFive(Context ctx) {

        // get medias with id from db
        String sql = "SELECT *\n" +
                "FROM Media\n" +
                "ORDER BY id DESC\n" +
                "LIMIT 5;";

        // Execute the raw SQL with bind parameters
        var result = dsl.fetch(sql);

        // Process the result
        result.forEach(record -> {
            System.out.println(record);
        });

        ctx.render("index.html", Map.of("medias", List.of(exampleMedia, exampleMedia, exampleMedia), "genres", List.of("Genre 1","Genre 2"), "mediatypes", List.of("type 1","type 2"), "jeuvideotypes", List.of("type 1","type 2")));
    }

    public static void getResults(Context ctx) {
        // à voircomment ajouter tous les paramètres
        String mediatype = "'" + ctx.queryParamAsClass("mediatype", String.class).get()+ "'";
        String Genre = "'" + ctx.queryParamAsClass("genre", String.class).get()+ "'";
        String videogametype = "'" + ctx.queryParamAsClass("videogametype", String.class).get()+ "'";
        Boolean color = ctx.queryParamAsClass("color", Boolean.class).get();
        Integer Pagesnb = ctx.queryParamAsClass("pagesnb", Integer.class).get();
        Integer Seasonnb = ctx.queryParamAsClass("seasonnb", Integer.class).get();
        String keyword = "'" + ctx.queryParamAsClass("keyword", String.class).get()+ "'";

        // get medias with id from db

        //quand inutile/optionnel, on met NULL
        String sql = "SELECT\n" +
                "m.id AS media_id,\n" +
                "m.nom AS media_name,\n" +
                "m.dateSortie AS release_date,\n" +
                "m.description AS media_description,\n" +
                "g.nom AS genre_name,\n" +
                "CASE\n" +
                "WHEN l.id IS NOT NULL THEN 'Livre'\n" +
                "WHEN b.id IS NOT NULL THEN 'BD'\n" +
                "WHEN f.id IS NOT NULL THEN 'Film'\n" +
                "WHEN s.id IS NOT NULL THEN 'Série'\n" +
                "WHEN jv.id IS NOT NULL THEN 'Jeu Vidéo'\n" +
                "END AS media_type,\n" +
                "l.nbPage AS book_pages,\n" +
                "b.couleur AS bd_color,\n" +
                "f.duree AS film_duration,\n" +
                "s.nbSaison AS series_seasons,\n" +
                "jt.nom AS game_type\n" +
                "FROM\n" +
                "Media m\n" +
                "LEFT JOIN Media_Genre mg ON m.id = mg.mediaId\n" +
                "LEFT JOIN Genre g ON mg.genrenom = g.nom\n" +
                "LEFT JOIN Papier p ON m.id = p.id\n" +
                "LEFT JOIN Numerique n ON m.id = n.id\n" +
                "LEFT JOIN Livre l ON m.id = l.id\n" +
                "LEFT JOIN BD b ON m.id = b.id\n" +
                "LEFT JOIN Film f ON m.id = f.id\n" +
                "LEFT JOIN Serie s ON m.id = s.id\n" +
                "LEFT JOIN JeuVideo jv ON m.id = jv.id\n" +
                "LEFT JOIN JeuVideo_Type jvt ON jv.id = jvt.jeuVideoId\n" +
                "LEFT JOIN Type jt ON jvt.typenom = jt.nom\n" +
                "WHERE\n" +
                "  -- Condition pour le genre\n" +
                "    (g.nom = COALESCE("+Genre+", g.nom))\n" +
                "  -- Condition pour un type spécifique de média\n" +
                "  AND (\n" +
                "    (COALESCE("+mediatype+", '') = 'Livre' AND l.id IS NOT NULL)\n" +
                "OR (COALESCE("+mediatype+", '') = 'BD' AND b.id IS NOT NULL)\n" +
                "OR (COALESCE("+mediatype+", '') = 'Film' AND f.id IS NOT NULL)\n" +
                "OR (COALESCE("+mediatype+", '') = 'Série' AND s.id IS NOT NULL)\n" +
                "OR (COALESCE("+mediatype+", '') = 'Jeu Vidéo' AND jv.id IS NOT NULL)\n" +
                "OR COALESCE("+mediatype+", '') = ''\n" +
                "    )\n" +
                "  -- Condition pour un jeu vidéo d'un type spécifique\n" +
                "  AND (\n" +
                "    (jv.id IS NOT NULL AND jt.nom = COALESCE("+videogametype+", jt.nom))\n" +
                "OR "+videogametype+" IS NULL\n" +
                "    )\n" +
                "  -- Condition pour une BD en couleur ou non\n" +
                "  AND (\n" +
                "    (b.id IS NOT NULL AND b.couleur = COALESCE("+color+", b.couleur))\n" +
                "OR "+color+" IS NULL\n" +
                "    )\n" +
                "  -- Condition pour un livre avec un nombre de pages minimum\n" +
                "  AND (\n" +
                "    (l.id IS NOT NULL AND l.nbPage >= COALESCE("+Pagesnb+", 0))\n" +
                "OR "+Pagesnb+" IS NULL\n" +
                "    )\n" +
                "  -- Condition pour une série avec un nombre de saisons minimum\n" +
                "  AND (\n" +
                "    (s.id IS NOT NULL AND s.nbSaison >= COALESCE("+Seasonnb+", 0))\n" +
                "OR "+Seasonnb+" IS NULL\n" +
                "    )\n" +
                "  -- Condition pour un mot spécifique dans le titre ou la description\n" +
                "  AND (\n" +
                "    (m.nom ILIKE '%' || COALESCE("+keyword+", '') || '%')\n" +
                "OR (m.description ILIKE '%' || COALESCE("+keyword+", '') || '%')\n" +
                "    );";

        // Execute the raw SQL with bind parameters
        var result = dsl.fetch(sql, Genre, mediatype, videogametype, color, Pagesnb, Seasonnb, keyword);

        // Process the result
        result.forEach(record -> {
            System.out.println(record);
        });


        ctx.render("result.html", Map.of("medias", List.of(exampleMedia, exampleMedia, exampleMedia)));
    }

    public static void addToList(Context ctx){
        Integer id = ctx.queryParamAsClass("id", Integer.class).get();
        String nom = ctx.queryParamAsClass("nom", String.class).get();
        String pseudo = ctx.queryParamAsClass("pseudo", String.class).get();

        // add to db
        String sql = "INSERT INTO Media_List (mediaId, listenom, listePseudo)\n" +
                "VALUES ("+id+", "+nom+", "+pseudo+");";

        var result = dsl.fetch(sql, id, nom);

        // Process the result
        result.forEach(record -> {
            System.out.println(record);
        });
    }

    public static void addComment(Context ctx){
        Commentaire commentaire = new Commentaire();

        commentaire.media.id = ctx.queryParamAsClass("id", Integer.class).get();
        commentaire.utilisateur.nom = ctx.queryParamAsClass("pseudo", String.class).get();
        commentaire.note = ctx.queryParamAsClass("note", Integer.class).get();
        commentaire.texte = ctx.queryParamAsClass("texte", String.class).get();

        // add to db
        String sql = "INSERT INTO Commentaire (pseudo, id, date, note, texte)\n" +
                "VALUES ("+commentaire.media.id+", "+commentaire.utilisateur.nom+", CUREENT_DATE, "+commentaire.note+", "+commentaire.texte+");";

        var result = dsl.fetch(sql, commentaire.media.id, commentaire.utilisateur.nom, commentaire.note, commentaire.texte);

        // Process the result
        result.forEach(record -> {
            System.out.println(record);
        });

    }

    public static void insertMedia(Context ctx){
        Media media = new Media();

        //media.id = ctx.formParamAsClass("id", Integer.class).get();
        media.nom = ctx.formParamAsClass("nom", String.class)
                .check(s -> !s.isBlank(), "name is empty").get();
        media.description = ctx.formParamAsClass("description", String.class)
                .check(s -> !s.isBlank(), "description is empty").get();

        String type = ctx.formParamAsClass("typemedia", String.class).get();

        try{
            media.typemedia = TypeMedia.valueOf(type);
        } catch (IllegalArgumentException e){
            throw new BadRequestResponse("Media type is not valid");
        }

        media.genres = ctx.formParamsAsClass("genres", String.class)
                .check(g -> !g.isEmpty(), "doesn't specify a genre").get();

        // verify that genres exists

        String date = ctx.formParamAsClass("datesortie", String.class).get();

        try{
            media.datesortie = new Date(date);
        } catch (DateTimeParseException e){
            throw new BadRequestResponse("bad date given");
        }

        List<Integer> createursIds = ctx.formParamsAsClass("createurs", Integer.class)
                .check(l -> !l.isEmpty(), "no creators given").get();

        if(createursIds.isEmpty())
            throw new BadRequestResponse("No creators given");

        // verify that createursIds are valid creators

        if(media.typemedia == TypeMedia.jeuvideo)
            media.jeuvideotypes = ctx.formParamsAsClass("jeuvideotypes", String.class)
                    .check(l -> !l.isEmpty(), "no video game type given").get();

        // verify that jeuvideotypes exists

        // create media in DB and get id
        String nom = "'" + media.nom + "'";
        String datesortie = "'" + media.datesortie + "'";
        String description = "'" + media.description + "'";
        String typemedia = "'" + media.typemedia + "'";
        String Genre = "'" + media.genres + "'"; //actuellement un problème de passer plus d'un genre ou type à la fois
        String typejeuvideo = "'" + media.jeuvideotypes + "'";

        Integer nbPages = null;
        Integer nbSeasons = null;
        Integer duree = null;
        Boolean color = null;

        Integer idCreateur = null;

        //mettre nul si cela ne concerne pas le media
        String sql = "SELECT insert_media(\n" +
                "    "+nom+", --nom media\n" +
                "    "+datesortie+",   --date de sortie\n" +
                "    "+description+", --description\n" +
                "    "+typemedia+", --type du media\n" +
                "    "+nbPages+",       -- nb_pages\n" +
                "    "+color+",       -- bd_couleur\n" +
                "    "+duree+",       -- film_duree\n" +
                "    "+nbSeasons+",       -- serie_nbSaisons\n" +
                "    "+typejeuvideo+",      -- jeu_video_type\n" +
                "    "+Genre+", -- genre_nom\n" +
                "    "+idCreateur+"           -- createur_id\n" +
                ");";

        // Execute the raw SQL with bind parameters
        var result = dsl.fetch(sql, media.nom, media.datesortie, media.description, media.typemedia, media.typemedia, media.genres);

        // Process the result
        result.forEach(record -> {
            System.out.println(record);
        });

        ctx.redirect("/media?id=" + media.id);
    }
}
