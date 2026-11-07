<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.*" %>
<%@ page import="ferme.previsionpoulet.*" %>
<%@ page import="magasin.Magasin" %>
<%@ page import="bean.CGenUtil" %>
<%@ page import="java.util.*" %>

<% try {
    String lien = (String) session.getValue("lien");
    user.UserEJB u = (user.UserEJB) session.getValue("u");

    String idFerme = request.getParameter("idFerme");
    String daty1 = request.getParameter("daty1");
    String daty2 = request.getParameter("daty2");
    boolean inclureMorts = "on".equalsIgnoreCase(request.getParameter("inclureMorts"))
                        || "1".equals(request.getParameter("inclureMorts"));

    String bute = "ferme/previsionpoulet/prevision-calendrier.jsp";

    // Liste des fermes (MAGASIN2 filtré si tu as un type ; sinon tous)
    Magasin crit = new Magasin();
    Magasin[] fermes = (Magasin[]) CGenUtil.rechercher(crit, null, null, "");

    if (idFerme == null || idFerme.trim().isEmpty()) {
%>
<div class="content-wrapper">
    <h1>Prévision poulets</h1>
    <form action="<%=lien%>?but=<%=bute%>" method="get" class="container card p-3">
        <input type="hidden" name="but" value="<%=bute%>">
        <div class="form-input">
            <label>Ferme</label>
            <select name="idFerme" class="form-control" required>
                <option value="">-- choisir --</option>
                <% for (int i = 0; i < fermes.length; i++) { %>
                    <option value="<%=fermes[i].getId()%>"><%=fermes[i].getVal()%></option>
                <% } %>
            </select>
        </div>
        <div class="form-input"><label>Date min</label>
            <input type="date" name="daty1" class="form-control"></div>
        <div class="form-input"><label>Date max</label>
            <input type="date" name="daty2" class="form-control"></div>
        <div class="form-input">
            <label><input type="checkbox" name="inclureMorts"> Inclure les morts</label>
        </div>
        <button class="btn btn-primary mt-2" type="submit">Afficher</button>
    </form>
</div>
<%
        return;
    }

    PrevisionCalendrier p = new PrevisionCalendrier(idFerme, daty1, daty2, inclureMorts);
    String[] listeMois = p.getListeMois();
    int[] listeJours = p.getJours();
    HashMap<String, Double[]> totaux = p.getTotal();
    List<String> avert = p.getAvertissements();
%>
<div class="content-wrapper">
    <h1>Prévision poulets</h1>

    <% for (int i = 0; i < avert.size(); i++) { %>
        <div class="alert alert-warning"><%= avert.get(i) %></div>
    <% } %>

    <div style="max-height:80vh;overflow:auto;border:1px solid #ccc">
        <table style="border-collapse:collapse;width:100%">
            <thead>
                <tr style="position:sticky;top:0;z-index:5;background:#f2f2f2">
                    <th style="text-align:left;padding:6px;border:1px solid #999">Jours</th>
                    <% for (int i = 0; i < listeMois.length; i++) { %>
                        <th style="padding:6px;border:1px solid #999"><%= listeMois[i].toUpperCase() %></th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
            <% for (int ji = 0; ji < listeJours.length; ji++) {
                   int jour = listeJours[ji]; %>
                <tr>
                    <td style="padding:6px;border:1px solid #999;font-weight:bold"><%= jour %></td>
                    <% for (int mi = 0; mi < listeMois.length; mi++) {
                           String mois = listeMois[mi];
                           double t = p.getTotalCellule(jour, mois);
                           String detailUrl = null;
                           if (t > 0) {
                               detailUrl = lien + "?but=ferme/previsionpoulet/prevision-detail.jsp"
                                       + "&idFerme=" + idFerme
                                       + "&date=" + java.net.URLEncoder.encode(mois, "UTF-8")
                                       + "&jour=" + jour
                                       + "&inclureMorts=" + (inclureMorts ? "1" : "0");
                           }
                    %>
                        <td style="padding:6px;border:1px solid #999;text-align:right">
                        <% if (detailUrl != null) { %>
                            <a href="<%= detailUrl %>"><%= String.format("%,.0f", t) %></a>
                        <% } else { %>-<% } %>
                        </td>
                    <% } %>
                </tr>
            <% } %>
            </tbody>
            <tfoot>
                <tr style="background:#e8e8e8;font-weight:bold">
                    <td style="padding:6px;border:1px solid #999">TOTAL MOIS</td>
                    <% for (int i = 0; i < listeMois.length; i++) {
                           Double[] t = totaux.get(listeMois[i]);
                    %>
                        <td style="padding:6px;border:1px solid #999;text-align:right">
                            <%= t == null ? "-" : String.format("%,.0f", t[0]) %>
                        </td>
                    <% } %>
                </tr>
                <tr style="background:#e8e8e8">
                    <td style="padding:6px;border:1px solid #999">MOYENNE/JOUR</td>
                    <% for (int i = 0; i < listeMois.length; i++) {
                           Double[] t = totaux.get(listeMois[i]);
                    %>
                        <td style="padding:6px;border:1px solid #999;text-align:right">
                            <%= (t == null || t[1] == null) ? "-" : String.format("%,.0f", t[1]) %>
                        </td>
                    <% } %>
                </tr>
            </tfoot>
        </table>
    </div>

    <p style="margin-top:15px">
        <a href="<%= lien %>?but=<%= bute %>">Changer de ferme ou de dates</a>
    </p>
</div>
<%
} catch (Exception e) {
    e.printStackTrace();
%>
<script>alert('<%= e.getMessage() == null ? e.getClass().getName() : e.getMessage().replace("'", "\\'") %>');</script>
<% } %>