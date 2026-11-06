<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsert"%>
<%@ page import="user.UserEJB" %>
<%@ page import="ferme.lot.Lot" %>
<%@page import="affichage.Liste"%>
<%@page import="bean.TypeObjet"%>
<%@page import="faturefournisseur.As_BonDeLivraison_Fille"%>
<%@page import="faturefournisseur.As_BonDeLivraison"%>
<%@page import="java.sql.Date"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="utilitaire.UtilDB" %>


<% try{ 
    UserEJB u = (user.UserEJB) session.getValue("u");
    String mapping = "ferme.lot.Lot";
    String nomTable = "LOT";
    String apres = "ferme/lot/lot-fiche.jsp";

    Lot o = new Lot();
    o.setNomTable("LOT");
    PageInsert pi = new PageInsert(o, request, u);
    pi.setLien((String) session.getValue("lien"));  
    pi.setTitre("Saisie lot");


    pi.getFormu().getChamp("nomlot").setLibelle("Nom du lot");
    pi.getFormu().getChamp("reference").setLibelle("R&eacute;f&eacute;rence");
    pi.getFormu().getChamp("dateeclosion").setLibelle("Date d'&eacute;closion");
    pi.getFormu().getChamp("datearrivee").setLibelle("Date d'arriv&eacute;e");
    pi.getFormu().getChamp("source").setLibelle("Source");
    pi.getFormu().getChamp("idferme").setLibelle("Ferme");
    pi.getFormu().getChamp("idarticle").setLibelle("Article");
    pi.getFormu().getChamp("idorigine").setLibelle("Origine");
    pi.getFormu().getChamp("idprogramme").setLibelle("Programme Zootechnique");
    pi.getFormu().getChamp("idSouche").setLibelle("Souche");
    pi.getFormu().getChamp("source").setAutre("readonly");
    pi.getFormu().getChamp("idSouche").setAutre("readonly");
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("dateCloture").setVisible(false);
    pi.getFormu().getChamp("idferme").setPageAppelComplete("magasin.Magasin","id","MAGASIN2","id","id");
    pi.getFormu().getChamp("idarticle").setPageAppelComplete("produits.Ingredients","id","AS_INGREDIENTS","id","id");
    pi.getFormu().getChamp("idorigine").setPageAppelComplete("ferme.configuration.OrigineLot","id","ORIGINELOT","id","id");
    pi.getFormu().getChamp("idprogramme").setPageAppelComplete("ferme.programme.ProgrammeZootechnique","id","PROGRAMMEZOOTECHNIQUE","id;idSouche","val;idSouche");
//    pi.getFormu().getChamp("idSouche").setPageAppelComplete("ferme.configuration.Souche","id","SOUCHE","id","val");


    System.out.println("AHAHAHAHHA");

    
    pi.getFormu().getChamp("idBlFille").setLibelle("Ligne de bon de livraison");
    // pi.getFormu().getChamp("idarticle").setAutre("readonly");

    affichage.Liste[] listef = new Liste[1];
    TypeObjet cl=new TypeObjet();
    cl.setNomTable("CATEGORIELOT");
    listef[0]=new Liste("idCategorieLot",cl,"val","id");

    pi.getFormu().getChamp("idBlFille").setPageAppelComplete(
    "faturefournisseur.As_BonDeLivraison_Fille",  // class name
    "id",                                          // value column (line PK)
    "AS_BONDELIVRAISON_LIBCPL",                    // view for choices
    "produit",                                     // field of the SELECTED line to copy
    "idarticle"                                    // form field to fill with it
    );
    pi.getFormu().changerEnChamp(listef);
    pi.getFormu().getChamp("idCategorieLot").setLibelle("Cat&eacute;gorie du Lot");

    String[] ordre = {"nomlot","reference","dateeclosion","datearrivee","source",
                  "idferme","idarticle","idorigine","idprogramme","idBlFille"};
    pi.getFormu().setOrdre(ordre);
 
    String acte = request.getParameter("acte");
    if(acte != null && acte.equalsIgnoreCase("update")){
        pi.setTitre("Modification lot");
    }

    pi.preparerDataFormu();

    // Prefill
    String idBlFilleParam = request.getParameter("idblfille");
    System.out.println("got the ibfille");
    if (idBlFilleParam != null && !idBlFilleParam.trim().isEmpty()) {
            System.out.println("inside the if");

        Connection tmpConn = new UtilDB().GetConn();
                    System.out.println("inside the conn");

        try {
            As_BonDeLivraison_Fille line = new As_BonDeLivraison_Fille();
line = (As_BonDeLivraison_Fille) line.getById(idBlFilleParam, null, tmpConn);

if (line == null) {
    // silent skip — the form will render blank; user can pick manually
} else {
    As_BonDeLivraison bl = null;
    if (line.getNumbl() != null) {
        bl = (As_BonDeLivraison) new As_BonDeLivraison().getById(line.getNumbl(), null, tmpConn);
    }

    // Now safe to use line / bl
    // pi.getFormu().getChamp("idBlFille").setDefaut(line.getId());
    pi.getFormu().getChamp("idBlFille").setValeur(line.getId());
    // pi.getFormu().getChamp("idarticle").setDefaut(line.getProduit());
    pi.getFormu().getChamp("idarticle").setValeur(line.getProduit());


    String datyStr = (bl != null && bl.getDaty() != null)
        ? utilitaire.Utilitaire.datetostring(bl.getDaty())
        : utilitaire.Utilitaire.dateDuJour();
    // pi.getFormu().getChamp("datearrivee").setDefaut(datyStr);
    pi.getFormu().getChamp("datearrivee").setValeur(datyStr);

    pi.getFormu().getChamp("idferme").setValeur(bl.getMagasin());


    pi.getFormu().getChamp("nomlot").setDefaut(line.getNumbl() + " - " + line.getProduit());
}

        } finally {
            tmpConn.close();
        }
    }

    System.out.println("done");

    pi.getFormu().makeHtmlInsertTabIndex();
%>

<div class="content-wrapper">
    <h1><%=pi.getTitre()%></h1>
    <form action="<%=pi.getLien()%>?but=apresTarif.jsp" method="post" name="<%=nomTable%>" id="<%=nomTable%>">
        <%
            out.println(pi.getFormu().getHtmlInsert());
            out.println(pi.getHtmlAddOnPopup());
        %>
        <input name="acte" type="hidden" id="nature" value="insert">
        <input name="bute" type="hidden" id="bute" value="<%=apres%>">
        <input name="classe" type="hidden" id="classe" value="<%=mapping%>">
        <input name="nomtable" type="hidden" id="nomtable" value="<%=nomTable%>">
    </form>
</div>

<% } catch (Exception e) {
  e.printStackTrace();
%>
<script language="JavaScript"> alert('<%=e.getMessage()%>');
    history.back();
</script>
<% }%>

