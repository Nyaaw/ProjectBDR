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

        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/mediatheque", "postgres", "postgres");
        DSLContext dsl = DSL.using(conn);

        MediaController mediaController = new MediaController(dsl);

        app.get("/", ctx -> ctx.render("index.html"));
        app.get("/explore.html", ctx -> ctx.render("explore.html", Map.of("medias", mediaController.getOne(1))));
        app.get("/insert.html", ctx -> ctx.render("insert.html"));
        app.get("/list.html", ctx -> ctx.render("list.html"));
        app.get("/login.html", ctx -> ctx.render("login.html"));
        app.get("/login_creation.html", ctx -> ctx.render("login_creation.html"));
        app.get("/media.html", ctx -> ctx.render("media.html"));
        app.get("/mylists.html", ctx -> ctx.render("mylists.html"));
        app.get("/result.html", ctx -> ctx.render("result.html"));
        List<Genre> genres = List.of(new Genre("Comedy"), new Genre("Science"));

        app.get("/", ctx -> {
            MediaController.getFive(ctx);
        });
        
        app.get("/explore", ctx -> {
            MediaController.getAll(ctx);
        });
        
        app.get("/insert", ctx -> {
            ctx.render("insert.html", Map.of("genres", genres));
        });
        
        app.get("/list", ctx -> {
            String name = getQueryParam(ctx, "nom");

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

        app.post("/insert/addgenre", ctx -> {
            MediaController.insertMedia(ctx);
        });

        app.post("/media/addtolist", ctx -> {

            // add to List

            ctx.redirect("/media?id=1");

        });

        app.post("/comment", ctx -> {

            String idParam = getQueryParam(ctx, "id");
            Integer id = checkForNumericParam(ctx, idParam);

            // comment

            ctx.redirect("/media?id=" + id);

        });
        
        app.get("/mylists", ctx -> {

            ListeController.getAll(ctx);
        });

        app.get("/result", ctx -> {
            String search = getQueryParam(ctx, "search");

            MediaController.getResults(ctx);
        });

        app.error(404, ctx -> ctx.render("error.html",
                Map.of("errorCode", "404", "errorMsg", "Cette page n'existe pas !")));

        app.error(400, ctx -> ctx.render("error.html",
                Map.of("errorCode", "400", "errorMsg", "Paramètre invalide")));


        app.start(PORT);
    }

    private static String getQueryParam(Context ctx, String param) {
        String value = ctx.queryParam(param);
        if (value == null) {
            throw new BadRequestResponse();
        }

        return value;
    }

    private static Integer checkForNumericParam(Context ctx, String param) {

        Integer num = null;
        try {

            num = Integer.parseInt(param);

        } catch (NumberFormatException e) {
            throw new BadRequestResponse();
        }

        return num;
    }

}
