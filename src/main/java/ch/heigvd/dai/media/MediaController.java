package ch.heigvd.dai.media;

import ch.heigvd.dai.DSLGetter;
import ch.heigvd.dai.commentaire.Commentaire;
import ch.heigvd.dai.createur.Createur;
import ch.heigvd.dai.createur.TypeCreateur;
import ch.heigvd.dai.liste.Liste;
import ch.heigvd.dai.utilisateur.Utilisateur;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import io.javalin.http.*;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.Record;

public class MediaController {

    public static DSLContext dsl;

    public MediaController() {

    }

    public static void getOne(Context ctx) {

        Integer id = ctx.queryParamAsClass("id", Integer.class).get();

        // get media with id from DB.

        // Define your raw SQL statement with placeholders
        String sql = """
                SELECT
                    m.id AS media_id,
                    m.nom AS media_name,
                    m.dateSortie AS release_date,
                    m.description AS media_description,
                    g.nom AS genre_name,
                    c.id AS creator_id,
                    c.nom AS creator_name,
                    jt.nom AS jeuvideotype_name,
                    CASE
                WHEN p.id IS NOT NULL THEN 'Papier'
                WHEN n.id IS NOT NULL THEN 'Numérique'
                END AS media_format,
                    CASE
                WHEN l.id IS NOT NULL THEN 'livre'
                WHEN b.id IS NOT NULL THEN 'bd'
                WHEN f.id IS NOT NULL THEN 'film'
                WHEN s.id IS NOT NULL THEN 'serie'
                WHEN jv.id IS NOT NULL THEN 'jeuvideo'
                END AS media_type,
                    l.nbPage AS book_pages,
                    b.couleur AS bd_color,
                    f.duree AS film_duration,
                    s.nbSaison AS series_seasons,
                    jt.nom AS game_type
                FROM
                    Media m
                LEFT JOIN Media_Genre mg ON m.id = mg.mediaId
                LEFT JOIN Genre g ON mg.genreNom = g.nom
                LEFT JOIN Media_Createur mc ON m.id = mc.mediaId
                LEFT JOIN Createur c ON mc.createurId = c.id
                LEFT JOIN Papier p ON m.id = p.id
                LEFT JOIN Numerique n ON m.id = n.id
                LEFT JOIN Livre l ON m.id = l.id
                LEFT JOIN BD b ON m.id = b.id
                LEFT JOIN Film f ON m.id = f.id
                LEFT JOIN Serie s ON m.id = s.id
                LEFT JOIN JeuVideo jv ON m.id = jv.id
                LEFT JOIN JeuVideo_Type jvt ON jv.id = jvt.jeuVideoId
                LEFT JOIN Type jt ON jvt.typenom = jt.nom
                WHERE
                    m.id = 
                """ + id + ";";


        // Execute the raw SQL with bind parameters
        Result<Record> result = dsl.fetch(sql, id);

        Media m = DSLGetter.getOneMedia(result);

        String pseudo = ctx.cookie("utilisateur");

        sql = "SELECT l.pseudo, l.nom\n" +
        "FROM Liste l\n" +
        "WHERE l.pseudo = '" + pseudo + "'\n" +
        "AND NOT EXISTS (\n" +
                "SELECT 1\n" +
                "FROM Media_Liste ml\n" +
                "WHERE ml.listePseudo = l.pseudo\n" +
                "AND ml.listeNom = l.nom\n" +
                "AND ml.idMedia = " + id + "\n" +
        ");";
        // Execute the raw SQL with bind parameters
        result = dsl.fetch(sql, pseudo);

        List<Liste> lists = DSLGetter.getMultipleLists(result);

        sql = "SELECT * FROM Commentaire where id = " + id + ";";
        // Execute the raw SQL with bind parameters
        result = dsl.fetch(sql, pseudo);

        List<Commentaire> coms = DSLGetter.getMultipleCommentaires(result);

        m.commentaires = coms;

        ctx.render("media.html", Map.of("media", m, "lists", lists));
    }


