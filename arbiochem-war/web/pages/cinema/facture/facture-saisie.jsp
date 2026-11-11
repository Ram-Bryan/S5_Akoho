<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsert" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.CinemaFactureClient" %>
<%@ page import="utilitaire.Utilitaire" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String mapping  = "cinema.CinemaFactureClient";
    String nomTable = "cinema_facture_client";
    String apres    = "cinema/facture/facture-fiche.jsp";

    CinemaFactureClient o = new CinemaFactureClient();
    PageInsert pi = new PageInsert(o, request, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie facture");

    // Libelles
    pi.getFormu().getChamp("numero").setLibelle("Numero");
    pi.getFormu().getChamp("datefacture").setLibelle("Date facture");
    pi.getFormu().getChamp("datefacture").setDefaut(Utilitaire.dateDuJour());
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("etat").setDefaut("1");
    pi.getFormu().getChamp("iduser").setVisible(false);
    pi.getFormu().getChamp("iduser").setDefaut("0");

    // AUTOCOMPLETE sur idreservation :
    // -> classe cinema.CinemaReservation
    // -> on affiche la colonne "id" (colonne de valeur)
    // -> on cherche dans la table cinema_reservation
    // -> champRetour = "id;id" : la valeur du champ (idreservation) + un libelle (idreservationlibelle)
    pi.getFormu().getChamp("idreservation").setPageAppelComplete(
        "cinema.CinemaReservation",   // classe
        "id",                          // colonne valeur
        "cinema_reservation",          // nom de table
        "id;id",                       // champRetour
        "idreservation;idreservationlibelle"   // champRetourMapping
    );
    pi.getFormu().getChamp("idreservation").setLibelle("Reservation");

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) pi.setTitre("Modification facture");

    pi.preparerDataFormu();
    pi.getFormu().makeHtmlInsertTabIndex();
%>
<div class="content-wrapper">
    <h1><%=pi.getTitre()%></h1>
    <form action="<%=pi.getLien()%>?but=apresTarif.jsp" method="post" name="<%=nomTable%>" id="<%=nomTable%>">
        <% out.println(pi.getFormu().getHtmlInsert()); %>
        <input name="acte"     type="hidden" value="insert">
        <input name="bute"     type="hidden" value="<%=apres%>">
        <input name="classe"   type="hidden" value="<%=mapping%>">
        <input name="nomtable" type="hidden" value="<%=nomTable%>">
    </form>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>