<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="bean.CGenUtil" %>
<%@ page import="ferme.receptionaeroport.ReceptionPoussinAeroport" %>
<%@ page import="ferme.receptionlieux.ReceptionLieux" %>
<%@ page import="ferme.lieu.Lieu" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="utilitaire.UtilDB" %>

<%
Connection c = null;
try {
    String idLot = request.getParameter("id");
    if (idLot == null || idLot.trim().isEmpty()) {
        out.println("<p class='alert alert-warning'>Lot introuvable.</p>");
        return;
    }

    String lien = (String) session.getValue("lien");
    if (lien == null) lien = "module.jsp";

    c = new UtilDB().GetConn();

    // 1. Latest validated airport reception for this lot
    ReceptionPoussinAeroport[] airports = (ReceptionPoussinAeroport[]) CGenUtil.rechercher(
        new ReceptionPoussinAeroport(), null, null, c,
        " AND IDLOT = '" + idLot.replace("'", "''") + "'"
        + " AND ETAT >= 11 ORDER BY DATY DESC, ID DESC");

    // 2. All steps for this lot
    ReceptionLieux[] steps = (ReceptionLieux[]) CGenUtil.rechercher(
        new ReceptionLieux(), null, null, c,
        " AND IDLOT = '" + idLot.replace("'", "''") + "' ORDER BY ORDRE");

    // 3. Build a unified list (airport as ordre 0, then steps 1..N)
    //    Row layout:
    //      [0] ordre (int)
    //      [1] id (String)
    //      [2] fichePath (String)     ← used for the click-through link
    //      [3] lieuLib (String)
    //      [4] daty (java.sql.Date)
    //      [5] qteRecus (double)
    //      [6] nbMort (double)
    //      [7] nbPerdu (double)
    //      [8] vivants (double)
    java.util.List<Object[]> rows = new java.util.ArrayList<>();

    if (airports != null && airports.length > 0) {
        ReceptionPoussinAeroport a = airports[0];
        Lieu l = (a.getIdLieu() != null)
            ? (Lieu) new Lieu().getById(a.getIdLieu(), null, c)
            : null;
        String lieuLib = (l != null) ? l.getLibelle() : "—";
        double viv = a.getQteRecus() - a.getNbMort() - a.getNbPerdu();
        rows.add(new Object[]{
            0,
            a.getId(),
            "ferme/receptionaeroport/receptionaeroport-fiche.jsp",
            lieuLib,
            a.getDaty(),
            a.getQteRecus(),
            a.getNbMort(),
            a.getNbPerdu(),
            viv
        });
    }

    if (steps != null) {
        for (ReceptionLieux s : steps) {
            Lieu l = (s.getIdLieu() != null)
                ? (Lieu) new Lieu().getById(s.getIdLieu(), null, c)
                : null;
            String lieuLib = (l != null) ? l.getLibelle() : "—";
            rows.add(new Object[]{
                s.getOrdre(),
                s.getId(),
                "ferme/receptionlieux/receptionlieux-fiche.jsp",
                lieuLib,
                s.getDaty(),
                s.getQteRecus(),
                s.getNbMort(),
                s.getNbPerdu(),
                s.getVivants()
            });
        }
    }

    if (rows.isEmpty()) {
        out.println("<center><h4>Aucune réception enregistrée pour ce lot.</h4></center>");
        return;
    }

    // 4. Render + cumulative
    double cumMort  = 0;
    double cumPerdu = 0;
    double prevViv  = 0;
%>

<div class="box-body">
    <table class="table table-hover table-striped">
        <thead>
            <tr class="head">
                <th>Étape</th>
                <th>Lieu</th>
                <th>Date</th>
                <th>Reçus</th>
                <th>Morts</th>
                <th>Perdus</th>
                <th>Vivants</th>
                <th>Écart</th>
                <th>Morts cumulés</th>
                <th>Perdus cumulés</th>
            </tr>
        </thead>
        <tbody>
        <%
            for (Object[] r : rows) {
                int ordre        = (Integer) r[0];
                String stepId    = (String)  r[1];
                String fichePath = (String)  r[2];
                String lieu      = (String)  r[3];
                java.sql.Date dt = (java.sql.Date) r[4];
                double qteRecus  = (Double)  r[5];
                double nbMort    = (Double)  r[6];
                double nbPerdu   = (Double)  r[7];
                double vivants   = (Double)  r[8];

                cumMort  += nbMort;
                cumPerdu += nbPerdu;

                double ecart = (ordre == 0) ? 0 : (qteRecus - prevViv);
                prevViv = vivants;

                String rowStyle = (Math.abs(ecart) > 0.01)
                    ? " style='background:#ffe8cc;'"
                    : "";

                String href = lien + "?but=" + fichePath + "&id=" + stepId;
        %>
            <tr<%= rowStyle %>>
                <td align="center">
                    <a href="<%= href %>" title="Ouvrir la fiche de cette étape">
                        <strong><%= ordre %></strong>
                    </a>
                </td>
                <td><%= lieu %></td>
                <td><%= dt == null ? "—" : new java.text.SimpleDateFormat("dd/MM/yyyy").format(dt) %></td>
                <td align="right"><%= new java.text.DecimalFormat("0.##").format(qteRecus) %></td>
                <td align="right"><%= new java.text.DecimalFormat("0.##").format(nbMort) %></td>
                <td align="right"><%= new java.text.DecimalFormat("0.##").format(nbPerdu) %></td>
                <td align="right"><%= new java.text.DecimalFormat("0.##").format(vivants) %></td>
                <td align="right"><%= (ordre == 0) ? "—" : new java.text.DecimalFormat("0.##").format(ecart) %></td>
                <td align="right"><%= new java.text.DecimalFormat("0.##").format(cumMort) %></td>
                <td align="right"><%= new java.text.DecimalFormat("0.##").format(cumPerdu) %></td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

<%
} catch (Exception e) {
    e.printStackTrace();
%>
<div class="alert alert-danger">
    Erreur lors du calcul de la traçabilité : <%= e.getMessage() %>
</div>
<%
} finally {
    if (c != null) try { c.close(); } catch (Exception e) {}
}
%>