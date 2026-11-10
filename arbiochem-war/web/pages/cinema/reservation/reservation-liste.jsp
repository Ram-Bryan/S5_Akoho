<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageRecherche" %>
<%@ page import="cinema.CinemaReservation" %>

<% try {
    CinemaReservation o = new CinemaReservation();
    String[] listeCrt  = {"id", "idclient", "idprogramme", "datereservation"};
    String[] listeInt  = {"datereservation"};
    String[] libEntete = {"id", "idclient", "idprogramme", "datereservation", "idstatutreservation", "etat"};
    PageRecherche pr = new PageRecherche(o, request, listeCrt, listeInt, 4, libEntete, libEntete.length);
    pr.setTitre("Liste des réservations");
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("cinema/reservation/reservation-liste.jsp");

    pr.getFormu().getChamp("id").setLibelle("Id");
    pr.getFormu().getChamp("idclient").setLibelle("Client");
    pr.getFormu().getChamp("idprogramme").setLibelle("Programme");
    pr.getFormu().getChamp("datereservation1").setLibelle("Date min");
    pr.getFormu().getChamp("datereservation2").setLibelle("Date max");

    pr.creerObjetPage(libEntete, null);

    pr.getFormu().setAnotherButton(
        "<a class=\"btn-secondary btn-bg-white btn btn-small\" " +
        "href=\"" + pr.getLien() + "?but=cinema/reservation/reservation-saisie.jsp\">" +
        "<i class='material-symbols-rounded'>add</i> Saisie</a>"
    );

    String[] lienTableau  = { pr.getLien() + "?but=cinema/reservation/reservation-fiche.jsp" };
    String[] colonneLien  = {"id"};
    String[] attributLien = {"id"};
    pr.getTableau().setLien(lienTableau);
    pr.getTableau().setColonneLien(colonneLien);
    pr.getTableau().setAttLien(attributLien);

    String[] libEnteteAffiche = {"Id", "Client", "Programme", "Date", "Statut", "&Eacute;tat"};
    pr.getTableau().setLibelleAffiche(libEnteteAffiche);
%>
<div class="content-wrapper">
    <section class="content-header"><h1><%= pr.getTitre() %></h1></section>
    <section class="content">
        <form action="<%=pr.getLien()%>?but=<%= pr.getApres() %>" method="post">
            <% out.println(pr.getFormu().getHtmlEnsemble()); %>
        </form>
        <% out.println(pr.getTableauRecap().getHtml()); %> <br>
        <% out.println(pr.getTableau().getHtml()); out.println(pr.getBasPage()); %>
    </section>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>