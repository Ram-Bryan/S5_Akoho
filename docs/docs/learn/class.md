Extends ClassMere: pour les classes qui regroupe
Extends ClassFille: pour les details des classes mere:
Ex: quand on achete un panier (mere), il y a bcq de produit a l'interieur (fille).

Il existe deja des fonctions qui fait les checks dans les classes. Ex: CheckQtFille()

POur creer les sequence on utilise une fonction qui get La sequence dans la base, et on l'appele dans le code, puis on preparePk() et setId(make Pk) pour le prochain PK
setLiaisonFille()  est la liaison avec la classe fille.

## Etat de Validation
Code: framework/apj-core/bean/ClassEtat.java
State Code	Constant	Meaning
1	etatCreer	Created (initial state)
11	etatValider	Validated (processed)
0	etatAnnuler	Cancelled
9	etatCloture	Closed
10	etatRejeter	Rejected
