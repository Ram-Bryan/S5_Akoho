<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.*" %>
<%@ page import="bean.CGenUtil" %>
<%@ page import="utilitaire.UtilDB" %>
<%@ page import="utilitaire.Utilitaire" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<% try {
    UserEJB u = (UserEJB) session.getValue("u");
    String lien = (String) session.getValue("lien");

    String idclient          = request.getParameter("idclient");
    String iddetailprogramme = request.getParameter("iddetailprogramme");
    String datereservation   = request.getParameter("datereservation");
    String nombreLigneStr    = request.getParameter("nombreLigne");
    int nombreLigne = nombreLigneStr != null ? Integer.parseInt(nombreLigneStr) : 0;

    List<String> places = new ArrayList<String>();
    for (int i = 0; i < nombreLigne; i++) {
        String p = request.getParameter("idplace_" + i);
        if (p != null && !p.trim().isEmpty()) places.add(p);
    }
    if (places.isEmpty()) throw new Exception("Aucune place sélectionnée");

    Connection c = null;
    try {
        c = new UtilDB().GetConn();

        // Client
        CinemaClient cli = new CinemaClient();
        cli.setId(idclient);
        CinemaClient[] clis = (CinemaClient[]) CGenUtil.rechercher(cli, null, null, c, "");
        String clientNom = (clis != null && clis.length > 0) ? clis[0].getNom() : idclient;

        // Film programmé
        CinemaDetailProgrammeLib dp = new CinemaDetailProgrammeLib();
        dp.setId(iddetailprogramme);
        CinemaDetailProgrammeLib[] dps =
            (CinemaDetailProgrammeLib[]) CGenUtil.rechercher(dp, null, null, c, "");
        String filmTitre = (dps != null && dps.length > 0) ? dps[0].getFilmlib() : iddetailprogramme;

        // Date de référence
        Date dateRef = (datereservation != null && !datereservation.trim().isEmpty())
            ? Date.valueOf(datereservation) : Utilitaire.dateDuJourSql();

        // Prix par place
        List<String> placesLib = new ArrayList<String>();
        List<Double> placesPrix = new ArrayList<Double>();
        double total = 0;
        for (String idplace : places) {
            CinemaPlace pl = new CinemaPlace();
            pl.setId(idplace);
            CinemaPlace[] pls = (CinemaPlace[]) CGenUtil.rechercher(pl, null, null, c, "");
            String lib = (pls != null && pls.length > 0) ? pls[0].getLibelle() : idplace;

            double prix = CinemaDetailReservation.getPrixUnitaire(
                iddetailprogramme, idplace, dateRef, c);
            placesLib.add(lib);
            placesPrix.add(prix);
            total += prix;
        }
%>
<div class="content-wrapper">
    <h1>Confirmation de réservation</h1>
    <div class="box"><div class="box-body">

        <p><strong>Client :</strong> <%= clientNom %></p>
        <p><strong>Film :</strong> <%= filmTitre %></p>
        <p><strong>Date de réservation :</strong> <%= datereservation %></p>

        <h3>Places sélectionnées</h3>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Place</th>
                    <th style="text-align:right">Prix unitaire (Ar)</th>
                </tr>
            </thead>
            <tbody>
                <% for (int i = 0; i < placesLib.size(); i++) { %>
                    <tr>
                        <td><%= placesLib.get(i) %></td>
                        <td style="text-align:right"><%= String.format("%,.2f", placesPrix.get(i)) %></td>
                    </tr>
                <% } %>
            </tbody>
            <tfoot>
                <tr>
                    <th>Total</th>
                    <th style="text-align:right"><%= String.format("%,.2f", total) %> Ar</th>
                </tr>
            </tfoot>
        </table>

        <form action="<%= lien %>?but=apresMultiple.jsp" method="post">
            <input type="hidden" name="acte"         value="insert">
            <input type="hidden" name="bute"         value="cinema/reservation/reservation-fiche.jsp">
            <input type="hidden" name="classe"       value="cinema.CinemaReservation">
            <input type="hidden" name="classefille"  value="cinema.CinemaDetailReservation">
            <input type="hidden" name="nomtable"     value="cinema_detail_reservation">
            <input type="hidden" name="nombreLigne"  value="<%= nombreLigne %>">
            <input type="hidden" name="colonneMere"  value="idreservation">
            <input type="hidden" name="idclient"     value="<%= idclient %>">
            <input type="hidden" name="iddetailprogramme" value="<%= iddetailprogramme %>">
            <input type="hidden" name="datereservation"   value="<%= datereservation %>">
            <input type="hidden" name="idstatutreservation" value="<%= ConstanteCinema.STATUTRESERVE %>">
            <input type="hidden" name="etat"    value="1">
            <input type="hidden" name="iduser"  value="0">
            <% for (int i = 0; i < nombreLigne; i++) {
                String p = request.getParameter("idplace_" + i);
            %>
                <input type="hidden" name="idplace_<%= i %>" value="<%= p != null ? p : "" %>">
            <% } %>

            <a href="<%= lien %>?but=cinema/reservation/reservation-saisie.jsp"
               class="btn btn-secondary">Annuler</a>
            <button type="submit" class="btn btn-primary">Confirmer la réservation</button>
        </form>

    </div></div>
</div>
<%
        } finally {
            if (c != null) try { c.close(); } catch (Exception e) {}
        }
    } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>