<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="cinema.*" %>
<%@ page import="affichage.PageInsertMultiple" %>
<%@ page import="affichage.Champ" %>
<%@ page import="bean.CGenUtil" %>
<%@ page import="utilitaire.UtilDB" %>
<%@ page import="utilitaire.Utilitaire" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.*" %>

<%
    String lien = (String) session.getValue("lien");
    user.UserEJB u = (user.UserEJB) session.getValue("u");
    
    Connection c = null;
    CinemaPlaceLib[] places = null;
    CinemaDetailProgrammeLib[] detailProgrammes = null;
    CinemaClient[] clients = null;
    Map<String, Set<String>> reservedPlacesMap = new HashMap<>();
    
    try {
        c = new UtilDB().GetConn();
        places = (CinemaPlaceLib[]) CGenUtil.rechercher(new CinemaPlaceLib(), null, null, c, " order by sallelib, rangelib, libelle ");
        detailProgrammes = (CinemaDetailProgrammeLib[]) CGenUtil.rechercher(new CinemaDetailProgrammeLib(), null, null, c, " order by idprogramme, filmlib ");
        clients = (CinemaClient[]) CGenUtil.rechercher(new CinemaClient(), null, null, c, " order by nom ");
        
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
    
    // Initialisation du framework pour générer les champs cachés corrects
    CinemaReservation mere = new CinemaReservation();
    CinemaDetailReservation fille = new CinemaDetailReservation();
    int taille = 20; // On génère 20 lignes cachées (max de places par résa)
    
    PageInsertMultiple pi = new PageInsertMultiple(mere, fille, request, taille, u);
    pi.setLien(lien);

    // On cache tous les champs du framework car on a notre propre UI
    pi.getFormu().getChamp("idclient").setVisible(false);
    pi.getFormu().getChamp("iddetailprogramme").setVisible(false);
    pi.getFormu().getChamp("datereservation").setVisible(false);
    pi.getFormu().getChamp("idstatutreservation").setVisible(false);
    pi.getFormu().getChamp("idstatutreservation").setDefaut(ConstanteCinema.STATUTRESERVE);
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("etat").setDefaut("1");
    pi.getFormu().getChamp("iduser").setVisible(false);
    pi.getFormu().getChamp("iduser").setDefaut("0");
    
    pi.getFormufle().getChampMulitple("idplace").setVisible(false);
    pi.getFormufle().getChampMulitple("id").setVisible(false);
    pi.getFormufle().getChampMulitple("idreservation").setVisible(false);
    pi.getFormufle().getChampMulitple("etat").setVisible(false);
    pi.getFormufle().getChampMulitple("iduser").setVisible(false);
    
    pi.preparerDataFormu();
    pi.getFormu().makeHtmlInsertTabIndex();
    pi.getFormufle().makeHtmlInsertTableauIndex();
    
    String todayFr = Utilitaire.dateDuJour();
    String[] todayParts = todayFr.split("/");
    String todayIso = todayParts[2] + "-" + todayParts[1] + "-" + todayParts[0];
%>

<style>
    /* ... (Garder tout le CSS précédent) ... */
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
    
    /* On cache le tableau du framework */
    .hidden-framework-table { display: none; }
</style>

<div class="content-wrapper">
    <h1 class="box-title" style="margin-bottom: 20px;">
        <a href="<%= lien %>?but=cinema/reservation/reservation-saisie.jsp"><i class="fa fa-angle-left"></i></a>
        Saisie visuelle d'une réservation
    </h1>
    
    <!-- On utilise l'action de PageInsertMultiple -->
    <form id="formId" action="<%=lien%>?but=apresMultiple.jsp" method="post">
        
        <!-- Le framework génère ses champs cachés ici. On les cache. -->
        <div class="hidden-framework-table">
            <% out.println(pi.getFormu().getHtmlInsert()); %>
            <% out.println(pi.getFormufle().getHtmlTableauInsert()); %>
        </div>

        <!-- L'UI Custom -->
        <div class="box box-primary">
            <div class="box-body" style="padding: 25px 30px;">
                <div class="row">
                    <div class="col-md-4">
                        <div class="form-group" style="margin-bottom: 0;">
                            <label style="font-weight: 600;">Client</label>
                            <select id="ui_idclient" class="form-control" required style="height: 40px;">
                                <option value="">-- Choisir un client --</option>
                                <% for (CinemaClient cli : clients) { %>
                                <option value="<%= cli.getId() %>"><%= cli.getNom() %></option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-5">
                        <div class="form-group" style="margin-bottom: 0;">
                            <label style="font-weight: 600;">Film programmé</label>
                            <select id="ui_iddetailprogramme" class="form-control" required style="height: 40px;">
                                <option value="">-- Choisir un programme --</option>
                                <% for (CinemaDetailProgrammeLib dp : detailProgrammes) { %>
                                <option value="<%= dp.getId() %>"><%= dp.getProgrammefilm() %></option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group" style="margin-bottom: 0;">
                            <label style="font-weight: 600;">Date</label>
                            <input type="date" id="ui_dateInput" class="form-control" value="<%= todayIso %>" required style="height: 40px;">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="box" id="planContainer">
            <div class="box-body" style="min-height: 200px;">
                <p style="color: #999; text-align: center; padding: 60px 20px;">
                    <i class="fa fa-info-circle"></i> Sélectionnez un programme pour voir le plan de la salle.
                </p>
            </div>
        </div>
        
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
        
        <!-- Paramètres d'action pour apresMultiple.jsp -->
        <input type="hidden" name="acte" value="insert">
        <input type="hidden" name="bute" value="cinema/reservation/reservation-fiche.jsp">
        <input type="hidden" name="classe" value="cinema.CinemaReservation">
        <input type="hidden" name="classefille" value="cinema.CinemaDetailReservation">
        <input type="hidden" name="nomtable" value="cinema_detail_reservation">
        <input type="hidden" name="nombreLigne" value="<%=taille%>">
        <input type="hidden" name="colonneMere" value="idreservation">
    </form>
</div>

<script>
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
    
    // ============ Synchronisation UI vers Champs Cachés Framework ============
    function syncHiddenFields() {
    // 1. Mère
    document.getElementsByName("idclient")[0].value = document.getElementById('ui_idclient').value;
    document.getElementsByName("iddetailprogramme")[0].value = document.getElementById('ui_iddetailprogramme').value;
    
    var dParts = document.getElementById('ui_dateInput').value.split('-');
    if(dParts.length === 3) {
        document.getElementsByName("datereservation")[0].value = dParts[2] + '/' + dParts[1] + '/' + dParts[0];
    }

    // 2. Filles : reset les inputs idplace_X
    var inputsPlace = document.querySelectorAll('[name^="idplace_"]');
    inputsPlace.forEach(function(input) { input.value = ''; });

    // 3. Remplir idplace_X pour les places sélectionnées
    selectedPlaces.forEach(function(id, i) {
        if(inputsPlace[i]) inputsPlace[i].value = id;
    });

    // 4. CRUCIAL : Cocher/Décocher les checkboxes du framework
        // 4. CRUCIAL : Cocher/Décocher les checkboxes du framework
    var hiddenContainer = document.querySelector('.hidden-framework-table');
    if (hiddenContainer) {
        var allCheckboxes = hiddenContainer.querySelectorAll('input[type="checkbox"]');
        
        // FILTRE CRUCIAL : ignorer le "select all" (nommé chk_all, select_all, etc.)
        // On ne garde que les checkboxes dont le name contient un chiffre (chk_0, chk_1, ...)
        var checkboxes = Array.from(allCheckboxes).filter(function(cb) {
            var name = cb.name || '';
            return /\d/.test(name); // garde seulement si le nom contient un chiffre
        });
        
        // Fallback de sécurité : si le filtre ne trouve rien, on saute la première checkbox
        if (checkboxes.length === 0 && allCheckboxes.length > 0) {
            checkboxes = Array.from(allCheckboxes).slice(1);
        }
        
        // Ajuster le nombre de checkboxes à cocher pour éviter le décalage
        checkboxes.forEach(function(cb, idx) {
            cb.checked = (idx < selectedPlaces.length);
        });
        
        // Debug : à retirer une fois validé
        console.log('Checkboxes totales :', allCheckboxes.length, 
                    '| Après filtre :', checkboxes.length, 
                    '| Places sélectionnées :', selectedPlaces.length);
    } else {
        console.error('ERREUR: .hidden-framework-table introuvable !');
    }
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
        
        html += '<div class="legend-container"><div class="legend-item"><div class="legend-color place-standard"></div> Standard</div><div class="legend-item"><div class="legend-color place-confort"></div> Confort</div><div class="legend-item"><div class="legend-color place-vip"></div> VIP</div><div class="legend-item"><div class="legend-color place-reserved"></div> Réservée</div></div></div></div>';
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
        renderPlan(document.getElementById('ui_iddetailprogramme').value);
    }
    
    document.getElementById('ui_iddetailprogramme').addEventListener('change', function() {
        selectedPlaces = [];
        syncHiddenFields(); // reset champs
        updateRecap();
        renderPlan(this.value);
    });

    // On synchronise aussi quand on change le client ou la date
    document.getElementById('ui_idclient').addEventListener('change', syncHiddenFields);
    document.getElementById('ui_dateInput').addEventListener('change', syncHiddenFields);
    
    document.getElementById('formId').addEventListener('submit', function(e) {
        if (selectedPlaces.length === 0) {
            e.preventDefault();
            alert('Veuillez sélectionner au moins une place.');
        }
    });
</script>