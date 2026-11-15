<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.*" %>
<%@ page import="utilitaire.UtilDB" %>
<%@ page import="java.sql.Connection" %>

<%
    UserEJB u = (user.UserEJB) session.getValue("u");
    String refUser = u.getUser() != null ? u.getUser().getTuppleID() : "0";
    String lien = (String) session.getValue("lien");
    
    Connection c = null;
    try {
        c = new UtilDB().GetConn();
        c.setAutoCommit(false); // Transaction globale
        
        // 1. Créer la Salle
        String libelleSalle = request.getParameter("libelle");
        CinemaSalle salle = new CinemaSalle();
        salle.setLibelle(libelleSalle);
        salle.createObject(refUser, c);
        
        // 2. Parcourir les Ranges
        int i = 0;
        while(request.getParameter("nomrange_" + i) != null) {
            String nomRange = request.getParameter("nomrange_" + i);
            String idCategorie = request.getParameter("idcategorie_" + i);
            String nbPlacesStr = request.getParameter("nbplaces_" + i);
            
            if(nomRange != null && !nomRange.trim().isEmpty() && nbPlacesStr != null) {
                int nbPlaces = Integer.parseInt(nbPlacesStr);
                
                // 2.a Créer la Range
                CinemaRange range = new CinemaRange();
                range.setLibelle(nomRange);
                range.setIdsalle(salle.getId());
                range.setIdcategorierange(idCategorie);
                range.createObject(refUser, c);
                
                // 2.b Créer les Places A1, A2... An
                for(int p = 1; p <= nbPlaces; p++) {
                    CinemaPlace place = new CinemaPlace();
                    place.setLibelle(nomRange + p);
                    place.setIdrange(range.getId());
                    place.createObject(refUser, c);
                }
            }
            i++;
        }
        
        c.commit(); // On valide tout
        
        // REDIRECTION JAVASCRIPT (car module.jsp a déjà commencé à écrire le HTML)
        %>
        <script>
            window.location.href = '<%= lien %>?but=cinema/salle/salle-fiche.jsp&id=<%= salle.getId() %>';
        </script>
        <%
        return; // On arrête le traitement de la JSP ici
        
    } catch(Exception e) {
        if(c != null) try { c.rollback(); } catch(Exception ex){}
        e.printStackTrace();
        
        String msg = e.getMessage() == null ? "Erreur inconnue" : e.getMessage().replace("'", "\\'");
        %>
        <script>
            alert('Erreur lors de la création : <%=msg%>'); 
            history.back();
        </script>
        <%
    } finally {
        if(c != null) try { c.close(); } catch(Exception ex){}
    }
%>