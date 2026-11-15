# Faire des requetes:
// Récupérer tous les statuts
CinemaStatutReservation crit = new CinemaStatutReservation();
CinemaStatutReservation[] statuts = 
    (CinemaStatutReservation[]) CGenUtil.rechercher(crit, null, null, c, "");

// Récupérer par ID (unitaire)
CinemaStatutReservation s = new CinemaStatutReservation();
s = (CinemaStatutReservation) s.getById("CSTR000001", null, c);

// Récupérer avec filtre
CinemaFilm crit = new CinemaFilm();
CinemaFilm[] films = 
    (CinemaFilm[]) CGenUtil.rechercher(crit, null, null, c, " AND duree > 120 ");

# Faire un dropdown:
<%
    // 1. Create a Liste object: (fieldName, ClassToFetch, DisplayField, ValueField)
    Liste listeCategorie = new Liste("idcategorie", new CinemaCategorie(), "libelle", "id");
    
    // 2. Tell the form to replace the text input with this dropdown
    pr.getFormu().changerEnChamp(new Liste[]{ listeCategorie });
%>

"idcategorie" : The name of the field in the database and in listeCrt.

new CinemaCategorie() : The model used to fetch the data. The framework does a SELECT * FROM cinema_categorie.

"libelle" : What the user sees in the dropdown (e.g., "Action", "Drame").

"id" : What is sent to the server when the form is submitted (e.g., "CCAT000001").

## Les tableau en jsp:
String[] listeCrt  = {"id", "titre", "duree"};
String[] listeInt  = {"duree"};
String[] libEntete = {"id", "titre", "duree"};

pr.getTableau().setLibelleAffiche(new String[]{"Id", "Titre", "Durée (min)"});


listeCrt = pour les filtres
listeInt = deviendra 2 champs min max
libEntete = les colonnes a affiche sur la table
setLibelleAffiche = le noms des colonnes (meme si libEntete et setLibelleAffiche est different, ca affiche toujours, ca c'est libentete qui gere les donne)

## colonne avec un lien:

pr.getTableau().setLien(new String[]{ pr.getLien() + "?but=cinema/film/film-fiche.jsp" });
    pr.getTableau().setColonneLien(new String[]{"id"});
    pr.getTableau().setAttLien(new String[]{"id"});

Doit matcher par position dans le tableau:
setLien = le lien
setClonneLIen = quel colonne a le lien
setAttlien = quel parametre envoyer dans le lien

## Page constule:
getChampByName() = Prend le champ. Pour PAgeConsulte et non PageRecherche (getChamp pour PageRecherche)

pc.setOrdre(new String[]{"id", "titre", "description", "duree", "realisateur"});

donne l'ordre de l'affichage des infos.