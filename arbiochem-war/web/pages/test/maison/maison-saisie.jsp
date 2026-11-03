<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsertMultiple"%>
<%@ page import="user.UserEJB" %>
<%@ page import="test.maison.Maison" %>
<%@ page import="test.maison.Piece" %>
<%@ page import="affichage.Champ" %>

<% try{
    UserEJB u = (user.UserEJB) session.getValue("u");
    String classeMere   = "test.maison.Maison";
    String classeFille  = "test.maison.Piece";
    String nomTableFille= "PIECE";
    String colonneMere  = "idmere";
    String apres        = "test/maison/maison-fiche.jsp";
    int    taille       = 10;

    Maison mere = new Maison();
    Piece  fille= new Piece();
    PageInsertMultiple pi = new PageInsertMultiple(mere, fille, request, taille, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie d'une maison");

    // --- Mère form ---
    pi.getFormu().getChamp("nom").setLibelle("Nom");
    pi.getFormu().getChamp("adresse").setLibelle("Adresse");
    pi.getFormu().getChamp("ville").setLibelle("Ville");
    pi.getFormu().getChamp("codepostal").setLibelle("Code postal");
    pi.getFormu().getChamp("surface").setLibelle("Surface (m&sup2;)");
    pi.getFormu().getChamp("anneconstruction").setLibelle("Ann&eacute;e de construction");
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("etat").setDefaut("1");
    pi.getFormu().getChamp("iduser").setVisible(false);
    pi.getFormu().getChamp("iduser").setDefaut("0");

    // --- Fille form (row 0) ---
    pi.getFormufle().getChamp("nom_0").setLibelle("Nom de la pi&egrave;ce");
    pi.getFormufle().getChamp("superficie_0").setLibelle("Superficie (m&sup2;)");
    pi.getFormufle().getChamp("etage_0").setLibelle("&Eacute;tage");
    pi.getFormufle().getChamp("typepiece_0").setLibelle("Type de pi&egrave;ce");
    Champ.setVisible(pi.getFormufle().getChampMulitple("id").getListeChamp(), false);
    Champ.setVisible(pi.getFormufle().getChampMulitple("idmere").getListeChamp(), false);
    Champ.setVisible(pi.getFormufle().getChampMulitple("etat").getListeChamp(), false);
    Champ.setVisible(pi.getFormufle().getChampMulitple("iduser").getListeChamp(), false);

    String[] colOrdre = {"id","nom","superficie","etage","typepiece"};
    pi.getFormufle().setColOrdre(colOrdre);

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) pi.setTitre("Modification d'une maison");

    pi.preparerDataFormu();
    pi.getFormu().makeHtmlInsertTabIndex();
    pi.getFormufle().makeHtmlInsertTableauIndex();
%>
<div class="content-wrapper">
    <h1><%=pi.getTitre()%></h1>
    <form id="formId" class='container' action="<%=pi.getLien()%>?but=apresMultiple.jsp" method="post" >
        <% out.println(pi.getFormu().getHtmlInsert());
           out.println(pi.getFormufle().getHtmlTableauInsert()); %>
        <input name="acte" type="hidden" id="nature" value="insert">
        <input name="bute" type="hidden" id="bute" value="<%=apres%>">
        <input name="classe" type="hidden" id="classe" value="<%=classeMere%>">
        <input name="classefille" type="hidden" id="classefille" value="<%=classeFille%>">
        <input name="nomtable" type="hidden" id="nomtable" value="<%=nomTableFille%>">
        <input name="nombreLigne" type="hidden" id="nombreLigne" value="<%=taille%>">
        <input name="colonneMere" type="hidden" id="colonneMere" value="<%=colonneMere%>">
    </form>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>