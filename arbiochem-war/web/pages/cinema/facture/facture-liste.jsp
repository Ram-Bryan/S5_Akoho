<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageRecherche" %>
<%@ page import="cinema.FactureClient" %>

<% try {
    FactureClient o = new FactureClient();
    String[] listeCrt  = {"id", "idclient", "idreservationcinema", "daty"};
    String[] listeInt  = {"daty"};
    String[] libEntete = {"id", "idclient", "idreservationcinema", "daty", "etat"};
    PageRecherche pr = new PageRecherche(o, request, listeCrt, listeInt, 4, libEntete, libEntete.length);
    pr.setTitre("Liste des factures");
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("cinema/facture/facture-liste.jsp");

    pr.getFormu().getChamp("id").setLibelle("Id");
    pr.getFormu().getChamp("idclient").setLibelle("Client");
    pr.getFormu().getChamp("idreservationcinema").setLibelle("Réservation");
    pr.getFormu().getChamp("daty1").setLibelle("Date min");
    pr.getFormu().getChamp("daty2").setLibelle("Date max");

    pr.creerObjetPage(libEntete, null);

    pr.getTableau().setLien(new String[]{ pr.getLien() + "?but=cinema/facture/facture-fiche.jsp" });
    pr.getTableau().setColonneLien(new String[]{"id"});
    pr.getTableau().setAttLien(new String[]{"id"});
    pr.getTableau().setLibelleAffiche(new String[]{"Numéro", "Client", "Réservation", "Date", "État"});
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