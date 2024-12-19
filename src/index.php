<?php include_once("includes/header.php"); ?>

<main>
  <div id="part_1">
    <img src="img/banner.jpg" alt="">
    <div id="ainfo">
        <h2>Explore our wide range of media</h2>
        <a class="bouton" href="explore.php">Explore</a>
    </div>
  </div>
  <div id="part_2">
    <form action="" method="post">
      <h1>Search</h1>
      <div id="checks">
        <div>
          <h3>Genres</h3>
          <div class="box">
            Genre 1
            <input type="checkbox" id="box" name="genre1" value="Genre 1">
          </div>
          <div class="box">
            Genre 2
            <input type="checkbox" id="box" name="genre2" value="Genre 2">
          </div>
          <div class="box">
            Genre 3
            <input type="checkbox" id="box" name="genre3" value="Genre 3">
          </div>
        </div>
        <div>
          <h3>Media type</h3>
          <div class="box">
            Type 1
            <input type="checkbox" id="box" name="genre3" value="Genre 3">
          </div>
          <div class="box">
              Type 2
            <input type="checkbox" id="box" name="genre3" value="Genre 3">
          </div>
        </div>
        <!-- if video game checked -->
        <div>
          <h3>Video game type</h3>
          <div class="box">
            Type 1
            <input type="checkbox" id="box" name="genre3" value="Genre 3">
          </div>
          <div class="box">
              Type 2
            <input type="checkbox" id="box" name="genre3" value="Genre 3">
          </div>
        </div>
        <!-- if BD checked -->
        <div>
          <h3>BD option</h3>
          <div class="box">
            Color
            <input type="checkbox" id="box" name="genre3" value="color">
          </div>
        </div>
        <!-- if book checked -->
        <div>
          <h3>Book option</h3>
          <p>Number of pages</p>
          <input type="number" name="Number of pages" min="1">
        </div>
        <!-- if film checked -->
        <div>
          <h3>Film option</h3>
          <p>Duration (minutes)</p>
          <input type="number" name="Duration" min="1">
        </div>
        <!-- if serie checked -->
        <div>
          <h3>Serie option</h3>
          <p>Number of season</p>
          <input type="number" name="Number of seasons">
        </div>
        <div>
          <h3>Keyword</h3>
          <input type="text" id="search" name="Search..">
          <button type="submit" class="btn btn-success btn-block mb-3 btn-rounded" value="submit">Search</button>
        </div>
      </div>
    </form>
  </div>
  <div id="part_3">
    <h1>Last added</h1>
    <div class="media_list">
      <div id="List">
        <h3>Name</h3>
        <ul id="List_item">
          <li>Genre 1</li>
          <li>Genre 2</li>
        </ul>
      </div>
      <div>
        <p>Added: dd.mm.yyyy</p>
      </div>
    </div>
  </div>
</main>

<?php include_once("includes/footer.php"); ?>