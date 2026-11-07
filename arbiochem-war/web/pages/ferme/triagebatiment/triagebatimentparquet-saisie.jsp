<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsertMultiple"%>
<%@ page import="user.UserEJB" %>
<%@ page import="ferme.triageBatiment.TriageBatimentParquet" %>
<%@ page import="ferme.triageBatiment.TriageBatimentParquetDetail" %>
<%@ page import="affichage.Champ" %>
<%@ page import="affichage.Liste" %>
<%@ page import="bean.TypeObjet" %>
<%@ page import="bean.CGenUtil" %>
<%@ page import="ferme.configuration.Parquet" %>
<%@ page import="ferme.utils.ConstanteFerme" %>
<%@ page import="ferme.configuration.Batiment" %>
<%@ page import="ferme.configuration.ParquetBatiment" %>
<%@ page import="ferme.configuration.QualitePoussin" %>
<%@ page import="ferme.receptionlieux.ReceptionLieux" %>
<%@ page import="utilitaire.UtilDB" %>
<%@ page import="java.sql.Connection" %>

<% try{ 
    UserEJB u = (user.UserEJB) session.getValue("u");
    String classeMere = "ferme.triageBatiment.TriageBatimentParquet";
    String classeFille = "ferme.triageBatiment.TriageBatimentParquetDetail";
    String nomTableFille = "TRIAGEBATIMENTPARQUETDETAIL";
    String colonneMere = "idmere";
    String apres = "ferme/triagebatiment/triagebatimentparquet-fiche.jsp";

    String idLot = request.getParameter("idLot");

    TriageBatimentParquet mere = new TriageBatimentParquet();
    mere.setNomTable("TRIAGEBATIMENTPARQUET");
    TriageBatimentParquetDetail fille = new TriageBatimentParquetDetail();
    fille.setNomTable("TRIAGEBATIMENTPARQUETDETAIL");
    int taille = 10;
    PageInsertMultiple pi = new PageInsertMultiple(mere, fille, request, taille, u);
    pi.setLien((String) session.getValue("lien"));  
    pi.setTitre("Saisie triage batiment par parquet");

    pi.getFormu().getChamp("daty").setLibelle("Date");
    pi.getFormu().getChamp("idlot").setLibelle("lot");
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("idlot").setPageAppelComplete("ferme.lot.Lot","id","LOT","id","id");

    if (idLot != null && !idLot.isEmpty()) {
        pi.getFormu().getChamp("idlot").setDefaut(idLot);
        pi.getFormu().getChamp("idlot").setAutre("readonly");

        double femelleDispo = mere.getDisponnibleLib(idLot, ConstanteFerme.IDSEXEFEMELLE, ConstanteFerme.qualiteConforme, null);
        double maleDispo    = mere.getDisponnibleLib(idLot, ConstanteFerme.IDSEXEMALE,   ConstanteFerme.qualiteConforme, null);
        pi.getFormu().getChamp("dispomale").setDefaut(Double.toString(maleDispo));
        pi.getFormu().getChamp("dispofemelle").setDefaut(Double.toString(femelleDispo));
        pi.getFormu().getChamp("dispomale").setAutre("readonly");
        pi.getFormu().getChamp("dispofemelle").setAutre("readonly");
    }

    // Hidden — replaced by the "vivants disponibles" banner below
    pi.getFormu().getChamp("dispomale").setVisible(false);
    pi.getFormu().getChamp("dispofemelle").setVisible(false);

    // ---- Compute vivants available + already triaged ----
    double vivantsDispo = 0;
    double alreadyTriaged = 0;
    String vivantsMsg = null;
    if (idLot != null && !idLot.isEmpty()) {
        Connection cViv = null;
        try {
            cViv = new UtilDB().GetConn();
            vivantsDispo = ReceptionLieux.getVivantsDisponibles(idLot, cViv);

            Object o = CGenUtil.rechercherSimple(
                "SELECT NVL(SUM(D.QTE), 0) FROM TRIAGEBATIMENTPARQUETDETAIL D "
                + "JOIN TRIAGEBATIMENTPARQUET M ON M.ID = D.IDMERE "
                + "WHERE M.IDLOT = '" + idLot.replace("'", "''") + "' "
                + "AND M.ETAT != 0",
                cViv);
            alreadyTriaged = (o == null) ? 0 : ((Number) o).doubleValue();
        } catch (Exception ex) {
            vivantsMsg = ex.getMessage();
        } finally {
            if (cViv != null) try { cViv.close(); } catch (Exception ex) {}
        }
    }
    double reste = vivantsDispo - alreadyTriaged;

    // ---- Fille: batiment, parquet, sexe, qualite (hidden), indicesante, qte ----
    Liste[] liste = new Liste[4];
    TypeObjet sexe = new TypeObjet();
    sexe.setNomTable("SEXE");
    liste[0] = new Liste("idsexe", sexe, "val", "id");

    ParquetBatiment parquet = new ParquetBatiment();
    liste[2] = new Liste("idparquet", parquet, "val", "id");

    liste[1] = new Liste("idbatiment", new Batiment(), "nomBatiment", "id");
    liste[1].setDeroulanteDependante(liste[2], "idbatiment", "onchange");

    QualitePoussin qualitePoussin = new QualitePoussin();
    liste[3] = new Liste("idqualite", qualitePoussin, "val", "id");

    Champ.setDefaut(pi.getFormufle().getChampMulitple("idqualite").getListeChamp(),
                    ConstanteFerme.qualiteConforme);
    Champ.setVisible(pi.getFormufle().getChampMulitple("idqualite").getListeChamp(), false);

    pi.getFormufle().changerEnChamp(liste);

    // indicesante as a 1..10 dropdown
    pi.getFormufle().changerEnChamp(new Liste[]{
        ConstanteFerme.getListeIndiceSante("indicesante", "Indice santé (1-10)")
    });

   
    // Labels
    pi.getFormufle().getChamp("idbatiment_0").setLibelle("B&acirc;timent");
    pi.getFormufle().getChamp("idparquet_0").setLibelle("Parquet");
    pi.getFormufle().getChamp("idsexe_0").setLibelle("Sexe");
    pi.getFormufle().getChamp("qte_0").setLibelle("Quantit&eacute;");
    pi.getFormufle().getChamp("indicesante_0").setLibelle("Indice sant&eacute; (1-10)");
    pi.getFormufle().getChamp("idqualite_0").setLibelle("Qualit&eacute;");

    Champ.setVisible(pi.getFormufle().getChampMulitple("idmere").getListeChamp(), false);

    String[] colOrdre = {"idbatiment","idparquet","idsexe","indicesante","qte"};
    pi.getFormufle().setColOrdre(colOrdre);

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) {
        pi.setTitre("Modification triage batiment par parquet");
    }

    pi.preparerDataFormu();
    pi.getFormu().makeHtmlInsertTabIndex();
    pi.getFormufle().makeHtmlInsertTableauIndex();
