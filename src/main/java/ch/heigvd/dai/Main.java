package ch.heigvd.dai;

import io.javalin.Javalin;
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
            ctx.render("list.html", Map.of("genres", genres));
        });
        
        app.get("/login", ctx -> {
            ctx.render("login.html");
        });
        
        app.get("/login_creation", ctx -> {
            ctx.render("login_creation.html");
        });
        
        app.get("/media", ctx -> {
            ctx.render("media.html", Map.of("genres", genres));
        });
        
        app.get("/mylists", ctx -> {
            ctx.render("mylists.html", Map.of("genres", genres));
        });
        
        app.get("/result", ctx -> {
            ctx.render("result.html", Map.of("genres", genres));
        });
        


        app.start(PORT);
    }
}
