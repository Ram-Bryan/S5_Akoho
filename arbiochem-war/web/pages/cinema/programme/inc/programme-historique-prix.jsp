<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageRecherche" %>
<%@ page import="cinema.CinemaHistoriquePrixProgramme" %>

<% try {
    CinemaHistoriquePrixProgramme o = new CinemaHistoriquePrixProgramme();
    String[] libEntete = {"id", "prix", "datedebut"};
    PageRecherche pr = new PageRecherche(o, request, new String[]{}, new String[]{}, 3, libEntete, libEntete.length);
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));

    if (request.getParameter("id") != null) {
        pr.setAWhere(" and idprogramme = '" + request.getParameter("id") + "' ");
    }

    pr.creerObjetPage(libEntete, null);

    pr.getTableau().setLibelleAffiche(new String[]{"Id", "Prix", "Date début"});
%>
<div class="box-body">
    <% if (pr.getTableau().getHtml() != null) {
           out.println(pr.getTableau().getHtml());
       } else { %>
           <center><h4>Aucun prix enregistré pour ce programme</h4></center>
    <% } %>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>