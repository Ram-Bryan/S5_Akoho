# Prevision du nombre de poulets dans le calendrier:

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




Reponses 1 :
1. ON a date min et date max (nullable chaqun pour avoir tous jusqu au debut). Ensuite on regarde les poulet exsitatns dans cette tranche de date. ON inclus les evenemnts du genre: des oeufs ont eclos, des poulets ont pondus des oeufs et ces derniers ont eclos (nouveau lot), des poulets sont morts (surveiller pour les femelles car c'est elles qui pondent et qui ajoute dans le truc),...Donc c'est tres dynamique.

2. Male et femelle inclus. ON ne compte pas les oeufs encore oeufs. 

3. OUi inclus les oeufs qui vont eclores (et non ceux qui sont encore des oeufs).

4. 1 lot = 1 triage. Mais 1 lot = n triage details, car on repartis les nombres de ceux lots dasn des comparteimnts/parquets details.

5. Oui pour le a

6. Oui pour ce que tu dis. Cette feature morts/vivant inclus est globale au calendrier et non pour un jour precis

7. Pour le suivi jouralier, il y a deja une entite qui fait ca mais je pense qu on devrais la modifier selon nos features

8. Point de depart du comptage, ca revient a la date de creation du bon de commande enft. Car c'est la que on a recu les poulets et ils ont surements des ages. Ca c'est l'age, sinon le comptage commence a la date ou on les tris dans les parquets

9. Enft je n'aime pas jouer avec les sql (peut etre faire des view avec join seulemet et acceptable mais pas de business logic lourds dans le sql.). On va tout faire cote java


Reponses 2:

Enft on doit avoir une race, et dans cette race on doit avoir un taux d'eclosion combine deviendra femelle et l'eclosion se fait automatique et apres maturite cela aussi vont donee des oeufs qui vont aussi eclore etc,...et bien sur il y a ceux qui sont vieux qui ne vont plus pondre apres n jours. Tous ca c'est defini dans la race.  

Donc oui Option C generation multiples, avec hatching automatique. On ne creera pas le lot automatiquement directement, reelement dans la DB. C'est juste une prevision pour voir ce qui se passera dans le futur.

Donne moi les query necessaire si tu veux vraiment voir programmewootochenquie. TU me guides pour pouoir implementer ca. 
Donne moi les query necessaire pour verifier des choses etc...

Les tables qui peuvent etre touche donc sont: lot, bon de livraison ou bon de commande, souche, programme zootechnique, suivi journalier,...
Je crois que la race est donne par le programme zootehcniqe ou la souche



Reponse Claude 1:
IL n y a des colonnes dans ProgrammeZootechniqueDetails que je ne comprends pas: idsexe, pontecumulee, oeufcumule, oacpourcentage, oachh, poussins,femmellebw, malebw, ratioproduction, fertilite (c'est pas ceux qui peuvent pondre ca ? Genre c est un taux pour femelle ?), uniformiteCible, cvMax.

Dans le suivi journlaiie, avec l'entree de morts et de perdus, il faudrais aussi avoir un check qui check si ceux qu on rentre est coherent avec le nb poulet (male et femelle aussi) restant.

Il faudra faire insert ISA BROWN dans la race.

Reponse a tes questions:
Je suis encore perplexe sur l'age. Parce que on peut commander des poussins de 1 jour d'age le 01/01/2026. Et on ne les recois (repartis) le 01/02/2026. Donc 1 mois c'est ecouler, Or a 21 jours, eu memes ils pondent, et donc (aussi invraisembable que ca parait etre peut etre pondre dans l'avion) il y aura de nouveau poulet a l'arrive. 
Oui on ajoute isa brown
Prouve moi que culls fait diminuer l effectif. Sinon, on pourra toujorus cree une nouvelle colonne disparu. Enft je me demande aussi si mortalite est le nb de morts et non un taux.
Okay pour le suivi reelle que tu as dis
Oui pour les autres

Reponse 2:
Je ne comprend toujours pas c est quoi fertilite
Je propose d'ajouter vraiment les colonnes nbmorts, et nbperdus car dans le code je ne trouve pas de truc qui fait descendre l'effectif.

Reponse3:
il y a deja les constante ConstanteFerme IDSEXEFEMELLE et IDSEXEMALE dans le code. On les utilise juste.
Il est possible qu on commande deja des poulet qui pondent des qu ils sont arrive a l'enclos. (genre ils ont deja age = 21 jours arrive)


Reponse 4:
Yes but these were the datas before we implemented any changes, what I mean is that for mort now we dont rely on the quality, but we enter it ourself direclty how many and how many. LEs autres sont vivants. 
C'est 21 semaines. On stocke en jour, amis peut etre cote affichage on pourra avoir un swithc jour/semaine quand on insert des trucs par exmpel
Pontehebdnomadaire je pensias pas faire ne pourcentage mais direct en oeuf par jour, et je croisque  programme zootechniqeu a deja ca non ? 
POur OACPOURCENTAGE, FERTILITE, TAUXECLOSION, oui, on defaut a 100%

Les valuers de test je les fait

CE que je te demande juste c'est definir les logique necessaires pour faire ca. Faire les specs, et les scripts.
Je ne veux plsu te donner plus de detail. Je veu xque tu me donne la specs avec logique necessaries (logqie metier en mot), et avec les sql avec create ou alter table si necessaires

Reponse Deep:



Give the full specs
Give CaCalendirer and Detail