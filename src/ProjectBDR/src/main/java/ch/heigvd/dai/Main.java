package ch.heigvd.dai;

import io.javalin.Javalin;
import io.javalin.rendering

public class Main {
    public static final int PORT = 8080;

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.fileRenderer(new JavalinThymeleaf());
        });

        app.get("/", ctx -> ctx.result("Hello, world!"));

        app.start(PORT);
    }
}
