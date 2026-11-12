<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsert" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.CinemaCategorieRange" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String mapping  = "cinema.CinemaCategorieRange";
    String nomTable = "cinema_categorie_range";
    String apres    = "cinema/categorieRange/categorieRange-fiche.jsp";

    CinemaCategorieRange o = new CinemaCategorieRange();
    PageInsert pi = new PageInsert(o, request, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie catégorie de range");

    pi.getFormu().getChamp("libelle").setLibelle("Libellé");
    pi.getFormu().getChamp("prix").setLibelle("Supplément (Ar)");
pi.getFormu().getChamp("prix").setDefaut("0");

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) pi.setTitre("Modification catégorie de range");

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