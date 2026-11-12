<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageRecherche" %>
<%@ page import="cinema.CinemaCategorieRange" %>

<% try {
    CinemaCategorieRange o = new CinemaCategorieRange();
    
    // On ajoute "prix" aux critères si on veut pouvoir filtrer dessus
    String[] listeCrt  = {"id", "libelle", "prix"}; 
    String[] listeInt  = {};
    
    // On ajoute "prix" aux colonnes affichées
    String[] libEntete = {"id", "libelle", "prix"};
    
    PageRecherche pr = new PageRecherche(o, request, listeCrt, listeInt, 4, libEntete, libEntete.length);
    pr.setTitre("Liste des catégories de range");
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("cinema/categorieRange/categorieRange-liste.jsp");

    pr.getFormu().getChamp("id").setLibelle("Id");
    pr.getFormu().getChamp("libelle").setLibelle("Libellé");
    
    // Comme "prix" est maintenant dans listeCrt, ce getChamp ne retournera plus null
    pr.getFormu().getChamp("prix").setLibelle("Supplément max");

    pr.creerObjetPage(libEntete, null);

    pr.getFormu().setAnotherButton(
        "<a class=\"btn-secondary btn-bg-white btn btn-small\" " +
        "href=\"" + pr.getLien() + "?but=cinema/categorieRange/categorieRange-saisie.jsp\">" +
        "<i class='material-symbols-rounded'>add</i> Saisie</a>"
    );

    pr.getTableau().setLien(new String[]{ pr.getLien() + "?but=cinema/categorieRange/categorieRange-fiche.jsp" });
    pr.getTableau().setColonneLien(new String[]{"id"});
    pr.getTableau().setAttLien(new String[]{"id"});
    
    // Libellés d'affichage du tableau
    pr.getTableau().setLibelleAffiche(new String[]{"Id", "Libellé", "Supplément (Ar)"});
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