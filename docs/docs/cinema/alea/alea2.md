Nouvelle fonctionnalite :

table remise:
id 
id_categorie
seuil_place
pourcentage
id_categorie_target

Regle:
si on achete un nombre x de billet tel que x >= seuil_place pour une categorie donne  sur une meme rervation, alors on reduit de y% le prix sur la categorie target.
Par exemple, si on achete 4 place pour ctegorie A, alors on recoit une remise de 70% sur la categorie B.
id_categorie et id_categorie_target peuvent etre les meme