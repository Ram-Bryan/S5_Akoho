<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsert" %>
<%@ page import="affichage.Liste" %>
<%@ page import="user.UserEJB" %>
<%@ page import="ferme.receptionlieux.ReceptionLieux" %>
<%@ page import="ferme.lieu.Lieu" %>
<%@ page import="utilitaire.*" %>
<%@ page import="java.sql.Connection" %>

<%
try {
    UserEJB u = (UserEJB) session.getValue("u");
    String lien = (String) session.getValue("lien");

    String idLotParam = request.getParameter("idLot");
    if (idLotParam == null || idLotParam.trim().isEmpty()) {
        throw new Exception("Lot manquant dans l'URL.");
    }

    ReceptionLieux o = new ReceptionLieux();
    o.setNomTable("RECEPTIONLIEUX");
    PageInsert pi = new PageInsert(o, request, u);
    pi.setLien(lien);
    pi.setTitre("Réception sur site");

    // Labels
    pi.getFormu().getChamp("idLot").setLibelle("Lot");
    pi.getFormu().getChamp("daty").setLibelle("Date de réception");
    pi.getFormu().getChamp("qteRecus").setLibelle("Quantité reçue (morts et perdus compris)");
    pi.getFormu().getChamp("nbMort").setLibelle("Morts");
    pi.getFormu().getChamp("nbPerdu").setLibelle("Perdus");
    pi.getFormu().getChamp("heureDepart").setLibelle("Heure de départ");
    pi.getFormu().getChamp("heureDepart").setType("time");
    pi.getFormu().getChamp("heureArrive").setLibelle("Heure d'arrivée");
    pi.getFormu().getChamp("heureArrive").setType("time");
    pi.getFormu().getChamp("remarque").setLibelle("Remarque");

    // Hidden
    pi.getFormu().getChamp("ordre").setVisible(false);
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("idLot").setVisible(false);   // set via setDefaut, hidden

    // Lieu = LIEU_ETAPE view
    Lieu lieuEtape = new Lieu();
    lieuEtape.setNomTable("LIEU_ETAPE");
    Liste listeLieu = new Liste("idLieu", lieuEtape, "libelle", "id");
    pi.getFormu().changerEnChamp(new Liste[]{ listeLieu });
    pi.getFormu().getChamp("idLieu").setLibelle("Lieu de réception");

    // Order
    pi.getFormu().setOrdre(new String[]{
        "daty", "heureDepart", "heureArrive", "idLieu",
        "qteRecus", "nbMort", "nbPerdu", "remarque"
    });

    // --- PRE-FILL ---
    Connection c = null;
    try {
        c = new UtilDB().GetConn();

        // 1. Check that a previous step exists & is validated (else throw)
        int nextOrdre = ReceptionLieux.getNextOrdre(idLotParam, c);
        double prevVivants = ReceptionLieux.getVivantsPrecedents(idLotParam, nextOrdre, c);

        // 2. Set defaults
        pi.getFormu().getChamp("idLot").setDefaut(idLotParam);
        pi.getFormu().getChamp("daty").setDefaut(Utilitaire.dateDuJour());
        pi.getFormu().getChamp("heureArrive").setDefaut(Utilitaire.heureCouranteHM());
        pi.getFormu().getChamp("heureDepart").setDefaut("");
        pi.getFormu().getChamp("qteRecus").setDefaut(
            new java.text.DecimalFormat("0.##").format(prevVivants));
        pi.getFormu().getChamp("nbMort").setDefaut("0");
        pi.getFormu().getChamp("nbPerdu").setDefaut("0");
    } finally {
        if (c != null) c.close();
    }

    pi.preparerDataFormu();
    pi.getFormu().makeHtmlInsertTabIndex();
%>

<div class="content-wrapper">
    <h1><%= pi.getTitre() %></h1>
    <form action="<%= pi.getLien() %>?but=apresTarif.jsp" method="post">
        <% out.println(pi.getFormu().getHtmlInsert()); %>
        <input name="acte"     type="hidden" value="insert">
        <input name="bute"     type="hidden" value="ferme/receptionlieux/receptionlieux-fiche.jsp">
        <input name="classe"   type="hidden" value="ferme.receptionlieux.ReceptionLieux">
        <input name="nomtable" type="hidden" value="RECEPTIONLIEUX">
    </form>
</div>

<% } catch (Exception e) {
    e.printStackTrace();
%>
<script language="JavaScript"> alert('<%= e.getMessage().replace("'", "\\'") %>'); history.back(); </script>
<% } %>