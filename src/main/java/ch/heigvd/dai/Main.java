package ch.heigvd.dai;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.util.Map;

public class Main {
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

        app.get("/", ctx -> ctx.render("index.html"));
        app.get("/explore.html", ctx -> ctx.render("explore.html"));
        app.get("/insert.html", ctx -> ctx.render("insert.html"));
        app.get("/list.html", ctx -> ctx.render("list.html"));
        app.get("/login.html", ctx -> ctx.render("login.html"));
        app.get("/media.html", ctx -> ctx.render("media.html"));
        app.get("/mylists.html", ctx -> ctx.render("mylists.html"));


        app.start(PORT);
    }
}
