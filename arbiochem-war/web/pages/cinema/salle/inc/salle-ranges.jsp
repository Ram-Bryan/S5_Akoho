<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageRecherche" %>
<%@ page import="cinema.CinemaRange" %>

<% try {
    CinemaRange o = new CinemaRange();
    String[] listeCrt  = {};
    String[] listeInt  = {};
    String[] libEntete = {"id", "libelle", "idcategorierange"};
    PageRecherche pr = new PageRecherche(o, request, listeCrt, listeInt, 4, libEntete, libEntete.length);
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));

    if (request.getParameter("id") != null) {
        pr.setAWhere(" and idsalle = '" + request.getParameter("id") + "' ");
    }

    pr.creerObjetPage(libEntete, null);

    pr.getTableau().setLien(new String[]{ pr.getLien() + "?but=cinema/range/range-fiche.jsp" });
    pr.getTableau().setColonneLien(new String[]{"id"});
    pr.getTableau().setAttLien(new String[]{"id"});
    pr.getTableau().setLibelleAffiche(new String[]{"Id", "Libellé", "Catégorie"});
%>
<div class="box-body">
    <% if (pr.getTableau().getHtml() != null) {
           out.println(pr.getTableau().getHtml());
       } else { %>
           <center><h4>Aucune range pour cette salle</h4></center>
    <% } %>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>