%>

<div class="content-wrapper">
    <h1><%=pi.getTitre()%></h1>

    <% if (idLot != null && !idLot.isEmpty()) { %>
        <% if (vivantsMsg != null) { %>
            <div class="alert alert-warning" style="margin-bottom: 15px;">
                <strong>Attention :</strong> <%= vivantsMsg %>
            </div>
        <% } else { %>
            <p style="margin: 0 0 15px 0;">
                <strong>Poussins vivants à répartir :</strong>
                <span style="color:#c00; font-weight:bold;">
                    <%= new java.text.DecimalFormat("0.##").format(reste) %>
                </span>
                <br/>
                <small style="color:#555;">
                    Total reçu pour ce lot : <%= new java.text.DecimalFormat("0.##").format(vivantsDispo) %>
                    &minus; déjà réparti : <%= new java.text.DecimalFormat("0.##").format(alreadyTriaged) %>
                    <br/>
                    Vous devez répartir TOUS ces poussins dans les parquets ci-dessous.
                </small>
            </p>
        <% } %>
    <% } %>

    <form id="formId" class='container' action="<%=pi.getLien()%>?but=apresMultiple.jsp" method="post" >
        <%
            out.println(pi.getFormu().getHtmlInsert());
            out.println(pi.getFormufle().getHtmlTableauInsert());
        %>
        <input name="acte" type="hidden" id="nature" value="insert">
        <input name="bute" type="hidden" id="bute" value="<%=apres%>">
        <input name="classe" type="hidden" id="classe" value="<%=classeMere%>">
        <input name="classefille" type="hidden" id="classefille" value="<%=classeFille%>">
        <input name="nomtable" type="hidden" id="nomtable" value="<%=nomTableFille%>">
        <input name="nombreLigne" type="hidden" id="nombreLigne" value="<%=taille%>">
        <input name="colonneMere" type="hidden" id="colonneMere" value="<%=colonneMere%>">
    </form>

    <%-- Live running total — recomputes as the user fills qte_0..qte_9 --%>
    <div style="margin-top: 15px; padding: 10px 15px; border-top: 1px solid #eee;">
        <strong>Total réparti dans le formulaire :</strong>
        <span id="runningTotal" style="font-weight:bold; font-size:1.1em;">0</span>
        <span id="runningTotalStatus" style="color:#c00; margin-left:10px;"></span>
    </div>

    <script>
    (function() {
        var target = <%= new java.text.DecimalFormat("0.##").format(reste) %>;

        function recompute() {
            var total = 0;
            for (var i = 0; i < <%= taille %>; i++) {
                var el = document.getElementById("qte_" + i);
                if (el && el.value) {
                    var v = parseFloat(el.value);
                    if (!isNaN(v)) total += v;
                }
            }
            var span = document.getElementById("runningTotal");
            var status = document.getElementById("runningTotalStatus");
            if (span) {
                span.textContent = total.toFixed(2).replace(/\.?0+$/, '');
            }
            if (status) {
                if (Math.abs(total - target) < 0.01) {
                    span.style.color = "green";
                    status.textContent = "✓ Complet";
                    status.style.color = "green";
                } else if (total < target) {
                    span.style.color = "#c00";
                    status.textContent = "Reste " + (target - total).toFixed(2).replace(/\.?0+$/, '') + " à répartir";
                    status.style.color = "#c00";
                } else {
                    span.style.color = "#c00";
                    status.textContent = "Dépasse de " + (total - target).toFixed(2).replace(/\.?0+$/, '');
                    status.style.color = "#c00";
                }
            }
        }

        for (var i = 0; i < <%= taille %>; i++) {
            var el = document.getElementById("qte_" + i);
            if (el) {
                el.addEventListener("input", recompute);
                el.addEventListener("change", recompute);
            }
        }
        recompute();
    })();
    </script>
</div>

<% } catch (Exception e) {
    e.printStackTrace();
%>
<script language="JavaScript"> alert('<%= e.getMessage() == null ? e.getClass().getName() : e.getMessage().replace("'", "\\'") %>');
    history.back();
</script>
<% }%>