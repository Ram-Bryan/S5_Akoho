<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageRecherche" %>
<%@ page import="affichage.Liste" %>
<%@ page import="cinema.CinemaPaiement" %>
<%@ page import="faturefournisseur.ModePaiement" %>
<%@ page import="cinema.FactureClient" %>
<%@ page import="utilitaire.Utilitaire" %>

<% try {
    CinemaPaiement o = new CinemaPaiement();
    String[] listeCrt  = {"id", "idfacture", "daty"};
    String[] listeInt  = {"daty"};
    String[] libEntete = {"id", "idfacture", "daty", "montant", "idmodepaiement"};
    
    PageRecherche pr = new PageRecherche(o, request, listeCrt, listeInt, 10, libEntete, libEntete.length);
    pr.setTitre("Liste des paiements");
    pr.setUtilisateur((user.UserEJB) session.getValue("u"));
    pr.setLien((String) session.getValue("lien"));
    pr.setApres("cinema/paiement/paiement-liste.jsp");

    pr.getFormu().getChamp("id").setLibelle("Id");
    pr.getFormu().getChamp("idfacture").setLibelle("Facture");
    pr.getFormu().getChamp("daty1").setLibelle("Date min");
    pr.getFormu().getChamp("daty2").setLibelle("Date max");

    pr.creerObjetPage(libEntete, null);

    pr.getFormu().setAnotherButton(
        "<a class=\"btn-secondary btn-bg-white btn btn-small\" " +
        "href=\"" + pr.getLien() + "?but=cinema/paiement/paiement-saisie.jsp\">" +
        "<i class='material-symbols-rounded'>add</i> Saisie</a>"
    );

    pr.getTableau().setLien(new String[]{ pr.getLien() + "?but=cinema/paiement/paiement-fiche.jsp" });
    pr.getTableau().setColonneLien(new String[]{"id"});
    pr.getTableau().setAttLien(new String[]{"id"});
    pr.getTableau().setLibelleAffiche(new String[]{"Id", "Facture", "Date", "Montant (Ar)", "Mode de paiement"});
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