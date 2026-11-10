Je t'ai donne la conception de ce projet de cinema qui n'est pas encore implementer dans la codebase actuelle. Et j'ai qq questions de conceptions:
Questions:
- les prix dependent du film lui meme ou de la categorie auquel il est ? Je crois que c'est du film lui meme car ca depend de la popularite de ce film non ?
- le prix selon les range c'est par pourcentage ? Selon la conception que j ai propose, est ce que on devrait avoir une table de categorie range pour globaliser le prix des ranges ? 
- est ce que toutes les salles ont les memes disposition de range et de chaises ? 
- est ce que on a besoin de savoir les chaises aussi, genre avoir une table comme ca:
chaises:
- id
- libelle
- id_range
- est ce que on record le client aussi ? si un client paye pour 10 personnes, est ce que on record les 10 personnes. Genre une table;
client:
- id
- nom
- je pensais ajouter une table facture client. pour un systeme pro, on en a besoin non ?




Restes des questions et reactions:
- le prix est defini par le prix du film ou le cinema ? OU enft le prix defini par le cinema donc le prix est dans film mais c'est le cinema qui le definie. Si on prend l'autre cas, c'est vraiment la concgregation des auteurs et producteurs du film qui set le prix non ? Donc on le garde dans le film hein ? (historique prix)
- Et comment savoir tel ou tel places on deja ete reserve ? Hmmm oui je crois que je comprneds
- je dis que facture ne prend pas PU et montant car on denormalise pour maintenabilite et on peut direct le voir avec la date dans la facture


Questions4:
Je pensais ajouter ces tables:

reservation:
- id
- id_range
- id_client
- nb_billet_achete
- id_programme
- date_reservation

statut_reservation:
- id 
- libelle (reserve, annule)

historique_statut_reservation:
- id
- id_statut_reservation
- id_reservation
- date 


Et aussi facture_client.id_achat = 1:1 ? Une facture peut-elle regrouper plusieurs achats ?


Voici les reponses a tes questions et les ameliorations cote conception en sachant que acaht de billet = reservation (renommage de table):
2. reservation = achat
3. Oui 1:1
4. Oui
5. Pas de chauvement
6. Le prix courant est le prix le plus proche de la date d'aujourdui. Ou de la date donnee si jamais on joue avec les historiques.
7. Pas de devise
8. On joue toujours avec admin. PAs encore de gesion de roles complexe. Donc pas d'implementation de roles dans ce code a implemente.

Si tu est okay, alors je te passe le framework mtn ?




Prepare direct ces inserts:
-  categorillet de range: Standard, VIP. 
- 3 salle avec different disposition de range et categories et de chaises (une salle aura entre 20 a 50 places)