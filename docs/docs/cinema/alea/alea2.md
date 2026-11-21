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

On peut cumuler les remises par exmple si A a une remise vers B et si C a une remise vers B. Alors on recois les 2 remises de B.

On ne cumule pas les remises qui ont le meme id target et id categorie target, mais on prend le maximum de seuil SEULEMENT. Par exemple, si on a les remises:
si achete 3 places de A --> remise 10% a B. Regle 1
si achete 5 places de A --> remise 30% a B. Regle 2
J'achete 8 places --> 5 > 3. Donc on prend la regle 2. La regle 1 ne s'applique pas. Par contre si j achete 4 places, alors la regle 1 est applique, et la regle 2 n'est pas applique (trivial et evident).