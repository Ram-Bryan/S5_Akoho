<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsert" %>
<%@ page import="affichage.Liste" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.CinemaHistPrixCatRange" %>
<%@ page import="cinema.CinemaCategorieRange" %>
<%@ page import="utilitaire.Utilitaire" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String mapping  = "cinema.CinemaHistPrixCatRange";
    String nomTable = "cinema_hist_prix_cat_range";
    String apres    = "cinema/histPrixCatRange/histPrixCatRange-fiche.jsp";

    CinemaHistPrixCatRange o = new CinemaHistPrixCatRange();
    PageInsert pi = new PageInsert(o, request, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie historique prix");

    // 1. changerEnChamp AVANT setLibelle
    Liste listeCatRange = new Liste("idcategorierange", new CinemaCategorieRange(), "libelle", "id");
    pi.getFormu().changerEnChamp(new Liste[]{ listeCatRange });

    // 2. Libellés
    pi.getFormu().getChamp("idcategorierange").setLibelle("Catégorie de range");
    pi.getFormu().getChamp("prixsupplement").setLibelle("Supplément (Ar)");
    pi.getFormu().getChamp("datedebut").setLibelle("Date début");
    pi.getFormu().getChamp("datedebut").setDefaut(Utilitaire.dateDuJour());

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) pi.setTitre("Modification historique prix");

    pi.preparerDataFormu();
    pi.getFormu().makeHtmlInsertTabIndex();
%>
<div class="content-wrapper">
    <h1><%=pi.getTitre()%></h1>
    <form action="<%=pi.getLien()%>?but=apresTarif.jsp" method="post" name="<%=nomTable%>" id="<%=nomTable%>">
        <% out.println(pi.getFormu().getHtmlInsert()); %>
        <input name="acte"     type="hidden" value="insert">
        <input name="bute"     type="hidden" value="<%=apres%>">
        <input name="classe"   type="hidden" value="<%=mapping%>">
        <input name="nomtable" type="hidden" value="<%=nomTable%>">
    </form>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>