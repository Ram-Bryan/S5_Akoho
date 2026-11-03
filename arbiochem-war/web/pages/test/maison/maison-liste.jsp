<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageRecherche"%>
<%@ page import="test.maison.Maison" %>

<% try{
    Maison o = new Maison();
    String[] listeCrt  = {"id","nom","ville","codepostal","anneconstruction"};
    String[] listeInt  = {"anneconstruction"};
    String[] libEntete = {"id","nom","ville","codepostal","surface","anneconstruction","etat"};
    PageRecherche pr = new PageRecherche(o, request, listeCrt, listeInt, 4, libEntete, libEntete.length);
    pr.setTitre("Liste des maisons");
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("test/maison/maison-liste.jsp");

    pr.getFormu().getChamp("id").setLibelle("Id");
    pr.getFormu().getChamp("nom").setLibelle("Nom");
    pr.getFormu().getChamp("ville").setLibelle("Ville");
    pr.getFormu().getChamp("codepostal").setLibelle("Code postal");
    pr.getFormu().getChamp("anneconstruction1").setLibelle("Ann&eacute;e min");
    pr.getFormu().getChamp("anneconstruction2").setLibelle("Ann&eacute;e max");

    pr.creerObjetPage(libEntete, null);

    pr.getFormu().setAnotherButton(
        "<a class=\"btn-secondary btn-bg-white btn btn-small\" " +
        "href=\"" + pr.getLien() + "?but=test/maison/maison-saisie.jsp\">" +
        "<i class='material-symbols-rounded'>add</i> Saisie</a>"
    );

    String[] lienTableau  = { pr.getLien() + "?but=test/maison/maison-fiche.jsp" };
    String[] colonneLien  = {"id"};
    String[] attributLien = {"id"};
    pr.getTableau().setLien(lienTableau);
    pr.getTableau().setColonneLien(colonneLien);
    pr.getTableau().setAttLien(attributLien);

    String[] libEnteteAffiche = {"Id","Nom","Ville","Code postal","Surface","Ann&eacute;e","&Eacute;tat"};
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