Createur(<u>id</u>, nom)

Groupe(<u>id</u>)  
Groupe.id references Createur.id

Personne(<u>id</u>, prenom)  
Personne.id references Createur.id

Genre(<u>nom</u>)

Media(<u>id</u>, nom, dateSortie, description)

Media_Createur(<u>idMedia, idCreateur</u>)
Media_Createur.idMedia references Media.id
Media_Createur.idCreateur references Createur.id

Media_Genre(<u>idMedia, nomGenre</u>)
Media_Genre.idMedia references Media.id
Media_Genre.nomGenre references Genre.nom

Papier(<u>id</u>)  
Papier.id references Media.id

Livre(<u>id</u>, nbPages)  
Livre.id references Papier.id

BD(<u>id</u>, couleur)  
BD.id references Papier.id

Numerique(<u>id</u>)  
Numerique.id references Media.id

Film(<u>id</u>, duree)  
Film.id references Numerique.id

Serie(<u>id</u>, nbSaison)  
Serie.id references Numerique.id

Type(<u>nom</u>)

JeuVideo(<u>id</u>)  
JeuVideo.id references Numerique.id

JeuVideo_Type(<u>jeuVideoId, typeNom</u>)
JeuVideo_Type.jeuVideoId references JeuVideo.id
JeuVideo_Type.typeNom references Type.nom

Utilisateur(<u>pseudo</u>, motDePasse)

Liste(<u>pseudo, nom</u>, dateCreation, mediaId)  
Liste.pseudo references Utilisateur.pseudo
Liste.mediaId references Media.id

Media_Liste(<u>idMedia, pseudoListe, nomListe</u>)
Media_Liste.idMedia references Media.id
(Media_Liste.pseudoListe, Media_Liste) references (Liste.pseudo, Liste.nom)

Commentaire(<u>pseudo, id</u>, date, note, text)  
Commentaire.pseudo references Utilisateur.pseudo  
Commentaire.id references Media.id