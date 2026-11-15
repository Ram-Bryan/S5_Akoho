CRUD complet de chaque entite: 
    - categorie
    - film (avec ajout de categorie film)
    - salle: avec range et chaise
    - programme
    - client
    - reservation
    - facture client

Fonctionnalite attendu:
- page de reservation: 
    - on entre le client, le programme voulu
    - on rentre les places attendues: pour les chaises on peut choisir un par un, ou on peut directement choisir combien de chaise et dans quel rane. (fonctionnalite de gestion de chaise)
    - on calcule dynamiquement le prix selon le prix de la seance + le prix supplement
    - on sauvegarde et valider et vise --> transaction:
        - creation de reservation
        - creation de facture client
