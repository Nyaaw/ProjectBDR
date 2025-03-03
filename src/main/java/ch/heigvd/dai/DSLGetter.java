package ch.heigvd.dai;

import ch.heigvd.dai.commentaire.Commentaire;
import ch.heigvd.dai.createur.Createur;
import ch.heigvd.dai.createur.TypeCreateur;
import ch.heigvd.dai.media.Media;
import ch.heigvd.dai.media.TypeMedia;
import ch.heigvd.dai.liste.Liste;
import ch.heigvd.dai.utilisateur.Utilisateur;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import java.util.*;

public class DSLGetter {

    public static DSLContext dsl;

    public static Media getOneMedia(Result<Record> result){
        // Process the result into a list of media objects, grouped by media id
        Media m = null;

        for (var record : result) {
            Integer mediaId = (Integer) record.get("media_id");

            // If the media doesn't exist in the map, create a new media entry
            if (m == null) {

                m = new Media();
                m.id = mediaId;
                m.nom = (String) record.get("media_name");
                m.datesortie = (Date) record.get("release_date");
                m.description = (String) record.get("media_description");
                m.typemedia = TypeMedia.valueOf((String) record.get("media_type"));


                m.genres = new ArrayList<>();
                m.createurs = new ArrayList<>();
                switch(m.typemedia){
                    case jeuvideo:
                        m.jeuvideotypes = new ArrayList<>();
                        break;
                    case bd:
                        m.couleur = (Boolean) record.get("bd_color");
                        break;
                    case film:
                        m.duree = (Integer) record.get("film_duration");
                        break;
                    case serie:
                        m.saison = (Integer) record.get("series_seasons");
                        break;
                    case livre:
                        m.pages = (Integer) record.get("book_pages");
                        break;
                }

            }

            // Add genre to the media's genre list
            String genreNom = (String) record.get("genre_name");
            if (genreNom != null) {
                // Add the genre only if it isn't already in the list
                if (!m.genres.contains(genreNom)) {
                    m.genres.add(genreNom);
                }
            }

            String jeuVideoType = (String) record.get("jeuvideotype_name");
            if (jeuVideoType != null) {
                if (!m.jeuvideotypes.contains(jeuVideoType)) {
                    m.jeuvideotypes.add(jeuVideoType);
                }
            }

            String createurNom = (String) record.get("creator_name");
            String createurPrenom = (String) record.get("creator_firstname");
            Integer createurId = (Integer) record.get("creator_id");
            if (createurId != null) {
                if (m.createurs.stream().noneMatch(c -> c.id == createurId)) {
                    Createur c = new Createur();
                    c.id = createurId;
                    c.nom = createurNom;
                    c.prenom = createurPrenom;
                    m.createurs.add(c);
                }
            }

            //TODO commentaires
            //TODO specific fields like Livre.nbPages
        }

        return m;
    }

    public static List<Media> getMultipleMedias (Result<Record> result) {
        // Process the result into a list of media objects, grouped by media id
        Map<Integer, Media> mediaMap = new HashMap<>();

        for (var record : result) {
            Integer mediaId = (Integer) record.get("media_id");

            // If the media doesn't exist in the map, create a new media entry
            if (!mediaMap.containsKey(mediaId)) {
                mediaMap.put(mediaId, new Media());
                mediaMap.get(mediaId).id = mediaId;
                mediaMap.get(mediaId).nom = (String) record.get("media_name");
                mediaMap.get(mediaId).datesortie = (Date) record.get("release_date");
                mediaMap.get(mediaId).typemedia = TypeMedia.valueOf((String) record.get("media_type"));
                mediaMap.get(mediaId).genres = new ArrayList<String>();
            }

            // Add genre to the media's genre list
            String genreNom = (String) record.get("genre_name");
            if (genreNom != null) {
                List<String> genres = (List<String>) mediaMap.get(mediaId).genres;
                // Add the genre only if it isn't already in the list
                if (!genres.contains(genreNom)) {
                    genres.add(genreNom);
                }
            }
        }

        // Convert the map values to a list to pass to the view
        List<Media> medias = new ArrayList<>(mediaMap.values());

        return medias;
    }

    public static List<String> getMultipleGenres (Result<Record> result){
        List<String> genres = new ArrayList<>();

        for (var record : result) {
            genres.add((String) record.get("nom"));
        }

        return genres;
    }

    public static List<Createur> getMultipleCreateurs (Result<Record> result){

        // Process the result into a list of crea objects, grouped by crea id
        Map<Integer, Createur> creaMap = new HashMap<>();

        for (var record : result) {
            Integer creaId = (Integer) record.get("id");

            // If the crea doesn't exist in the map, create a new crea entry
            if (!creaMap.containsKey(creaId)) {
                creaMap.put(creaId, new Createur());
                creaMap.get(creaId).id = creaId;
                creaMap.get(creaId).nom = (String) record.get("nom");
                creaMap.get(creaId).typecreateur = TypeCreateur.valueOf((String) record.get("type"));
                if (creaMap.get(creaId).typecreateur == TypeCreateur.personne)
                    creaMap.get(creaId).prenom = (String) record.get("prenom");
            }


        }

        // Convert the map values to a list to pass to the view
        List<Createur> createurs = new ArrayList<>(creaMap.values());

        return createurs;
    }

    public static List<Liste> getMultipleLists (Result<Record> result){

        // Process the result into a list of crea objects, grouped by crea id
        Map<String, Liste> creaMap = new HashMap<>();

        for (var record : result) {
            String listId = (String) record.get("nom");

            // If the crea doesn't exist in the map, create a new crea entry
            if (!creaMap.containsKey(listId)) {
                creaMap.put(listId, new Liste());
                creaMap.get(listId).nom = listId;
                Utilisateur user = new Utilisateur();
                user.nom = (String) record.get("pseudo");
                creaMap.get(listId).utilisateur = user;
            }

        }

        // Convert the map values to a list to pass to the view
        List<Liste> lists = new ArrayList<>(creaMap.values());

        return lists;
    }

    public static List<String> getMultipleTypes(Result<Record> result){

        List<String> types = new ArrayList<>();

        for (var record : result) {
            types.add((String) record.get("nom"));
        }

        return types;
    }

    public static List<Commentaire> getMultipleCommentaires(Result<Record> result){

        List<Commentaire> coms = new ArrayList<>();

        for(var record : result){
            Commentaire c = new Commentaire();

            c.texte = (String) record.get("texte");
            c.note = (Integer) record.get("note");
            c.date = (Date) record.get("date");
            c.utilisateur = new Utilisateur();
            c.utilisateur.nom = (String) record.get("pseudo");

            coms.add(c);
        }

        return coms;
    }
}
