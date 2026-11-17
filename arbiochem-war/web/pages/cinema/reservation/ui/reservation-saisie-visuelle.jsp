<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="cinema.*" %>
<%@ page import="affichage.PageInsertMultiple" %>
<%@ page import="affichage.Champ" %>
<%@ page import="bean.CGenUtil" %>
<%@ page import="utilitaire.UtilDB" %>
<%@ page import="utilitaire.Utilitaire" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.*" %>
<%@ page import="affichage.Liste" %>

<%
    String lien = (String) session.getValue("lien");
    user.UserEJB u = (user.UserEJB) session.getValue("u");

    Connection c = null;
    CinemaPlaceLib[] places = null;
    CinemaDetailProgrammeLib[] detailProgrammes = null;
    Map<String, Set<String>> reservedPlacesMap = new HashMap<>();

    try {
        c = new UtilDB().GetConn();
        places = (CinemaPlaceLib[]) CGenUtil.rechercher(new CinemaPlaceLib(), null, null, c, " order by sallelib, rangelib, libelle ");
        detailProgrammes = (CinemaDetailProgrammeLib[]) CGenUtil.rechercher(new CinemaDetailProgrammeLib(), null, null, c, " order by idprogramme, filmlib ");

        String sql = "SELECT r.iddetailprogramme, d.idplace FROM cinema_reservation r " +
                     "JOIN cinema_detail_reservation d ON d.idreservation = r.id " +
                     "WHERE r.idstatutreservation NOT IN ('" + ConstanteCinema.STATUTANNULE + "', '" + ConstanteCinema.STATUTEXPIRE + "')";
        java.sql.Statement st = c.createStatement();
        java.sql.ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            String prog = rs.getString(1);
            String placeId = rs.getString(2);
            if (!reservedPlacesMap.containsKey(prog)) reservedPlacesMap.put(prog, new HashSet<String>());
            reservedPlacesMap.get(prog).add(placeId);
        }
        rs.close();
        st.close();
    } finally {
        if (c != null) try { c.close(); } catch (Exception e) {}
    }

    CinemaReservation mere = new CinemaReservation();
    CinemaDetailReservation fille = new CinemaDetailReservation();
    int taille = 20;

    PageInsertMultiple pi = new PageInsertMultiple(mere, fille, request, taille, u);
    pi.setLien(lien);

    // Client : autocomplete
    pi.getFormu().getChamp("idclient").setPageAppelComplete("client.ClientLib", "id", "CLIENT_ACTIF");

    Liste listeDetailProg = new Liste("iddetailprogramme", new CinemaDetailProgrammeLib(), "programmefilm", "id");
    pi.getFormu().changerEnChamp(new Liste[]{ listeDetailProg });

    // Champs techniques cachés
    pi.getFormu().getChamp("idstatutreservation").setVisible(false);
    pi.getFormu().getChamp("idstatutreservation").setDefaut(ConstanteCinema.STATUTRESERVE);
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("etat").setDefaut("1");
    pi.getFormu().getChamp("iduser").setVisible(false);
    pi.getFormu().getChamp("iduser").setDefaut("0");

    // Filles : masquer tous les champs (on utilise notre propre UI)
    pi.getFormufle().getChampMulitple("idplace").setVisible(false);
    pi.getFormufle().getChampMulitple("id").setVisible(false);
    pi.getFormufle().getChampMulitple("idreservation").setVisible(false);
    pi.getFormufle().getChampMulitple("etat").setVisible(false);
    pi.getFormufle().getChampMulitple("iduser").setVisible(false);

    // Libellés
    pi.getFormu().getChamp("idclient").setLibelle("Client");
    pi.getFormu().getChamp("iddetailprogramme").setLibelle("Film programmé");
    pi.getFormu().getChamp("datereservation").setLibelle("Date de réservation");
    pi.getFormu().getChamp("datereservation").setDefaut(Utilitaire.dateDuJour());

    pi.preparerDataFormu();
    pi.getFormu().makeHtmlInsertTabIndex();
    pi.getFormufle().makeHtmlInsertTableauIndex();
%>

