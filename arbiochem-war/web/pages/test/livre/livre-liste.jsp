<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageRecherche"%>
<%@ page import="test.livre.Livre" %>

<% try{
    Livre o = new Livre();
    String[] listeCrt  = {"id","titre","auteur","isbn","editeur","anneepublication"};
    String[] listeInt  = {"anneepublication"};
    String[] libEntete = {"id","titre","auteur","isbn","anneepublication","editeur","prix","etat"};
    PageRecherche pr = new PageRecherche(o, request, listeCrt, listeInt, 4, libEntete, libEntete.length);
    pr.setTitre("Liste des livres");
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("test/livre/livre-liste.jsp");

    pr.getFormu().getChamp("id").setLibelle("Id");
    pr.getFormu().getChamp("titre").setLibelle("Titre");
    pr.getFormu().getChamp("auteur").setLibelle("Auteur");
    pr.getFormu().getChamp("isbn").setLibelle("ISBN");
    pr.getFormu().getChamp("anneepublication1").setLibelle("Ann&eacute;e min");
    pr.getFormu().getChamp("anneepublication2").setLibelle("Ann&eacute;e max");

    pr.creerObjetPage(libEntete, null);

    pr.getFormu().setAnotherButton(
        "<a class=\"btn-secondary btn-bg-white btn btn-small\" " +
        "href=\"" + pr.getLien() + "?but=test/livre/livre-saisie.jsp\">" +
        "<i class='material-symbols-rounded'>add</i> Saisie</a>"
    );

    String[] lienTableau  = { pr.getLien() + "?but=test/livre/livre-fiche.jsp" };
    String[] colonneLien  = {"id"};
    String[] attributLien = {"id"};
    pr.getTableau().setLien(lienTableau);
    pr.getTableau().setColonneLien(colonneLien);
    pr.getTableau().setAttLien(attributLien);

    String[] libEnteteAffiche = {"Id","Titre","Auteur","ISBN","Ann&eacute;e","&Eacute;diteur","Prix","&Eacute;tat"};
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