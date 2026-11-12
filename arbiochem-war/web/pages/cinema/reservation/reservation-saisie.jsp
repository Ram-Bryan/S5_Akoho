<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsertMultiple" %>
<%@ page import="affichage.Champ" %>
<%@ page import="affichage.Liste" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.CinemaReservation" %>
<%@ page import="cinema.CinemaDetailReservation" %>
<%@ page import="cinema.CinemaClient" %>
<%@ page import="cinema.CinemaDetailProgrammeLib" %>
<%@ page import="cinema.CinemaPlaceLib" %>
<%@ page import="cinema.CinemaPlace" %>
<%@ page import="cinema.ConstanteCinema" %>
<%@ page import="bean.CGenUtil" %>
<%@ page import="utilitaire.UtilDB" %>
<%@ page import="java.sql.Connection" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String classeMere    = "cinema.CinemaReservation";
    String classeFille   = "cinema.CinemaDetailReservation";
    String nomTableFille = "cinema_detail_reservation";
    String colonneMere   = "idreservation";
    String apres         = "cinema/reservation/reservation-fiche.jsp";
    int    taille        = 10;

    Connection c = null;
    CinemaPlaceLib[] places = null;
    CinemaDetailProgrammeLib[] detailProgrammes = null;
    try {
        c = new UtilDB().GetConn();
        places = (CinemaPlaceLib[]) CGenUtil.rechercher(new CinemaPlaceLib(), null, null, c, " order by sallelib, libelle ");
        detailProgrammes = (CinemaDetailProgrammeLib[]) CGenUtil.rechercher(new CinemaDetailProgrammeLib(), null, null, c, " order by idprogramme, filmlib ");
    } finally {
        if (c != null) try { c.close(); } catch (Exception ignored) {}
    }

    CinemaReservation mere = new CinemaReservation();
    CinemaDetailReservation fille = new CinemaDetailReservation();

    PageInsertMultiple pi = new PageInsertMultiple(mere, fille, request, taille, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie d'une réservation");

    // Mère — dropdowns
    Liste listeClient = new Liste("idclient", new CinemaClient(), "nom", "id");
    Liste listeDetailProg = new Liste("iddetailprogramme", new CinemaDetailProgrammeLib(), "programmefilm", "id");
    pi.getFormu().changerEnChamp(new Liste[]{ listeClient, listeDetailProg });

    pi.getFormu().getChamp("idclient").setLibelle("Client");
    pi.getFormu().getChamp("iddetailprogramme").setLibelle("Film programmé");
    pi.getFormu().getChamp("datereservation").setLibelle("Date de réservation");
    pi.getFormu().getChamp("datereservation").setDefaut(utilitaire.Utilitaire.dateDuJour());

    pi.getFormu().getChamp("idstatutreservation").setVisible(false);
    pi.getFormu().getChamp("idstatutreservation").setDefaut(ConstanteCinema.STATUTRESERVE);

    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("etat").setDefaut("1");
    pi.getFormu().getChamp("iduser").setVisible(false);
    pi.getFormu().getChamp("iduser").setDefaut("0");

    // Filles — place dropdown
    Liste listePlace = new Liste("idplace", new CinemaPlace(), "libelle", "id");
    pi.getFormufle().changerEnChamp(new Liste[]{ listePlace });
    pi.getFormufle().getChamp("idplace_0").setLibelle("Place");

    Champ.setVisible(pi.getFormufle().getChampMulitple("id").getListeChamp(), false);
    Champ.setVisible(pi.getFormufle().getChampMulitple("idreservation").getListeChamp(), false);
    Champ.setVisible(pi.getFormufle().getChampMulitple("etat").getListeChamp(), false);
    Champ.setVisible(pi.getFormufle().getChampMulitple("iduser").getListeChamp(), false);

    String[] colOrdre = {"idplace"};
    pi.getFormufle().setColOrdre(colOrdre);

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) {
        pi.setTitre("Modification d'une réservation");
    }

    pi.preparerDataFormu();
    pi.getFormu().makeHtmlInsertTabIndex();
    pi.getFormufle().makeHtmlInsertTableauIndex();
