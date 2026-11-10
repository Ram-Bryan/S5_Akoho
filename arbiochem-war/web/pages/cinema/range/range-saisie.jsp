<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsert" %>
<%@ page import="affichage.Liste" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.CinemaRange" %>
<%@ page import="cinema.CinemaSalle" %>
<%@ page import="cinema.CinemaCategorieRange" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String mapping  = "cinema.CinemaRange";
    String nomTable = "cinema_range";
    String apres    = "cinema/range/range-fiche.jsp";

    CinemaRange o = new CinemaRange();
    PageInsert pi = new PageInsert(o, request, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie range");

    // 1. changerEnChamp AVANT setLibelle
    Liste listeSalle    = new Liste("idsalle", new CinemaSalle(), "libelle", "id");
    Liste listeCategorie = new Liste("idcategorierange", new CinemaCategorieRange(), "libelle", "id");
    pi.getFormu().changerEnChamp(new Liste[]{ listeSalle, listeCategorie });

    // 2. Libellés
    pi.getFormu().getChamp("libelle").setLibelle("Libellé");
    pi.getFormu().getChamp("idsalle").setLibelle("Salle");
    pi.getFormu().getChamp("idcategorierange").setLibelle("Catégorie de range");

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) pi.setTitre("Modification range");

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