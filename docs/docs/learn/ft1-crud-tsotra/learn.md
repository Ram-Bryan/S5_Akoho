un petit CRUD:
Livre

# Dans le DB:
- creer une sequence. creer une fonction qui appele cette sequence. Le framework appelle la fonction comme ca: "LIV" + get_seq => LIV0001.
- creer la table:
id doit etre un VARCHAR.
int NUMBER(4,0)
double NUMBER(18,2)
java.sql.Date DATE 

# Dans l'entite:
- public class Livre extends ClassEtat 
- attributs doit matcher le nom de colonne dans la base
- dans le constructeur, doit donner le nom de la table:
public Livre() throws Exception {
        this.setNomTable("LIVRE");
}
- pour la sequence:
@Override
public void construirePK(Connection c) throws Exception {
        this.preparePk("LIV", "getseq_livre");
        this.setId(makePK(c));
}
- pour dire quelle attribut est l'id, doit matcher attribut = colonne base
@Override public String getTuppleID()       { return id; }
@Override public String getAttributIDName() { return "id"; }


## CHoix d'extends:
classMapTable -- heritage --> classEtat.
extends classEtat:
- si persistence d'etat (Viser, updateEtat, validation,...)
- ajouter 2 colonne a la table:
ETAT              NUMBER(2)      DEFAULT 1,
IDUSER            VARCHAR2(50)   DEFAULT '0'
- pas besoin d'ajouter ces 2 attributs dans le modele
extends classMAPTable:
- si juste pour montrer qqchose. Sans etat


# Dans les pages:

3 types de pages:

| Verb | Page* class | What it does | What you output |
| --- | --- | --- | --- |
| List | PageRecherche | Runs SELECT with filters, pagination, sort, export | `formu.getHtmlEnsemble() + tableau.getHtml() + getBasPage()` |
| Write (insert/update) | PageInsert | Builds form, reads POST params back, calls `createObject` / `updateObject` | `formu.getHtmlInsert()` inside a `<form>` to `apresTarif.jsp` |
| Read one | PageConsulte | Runs SELECT by PK, renders field/value table | `pc.getHtml() + your own buttons` |

Tous les types de pages font:
session → entity → Page* → configure → print

## Liste = PageRecherche
1. Get la session et le lien
2. Creer l'entite voulue + la pageRecherche
3. Detail de configuration:
    - setTitre(): titre de la page
    - setLien()
    - setApres(): ou on va quand on fait une pagination, un filtre, un return,...Normalement on veut toujours revenir sur la page actuelle apres une action.
    - String[] quels attribut on peut filtrer
    - Stringp[] quels attribut est un min/max: => les noms de champs devient: champ1 et champ2
    - String[] quels colonnes dans la tables: String[] libEntete = {"id","titre","auteur","anneepublication","etat"};
    - creeObject(libEntete, colSomme): load les donnes (pas encore visible, juste prepare les donnees). libEntete est dans l'ordre des colonnes. colSomme somme une colonne: Passing new String[]{"prix"} means "sum prix": Total: 12   Somme de prix: 546.00. colSomme Peut etre null 
    - colonne clickable: for column X in the table, if the user clicks it, go to URL Y with ?<attLien>=<cell_value>:
        - setLien(String[]): ou les colonnes clikable vont
        - setColonneLien(String[]): quels colonnes est clickable. If you wanted both id and titre clickable (both go to fiche): 
            ```jsp
                setLien(new String[]{
                    pr.getLien() + "?but=test/livre/livre-fiche.jsp",
                    pr.getLien() + "?but=test/livre/livre-fiche.jsp"
                });
                setColonneLien(new String[]{"id", "titre"});
                setAttLien(new String[]{"id", "id"});   // both append &id=<cell_value_of_id>
            ```
        - setAttLien(String[]): quel attribut ajouter sur le lien. ..?but=test/livre/livre-fiche.jsp&id=LIV000001.
4. Html:
- form: filter form
- pr.getTableauRecap().getHtml(): montre les stats (total de livre, somme des prix,...)
- pr.getTableau().getHtml(): affiche le resultat tableau
- pr.getBasPage(): pagination

## Saisie = PageInsert (Insert et Update)
### Workflow:
- if request.getParameter("acte") == "update" & id existe => pre-rempli le form (pour savoir si c'est update ou insert)
- quand submit le form: POSTs vers apresTarif.jsp with avec les valeurs de tous les champs + acte (insert/update)

1. Get la session et le lien
2. Set quel classe (entity) est fait pour cette page. "test.livre.Livre"
3. Set le nom de la table + cree l'objet pour cette page. 
Il y a 3 valeurs car:
- "test.livre.Livre" les attributs a envoyer dans le form a apresTarif.jsp
- LIVRE nom de table ou on va insert est aussi envoye a apresTarif
- Livre o = new Livre();  est pour creer la page d'insert actuelle
4. Creer la pageInsert
5. Configuration similaire a la liste. Ajout de:
    - pi.getFormu().getChamp("etat").setVisible(false); pour que ca soit invisible MAIS ca existe
    - getChamp("x").setLibelle(...): avoir un libelle pour un champ
6. preparerDataFormu(): pour les dropdownlists. Mettre ca avant si il y a des dorpdownlist:
```jsp
Liste listeAuteur = new Liste("idAuteur", new Auteur(), "nom", "id");
//                                            ↑ singleton  ↑ libellé  ↑ id
pi.getFormu().changerEnChamp(new Liste[]{listeAuteur});
pi.preparerDataFormu();   // ← loads SELECT id, nom FROM AUTEUR
```

7. makeHtmlInsertTabIndex():
8. Html:
    - cree le formulaire post vers apresTarif:     <% out.println(pi.getFormu().getHtmlInsert()); %>
    - creer les inputs hidden:
        - acte (insert/update)
        - bute (apres, c'est a dire apres avoir submit on va dans quelle page)
        - classe
        - nomtable

## Fiche detail = PageConsulte
1. Session
2. Creer l'objet et lis l'id sur l'URL + objet PageConsult
3. Render avec: pc.getHtml()