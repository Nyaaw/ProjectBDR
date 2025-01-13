package ch.heigvd.dai.media;

import org.jooq.DSLContext;

public class MediaController {
    private DSLContext dsl;

    public MediaController(DSLContext dsl){
        this.dsl = dsl;
    }

    public Media getOne(int id) {

        // Define your raw SQL statement with placeholders
        String sql = "SELECT * FROM Media WHERE id = ?";

        // Execute the raw SQL with bind parameters
        var result = dsl.fetch(sql, id, "exampleName");

        // Process the result
        result.forEach(record -> {
            System.out.println(record);
        });

        return null;
    }

    public insertMedia(Media media){

    }
}
