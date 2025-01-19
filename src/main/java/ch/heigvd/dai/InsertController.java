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

    static List<Createur> creatorsExample = new ArrayList<>();
    static List<String> genresExamples = List.of("Horror", "Comedy");
    static List<String> jeuvideotypesExamples = List.of("Rogue Like", "Platformer");

    public static DSLContext dsl;

    static{
        Createur crea1 = new Createur();
        crea1.id = 1;
        crea1.typecreateur = TypeCreateur.personne;
        crea1.nom = "Hempuli";

        Createur crea2 = new Createur();
        crea2.id = 2;
        crea2.typecreateur = TypeCreateur.groupe;
        crea2.nom = "Nolla Games";

        creatorsExample.add(crea1);
        creatorsExample.add(crea2);
    }

    public static void RenderInsert(Context ctx){

        // get all creators from db
        List<Createur> allCreators = creatorsExample;

        // get all genres from db
        List<String> allGenres = genresExamples;

        // get all videogametypes
        List<String> allJeuvideotypes = jeuvideotypesExamples;


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

        // add creator in db and get id
        c.id = 3;
        creatorsExample.add(c);

        Set<Integer> insertCreators = ctx.sessionAttribute("createurs");

        if(insertCreators == null){
            insertCreators = Set.of(3);
        } else {
            insertCreators.add(3);
        }

        ctx.sessionAttribute("createurs", insertCreators);

        ctx.redirect("/insert");
    }

    public static void AddCreator(Context ctx){

        Integer idCreator = ctx.formParamAsClass("id", Integer.class).get();

        // get creator from db with id

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
            insertCreators = new HashSet<Integer>();
        } else {
            insertCreators.remove(idCreator);
        }

        ctx.sessionAttribute("createurs", insertCreators);

        ctx.redirect("/insert");
    }

    public static void AddGenre(Context ctx){
      
        String genre = ctx.formParamAsClass("genre", String.class).get();

        // get creator from db with id

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

        // get creator from db with id

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

        // get creator from db with id

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

        // get creator from db with id

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

