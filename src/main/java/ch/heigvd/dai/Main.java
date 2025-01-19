package ch.heigvd.dai;

import ch.heigvd.dai.liste.ListeController;
import ch.heigvd.dai.media.Media;
import ch.heigvd.dai.media.MediaController;
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
import java.util.List;
import java.util.Map;

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

        List<Genre> genres = List.of(new Genre("Comedy"), new Genre("Science"));

        app.get("/", ctx -> {
            MediaController.getFive(ctx);
        });
        
        app.get("/explore", ctx -> {
            MediaController.getAll(ctx);
        });

        app.get("/insert", ctx -> {
           InsertController.RenderInsert(ctx);
        });

        app.post("/insert/createcreator", ctx -> {
            InsertController.CreateCreator(ctx);
        });

        app.post("/insert/removecreator", ctx -> {
            InsertController.RemoveCreator(ctx);
        });

        app.post("/insert/addcreator", ctx -> {
            InsertController.AddCreator(ctx);
        });

        app.post("/insert/addgenre", ctx -> {
            InsertController.AddGenre(ctx);
        });

        app.post("/insert/removegenre", ctx -> {
            InsertController.RemoveGenre(ctx);
        });

        app.post("/insert/addvideogametype", ctx -> {
            InsertController.AddJeuvideotype(ctx);
        });

        app.post("/insert/removevideogametype", ctx -> {
            InsertController.RemoveJeuvideotype(ctx);
        });

        app.get("/list", ctx -> {
            ListeController.getOne(ctx);
        });

        app.get("/login", ctx -> {
            ctx.render("login.html");
        });

        app.get("/login_creation", ctx -> {
            ctx.render("login_creation.html");
        });

        app.get("/media", ctx -> {
            MediaController.getOne(ctx);
        });

        app.post("/media", ctx -> {
            MediaController.insertMedia(ctx);
        });

        app.post("/media/addtolist", ctx -> {

            // add to List

            ctx.redirect("/media?id=1");
        });

        app.post("/comment", ctx -> {

        });
        
        app.get("/mylists", ctx -> {

            ListeController.getAll(ctx);
        });

        app.post("/mylists/createlist", ctx -> {

            ListeController.insertList(ctx);
        });

        app.get("/result", ctx -> {
            MediaController.getResults(ctx);
        });

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