%>
<div class="content-wrapper">
    <h1><%=pi.getTitre()%></h1>
    <form id="formId" class='container' action="<%=pi.getLien()%>?but=apresMultiple.jsp" method="post">
        <%
            out.println(pi.getFormu().getHtmlInsert());
            out.println(pi.getFormufle().getHtmlTableauInsert());
        %>
        <input name="acte"         type="hidden" value="insert">
        <input name="bute"         type="hidden" value="cinema/reservation/reservation-fiche.jsp">
        <input name="classe"       type="hidden" value="cinema.CinemaReservation">
        <input name="classefille"  type="hidden" value="cinema.CinemaDetailReservation">
        <input name="nomtable"     type="hidden" value="cinema_detail_reservation">
        <input name="nombreLigne"  type="hidden" value="<%=taille%>">
        <input name="colonneMere"  type="hidden" value="idreservation">
    </form>
</div>
<script>
var cinemaPlaces = [
<% if (places != null) {
    for (int i = 0; i < places.length; i++) {
        CinemaPlaceLib p = places[i];
%>
    { id: "<%=p.getId()%>", libelle: "<%=p.getLibelle()%>", idsalle: "<%=p.getIdsalle()%>", sallelib: "<%=p.getSallelib()%>", categorierangelib: "<%=p.getCategorierangelib()%>" }<%= (i < places.length - 1) ? "," : "" %>
<% }
}%>
];
var programmeSalleMap = {
<% if (detailProgrammes != null) {
    for (int i = 0; i < detailProgrammes.length; i++) {
        CinemaDetailProgrammeLib dp = detailProgrammes[i];
%>
    "<%=dp.getId()%>": "<%=dp.getIdsalle()%>"<%= (i < detailProgrammes.length - 1) ? "," : "" %>
<% }
}%>
};

function remplirPlacesParProgramme(programmeId) {
    var selects = document.querySelectorAll('select[name^="idplace_"]');
    if (!selects || selects.length === 0) return;

    var salleId = programmeSalleMap[programmeId];

    selects.forEach(function(select) {
        var ancienValeur = select.value;
        select.innerHTML = '';

        var optionVide = document.createElement('option');
        optionVide.value = '';
        optionVide.text = programmeId && salleId ? '-- Sélectionner une place --' : '-- Choisir un programme --';
        select.appendChild(optionVide);

        if (!programmeId || !salleId) {
            return;
        }

        var placesDeLaSalle = cinemaPlaces.filter(function(place) {
            return String(place.idsalle) === String(salleId);
        });

        placesDeLaSalle.forEach(function(place) {
            var libelleCategorie = place.categorierangelib ? place.categorierangelib : '';
            var option = document.createElement('option');
            option.value = place.id;
            option.text = place.libelle + ' (' + place.sallelib + ') ' + libelleCategorie;
            select.appendChild(option);
        });

        if (placesDeLaSalle.length === 0) {
            var optionVideSalle = document.createElement('option');
            optionVideSalle.value = '';
            optionVideSalle.text = '-- Aucune place dans cette salle --';
            select.appendChild(optionVideSalle);
        }

        if (ancienValeur && Array.prototype.some.call(select.options, function(option) {
            return option.value === ancienValeur;
        })) {
            select.value = ancienValeur;
        }
    });
}

document.addEventListener('DOMContentLoaded', function() {
    var programmeSelect = document.getElementById('iddetailprogramme');
    if (!programmeSelect) return;

    programmeSelect.addEventListener('change', function() {
        remplirPlacesParProgramme(this.value);
    });

    remplirPlacesParProgramme(programmeSelect.value);
});
</script>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>