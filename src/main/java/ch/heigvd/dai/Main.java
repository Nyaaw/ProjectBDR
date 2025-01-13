package ch.heigvd.dai;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.util.Map;
import ch.heigvd.dai.auth.AuthController;
import ch.heigvd.dai.users.User;
import ch.heigvd.dai.users.UsersController;
import java.util.concurrent.ConcurrentHashMap;

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

        // This will serve as our database
        ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

        // Controllers
        AuthController authController = new AuthController(users);
        UsersController usersController = new UsersController(users);

        app.get("/", ctx -> ctx.render("index.html"));
        app.get("/explore.html", ctx -> ctx.render("explore.html"));
        app.get("/insert.html", ctx -> ctx.render("insert.html"));
        app.get("/list.html", ctx -> ctx.render("list.html"));
        app.get("/login.html", ctx -> ctx.render("login.html", authController::login));
        app.get("/login_creation.html", ctx -> ctx.render("login_creation.html"));
        app.get("/media.html", ctx -> ctx.render("media.html"));
        app.get("/mylists.html", ctx -> ctx.render("mylists.html"));
        app.get("/result.html", ctx -> ctx.render("result.html"));


        app.start(PORT);
    }
}
