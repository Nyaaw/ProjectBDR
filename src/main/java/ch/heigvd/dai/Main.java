package ch.heigvd.dai;

import ch.heigvd.dai.media.MediaController;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class Main {
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

        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/mediatheque", "root", "root");
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


        app.start(PORT);
    }
}
