<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageRecherche"%>
<%@ page import="test.maison.Piece" %>

<% try{
    Piece o = new Piece();
    String[] listeCrt  = {};
    String[] listeInt  = {};
    String[] libEntete = {"nom","superficie","etage","typepiece"};
    PageRecherche pr = new PageRecherche(o, request, listeCrt, listeInt, 4, libEntete, libEntete.length);
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));

    if (request.getParameter("id") != null) {
        pr.setAWhere(" and idmere = '" + request.getParameter("id") + "' ");
    }

    pr.creerObjetPage(libEntete, null);

    String[] libEnteteAffiche = {"Nom","Superficie (m&sup2;)","&Eacute;tage","Type"};
    pr.getTableau().setLibelleAffiche(libEnteteAffiche);
%>
<div class="box-body">
    <% if (pr.getTableau().getHtml() != null) {
           out.println(pr.getTableau().getHtml());
       } else { %>
           <center><h4>Aucune pi&egrave;ce trouv&eacute;e</h4></center>
    <% } %>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>