Okay now I want to dive in inside this project. Theres something i dont understand like I did this workflow:

demande achat --> bon de commnade --> bon de reception --> facture fournisseur --> insertion mvt stock. And I have a problme on the insertion of the mvt de stock. I want to insert the ingredient INGR00000111 and it says NULL if I'm reading right. But when I go to the page of listing of the ingredients it exists ! So I investaigated the DB, and looked insid e the table AS_INGREDIENTS and the view AS_INGREDIENTS_LIB but they both dont have it ! So how come is it appearing in the listing ???

<%-- 
    Document   : produit-recherche
    Created on : 26 janv. 2024, 10:25:56
    Author     : Angela
--%>




<%@page import="annexe.ProduitLib"%>
<%@page import="annexe.TypeProduit"%>
<%@page import="annexe.Unite"%>
<%@page import="annexe.Categorie"%>
<%@page import="annexe.Produit"%>
<%@page import="affichage.PageRecherche"%>
<%@page import="affichage.Liste"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="produits.IngredientsLib" %>
<%@page import="bean.TypeObjet"%>

<% try{ 
    IngredientsLib t = new IngredientsLib();
    t.getTypeProduitLib();
    t.setNomTable("AS_INGREDIENTS_LIB");
    String listeCrt[] = {"id", "libelle","categorieingredient","typeProduitLib","idunite","idDepartementLib", "typeStock","actif"};
    String listeInt[] = {};
    String libEntete[] = {"id", "libelle","categorieingredient","typeProduitLib","unite","idDepartementLib","typeStock"};
    PageRecherche pr = new PageRecherche(t, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
    pr.setTitre("Liste des produits");
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("annexe/produit/produit-liste-arbiochem.jsp");
    Liste[] liste = new Liste[4];
    TypeObjet unite = new TypeObjet();
    unite.setNomTable("as_unite");
    liste[0] = new Liste("idunite", unite, "val", "id");
    TypeObjet categorie=new TypeObjet();
    categorie.setNomTable("V_CATEGORIEINGREDIENT");
    liste[1] = new Liste("categorieingredient",categorie,"val","val");
    TypeObjet tp=new TypeObjet();
    tp.setNomTable("V_TYPE_PRODUIT");
    liste[2] = new Liste("typeProduitLib",tp,"val","val");
    liste[3] = new Liste("actif");
    liste[3].makeListeOuiNon();
    liste[3].setDefaut("1");
    pr.getFormu().changerEnChamp(liste);
    pr.getFormu().getChamp("id").setLibelle("Id");
    pr.getFormu().getChamp("libelle").setLibelle("Libell&eacute;");
    pr.getFormu().getChamp("categorieingredient").setLibelle("Cat&eacute;gorie");
    pr.getFormu().getChamp("idDepartementLib").setLibelle("D&eacute;partement");
    pr.getFormu().getChamp("idunite").setLibelle("Unit&eacute;");
    pr.getFormu().getChamp("typeProduitLib").setLibelle("Type de produit");
    pr.getFormu().getChamp("typeStock").setLibelle("Type de stock");

    String[] colSomme = null;
    pr.setNpp(100);
    pr.creerObjetPage(libEntete, colSomme);
    //Definition des lienTableau et des colonnes de lien
    String lienTableau[] = {pr.getLien() + "?but=produits/as-ingredients-arbiochem-fiche.jsp"};
    String colonneLien[] = {"id"};
    Map<String,String> lienTab=new HashMap<>();
    lienTab.put("modifier",pr.getLien() + "?but=produits/as-ingredients-saisie.jsp?acte=update");
//    lienTab.put("Rendre indisponible",pr.getLien() + "?but=rdv/rdv-saisie.jsp");
//    lienTab.put("Rendre disponible",pr.getLien() + "?but=rdv/rdv-saisie.jsp");
    pr.getTableau().setLienClicDroite(lienTab);
    pr.getTableau().setLien(lienTableau);
    pr.getTableau().setColonneLien(colonneLien);
    String libEnteteAffiche[] = {"ID", "Libell&eacute;", "Cat&eacute;gorie", "Type de produit", "Unit&eacute;", "D&eacute;partement", "Type de stock"};
    pr.getTableau().setLibelleAffiche(libEnteteAffiche);
    pr.getFormu().setAnotherButton("" +
            "<a class=\"btn btn-primary pull-right btn-small\" href=\"module.jsp?but=produits/as-ingredients-arbiochem-saisie.jsp\">\n" +
            "                    <i class=\"material-symbols-rounded\">add</i>Saisie d'un produit</a>"
    );
%>

<div class="content-wrapper">
    <section class="content-header">
        <h1><%= pr.getTitre() %></h1>
    </section>
    <section class="content">
        <form action="<%=pr.getLien()%>?but=<%= pr.getApres() %>" method="post">
            <%
                out.println(pr.getFormu().getHtmlEnsemble());
            %>
        </form>
        <%
            out.println(pr.getTableauRecap().getHtml());%>
        <br>
        <%
            out.println(pr.getTableau().getHtml());
            out.println(pr.getBasPage());
        %>
    </section>
</div>
    <%
    }catch(Exception e){

        e.printStackTrace();
    }
%>

ava:750)
15:06:32,273 INFO  [stdout] (default task-39) Requete select rechercher : select * from utilisateur where 1<2 
15:06:32,311 INFO  [stdout] (default task-39) ACTEinsert
15:06:32,378 INFO  [stdout] (default task-39) Requete select rechercher : select * from as_bondecommande_livre where 1<2 and upper(idbc) like upper('%BC001210%') 
15:06:32,446 INFO  [stdout] (default task-39) Requete d'insertion : INSERT INTO AS_BONDELIVRAISON ( id , remarque , idbc , daty , etat , idFournisseur , magasin , idFactureFournisseur , idDepenseDivers ) values ( 'BL002667','','BC001210','05/11/2026',1,'FRNA000003','MAGA0004','',''
15:06:32,453 INFO  [stdout] (default task-39) Requete d'insertion : INSERT INTO historique ( idHistorique , dateHistorique , heure , objet , action , idUtilisateur , refObjet ) values ( 'EX0000790999','05/11/2026','15:06:32','faturefournisseur.As_BonDeLivraison','Insertion par 1060','1060','BL002667'
15:06:32,481 INFO  [stdout] (default task-39) Requete select rechercher : select * from AS_INGREDIENTS where 1<2 and id like 'INGR00000111' 
15:06:32,653 INFO  [stdout] (default task-38) Requete select rechercher : select * from utilisateur where 1<2 
15:06:32,931 INFO  [stdout] (default task-38) Requete select rechercher : select * from AS_BONDELIVRAISON_TRAITE where 1<2 and id like 'BL002667' 
15:06:33,186 INFO  [stdout] (default task-38) Requete select rechercher Page : select * from (select id,produit,numbl,quantite,iddetailsfacturefournisseur,unite,idbc_fille,etat,produitlib,unitelib,pu,designation,montant,puar,tauxdechange,idFactureFournisseur,rowNum as r from (select * from AS_BONDELIVRAISON_LIBCPL where 1<2 and 1 < 2  and numbl='BL002667')) where r between 1 and 10
15:06:36,515 INFO  [stdout] (default task-35) Requete select rechercher : select * from utilisateur where 1<2 
15:06:36,747 INFO  [stdout] (default task-35) Requete select rechercher : select * from AS_BONDELIVRAISON where 1<2 and id like 'BL002667' 
15:06:36,755 INFO  [stdout] (default task-35) Requete select rechercher : select * from AS_BONDELIVRAISON_FILLE where 1<2 and upper(numbl) like upper('%BL002667%') 
15:06:36,805 INFO  [stdout] (default task-35) Requete d'insertion : INSERT INTO historique ( idHistorique , dateHistorique , heure , objet , action , idUtilisateur , refObjet ) values ( 'EX0000791002','05/11/2026','15:06:36','faturefournisseur.As_BonDeLivraison','Validation par 1060','1060','BL002667'
15:06:36,813 INFO  [stdout] (default task-35) Requete update : UPDATE AS_BONDELIVRAISON SET id='BL002667', remarque='', idbc='BC001210', daty='05/11/2026', etat=11, idFournisseur='FRNA000003', magasin='MAGA0004', idFactureFournisseur='', idDepenseDivers=''  WHERE id = 'BL002667'
15:06:36,902 INFO  [stdout] (default task-34) Requete select rechercher : select * from utilisateur where 1<2 
15:06:36,997 INFO  [stdout] (default task-34) Requete select rechercher : select * from AS_BONDELIVRAISON_TRAITE where 1<2 and id like 'BL002667' 
15:06:37,226 INFO  [stdout] (default task-34) Requete select rechercher Page : select * from (select id,produit,numbl,quantite,iddetailsfacturefournisseur,unite,idbc_fille,etat,produitlib,unitelib,pu,designation,montant,puar,tauxdechange,idFactureFournisseur,rowNum as r from (select * from AS_BONDELIVRAISON_LIBCPL where 1<2 and 1 < 2  and numbl='BL002667')) where r between 1 and 10
15:06:39,096 INFO  [stdout] (default task-43) Requete select rechercher : select * from utilisateur where 1<2 
15:06:39,268 INFO  [stdout] (default task-43) Requete select rechercher : select * from AS_BONDELIVRAISON where 1<2 and id like 'BL002667' 
15:06:39,319 INFO  [stdout] (default task-43) Requete select rechercher : select * from AS_BONDELIVRAISON where 1<2 and id like 'BL002667' 
15:06:39,332 INFO  [stdout] (default task-43) Requete select rechercher : select * from RAPPROCHEMENT_BF_LIVREE where 1<2 and upper(numbl) like upper('%BL002667%')  
15:06:39,523 INFO  [stdout] (default task-43) LE point est: MAGA0004
15:06:39,704 INFO  [stdout] (default task-43) Requete select rechercher : select * from categorieStock where 1<2  order by id asc
15:06:39,804 INFO  [stdout] (default task-43) Requete select rechercher : select * from POINT where 1<2  order by val ASC
15:06:39,812 INFO  [stdout] (default task-43) Requete select rechercher : select * from magasin2 where 1<2  order by val ASC
15:06:39,819 INFO  [stdout] (default task-43) Requete select rechercher : select * from typemvtstock where 1<2  order by val ASC
15:06:39,821 INFO  [stdout] (default task-43) Requete select rechercher : select * from AS_INGREDIENTS_LIB where 1<2 and id like 'INGR00000111' 
15:06:39,846 INFO  [stdout] (default task-43) null | null
15:06:45,273 INFO  [stdout] (default task-41) Requete select rechercher : select * from utilisateur where 1<2 
15:06:45,326 INFO  [stdout] (default task-41) ACTEinsert
15:06:45,397 INFO  [stdout] (default task-41) LE point est: PNT000085
15:06:45,531 INFO  [stdout] (default task-41) Requete d'insertion : INSERT INTO MVTSTOCK ( id , designation , idMagasin , idVente , idTransfert , idTypeMvStock , daty , etat , idPoint , idobjet , fabPrecedent , idCategorieStock , heure ) values ( 'MVTST019176150645','Mouvement de Stock pour BL002667','MAGA0004','','BL002667','TPMVST000001','05/11/2026',1,'PNT000085','BL002667','','CTGST000001','15:06'
15:06:45,560 INFO  [stdout] (default task-41) Requete d'insertion : INSERT INTO historique ( idHistorique , dateHistorique , heure , objet , action , idUtilisateur , refObjet ) values ( 'EX0000791003','05/11/2026','15:06:45','stock.MvtStock','Insertion par 1060','1060','MVTST019176150645'
15:06:45,563 INFO  [stdout] (default task-41) Requete select rechercher : select * from AS_INGREDIENTS where 1<2 and id like 'INGR00000111' 
15:06:45,565 ERROR [stderr] (default task-41) java.lang.NullPointerException
15:06:45,565 ERROR [stderr] (default task-41) 	at stock.MvtStockFille.controllerMvtSrc(MvtStockFille.java:613)
15:06:45,565 ERROR [stderr] (default task-41) 	at stock.MvtStockFille.controler(MvtStockFille.java:391)
15:06:45,566 ERROR [stderr] (default task-41) 	at bean.ClassMAPTable.controlerBatch(ClassMAPTable.java:428)
15:06:45,566 ERROR [stderr] (default task-41) 	at bean.ClassMAPTable.createObjectBatch(ClassMAPTable.java:422)
15:06:45,566 ERROR [stderr] (default task-41) 	at bean.ClassMere.createObjectFilleBatch(ClassMere.java:170)
15:06:45,566 ERROR [stderr] (default task-41) 	at bean.ClassMere.createObject(ClassMere.java:114)
15:06:45,566 ERROR [stderr] (default task-41) 	at user.CreateObject.createObject(CreateObject.java:44)
15:06:45,566 ERROR [stderr] (default task-41) 	at user.UserEJBBean.createObject(UserEJBBean.java:1952)
15:06:45,566 ERROR [stderr] (default task-41) 	at user.UserEJBBean.createObject

