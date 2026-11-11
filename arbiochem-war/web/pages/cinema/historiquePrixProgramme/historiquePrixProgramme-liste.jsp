<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageRecherche" %>
<%@ page import="affichage.Liste" %>
<%@ page import="cinema.CinemaHistoriquePrixProgramme" %>
<%@ page import="cinema.CinemaProgramme" %>

<% try {
    CinemaHistoriquePrixProgramme o = new CinemaHistoriquePrixProgramme();
    String[] listeCrt  = {"id", "idprogramme", "datedebut"};
    String[] listeInt  = {"datedebut"};
    String[] libEntete = {"id", "idprogramme", "prix", "datedebut"};
    PageRecherche pr = new PageRecherche(o, request, listeCrt, listeInt, 4, libEntete, libEntete.length);
    pr.setTitre("Historique des prix programmes");
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("cinema/historiquePrixProgramme/historiquePrixProgramme-liste.jsp");

    Liste listeProg = new Liste("idprogramme", new CinemaProgramme(), "id", "id");
    pr.getFormu().changerEnChamp(new Liste[]{ listeProg });

    pr.getFormu().getChamp("id").setLibelle("Id");
    pr.getFormu().getChamp("idprogramme").setLibelle("Programme");
    pr.getFormu().getChamp("datedebut1").setLibelle("Date min");
    pr.getFormu().getChamp("datedebut2").setLibelle("Date max");

    pr.creerObjetPage(libEntete, null);

    pr.getFormu().setAnotherButton(
        "<a class=\"btn-secondary btn-bg-white btn btn-small\" " +
        "href=\"" + pr.getLien() + "?but=cinema/historiquePrixProgramme/historiquePrixProgramme-saisie.jsp\">" +
        "<i class='material-symbols-rounded'>add</i> Saisie</a>"
    );

    pr.getTableau().setLien(new String[]{ pr.getLien() + "?but=cinema/historiquePrixProgramme/historiquePrixProgramme-fiche.jsp" });
    pr.getTableau().setColonneLien(new String[]{"id"});
    pr.getTableau().setAttLien(new String[]{"id"});
    pr.getTableau().setLibelleAffiche(new String[]{"Id", "Programme", "Prix", "Date début"});
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