<style>
    /* Layout 3 colonnes pour les champs du framework (Client / Programme / Date) */
    #mother-fields-wrapper .form-group {
        display: inline-block;
        width: 32%;
        vertical-align: top;
        margin-right: 1%;
        margin-bottom: 15px;
    }
    #mother-fields-wrapper .form-group:last-child {
        margin-right: 0;
    }

    /* Plan de salle */
    .plan-salle-container { background-color: #f9f9f9; padding: 30px 20px; border-radius: 8px; display: flex; flex-direction: column; align-items: flex-start; }
    .range-row { display: flex; align-items: center; margin-bottom: 12px; }
    .range-label { width: 120px; text-align: right; font-weight: bold; color: #555; margin-right: 25px; font-size: 14px; }
    .places-group { display: flex; gap: 8px; flex-wrap: wrap; }
    .place-square { width: 38px; height: 38px; border-radius: 6px; display: flex; justify-content: center; align-items: center; font-size: 12px; color: #fff; font-weight: 600; cursor: pointer; transition: transform 0.2s, box-shadow 0.2s; box-shadow: 0 2px 4px rgba(0,0,0,0.2); user-select: none; }
    .place-square:hover { transform: scale(1.12); box-shadow: 0 4px 8px rgba(0,0,0,0.3); }
    .place-standard { background-color: #0064a6; }
    .place-confort  { background-color: #28ad00; }
    .place-vip      { background-color: #dd8839; }
    .place-other    { background-color: #777; }
    .place-reserved { background-color: #dd4b39 !important; }
    .place-selected { border: 3px solid #fff; box-shadow: 0 0 0 2px #333, 0 4px 8px rgba(0,0,0,0.4); transform: scale(1.08); }
    .legend-container { margin-top: 30px; display: flex; gap: 30px; padding-top: 20px; border-top: 1px solid #ddd; width: 100%; }
    .legend-item { display: flex; align-items: center; gap: 10px; font-size: 14px; color: #555; }
    .legend-color { width: 20px; height: 20px; border-radius: 4px; }
</style>

<div class="content-wrapper">
    <h1 class="box-title" style="margin-bottom: 20px;">
        <a href="<%= lien %>?but=cinema/reservation/reservation-saisie.jsp"><i class="fa fa-angle-left"></i></a>
        Saisie visuelle d'une réservation
    </h1>

    <form id="formId" action="<%= lien %>?but=apresMultiple.jsp" method="post">

        <!-- 1. Champs mère (client autocomplete, programme, date) générés par le framework -->
        <div class="box box-primary">
            <div class="box-body" style="padding: 25px 30px;">
                <div id="mother-fields-wrapper">
                    <% out.println(pi.getFormu().getHtmlInsert()); %>
                </div>
            </div>
        </div>

        <!-- 2. Tableau filles CACHÉ (indispensable pour le submit du framework) -->
        <div style="display: none;">
            <% out.println(pi.getFormufle().getHtmlTableauInsert()); %>
        </div>

        <!-- 3. Plan de salle (notre UI custom) -->
        <div class="box" id="planContainer">
            <div class="box-body" style="min-height: 200px;">
                <p style="color: #999; text-align: center; padding: 60px 20px;">
                    <i class="fa fa-info-circle"></i> Sélectionnez un programme pour voir le plan de la salle.
                </p>
            </div>
        </div>

        <!-- 4. Récap + bouton -->
        <div class="box" style="border-top-color: #3c8dbc;">
            <div class="box-body" style="padding: 20px 30px;">
                <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 15px;">
                    <div style="flex: 1;">
                        <div style="font-size: 14px; color: #555; margin-bottom: 5px;"><strong>Places sélectionnées :</strong></div>
                        <div id="recap" style="font-size: 16px; color: #333; font-weight: 600;">Aucune</div>
                        <div style="margin-top: 5px; color: #666;">Total : <strong><span id="count">0</span></strong> place(s)</div>
                    </div>
                    <div>
                        <button type="submit" class="btn btn-primary btn-lg" id="btnSubmit" disabled>
                            <i class="fa fa-check"></i> Confirmer la réservation
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 5. Paramètres d'action -->
        <input type="hidden" name="acte" value="insert">
        <input type="hidden" name="bute" value="cinema/reservation/reservation-fiche.jsp">
        <input type="hidden" name="classe" value="cinema.CinemaReservation">
        <input type="hidden" name="classefille" value="cinema.CinemaDetailReservation">
        <input type="hidden" name="nomtable" value="cinema_detail_reservation">
        <input type="hidden" name="nombreLigne" value="<%= taille %>">
        <input type="hidden" name="colonneMere" value="idreservation">
    </form>
</div>

<script>
    // Fix pour le bug JS "checkbox is not defined" du framework
    var checkbox = false;

    // ============ Données depuis Java ============
    var cinemaPlaces = [
        <% if (places != null) { for (int i = 0; i < places.length; i++) { CinemaPlaceLib p = places[i]; %>
        { id: "<%= p.getId() %>", libelle: "<%= p.getLibelle() %>", idsalle: "<%= p.getIdsalle() %>", sallelib: "<%= p.getSallelib() %>", rangelib: "<%= p.getRangelib() %>", categorierangelib: "<%= p.getCategorierangelib() %>" }<%= (i < places.length - 1) ? "," : "" %>
        <% } } %>
    ];

    var programmeSalleMap = {
        <% if (detailProgrammes != null) { for (int i = 0; i < detailProgrammes.length; i++) { CinemaDetailProgrammeLib dp = detailProgrammes[i]; %>
        "<%= dp.getId() %>": "<%= dp.getIdsalle() %>"<%= (i < detailProgrammes.length - 1) ? "," : "" %>
        <% } } %>
    };

    var reservedPlacesMap = {
        <% boolean firstEntry = true; for (Map.Entry<String, Set<String>> e : reservedPlacesMap.entrySet()) { if (!firstEntry) out.print(","); firstEntry = false; %>
        "<%= e.getKey() %>": [ <% boolean firstPlace = true; for (String pid : e.getValue()) { if (!firstPlace) out.print(","); firstPlace = false; %>"<%= pid %>"<% } %> ]
        <% } %>
    };

    var selectedPlaces = [];

    // ============ Synchronisation avec les champs cachés du framework ============
    function syncHiddenFields() {
        // Remplir les idplace_X
        var inputsPlace = document.querySelectorAll('[name^="idplace_"]');
        inputsPlace.forEach(function(input) { input.value = ''; });
        selectedPlaces.forEach(function(id, i) {
            if (inputsPlace[i]) inputsPlace[i].value = id;
        });

        // Cocher les checkboxes du framework (filtre le "select all")
        var allCheckboxes = document.querySelectorAll('input[type="checkbox"]');
        var checkboxes = Array.from(allCheckboxes).filter(function(cb) {
            return /\d/.test(cb.name || '');
        });
        if (checkboxes.length === 0 && allCheckboxes.length > 0) {
            checkboxes = Array.from(allCheckboxes).slice(1);
        }
        checkboxes.forEach(function(cb, idx) {
            cb.checked = (idx < selectedPlaces.length);
        });
    }

    // ============ Rendu du plan ============
    function renderPlan(programmeId) {
        var boxBody = document.querySelector('#planContainer .box-body');

        if (!programmeId) {
            boxBody.innerHTML = '<p style="color: #999; text-align: center; padding: 60px 20px;"><i class="fa fa-info-circle"></i> Sélectionnez un programme pour voir le plan de la salle.</p>';
            return;
        }

        var salleId = programmeSalleMap[programmeId];
        if (!salleId) {
            boxBody.innerHTML = '<p style="color: #999; text-align: center; padding: 60px 20px;">Salle introuvable pour ce programme.</p>';
            return;
        }

        var reserved = reservedPlacesMap[programmeId] || [];
        var placesDeLaSalle = cinemaPlaces.filter(function(p) { return String(p.idsalle) === String(salleId); });

        if (placesDeLaSalle.length === 0) {
            boxBody.innerHTML = '<p style="color: #999; text-align: center; padding: 60px 20px;">Aucune place configurée pour cette salle.</p>';
            return;
        }

        var grouped = {};
        var salleNom = '';
        placesDeLaSalle.forEach(function(p) {
            var key = p.rangelib || 'Autre';
            if (!grouped[key]) grouped[key] = [];
            grouped[key].push(p);
            if (!salleNom) salleNom = p.sallelib;
        });

        var html = '<div style="padding: 20px 30px;">';
        html += '<h4 style="margin-bottom: 20px; color: #3c8dbc;"><i class="fa fa-map"></i> Plan - ' + salleNom + '</h4>';
        html += '<div class="plan-salle-container">';

        Object.keys(grouped).sort().forEach(function(rangelib) {
            html += '<div class="range-row"><div class="range-label">' + rangelib + '</div><div class="places-group">';
            grouped[rangelib].forEach(function(p) {
                var cat = (p.categorierangelib || '').toLowerCase();
                var colorClass = 'place-other';
                if (cat.indexOf('standard') !== -1) colorClass = 'place-standard';
                else if (cat.indexOf('confort') !== -1 || cat.indexOf('premium') !== -1) colorClass = 'place-confort';
                else if (cat.indexOf('vip') !== -1) colorClass = 'place-vip';

                var isReserved = reserved.indexOf(p.id) !== -1;
                var isSelected = selectedPlaces.indexOf(p.id) !== -1;
                var classes = 'place-square ' + colorClass;
                if (isReserved) classes += ' place-reserved';
                if (isSelected) classes += ' place-selected';

                var title = p.libelle + ' (' + p.categorierangelib + ')' + (isReserved ? ' - RÉSERVÉE' : '');
                html += '<div class="' + classes + '" data-place-id="' + p.id + '" title="' + title + '">' + p.libelle + '</div>';
            });
            html += '</div></div>';
        });

        html += '<div class="legend-container">';
        html += '<div class="legend-item"><div class="legend-color place-standard"></div> Standard</div>';
        html += '<div class="legend-item"><div class="legend-color place-confort"></div> Confort</div>';
        html += '<div class="legend-item"><div class="legend-color place-vip"></div> VIP</div>';
        html += '<div class="legend-item"><div class="legend-color place-reserved"></div> Réservée</div>';
        html += '</div></div></div>';

        boxBody.innerHTML = html;

        boxBody.querySelectorAll('.place-square').forEach(function(sq) {
            sq.addEventListener('click', function() {
                togglePlace(this.getAttribute('data-place-id'));
            });
        });
    }

    function togglePlace(placeId) {
        var idx = selectedPlaces.indexOf(placeId);
        if (idx !== -1) selectedPlaces.splice(idx, 1);
        else selectedPlaces.push(placeId);
        updateRecap();
    }

    function updateRecap() {
        var recap = document.getElementById('recap');
        var count = document.getElementById('count');
        var btn = document.getElementById('btnSubmit');

        if (selectedPlaces.length === 0) {
            recap.textContent = 'Aucune';
            count.textContent = '0';
            btn.disabled = true;
        } else {
            var libs = selectedPlaces.map(function(id) {
                var p = cinemaPlaces.find(function(x) { return x.id === id; });
                return p ? p.libelle : id;
            });
            recap.textContent = libs.join(', ');
            count.textContent = selectedPlaces.length;
            btn.disabled = false;
        }

        syncHiddenFields();
        renderPlan(document.getElementById('iddetailprogramme').value);
    }

    // ============ Init ============
    document.addEventListener('DOMContentLoaded', function() {
        var progSel = document.getElementById('iddetailprogramme');
        if (!progSel) return;

        // Le framework a déjà son select programme. On écoute son change.
        progSel.addEventListener('change', function() {
            selectedPlaces = [];
            syncHiddenFields();
            updateRecap();
            renderPlan(this.value);
        });

        // Si pré-rempli (mode update), charger immédiatement
        if (progSel.value) {
            renderPlan(progSel.value);
        }
    });

    document.getElementById('formId').addEventListener('submit', function(e) {
        if (selectedPlaces.length === 0) {
            e.preventDefault();
            alert('Veuillez sélectionner au moins une place.');
        }
    });
</script>