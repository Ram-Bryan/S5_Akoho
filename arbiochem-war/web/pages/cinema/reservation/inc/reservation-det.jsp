<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8;" %>
<%@ page import="cinema.CinemaDetailReservationCpl" %>
<%@ page import="user.*" %>
<%@ page import="bean.*" %>
<%@ page import="utilitaire.*" %>
<%@ page import="affichage.*" %>

<%
    try {
        CinemaDetailReservationCpl t = new CinemaDetailReservationCpl();
        t.setNomTable("cinema_detail_reservation_cpl");
        
        String listeCrt[] = {};
        String listeInt[] = {};
        String libEntete[] = {"id", "placelib", "rangelib", "sallelib", "prix"};
        
        PageRecherche pr = new PageRecherche(t, request, listeCrt, listeInt, 3, libEntete, libEntete.length);
        pr.setUtilisateur((user.UserEJB) session.getValue("u"));
        pr.setLien((String) session.getValue("lien"));
        
        if (request.getParameter("id") != null) {
            pr.setAWhere(" and idreservation='" + request.getParameter("id") + "' order by placelib");
        }
        
        String[] colSomme = null;
        pr.creerObjetPage(libEntete, colSomme);
%>

<div class="box-body">
    <%
        String libEnteteAffiche[] = {"ID", "Place", "Range", "Salle", "Prix (Ar)"};
        pr.getTableau().setLibelleAffiche(libEnteteAffiche);
        
        if (pr.getTableau().getHtml() != null) {
            out.println(pr.getTableau().getHtml());
    %>
    <div class="w-100" style="display: flex; flex-direction: row-reverse;">
        <table style="width: 30%" class="table">
            <tr>
                <td><b>Nombre de places :</b></td>
                <td><b><%= pr.getListe().length %></b></td>
            </tr>
            <tr>
                <td><b>Total &agrave; payer :</b></td>
                <td><b><%= Utilitaire.formaterAr(AdminGen.calculSommeDouble(pr.getListe(), "prix")) %> Ar</b></td>
            </tr>
        </table>
    </div>
    <% 
        } else { 
    %>
        <div style="text-align: center;"><h4>Aucune place réservée</h4></div>
    <% 
        } 
    %>
</div>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>