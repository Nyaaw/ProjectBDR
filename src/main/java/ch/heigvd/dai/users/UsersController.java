package ch.heigvd.dai.users;

import io.javalin.http.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UsersController {
    private final ConcurrentHashMap<Integer, User> users;
    private final AtomicInteger userId = new AtomicInteger(1);

    public UsersController(ConcurrentHashMap<Integer, User> users) {
        this.users = users;
    }

    public void create(Context ctx) {
        User newUser =
                ctx.bodyValidator(User.class)
                        .check(obj -> obj.email != null, "Missing email")
                        .check(obj -> obj.password != null, "Missing password")
                        .get();

        for (User user : users.values()) {
            if (user.email.equalsIgnoreCase(newUser.email)) {
                throw new ConflictResponse();
            }
        }

        User user = new User();

        user.id = userId.getAndIncrement();
        user.email = newUser.email;
        user.password = newUser.password;

        users.put(user.id, user);

        ctx.status(HttpStatus.CREATED);
        ctx.json(user);
    }

    public void getOne(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get();

        User user = users.get(id);

        if (user == null) {
            throw new NotFoundResponse();
        }

        ctx.json(user);
    }


    public void update(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get();

        User updateUser =
                ctx.bodyValidator(User.class)
                        .check(obj -> obj.email != null, "Missing email")
                        .check(obj -> obj.password != null, "Missing password")
                        .get();

        User user = users.get(id);

        if (user == null) {
            throw new NotFoundResponse();
        }

        user.email = updateUser.email;
        user.password = updateUser.password;

        users.put(id, user);

        ctx.json(user);
    }

    public void delete(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get();

        if (!users.containsKey(id)) {
            throw new NotFoundResponse();
        }

        users.remove(id);

        ctx.status(HttpStatus.NO_CONTENT);
    }
}