    public static void getAll(Context ctx) {

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
                LEFT JOIN Type jt ON jvt.typenom = jt.nom;
                """;

        // Execute the raw SQL with bind parameters
        var result = dsl.fetch(sql);

        List<Media> medias = DSLGetter.getMultipleMedias(result);

        ctx.render("explore.html", Map.of("medias", medias));
    }


    public static void getFive(Context ctx) {

        // get medias with id from db
        String sql = """
                SELECT
                    m.id AS media_id,
                    m.nom AS media_name,
                    m.dateSortie AS release_date,
                    m.description AS media_description,
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
                ORDER BY m.id DESC
                LIMIT 5;
                """;

        // Execute the raw SQL with bind parameters
        var result = dsl.fetch(sql);

        List<Media> medias = DSLGetter.getMultipleMedias(result);

        sql = "SELECT * FROM Genre";

        result = dsl.fetch(sql);

        List<String> genres = DSLGetter.getMultipleGenres(result);

        sql = "SELECT * FROM Type";
        result = dsl.fetch(sql);

        List<String> types = DSLGetter.getMultipleTypes(result);

        ctx.render("index.html", Map.of(
                "medias", medias,
                "genres", genres,
                "mediatypes", List.of("livre", "film", "jeuvideo", "serie", "bd"),
                "jeuvideotypes", types
        ));
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
        String sql = """
                SELECT
                m.id AS media_id,
                m.nom AS media_name,
                m.dateSortie AS release_date,
                m.description AS media_description,
                g.nom AS genre_name,
                CASE
                WHEN l.id IS NOT NULL THEN 'livre'
                WHEN b.id IS NOT NULL THEN 'bd'
                WHEN f.id IS NOT NULL THEN 'film'
                WHEN s.id IS NOT NULL THEN 'serie'
                WHEN jv.id IS NOT NULL THEN 'jeuvideo'
                END AS media_type,
                l.nbPage AS book_pages,
                b.couleur AS bd_color,
                f.duree AS film_duration,
                s.nbSaison AS series_seasons,
                jt.nom AS game_type
                FROM
                Media m
                LEFT JOIN Media_Genre mg ON m.id = mg.mediaId
                LEFT JOIN Genre g ON mg.genrenom = g.nom
                LEFT JOIN Papier p ON m.id = p.id
                LEFT JOIN Numerique n ON m.id = n.id
                LEFT JOIN Livre l ON m.id = l.id
                LEFT JOIN BD b ON m.id = b.id
                LEFT JOIN Film f ON m.id = f.id
                LEFT JOIN Serie s ON m.id = s.id
                LEFT JOIN JeuVideo jv ON m.id = jv.id
                LEFT JOIN JeuVideo_Type jvt ON jv.id = jvt.jeuVideoId
                LEFT JOIN Type jt ON jvt.typenom = jt.nom
                WHERE
                  -- Condition pour le genre
                """ + 
                "    (g.nom = COALESCE("+Genre+", g.nom))\n" +
                "  -- Condition pour un type spécifique de média\n" +
                "  AND (\n" +
                "    (COALESCE("+mediatype+", '') = 'livre' AND l.id IS NOT NULL)\n" +
                "OR (COALESCE("+mediatype+", '') = 'bd' AND b.id IS NOT NULL)\n" +
                "OR (COALESCE("+mediatype+", '') = 'film' AND f.id IS NOT NULL)\n" +
                "OR (COALESCE("+mediatype+", '') = 'serie' AND s.id IS NOT NULL)\n" +
                "OR (COALESCE("+mediatype+", '') = 'jeuvideo' AND jv.id IS NOT NULL)\n" +
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

        List<Media> medias = DSLGetter.getMultipleMedias(result);

        ctx.render("result.html", Map.of(
                "medias", medias));
    }

    public static void addToList(Context ctx){

        Integer id = ctx.formParamAsClass("id", Integer.class).get();
        String nom = ctx.formParamAsClass("nom", String.class).get();

        String pseudo = ctx.cookie("utilisateur");

        // add to db
        String sql = "INSERT INTO Media_Liste (idmedia, listenom, listePseudo)\n" +
                "VALUES ('"+id+"', '"+nom+"', '"+pseudo+"');";

        var result = dsl.fetch(sql);

        ctx.redirect("/media?id=" + id);
    }

    public static void addComment(Context ctx){
        Commentaire commentaire = new Commentaire();

        String pseudo = ctx.cookie("utilisateur");

        Integer mediaId = ctx.formParamAsClass("id", Integer.class).get();
        commentaire.note = ctx.formParamAsClass("note", Integer.class).get();
        commentaire.texte = ctx.formParamAsClass("texte", String.class).get();

        // add to db
        String sql = "INSERT INTO Commentaire (id, pseudo, date, note, texte)\n" +
                "VALUES ("+mediaId+", '"+ pseudo +"', CURRENT_DATE, "+commentaire.note+", '"+commentaire.texte+"');";

        var result = dsl.fetch(sql);

        // Process the result
        result.forEach(record -> {
            System.out.println(record);
        });

        ctx.redirect("/media?id=" + mediaId);
    }

    public static void insertMedia(Context ctx){
        Media media = new Media();

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

        String date = ctx.formParamAsClass("datesortie", String.class).get();

        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            media.datesortie = formatter.parse(date);
        } catch (ParseException e) {
            throw new BadRequestResponse("bad date given");
        }

        List<Integer> createursIds = ctx.formParamsAsClass("createurs", Integer.class)
                .check(l -> !l.isEmpty(), "no creators given").get();

        if(media.typemedia == TypeMedia.jeuvideo)
            media.jeuvideotypes = ctx.formParamsAsClass("jeuvideotypes", String.class)
                    .check(l -> !l.isEmpty(), "no video game type given").get();

        // create media in DB and get id
        String nom = "'" + media.nom + "'";
        String datesortie = "'" + media.datesortie + "'";
        String description = "'" + media.description + "'";
        String typemedia = "'" + media.typemedia + "'";

        Integer nbPages = ctx.formParamAsClass("pages", Integer.class).get();
        Integer nbSeasons = ctx.formParamAsClass("saison", Integer.class).get();
        Integer duree = ctx.formParamAsClass("duree", Integer.class).get();
        Boolean color = ctx.formParamAsClass("couleur", Boolean.class).get();

        StringBuilder idCreateurs = new StringBuilder("'{");
        for(int i = 0; i < createursIds.size(); i++){
            idCreateurs.append(createursIds.get(i));
            if(i + 1 != createursIds.size())
                idCreateurs.append(", ");
        }
        idCreateurs.append("}'");

        StringBuilder genres = new StringBuilder("'{");
        for(int i = 0; i < media.genres.size(); i++){
            genres.append(media.genres.get(i));
            if(i + 1 != media.genres.size())
                genres.append(", ");
        }
        genres.append("}'");

        StringBuilder typesjeuvideo = new StringBuilder();
        if(media.typemedia == TypeMedia.jeuvideo){

            typesjeuvideo.append("'{");
            for(int i = 0; i < media.jeuvideotypes.size(); i++){
                typesjeuvideo.append(media.jeuvideotypes.get(i));
                if(i + 1 != media.jeuvideotypes.size()) {
                    typesjeuvideo.append(", ");
                }
            }
            typesjeuvideo.append("}'");
        } else {
            typesjeuvideo.append("null");
        }

        //mettre nul si cela ne concerne pas le media
        String sql = "SELECT insert_media(" +
                nom + "::TEXT,           -- nom media\n" +
                datesortie + "::DATE,    -- date de sortie\n" +
                description + "::TEXT,   -- description\n" +
                typemedia + "::TEXT,     -- type du media\n" +
                nbPages + "::INTEGER,    -- nb_pages\n" +
                color + "::BOOLEAN,      -- bd_couleur\n" +
                duree + "::INTEGER,      -- film_duree\n" +
                nbSeasons + "::INTEGER,  -- serie_nbSaisons\n" +
                typesjeuvideo + "::TEXT[],  -- jeu_video_type\n" +
                genres + "::TEXT[],         -- genre_nom\n" +
                idCreateurs + "::INTEGER[]  -- createur_id\n" +
                ");";

        // Execute the raw SQL with bind parameters
        var result = dsl.fetch(sql);

        media.id = result.get(0).get("insert_media", Integer.class);

        ctx.redirect("/media?id=" + media.id);
    }
}
