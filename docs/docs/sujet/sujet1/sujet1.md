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










- Bon de Commandes
- Bon de livraison
- Bon de Reception
- Record les morts et les vivants

Step 2: Trajet
A chaque arrive de trajet:
- bon de reception
- record les morts et les vivants

Step 3: Ferme: repartion dans les compartiments/parquets
- choisir le nombre, le sexe.
- choisir l'indice de sante: 1 a 10



Questions: 
- J ai fini de faire l'achat et le bon de commande dans la page de bon de commade fournisseur bondecommande-arbiochem-liste.jsp. J'ai aussi fait le bon de reception/livraison dans bondelivraison-arbiochem-liste.jsp. Maintennat je suis sur la partie ou je suis confus: il y a une section qui gere la ferme, dans cette section il y a reception a l'aeroport receptionaeroport-saisie.jsp. Premiere question: c est quoi la difference entre cette reception et celui du bon de commande que j ia fais au debut. Ensuite dans ce formulaire aussi il y a un input qui demande un lot. Je vais dans saisie lot lot-saisie.jsp et a l'interieur il y a plusieur inputs. Mais il n y a pas l'input logique: le bon de commande et le lot comment ils sont lie ? Parce que la, il n y a auncun lien cote interface ue je peut faire. Genre comment savoir que ce lot c'est ce bon de commande. Enft ingredient = poulet ou n'importe quoi d'autre aussi parcq on peut importer d autres choses. ou est ce que c'est le flow du projet que je ne comprend pas ?




- est ce que la date du bon de commande c'est la date ou on fait le bon de commande, ou c'est la date prevu pour la reception ? Date limite c'est la date de livraison ? 



SELECT column_name, data_type, nullable FROM USER_TAB_COLUMNS WHERE table_name = 'LOT' ORDER BY column_id;