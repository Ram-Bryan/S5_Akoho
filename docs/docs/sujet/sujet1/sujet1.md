Scenario: Aeroport --> Camion --> Repartition dans Compartiment

## Plain text
On fait un bon de commande pour commander des poulets/poussins. Ensuite on les receptionne a l'aeroport. On dit combien sont morts ou perdus en chemin,...Ensuite on les emmene par camion pour aller vers la ferme finale. Dans ce trajet, il se peut que le camion s'arrete souvent. Donc a chaque arret on compte le nb de morts et perdus, a chaque fois donc on fait un bon de commande. Puis arrive a la ferme, on compte une derniere fois et finalemement on repartis dans des compartiments (parquets) les males et les femelles.

## Saisir un bon de commandes fournisseur:
Workflow:
1. page de saisie de demande d'achat:
form:
    - date (libelle "date de saisie de la demande")
    - date limite (libelle "date de la livraison")
    - fournisseur
    - categorie (default to "Poussin")
    - departement (default to "Ferme")
    - provenance (default to "locale")

2. page de saisie de bon de commande:
    - setVisible false remarque, remise, reference proforma, reference 
    - si on passe par la saisie de deande d'achat, seul designation serait a remplir.

3. page de saisie de bon de reception: bondelivraison-arbiochem-saisie.jsp
    - si on passe par la saisie de bon de commande, on enregistre directement.

Ensuite il y a 2 chemins:
4. page de creation de facture definitive fournisseur 
5. page d'insertion de mouvement de stock: !!! IL FAUT QUE LE TYPE DE L"INGREDIENT NE SOIT PAS NULL

6. page de detail de bon de livraison ---> creer un lot

7. page de reception aeroport:
- enleve nb carton male/femelle (setvisible false et default to 0)
- enlever le tableau mulitple en bas (make the link to ReceptionFille existant but just kill the link for UX purpose)
- date de reception c'est la date a laquelle on a recus le lot ? Si oui, prefill by BL of lot:
    - date = date arrive BL
    - qt recus = qt demande en BL
    - heure depart et arrive prefill a maintenant
- ajouter input mort + perdues
- ajouter table lieux pour les lieux de receptions.

Changement DB:
- ajout table lieu avec type de lieu
- ajouter colonne mort et perdues sur la table rececptionaeroport

8. Reception petit a petit par lieu:
- apres reception a l'aeorport (dans la fiche de aeropotrt): ajouter bouton "aller au prochain point" et bouton: "triage dans les batiments"
- page saisie de "aller au prochain point": 
    - form: date, qt recus, mort, perdues, lieu
    - bouton: ajouter --> aller vers une sort de fiche pour valider --> cree un bon de livraison/reception (avoir lien bon de livraison mere - fille pour tracabilite) --> "aller au prochain point" et |triage dans les batiments"
- le bouton  triage dans les batiments va directement dans la page
- page de tracabilite des lieux

9. repartiiton dans les compartiemnt:
form mere:
    - date et lot
    - enleve male et femelle disponible
    - avoir un indicateur de combien sont vivant

form fille:
    - qualite devient un indice de 1 a 10 pour savoir la qualite de sante (peut etre ajoute une colonne indice pour ne pas break la strcuture deja existante de la table et des modele. Ensuite on hide la colonne qulaite actuelle et on remplace par cette colonne)   
