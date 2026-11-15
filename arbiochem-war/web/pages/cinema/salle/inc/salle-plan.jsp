<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8;" %>
<%@ page import="cinema.CinemaPlaceLib" %>
<%@ page import="bean.CGenUtil" %>
<%@ page import="utilitaire.UtilDB" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.*" %>

<%
    String idsalle = request.getParameter("id");
    if (idsalle == null || idsalle.trim().isEmpty()) {
        out.println("<p>ID Salle manquant</p>");
        return;
    }

    Connection c = null;
    CinemaPlaceLib[] places = null;
    try {
        c = new UtilDB().GetConn();
        CinemaPlaceLib crit = new CinemaPlaceLib();
        // On récupère toutes les places de la salle, triées par range et par nom
        places = (CinemaPlaceLib[]) CGenUtil.rechercher(crit, null, null, c, " AND idsalle = '" + idsalle + "' ORDER BY rangelib ASC, libelle ASC ");
    } finally {
        if (c != null) try { c.close(); } catch (Exception e) {}
    }

    // On regroupe les places par nom de range (ex: "A (Standard)", "B (Confort)")
    Map<String, List<CinemaPlaceLib>> ranges = new LinkedHashMap<String, List<CinemaPlaceLib>>();
    if (places != null) {
        for (CinemaPlaceLib p : places) {
            String key = p.getRangelib() != null ? p.getRangelib() : "Autre";
            if (!ranges.containsKey(key)) {
                ranges.put(key, new ArrayList<CinemaPlaceLib>());
            }
            ranges.get(key).add(p);
        }
    }
%>

<style>
    /* Le conteneur du plan */
    .plan-salle-container {
        background-color: #f9f9f9;
        padding: 40px 20px;
        border-radius: 8px;
        display: flex;
        flex-direction: column;
        align-items: flex-start; /* Aligne les éléments à gauche */
        font-family: Arial, sans-serif;
    }
    
    /* Une ligne de places */
    .range-row {
        display: flex;
        align-items: center;
        margin-bottom: 12px;
    }
    
    /* Le nom de la range (A, B, C...) */
    .range-label {
        width: 120px; /* Un peu plus large pour les longs noms */
        text-align: right;
        font-weight: bold;
        color: #555;
        margin-right: 25px;
        font-size: 14px;
    }
    
    /* Le groupe de carrés */
    .places-group {
        display: flex;
        gap: 8px;
    }
    
    /* Le carré d'une place */
    .place-square {
        width: 38px;
        height: 38px;
        border-radius: 6px;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 12px;
        color: #fff;
        font-weight: 600;
        cursor: pointer;
        transition: transform 0.2s, box-shadow 0.2s;
        box-shadow: 0 2px 4px rgba(0,0,0,0.2);
    }
    
    .place-square:hover {
        transform: scale(1.15);
        box-shadow: 0 4px 8px rgba(0,0,0,0.3);
    }
    
    /* Les couleurs par catégorie */
    .place-standard { background-color: #0064a6; } /* Vert */
    .place-confort  { background-color: #28ad00; } /* Orange */
    .place-vip      { background-color: #dd8839; } /* Rouge */
    .place-other    { background-color: #777; }    /* Gris */

    /* La légende en bas */
    .legend-container {
        margin-top: 50px;
        display: flex;
        gap: 30px;
        padding-top: 20px;
        border-top: 1px solid #ddd;
        width: 100%;
        justify-content: flex-start; /* Aligne la légende à gauche aussi */
    }
    .legend-item {
        display: flex;
        align-items: center;
        gap: 10px;
        font-size: 14px;
        color: #555;
    }
    .legend-color {
        width: 20px;
        height: 20px;
        border-radius: 4px;
    }
</style>

<div class="box-body">
    <div class="plan-salle-container">
        
        <% if (ranges.isEmpty()) { %>
            <p style="color: #999;">Aucune place trouvée pour cette salle.</p>
        <% } else { 
            for (Map.Entry<String, List<CinemaPlaceLib>> entry : ranges.entrySet()) { 
                String rangelib = entry.getKey();
                List<CinemaPlaceLib> placesDeLaRange = entry.getValue();
        %>
            <div class="range-row">
                <div class="range-label"><%= rangelib %></div>
                <div class="places-group">
                    <% for (CinemaPlaceLib p : placesDeLaRange) { 
                        // Détermination de la couleur selon la catégorie de la place
                        String cat = p.getCategorierangelib() != null ? p.getCategorierangelib().toLowerCase() : "";
                        String colorClass = "place-other";
                        
                        if (cat.contains("standard")) {
                            colorClass = "place-standard";
                        } else if (cat.contains("confort") || cat.contains("premium")) {
                            colorClass = "place-confort";
                        } else if (cat.contains("vip")) {
                            colorClass = "place-vip";
                        }
                    %>
                        <div class="place-square <%= colorClass %>" title="<%= p.getLibelle() %> (<%= p.getCategorierangelib() %>)">
                            <%= p.getLibelle() %>
                        </div>
                    <% } %>
                </div>
            </div>
        <%  } 
           } %>
           
        <!-- Légende des couleurs -->
        <div class="legend-container">
            <div class="legend-item">
                <div class="legend-color place-standard"></div> Standard
            </div>
            <div class="legend-item">
                <div class="legend-color place-confort"></div> Confort
            </div>
            <div class="legend-item">
                <div class="legend-color place-vip"></div> VIP
            </div>
        </div>
    </div>
</div>