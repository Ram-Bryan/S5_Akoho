<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.*" %>
<%@ page import="ferme.previsionpoulet.*" %>
<%@ page import="java.time.*" %>
<%@ page import="java.util.*" %>

<% try {
    String lien = (String) session.getValue("lien");
    String idFerme = request.getParameter("idFerme");
    String mois = request.getParameter("date");
    String jourStr = request.getParameter("jour");
    boolean inclureMorts = "1".equals(request.getParameter("inclureMorts"));

    if (idFerme == null || mois == null || jourStr == null) {
        throw new Exception("Paramètres manquants (idFerme, date, jour)");
    }

    int jour = Integer.parseInt(jourStr);
    String[] moisFr = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                       "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
    String[] moisParts = mois.split(" ");
    int moisNum = 1;
    for (int i = 0; i < moisFr.length; i++) {
        if (moisFr[i].equalsIgnoreCase(moisParts[0])) {
            moisNum = i + 1;
            break;
        }
    }
    int annee = Integer.parseInt(moisParts[1]);
    LocalDate dateCible = LocalDate.of(annee, moisNum, jour);

    // Le calendrier a déjà tout calculé. On instancie avec la même plage
    // pour récupérer les détails de CE jour.
    PrevisionCalendrier p = new PrevisionCalendrier(idFerme, dateCible.toString(), dateCible.toString(), inclureMorts);
    PrevisionJourDetail[] rows = p.getDetailByDay(jour, mois);

    // Tri par lot puis bâtiment puis parquet (ou selon le paramètre)
    String sort = request.getParameter("sort");
    String dir = request.getParameter("dir");
    if (sort == null) sort = "lot";
    if (dir == null) dir = "asc";

    List<PrevisionJourDetail> liste = new ArrayList<PrevisionJourDetail>();
    for (int i = 0; i < rows.length; i++) liste.add(rows[i]);

    Comparator<PrevisionJourDetail> cmp = new Comparator<PrevisionJourDetail>() {
        public int compare(PrevisionJourDetail a, PrevisionJourDetail b) {
            String va = "";
            String vb = "";
            if ("bat".equals(sort)) {
                va = a.getLibelleBatiment();
                vb = b.getLibelleBatiment();
            } else if ("parquet".equals(sort)) {
                va = a.getLibelleParquet();
                vb = b.getLibelleParquet();
            } else {
                va = a.getLibelleLot();
                vb = b.getLibelleLot();
            }
            return va.compareToIgnoreCase(vb);
        }
    };
    Collections.sort(liste, cmp);
    if ("desc".equalsIgnoreCase(dir)) {
        Collections.reverse(liste);
    }

    double totP = 0, totA = 0, totM = 0;
    for (int i = 0; i < liste.size(); i++) {
        PrevisionJourDetail e = liste.get(i);
        totP += e.getPoussins();
        totA += e.getAdultes();
        totM += e.getMorts();
    }

    String baseQS = lien + "?but=ferme/previsionpoulet/prevision-detail.jsp"
            + "&idFerme=" + idFerme
            + "&date=" + java.net.URLEncoder.encode(mois, "UTF-8")
            + "&jour=" + jour
            + "&inclureMorts=" + (inclureMorts ? "1" : "0");
    String prochainDir = "asc".equalsIgnoreCase(dir) ? "desc" : "asc";
%>
<div class="content-wrapper">
    <h1><%= dateCible.toString() %> — <%= idFerme %></h1>

    <p>
        <a href="<%= lien %>?but=ferme/previsionpoulet/prevision-calendrier.jsp&idFerme=<%= idFerme %>&inclureMorts=<%= inclureMorts ? "on" : "" %>">
            &laquo; Retour au calendrier
        </a>
    </p>

    <table class="table table-bordered" style="width:100%">
        <thead>
            <tr style="background:#f2f2f2">
                <th><a href="<%= baseQS %>&sort=lot&dir=<%= prochainDir %>">Lot</a></th>
                <th><a href="<%= baseQS %>&sort=bat&dir=<%= prochainDir %>">Bâtiment</a></th>
                <th><a href="<%= baseQS %>&sort=parquet&dir=<%= prochainDir %>">Parquet</a></th>
                <th style="text-align:right">Poussins</th>
                <th style="text-align:right">Adultes</th>
                <th style="text-align:right">Morts</th>
                <th style="text-align:right">Total <%= inclureMorts ? "(+morts)" : "" %></th>
            </tr>
        </thead>
        <tbody>
            <% for (int i = 0; i < liste.size(); i++) {
                   PrevisionJourDetail e = liste.get(i); %>
            <tr>
                <td>
                    <%= e.getLibelleLot() %>
                    <% if (e.getAvertissement() != null) { %>
                        <span title="<%= e.getAvertissement() %>" style="color:orange">&#9888;</span>
                    <% } %>
                </td>
                <td><%= e.getLibelleBatiment() %></td>
                <td><%= e.getLibelleParquet() %></td>
                <td style="text-align:right"><%= String.format("%,.0f", e.getPoussins()) %></td>
                <td style="text-align:right"><%= String.format("%,.0f", e.getAdultes()) %></td>
                <td style="text-align:right"><%= String.format("%,.0f", e.getMorts()) %></td>
                <td style="text-align:right"><b><%= String.format("%,.0f", e.getTotal(inclureMorts)) %></b></td>
            </tr>
            <% } %>
        </tbody>
        <tfoot>
            <tr style="background:#e8e8e8;font-weight:bold">
                <td colspan="3">TOTAL FERME</td>
                <td style="text-align:right"><%= String.format("%,.0f", totP) %></td>
                <td style="text-align:right"><%= String.format("%,.0f", totA) %></td>
                <td style="text-align:right"><%= String.format("%,.0f", totM) %></td>
                <td style="text-align:right"><%= String.format("%,.0f", totP + totA + (inclureMorts ? totM : 0)) %></td>
            </tr>
        </tfoot>
    </table>
</div>
<%
} catch (Exception e) {
    e.printStackTrace();
%>
<script>alert('<%= e.getMessage() == null ? e.getClass().getName() : e.getMessage().replace("'", "\\'") %>'); history.back();</script>
<% } %>