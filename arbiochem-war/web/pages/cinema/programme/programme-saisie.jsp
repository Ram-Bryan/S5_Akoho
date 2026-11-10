<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsert" %>
<%@ page import="affichage.Liste" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.CinemaProgramme" %>
<%@ page import="cinema.CinemaFilm" %>
<%@ page import="cinema.CinemaSalle" %>
<%@ page import="utilitaire.Utilitaire" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String mapping  = "cinema.CinemaProgramme";
    String nomTable = "cinema_programme";
    String apres    = "cinema/programme/programme-fiche.jsp";

    CinemaProgramme o = new CinemaProgramme();
    PageInsert pi = new PageInsert(o, request, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie programme");

    // 1. changerEnChamp AVANT setLibelle
    Liste listeFilm  = new Liste("idfilm", new CinemaFilm(), "titre", "id");
    Liste listeSalle = new Liste("idsalle", new CinemaSalle(), "libelle", "id");
    pi.getFormu().changerEnChamp(new Liste[]{ listeFilm, listeSalle });

    // 2. Libellés
    pi.getFormu().getChamp("idfilm").setLibelle("Film");
    pi.getFormu().getChamp("idsalle").setLibelle("Salle");
    pi.getFormu().getChamp("datedebut").setLibelle("Date début");
    pi.getFormu().getChamp("datedebut").setDefaut(Utilitaire.dateDuJour());
    pi.getFormu().getChamp("heuredebut").setLibelle("Heure début (HH:mm)");
    pi.getFormu().getChamp("datefin").setLibelle("Date fin");
    pi.getFormu().getChamp("datefin").setDefaut(Utilitaire.dateDuJour());
    pi.getFormu().getChamp("heurefin").setLibelle("Heure fin (HH:mm)");
    pi.getFormu().getChamp("prixbase").setLibelle("Prix base");
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("etat").setDefaut("1");
    pi.getFormu().getChamp("iduser").setVisible(false);
    pi.getFormu().getChamp("iduser").setDefaut("0");

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) pi.setTitre("Modification programme");

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