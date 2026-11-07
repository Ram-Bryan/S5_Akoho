<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="user.UserEJB" %>
<%@ page import="affichage.PageConsulte" %>
<%@ page import="ferme.receptionlieux.ReceptionLieux" %>
<%@ page import="ferme.lieu.Lieu" %>
<%@ page import="constante.ConstanteEtat" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="utilitaire.UtilDB" %>
<%@ page import="utilitaire.Utilitaire" %>

<%
Connection c = null;
try {
    c = new UtilDB().GetConn();

    UserEJB u = (UserEJB) session.getValue("u");
    String lien = (String) session.getValue("lien");

    ReceptionLieux o = new ReceptionLieux();
    o.setNomTable("RECEPTIONLIEUX");
    PageConsulte pc = new PageConsulte(o, request, u);
    pc.setTitre("Fiche de réception sur site");
    String id = pc.getBase().getTuppleID();
    o = (ReceptionLieux) pc.getBase();

    // Labels — hide raw FK that we'll display manually
    pc.getChampByName("id").setLibelle("Id");
    pc.getChampByName("idLot").setLibelle("Lot");
    pc.getChampByName("ordre").setLibelle("Étape");
    pc.getChampByName("daty").setLibelle("Date");
    pc.getChampByName("qteRecus").setLibelle("Reçus");
    pc.getChampByName("nbMort").setLibelle("Morts");
    pc.getChampByName("nbPerdu").setLibelle("Perdus");
    pc.getChampByName("heureDepart").setLibelle("Heure départ");
    pc.getChampByName("heureArrive").setLibelle("Heure arrivée");
    pc.getChampByName("remarque").setLibelle("Remarque");

    String lieuLib = "—";
    if (o.getIdLieu() != null && !o.getIdLieu().trim().isEmpty()) {
        Lieu l = (Lieu) new Lieu().getById(o.getIdLieu(), null, c);
        if (l != null) lieuLib = l.getLibelle();
    }

    pc.getChampByName("idLieu").setLibelle("Lieu de réception");
    pc.getChampByName("idLieu").setValeur(lieuLib);

    pc.getChampByName("etat").setVisible(false);

    pc.setOrdre(new String[]{
        "ordre", "daty", "idLieu", "heureDepart", "heureArrive",
        "qteRecus", "nbMort", "nbPerdu", "remarque"
    });

    // Vivants + avertissement
    double vivants = o.getVivants();
    String avert = o.getAvertissement(c);

    String pageRetour      = "ferme/lot/lot-fiche.jsp&id=" + o.getIdLot();
    String pageActuel      = "ferme/receptionlieux/receptionlieux-fiche.jsp";
    String pageModif       = "ferme/receptionlieux/receptionlieux-saisie.jsp&acte=update";
    String pageApresDelete = "ferme/lot/lot-fiche.jsp&id=" + o.getIdLot();
    String pageSaisieSuiv  = "ferme/receptionlieux/receptionlieux-saisie.jsp&idLot=" + o.getIdLot();
    String pageTriage      = "ferme/triagebatiment/triagebatimentparquet-saisie.jsp&idLot=" + o.getIdLot();
    String classe          = "ferme.receptionlieux.ReceptionLieux";
%>

<div class="content-wrapper">
    <h1 class="box-title">
        <a href="<%= lien %>?but=<%= pageRetour %>"><i class="fa fa-angle-left"></i></a>
        <%= pc.getTitre() %> (Étape <%= o.getOrdre() %>)
    </h1>

    <div class="row m-0">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <div class="box-fiche"><div class="box"><div class="box-body">
                <% out.println(pc.getHtml()); %>

                <!-- <p><strong>Lieu de réception :</strong> <%= lieuLib %></p> -->

                <!-- <div class="alert alert-info" style="margin-top: 15px;">
                    <strong>Poussins vivants :</strong>
                    <%= new java.text.DecimalFormat("0.##").format(vivants) %>
                    <small style="color:#555;">
                        (reçus : <%= new java.text.DecimalFormat("0.##").format(o.getQteRecus()) %>
                        &minus; morts : <%= new java.text.DecimalFormat("0.##").format(o.getNbMort()) %>
                        &minus; perdus : <%= new java.text.DecimalFormat("0.##").format(o.getNbPerdu()) %>)
                    </small>
                </div> -->

                <% if (avert != null) { %>
                    <div class="alert alert-warning">
                        <strong>Attention :</strong> <%= avert %>
                    </div>
                <% } %>

                <br/>
                <div class="box-footer">
                    <a class="btn btn-danger pull-left"
                       href="<%= lien %>?but=apresTarif.jsp&id=<%= id %>&acte=delete&bute=<%= pageApresDelete %>&classe=<%= classe %>">
                       Supprimer
                    </a>
                    <% if (o.getEtat() < ConstanteEtat.getEtatValider()) { %>
                        <a class="btn btn-secondary pull-right"
                           href="<%= lien %>?but=<%= pageModif %>&id=<%= id %>"
                           style="margin-right:10px">Modifier</a>
                        <a class="btn btn-primary pull-right"
                           href="<%= lien %>?but=apresTarif.jsp&acte=valider&classe=<%= classe %>&nomtable=RECEPTIONLIEUX&bute=<%= pageActuel %>&id=<%= id %>"
                           style="margin-right:10px">Viser</a>
                    <% } %>
                    <% if (o.getEtat() >= ConstanteEtat.getEtatValider()) { %>
                        <a class="btn btn-primary pull-right"
                           href="<%= lien %>?but=<%= pageSaisieSuiv %>"
                           style="margin-right:10px">Aller au prochain point</a>
                        <a class="btn btn-tertiary pull-right"
                           href="<%= lien %>?but=<%= pageTriage %>"
                           style="margin-right:10px">Triage dans les bâtiments</a>
                    <% } %>
                </div>
                <br/>
            </div></div></div>
        </div>
    </div>
</div>

<% } catch (Exception e) {
    e.printStackTrace();
%>
<script language="JavaScript"> alert('<%= e.getMessage() == null ? e.getClass().getName() : e.getMessage().replace("'", "\\'") %>'); history.back(); </script>
<% } finally {
    if (c != null) try { c.close(); } catch (Exception e) {}
} %>