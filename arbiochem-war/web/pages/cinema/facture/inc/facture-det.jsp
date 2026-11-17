<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8;" %>
<%@ page import="cinema.FactureClientDetails" %>
<%@ page import="user.*" %>
<%@ page import="bean.*" %>
<%@ page import="utilitaire.*" %>
<%@ page import="affichage.*" %>

<%
    try {
        FactureClientDetails t = new FactureClientDetails();
        String listeCrt[] = {};
        String listeInt[] = {};
        String libEntete[] = {"id", "idfactureclient", "qte", "pu"};
        
        PageRecherche pr = new PageRecherche(t, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
        pr.setUtilisateur((user.UserEJB) session.getValue("u"));
        pr.setLien((String) session.getValue("lien"));
        
        if (request.getParameter("id") != null) {
            pr.setAWhere(" and idfactureclient = '" + request.getParameter("id") + "' ");
        }
        
        String[] colSomme = null;
        pr.creerObjetPage(libEntete, colSomme);
%>
<div class="box-body">
    <%
        String libEnteteAffiche[] = {"ID", "Facture", "Quantité", "Prix Unitaire"};
        pr.getTableau().setLibelleAffiche(libEnteteAffiche);
        
        if (pr.getTableau().getHtml() != null) {
            out.println(pr.getTableau().getHtml());
    %>
    <div class="w-100" style="display: flex; flex-direction: row-reverse;">
        <table style="width: 30%" class="table">
            <tr>
                <td><b>Total :</b></td>
                <td><b><%= Utilitaire.formaterAr(AdminGen.calculSommeDouble(pr.getListe(), "pu")) %> Ar</b></td>
            </tr>
        </table>
    </div>
    <% 
        } else { 
    %>
        <div style="text-align: center;"><h4>Aucun détail pour cette facture</h4></div>
    <% 
        } 
    %>
</div>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>