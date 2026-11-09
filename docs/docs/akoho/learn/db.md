Programe zootechnique: definit l'entretien des lots de poulets (repas,..)

Categorie lot:definit que le lot est vient de Chair ou de Ponte

Originie Lot: C'est quoi le lot mere ?

As unite: les unites (kg, g,...)
Magasin2: la ferme
QualitePoussin: donne faible/mort/conforme
TransfertPouletransferPouletDetail: c'est ici qu'on transfere les poulets de l aeroport vers la ferme ?


Workflow achat:
- insert into DMDACHAT 
- insert into HISTORIQUE
- update DMDACHAT
- insert into HISTORIQUEVALIDATIONDMDACHAT

Faire bon de commande:
- insert into: AS_BONDECOMMANDE
- insert into historique 
- update AS_BONDECOMMANDE (change l'etat)


Faire une reception:
- insert into AS_BONDELIVRAISON
- insert historique
- update AS_BONDELIVRAISON (viser)

Doit faire une entree de stock mais il y a le bug: champ point obligatoire. Le champ point etati deja rempli (dropdownlist) mais on ne pouvias pas manipuler le champs.

Faire la facture fournisseur:
- insert into FACTUREFOURNISSEUR
- insert into historique
- update AS_BONDELIVRAISON
- insert into historique

Facture fournisee apres viser:
- update FACTUREFOURNISSEUR
- insert into compta_ecriture
- insert into historique
- insert into compta_sous_ecriture
- insert into historique
- insert into compta_sous_ecriture (2eme fois pourquoi ?)
- insert into historique
- insert into PREVISION
- update AS_INGREDIENTS whyy ??

FCF001784

Creer un lot:
- insert into LOT
- insert into historique
- Viser --> insert into historique

Reception a l'aeroport:
- insert into receptionPoussinAeroport
- insert into historique
- viser --> update receptionPoussinAeroport

Otrn tsisy ilavan zany cartons male sy femelle
