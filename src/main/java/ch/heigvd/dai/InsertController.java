package ch.heigvd.dai;

import ch.heigvd.dai.createur.Createur;
import ch.heigvd.dai.createur.TypeCreateur;
import ch.heigvd.dai.media.Media;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.Cookie;
import org.jooq.DSLContext;

import java.util.*;

public class InsertController {

    public static DSLContext dsl;

    public static void RenderInsert(Context ctx){

        // get all creators from db
        String sql = """
                Select 
                    c.nom,
                    c.id,
                    p.prenom,
                CASE
                WHEN p.id IS NOT NULL THEN 'personne'
                WHEN g.id IS NOT NULL THEN 'groupe'
                END AS type
                FROM Createur c
                LEFT JOIN Personne p
                ON c.id = p.id
                LEFT JOIN Groupe g
                ON c.id = g.id
                """;

        var result = dsl.fetch(sql);

        List<Createur> allCreators = DSLGetter.getMultipleCreateurs(result);

        sql = "SELECT * FROM Genre;";

        result = dsl.fetch(sql);

        List<String> allGenres = DSLGetter.getMultipleGenres(result);

        sql = "SELECT * FROM Type;";

        result = dsl.fetch(sql);

        List<String> allJeuvideotypes = DSLGetter.getMultipleTypes(result);

        Set<Integer> set = ctx.sessionAttribute("createurs");

        if(set == null){
            set = new HashSet<>();

            ctx.sessionAttribute("createurs", set);
            ctx.sessionAttribute("genres", new HashSet<String>());
            ctx.sessionAttribute("jeuvideotypes", new HashSet<String>());
        }

        // load selected creators infos from their ids
        List<Createur> createurs = new ArrayList<>();

        for (Integer id : set) {
            createurs.add(allCreators.stream().filter(c -> c.id == id).findFirst().get());
        }

        var session = ctx.sessionAttributeMap();

        ctx.render("insert.html", Map.of(
                "session", session,
                "selectedCreators", createurs,
                "allCreators", allCreators,
                "allGenres", allGenres,
                "allJeuvideotypes", allJeuvideotypes
        ));
    }

    public static void CreateCreator(Context ctx){
        Createur c = new Createur();

        c.nom = ctx.formParamAsClass("nom", String.class).get();
        c.prenom = ctx.formParamAsClass("prenom", String.class).get();

        String typecreateur = ctx.formParamAsClass("typecreateur", String.class).get();

        try{
            c.typecreateur = TypeCreateur.valueOf(typecreateur);
        } catch (IllegalArgumentException e) {
            throw new BadRequestResponse("type createur is invalid");
        }

        String sql = " WITH inserted_createur AS ( " +
                "INSERT INTO createur (nom) VALUES ('" + c.nom + "') RETURNING id)";


        if(c.typecreateur == TypeCreateur.personne){
            sql += " INSERT INTO personne (id, prenom)\n" +
                    "SELECT id, '" + (c.prenom == null ? "" : c.prenom) + "'\n" +
                    "FROM inserted_createur\n" +
                    "RETURNING id;";
        } else {
            sql += " INSERT INTO groupe (id)\n" +
                    "SELECT id\n" +
                    "FROM inserted_createur\n" +
                    "RETURNING id;";
        }

        var result = dsl.fetch(sql);
        Integer id = result.get(0).get("id", Integer.class);

        Set<Integer> insertCreators = ctx.sessionAttribute("createurs");

        if(insertCreators == null){
            insertCreators = Set.of(id);
        } else {
            insertCreators.add(id);
        }

        ctx.sessionAttribute("createurs", insertCreators);

        ctx.redirect("/insert");
    }

    public static void AddCreator(Context ctx){

        Integer idCreator = ctx.formParamAsClass("id", Integer.class).get();

        Set<Integer> insertCreators = ctx.sessionAttribute("createurs");

        if(insertCreators == null){
            insertCreators = Set.of(idCreator);
        } else {
            insertCreators.add(idCreator);
        }

        ctx.sessionAttribute("createurs", insertCreators);

        ctx.redirect("/insert");
    }

    public static void RemoveCreator(Context ctx){
        Integer idCreator = ctx.formParamAsClass("id", Integer.class).get();

        HashSet<Integer> insertCreators = ctx.sessionAttribute("createurs");

        if(insertCreators == null){
            insertCreators = new HashSet<>();
        } else {
            insertCreators.remove(idCreator);
        }

        ctx.sessionAttribute("createurs", insertCreators);

        ctx.redirect("/insert");
    }

    public static void AddGenre(Context ctx){
      
        String genre = ctx.formParamAsClass("genre", String.class).get();

        Set<String> insertGenres = ctx.sessionAttribute("genres");

        if(insertGenres == null){
            insertGenres = Set.of(genre);
        } else {
            insertGenres.add(genre);
        }

        ctx.sessionAttribute("genres", insertGenres);

        ctx.redirect("/insert");
    }
    
    public static void RemoveGenre(Context ctx){
      
        String genre = ctx.formParamAsClass("genre", String.class).get();

        HashSet<String> insertGenres = ctx.sessionAttribute("genres");

        if(insertGenres == null){
            insertGenres = new HashSet<>();
        } else {
            insertGenres.remove(genre);
        }

        ctx.sessionAttribute("genres", insertGenres);

        ctx.redirect("/insert");
    } 
    
    public static void AddJeuvideotype(Context ctx){
      
        String jeuvideotype = ctx.formParamAsClass("jeuvideotype", String.class).get();

        Set<String> insertjeuvideotypes = ctx.sessionAttribute("jeuvideotypes");

        if(insertjeuvideotypes == null){
            insertjeuvideotypes = Set.of(jeuvideotype);
        } else {
            insertjeuvideotypes.add(jeuvideotype);
        }

        ctx.sessionAttribute("jeuvideotypes", insertjeuvideotypes);

        ctx.redirect("/insert");
    }
    
    public static void RemoveJeuvideotype(Context ctx){
      
        String jeuvideotype = ctx.formParamAsClass("jeuvideotype", String.class).get();

        HashSet<String> insertjeuvideotypes = ctx.sessionAttribute("jeuvideotypes");

        if(insertjeuvideotypes == null){
            insertjeuvideotypes = new HashSet<>();
        } else {
            insertjeuvideotypes.remove(jeuvideotype);
        }

        ctx.sessionAttribute("jeuvideotypes", insertjeuvideotypes);

        ctx.redirect("/insert");
    }
}

