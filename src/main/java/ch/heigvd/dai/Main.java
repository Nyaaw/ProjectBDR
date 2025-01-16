package ch.heigvd.dai;

import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.List;
import java.util.Map;

public class Main {

    record Genre(String nom){}
    public static final int PORT = 8080;

    public static void main(String[] args) {
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
            ctx.render("index.html", Map.of("genres", genres));
        });
        
        app.get("/explore", ctx -> {
            ctx.render("explore.html", Map.of("genres", genres));
        });
        
        app.get("/insert", ctx -> {
            ctx.render("insert.html", Map.of("genres", genres));
        });
        
        app.get("/list", ctx -> {
            String name = getQueryParam(ctx, "name");

            ctx.render("list.html", Map.of("genres", genres));
        });
        
        app.get("/login", ctx -> {
            ctx.render("login.html");
        });
        
        app.get("/login_creation", ctx -> {
            ctx.render("login_creation.html");
        });
        
        app.get("/media", ctx -> {
            String idParam = getQueryParam(ctx, "id");
            Integer id = checkForNumericParam(ctx, idParam);

            ctx.render("media.html", Map.of("genres", genres));
        });

        app.post("/media/addtolist", ctx -> {

            String idParam = getQueryParam(ctx, "id");
            Integer id = checkForNumericParam(ctx, idParam);

            // add to List

            ctx.redirect("/media?id=" + id);

        });

        app.post("/comment", ctx -> {

            String idParam = getQueryParam(ctx, "id");
            Integer id = checkForNumericParam(ctx, idParam);

            // comment

            ctx.redirect("/media?id=" + id);

        });
        
        app.get("/mylists", ctx -> {
            String idParam = getQueryParam(ctx, "id");
            Integer id = checkForNumericParam(ctx, idParam);

            ctx.render("mylists.html", Map.of("genres", genres));
        });

        app.get("/result", ctx -> {
            String search = getQueryParam(ctx, "search");

            ctx.render("result.html", Map.of("genres", genres));
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
