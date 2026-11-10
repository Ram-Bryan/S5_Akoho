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
Liste listeCategorieRange = new Liste(
        "idcategorierange",                    // nom du champ formulaire
        new CinemaCategorieRange(),            // objet critère (vide)
        "libelle",                             // colonne à afficher
        "id"                                   // colonne valeur (PK)
    );