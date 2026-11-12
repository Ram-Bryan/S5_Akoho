<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsert" %>
<%@ page import="affichage.Liste" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.CinemaHistoriquePrixProgramme" %>
<%@ page import="cinema.CinemaProgramme" %>
<%@ page import="utilitaire.Utilitaire" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String mapping  = "cinema.CinemaHistoriquePrixProgramme";
    String nomTable = "cinema_hist_prix_programme";
    String apres    = "cinema/historiquePrixProgramme/historiquePrixProgramme-fiche.jsp";

    CinemaHistoriquePrixProgramme o = new CinemaHistoriquePrixProgramme();
    PageInsert pi = new PageInsert(o, request, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie prix programme");

    Liste listeProg = new Liste("idprogramme", new CinemaProgramme(), "id", "id");
    pi.getFormu().changerEnChamp(new Liste[]{ listeProg });

    if (request.getParameter("idprogramme") != null) {
    pi.getFormu().getChamp("idprogramme").setDefaut(request.getParameter("idprogramme"));
}

    pi.getFormu().getChamp("idprogramme").setLibelle("Programme");
    pi.getFormu().getChamp("prix").setLibelle("Prix (Ar)");
    pi.getFormu().getChamp("datedebut").setLibelle("Date d'effet");
    pi.getFormu().getChamp("datedebut").setDefaut(Utilitaire.dateDuJour());

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) pi.setTitre("Modification prix programme");

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