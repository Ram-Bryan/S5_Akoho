Voir learn.md de ft1-crud-tsotra pour les bases du framework

# Mere-Fille
ClassMAPTable --> ClassEtat --> ClassMere / ClassFille

## Dans la DB
Chacune des 2 tables ont ETAT et IDUSER. La table fille a IDMERE qui FK la mere.
Par convention dans la base et dans le modeles on utilise "idmere"

## Dans les modeles entity:
### La Mere:

- ajouter les 2 methodes et ces methodes dans le constructeur:
```java

this.setNomClasseFille("test.maison.Piece");
this.setLiaisonFille("idmere");

@Override public String getNomClasseFille() { return "test.maison.Piece"; }
@Override public String getLiaisonFille()    { return "idmere"; }
```
### La Fille:

- ajouter les 2 methodes et ces methodes dans le constructeur:
```java

this.setNomClasseMere("test.maison.Maison");
this.setLiaisonMere("idmere");

@Override public String getNomClasseMere() { return "test.maison.Maison"; }
@Override public String getLiaisonMere()    { return "idmere"; }
```

## Dans les views
### Liste = PageRecherche
Comme pour le simple (voir ft1-crud-tsotra)

### Saisie = PageInsertMultiple
- this.formu (mère) and this.formufle (fille) pour un seul <form>
- Blueprint:
```jsp
   Maison mere = new Maison();
   Piece  fille = new Piece();
   PageInsertMultiple pi = new PageInsertMultiple(mere, fille, request, 10, u);
   /* ... config labels ... */
   pi.preparerDataFormu();
   pi.getFormu().makeHtmlInsertTabIndex();
   pi.getFormufle().makeHtmlInsertTableauIndex();

   <form id="formId" action="<%=pi.getLien()%>?but=apresMultiple.jsp" method="post">
    <% /* 2. RENDER */ 
       out.println(pi.getFormu().getHtmlInsert());          // MÈRE fields
       out.println(pi.getFormufle().getHtmlTableauInsert()); // FILLE rows
    %>
  </form>
```
- ?but=apresMultiple.jsp
- champ hidden:
acte, bute, classe, **classefille**, **nomtable**(=LA TABLE DE LA FILLE), **colonneMere**, **nombreLigne** (nb de lignes dans la table de l'insert fille. Par exemple 10)
- champs fille: pi.getFormufle().getChamp("nom_0") avec le _0 a la fin
- pi.getFormufle().getChampMulitple("id").getListeChamp() donne tous les champs de "id". getChamp("id_0") retourne seulement le champ id numero 0.
- les 2 boutons Enregistrer font la meme chose. Celui d'en bas existe si le tableau est tres long. Juste pour choix de design UX.

### Fiche = PageConsulte
- On cree un fichier dans inc/ pour la fille (afficher le tableau). Ensuite on include le fichier dans le tab de de la mere.
- On cree un tab: le tab permet de switch de fille si jamais la mere a plusieurs fille. Par exmepl, Maison peut avoir Piece, ou Meubles,...: cree un Map pour savoir quel tab est active
- passer l'id mere dans la fille pour avoir la fille de cette mere:
    - URL: module.jsp?but=test/maison/maison-fiche.jsp&id=MSN000001&tab=inc/piece-det
    - dans jsp:param on passe l'id
    - la fille l'utilise comme ca: pr.setAWhere(" and idmere = '" + request.getParameter("id") + "' ");