15:13:30,292 ERROR [stderr] (default task-27) 	at java.lang.Thread.run(Thread.java:750)
15:17:15,387 INFO  [stdout] (default task-33) Requete select rechercher : select * from utilisateur where 1<2 
15:17:15,539 INFO  [stdout] (default task-33) Requete select rechercher Page : select * from (select id,libelle,parfums,seuil,unite,quantiteParPack,pu,actif,photo,calorie,compose,composelib,idcategorieingredient,categorieIngredient,idcategorie,idFournisseur,daty,bienOuServ,etatlib,compte_vente,compte_achat,pv,tva,typeStock,idunite,refpost,refqualification,refpostLib,refqualificationLib,idFamilleLib,typeProduit,idligne,compte_stock,compte_sortie_stock,idFournisseurLib,idDepartementLib,libellevente,nbpiece,typeProduitLib,compte_ristourne,rowNum as r from (select * from AS_INGREDIENTS_LIB where 1<2 and 1 < 2  and actif like '1')) where r between 1 and 100
15:17:15,765 INFO  [stdout] (default task-33) Requete select rechercher : select * from V_CATEGORIEINGREDIENT where 1<2  order by val ASC
15:17:15,770 INFO  [stdout] (default task-33) Requete select rechercher : select * from V_TYPE_PRODUIT where 1<2  order by val ASC
15:17:15,772 INFO  [stdout] (default task-33) Requete select rechercher : select * from as_unite where 1<2  order by val ASC
15:18:27,641 INFO  [stdout] (default task-37) Requete select rechercher : select * from utilisateur where 1<2 
15:18:28,154 INFO  [stdout] (default task-37) Requete select rechercher : select * from as_ingredients_lib where 1<2 and id like 'INGR00000112' 
15:18:28,384 INFO  [stdout] (default task-37) Requete select rechercher : select * from AS_RECETTE_LIBCOMPLET where 1<2 and upper(idproduits) like upper('%INGR00000112%') 
15:18:29,042 INFO  [stdout] (default task-37) Requete select rechercher : select * from AS_RECETTE_LIBCOMPLET where 1<2 and upper(idingredients) like upper('%INGR00000112%') 
15:18:29,216 INFO  [stdout] (default task-37) Requete select rechercher : select * from AlimentPoussin_LIBCOMPLET where 1<2 and upper(idproduits) like upper('%INGR00000112%') 
15:18:29,729 INFO  [stdout] (default task-37) null | null
15:18:29,983 INFO  [stdout] (default task-37) Requete select rechercher : select * from RECETTE_MERE_LIB where 1<2  and IDPRODUIT = 'INGR00000112'
15:18:30,482 INFO  [stdout] (default task-37) Requete select rechercher : select * from HISTORIQUEPUINGTRIE where 1<2 and upper(idIngredients) like upper('%INGR00000112%')  
15:23:32,489 INFO  [stdout] (default task-40) Requete select rechercher : select * from utilisateur where 1<2 
15:23:32,681 INFO  [stdout] (default task-40) Requete select rechercher Page : select * from (select id,libelle,parfums,seuil,unite,quantiteParPack,pu,actif,photo,calorie,compose,composelib,idcategorieingredient,categorieIngredient,idcategorie,idFournisseur,daty,bienOuServ,etatlib,compte_vente,compte_achat,pv,tva,typeStock,idunite,refpost,refqualification,refpostLib,refqualificationLib,idFamilleLib,typeProduit,idligne,compte_stock,compte_sortie_stock,idFournisseurLib,idDepartementLib,libellevente,nbpiece,typeProduitLib,compte_ristourne,rowNum as r from (select * from AS_INGREDIENTS_LIB where 1<2 and 1 < 2  and actif like '1')) where r between 1 and 100
15:23:32,900 INFO  [stdout] (default task-40) Requete select rechercher : select * from V_CATEGORIEINGREDIENT where 1<2  order by val ASC
15:23:32,903 INFO  [stdout] (default task-40) Requete select rechercher : select * from V_TYPE_PRODUIT where 1<2  order by val ASC
15:23:32,906 INFO  [stdout] (default task-40) Requete select rechercher : select * from as_unite where 1<2  order by val ASC
15:25:21,274 INFO  [stdout] (default task-30) Requete select rechercher : select * from utilisateur where 1<2 
15:25:21,399 INFO  [stdout] (default task-30) Requete select rechercher Page : select * from (select id,libelle,parfums,seuil,unite,quantiteParPack,pu,actif,photo,calorie,compose,composelib,idcategorieingredient,categorieIngredient,idcategorie,idFournisseur,daty,bienOuServ,etatlib,compte_vente,compte_achat,pv,tva,typeStock,idunite,refpost,refqualification,refpostLib,refqualificationLib,idFamilleLib,typeProduit,idligne,compte_stock,compte_sortie_stock,idFournisseurLib,idDepartementLib,libellevente,nbpiece,typeProduitLib,compte_ristourne,rowNum as r from (select * from AS_INGREDIENTS_LIB where 1<2 and id like 'INGR00000111' and 1 < 2  and actif like '1')) where r between 1 and 100
15:25:21,406 INFO  [stdout] (default task-30) Requete select rechercher : select * from V_CATEGORIEINGREDIENT where 1<2  order by val ASC
15:25:21,409 INFO  [stdout] (default task-30) Requete select rechercher : select * from V_TYPE_PRODUIT where 1<2  order by val ASC
15:25:21,415 INFO  [stdout] (default task-30) Requete select rechercher : select * from as_unite where 1<2  order by val ASC


