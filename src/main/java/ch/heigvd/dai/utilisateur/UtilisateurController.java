package ch.heigvd.dai.utilisateur;

import org.jooq.DSLContext;

import java.util.Map;

import io.javalin.http.*;

public class UtilisateurController {

    public static DSLContext dsl;

    public static void login(Context ctx) {
        String nom = ctx.formParam("nom");
        String motdepasse = ctx.formParam("motdepasse");

        if (nom == null || motdepasse == null) {
            throw new BadRequestResponse("Missing name or password");
        }

        String query = "SELECT * FROM utilisateur WHERE pseudo = ? AND motdepasse = ?";

        var utilisateurRecord = dsl.fetchOne(query, nom, motdepasse);

        if (utilisateurRecord != null) {
            ctx.cookie("utilisateur", utilisateurRecord.get("pseudo").toString());
            ctx.redirect("/");
        } else {
            throw new BadRequestResponse("Wrong credentials");
        }
    }

    public static void signup(Context ctx) {
        String nom = ctx.formParam("nom");
        String motdepasse = ctx.formParam("motdepasse");

        if (nom == null || motdepasse == null) {
            throw new BadRequestResponse("Missing name or password");
        }

        // Check for existing username
        String checkQuery = "SELECT * FROM utilisateur WHERE pseudo = ?";
        var existingUserRecord = dsl.fetchOne(checkQuery, nom);

        if (existingUserRecord != null) {
            throw new BadRequestResponse("Username already exists");
        }

        // Insert the new user into the database
        String insertQuery = "INSERT INTO utilisateur (pseudo, motdepasse) VALUES (?, ?)";

        var newUserRecord = dsl.fetchOne(insertQuery, nom, motdepasse);

        ctx.status(201).json(Map.of(
                "message", "User created  " + newUserRecord
        ));

        ctx.cookie("utilisateur", nom);
        ctx.redirect("/");
    }

    public static void logout(Context ctx) {
        ctx.removeCookie("utilisateur");
        ctx.redirect("/");
    }

}