<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageRecherche" %>
<%@ page import="cinema.CinemaDetailReservation" %>

<% try {
    CinemaDetailReservation o = new CinemaDetailReservation();
    String[] listeCrt  = {};
    String[] listeInt  = {};
    String[] libEntete = {"id", "idplace"};
    PageRecherche pr = new PageRecherche(o, request, listeCrt, listeInt, 4, libEntete, libEntete.length);
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));

    if (request.getParameter("id") != null) {
        pr.setAWhere(" and idreservation = '" + request.getParameter("id") + "' ");
    }

    pr.creerObjetPage(libEntete, null);

    String[] libEnteteAffiche = {"Id", "Place"};
    pr.getTableau().setLibelleAffiche(libEnteteAffiche);
%>
<div class="box-body">
    <% if (pr.getTableau().getHtml() != null) {
           out.println(pr.getTableau().getHtml());
       } else { %>
           <center><h4>Aucune place réservée</h4></center>
    <% } %>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>