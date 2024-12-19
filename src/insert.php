<h1>New Media</h1>
<form>
    <label for="name">Name:</label>
    <input type="text" id="name" name="name">

    <br>

    <label for="description">Description:</label>
    <textarea name="description" id="description"></textarea>

    <br>

    <label for="genre">Add genre:</label>
    <select id="genre" name="genre">
        <option value="genre1">Genre 1</option>
        <option value="genre2">Genre 2</option>
        <!-- tous les genres ici -->
    </select>
    <input type="button" value="Add">
    <br>
    <div>
        <h2>Genres: </h2>
        <ul>
            <li>Horror <btn>remove</btn></li>
            <li>Comedy <btn>remove</btn></li>
        </ul>
    </div>

    <br>

    <form>
        <label for="creator">Select creator:</label>
        <select id="creator" name="creator">
            <option value="0">John Cena</option>
            <option value="1">Mario</option>
            <!-- tous les créateurs ici -->
        </select>
        <br>
        <input type="button" value="Add">
    </form>
    
    <br>

    <form>
        <label for="creatorName">Add creator:</label>
        <input type="text" id="creatorName" name="creatorName">
        <br>
        <label for="creatorType">type de créateur:</label>
        <input type="radio" name="creatorType" id="creatorTypePerson"> Personne
        <input type="radio" name="creatorType" id="creatorTypeGroup"> Groupe
        <br>
        <input type="button" value="Add">
    </form>

    <br>
    <div>
        <h2>Creators: </h2>
        <ul>
            <li>Luigi <btn>remove</btn></li>
            <li>Zelda <btn>remove</btn></li>
            <li>Studio Ghibli <btn>remove</btn></li>
        </ul>
    </div>

    <label for="type">Type de média:</label>
    <select id="type" name="type">
        <option value="film">Film</option>
        <option value="serie">Série</option>
        <option value="jeuvideo">Jeu-Vidéo</option>
        <option value="livre">Livre</option>
        <option value="bd">BD</option>
    </select>


    <br>

    <!-- SEULEMENT POUR JEUX VIDEOS -->

    <label for="videoGameType">Add video game type</label>
    <select id="videoGameType" name="videoGameType">
        <option value="type1">Type 1</option>
        <option value="type2">Type 2</option>
        <!-- tous les genres ici -->
    </select>
    <input type="button" value="Add">

    <br>
    <div>
        <h2>Video game types: </h2>
        <ul>
            <li>FPS <btn>remove</btn></li>
            <li>Multiplayer <btn>remove</btn></li>
        </ul>
    </div>

    <br>

    <input type="button" value="Create !">
</form> 