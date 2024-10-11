# Projet BDR
Groupe 9 : Lisa Gorgerat - Alex Berberat - Pierric Ripoll

## Description brève
Nous prévoyons de faire une application web pour maintenir une liste personnelle de médias artistiques
et la possibilité de classer ces médias, de mettres une note et des commentaires, et éventuellement de
comparer sa liste avec d'autres utilisateurs.

Les médias gérés sont : 
- Films
- Livres
- Bande-dessinées
- Jeux vidéos
- Séries

## Données des médias
### Média
Un média de manière générale sera caractérisé par :

- Son nom
- Date de sortie
- Classification d'âge
- Genre (par exemple: action, hoerreur, policier, etc.)
- Le créateur (réalisateur, écrivain, studio de développement) qui est un concept à part entière

À ces caractéristiques générales s'ajoutent des caractéristique spécifiques aux différents types de médias: 

### Films
- Durée

### Livres
- Format de livre (par exemple: receuil de poèmes, roman, nouvelle, essai, pièce de théâtre)

### Bande-dessinées
Aucune caractéristique supplémentaire

### Jeu-vidéo
- Type de jeu (par exemple: Platformer, FPS, RPG, Course, etc.)

### Séries
- Nombre d'épisodes
- Nombre de saisons

### Donnée des médias relatives à un utilisateur
- Note d'un média
- Favori (ou pas)
- Commentaires

### Entité créateur
- Nom (par exemple: Studio Ghibli, Ubisoft, Quentin Tarantino)

## Fonctionnalités
### Compte utilisateur
Possibilité de faire un compte utilisateur en donnant un pseudonyme, et un mot de passe.
À noter que dans notre application, il n'y aura pas d'utilisateurs avec des droits plus élevés que d'autres.

### Ajout d'un média à la liste
Un utilisateur pourra chercher dans la base de donnée un média pour l'ajouter à sa liste.
Il pourra choisir si ce média est un favori, et lui mettre une note.
La date d'ajout à la liste est conservée.
Après-coup, l'utilisateur pourra modifier sa note ou le statut de favori d'un élément de sa liste.

### Ajout d'un média dans la base
Si le média n'existe pas encore, l'utilisateur pourra créer un nouveau média ( qui sera ajouté à la
base de données pour tout le monde).
Les médias pourront aussi être modifiés ou supprimés de la base de donnée.

### Commentaire
Un utilisateur pourra mettre un commentaire sur les médias qui sont dans sa liste uniquement.
Les commentaires sont caractérisé par leur contenu (texte) et la date. Ils pourront être
supprimés uniquement par l'utilisateur qui a écrit le commentaire. Ils ne pourront pas être
modifiés.

### Fonctionnalités supplémentaires qui seront ajoutées si on a le temps
#### Comparaison
Comparaison entre deux utilisateurs pour savoir quels sont les films en commun et leur
différence de note.

#### Recommendations
Système de recommendation basé sur les listes et les notes d'autres utilisateurs 

## Technologies utilisées: 
- PHP sans framework
- PostgreSQL
- HTML
- CSS avec feuille d'aide type bootstrap
