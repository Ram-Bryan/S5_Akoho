<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsertMultiple" %>
<%@ page import="affichage.Champ" %>
<%@ page import="affichage.Liste" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.CinemaReservation" %>
<%@ page import="cinema.CinemaDetailReservation" %>
<%@ page import="cinema.CinemaClient" %>
<%@ page import="cinema.CinemaDetailProgrammeLib" %>
<%@ page import="cinema.CinemaPlace" %>
<%@ page import="cinema.ConstanteCinema" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String classeMere    = "cinema.CinemaReservation";
    String classeFille   = "cinema.CinemaDetailReservation";
    String nomTableFille = "cinema_detail_reservation";
    String colonneMere   = "idreservation";
    String apres         = "cinema/reservation/reservation-fiche.jsp";
    int    taille        = 10;

    CinemaReservation mere = new CinemaReservation();
    CinemaDetailReservation fille = new CinemaDetailReservation();

    PageInsertMultiple pi = new PageInsertMultiple(mere, fille, request, taille, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie d'une réservation");

    // Mère — dropdowns
    Liste listeClient = new Liste("idclient", new CinemaClient(), "nom", "id");
    Liste listeDetailProg = new Liste("iddetailprogramme", new CinemaDetailProgrammeLib(), "filmlib", "id");
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
        <input name="bute"         type="hidden" value="<%=apres%>">
        <input name="classe"       type="hidden" value="<%=classeMere%>">
        <input name="classefille"  type="hidden" value="<%=classeFille%>">
        <input name="nomtable"     type="hidden" value="<%=nomTableFille%>">
        <input name="nombreLigne"  type="hidden" value="<%=taille%>">
        <input name="colonneMere"  type="hidden" value="<%=colonneMere%>">
    </form>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>