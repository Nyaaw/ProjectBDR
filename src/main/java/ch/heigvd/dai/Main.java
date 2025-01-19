package ch.heigvd.dai;

import ch.heigvd.dai.liste.ListeController;
import ch.heigvd.dai.media.Media;
import ch.heigvd.dai.media.MediaController;
import ch.heigvd.dai.utilisateur.UtilisateurController;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    record Genre(String nom){}
    public static final int PORT = 8080;

    public static void main(String[] args) throws SQLException {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(new ClassLoaderTemplateResolver(){{
                setPrefix("/templates/");
                setSuffix(".html");
                setTemplateMode(TemplateMode.HTML);
                setCacheable(false);
            }});

            config.fileRenderer(new JavalinThymeleaf(templateEngine));
        });


        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5666/mediatheque", "postgres", "trustno1");
        DSLContext dsl = DSL.using(conn);

        MediaController.dsl = dsl;
        ListeController.dsl = dsl;
        InsertController.dsl = dsl;
        UtilisateurController.dsl = dsl;

        List<Genre> genres = List.of(new Genre("Comedy"), new Genre("Science"));

        app.get("/", MediaController::getFive);
        
        app.get("/explore", MediaController::getAll);

        app.get("/insert", InsertController::RenderInsert);

        app.post("/insert/createcreator", InsertController::CreateCreator);

        app.post("/insert/removecreator", InsertController::RemoveCreator);

        app.post("/insert/addcreator", InsertController::AddCreator);

        app.post("/insert/addgenre", InsertController::AddGenre);

        app.post("/insert/removegenre", InsertController::RemoveGenre);

        app.post("/insert/addvideogametype", InsertController::AddJeuvideotype);

        app.post("/insert/removevideogametype", InsertController::RemoveJeuvideotype);

        app.get("/list", ListeController::getOne);

        app.get("/login", ctx -> {
            ctx.render("login.html");
        });

        app.post("/login", UtilisateurController::login);

        app.get("/login_creation", ctx -> {
            ctx.render("login_creation.html");
        });

        app.post("/login_creation", UtilisateurController::signup);

        app.get("/logout", UtilisateurController::logout);

        app.get("/media", MediaController::getOne);

        app.post("/media", MediaController::insertMedia);

        app.post("/media/addtolist", MediaController::addToList);

        app.post("/media/comment", ctx -> {

        });
        
        app.get("/mylists", ListeController::getAll);

        app.post("/mylists/createlist", ListeController::insertList);

        app.get("/result", MediaController::getResults);

        app.error(404, ctx -> {
            ctx.render("error.html",
                    Map.of("errorCode", "404", "errorMsg", "Cette page n'existe pas !"));
        });

        app.error(400, ctx -> {
            ctx.render("error.html",
                    Map.of("errorCode", "400", "errorMsg", "Paramètre invalide"));
        });


        app.start(PORT);
    }

}
