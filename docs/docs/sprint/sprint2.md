Prevision du nombre de poulets dans le calendrier:

On cherche a donne un nombre cumule de nombre de poulet dans une ferme tous les jours. Quand on clique sur un jour on a POUR CHAQUE LOT:
    - le nombre de poulet adulte
    - le nombre de poussin
    - le nombre de mort
    - le nombre total de poulet + poussin + mort dans le lot (on peut decider de filtrer les morts)
- le nombre total de poulet et de poussin dans la ferme
on peut trier par date max et min, par batiment, par lot, par compartiment/parquet.

On fait un suivie journalier de la mort/disparition des poulets 

On sait:
- quand est ce que un poulet est capable de pondre
- quand est ce que un poulet va pondre
- la frequence de pondaison
- l'age ou la mort d'un poulet

On peut manipuler:
- la mort de poulet dans un lot: un poulet mort ne diminue pas le nombre de poulet dans ce lot. Son etat est juste "mort"
- la disparition de poulet dans un lot: des poulets disparues diminue le nombre de poulet a l'interieru du lot

Logic:
Si le nombre de poulet adulte descend ou augmente, automatiquement par prevision, le nombre de prevision change.

Affichage:
S'inspire de l'affichage de vente avec CA journalier: calendrier avec valeur total par jour et quand on clique, on va dans une page qui affiche les details.
