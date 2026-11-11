<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsertMultiple" %>
<%@ page import="affichage.Champ" %>
<%@ page import="affichage.Liste" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.CinemaProgramme" %>
<%@ page import="cinema.CinemaDetailProgramme" %>
<%@ page import="cinema.CinemaFilm" %>
<%@ page import="cinema.CinemaSalle" %>
<%@ page import="utilitaire.Utilitaire" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String classeMere    = "cinema.CinemaProgramme";
    String classeFille   = "cinema.CinemaDetailProgramme";
    String nomTableFille = "cinema_detail_programme";
    String colonneMere   = "idprogramme";
    String apres         = "cinema/programme/programme-fiche.jsp";
    int    taille        = 5;

    CinemaProgramme mere = new CinemaProgramme();
    CinemaDetailProgramme fille = new CinemaDetailProgramme();

    PageInsertMultiple pi = new PageInsertMultiple(mere, fille, request, taille, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie d'un programme");

    // ---------- MÈRE : listes ----------
    Liste listeSalle = new Liste("idsalle", new CinemaSalle(), "libelle", "id");
    pi.getFormu().changerEnChamp(new Liste[]{ listeSalle });

    pi.getFormu().getChamp("idsalle").setLibelle("Salle");
    pi.getFormu().getChamp("datedebut").setLibelle("Date de début");
    pi.getFormu().getChamp("datedebut").setDefaut(Utilitaire.dateDuJour());
    pi.getFormu().getChamp("heuredebut").setLibelle("Heure de début (HH:mm)");
    pi.getFormu().getChamp("heuredebut").setDefaut("14:00");
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("etat").setDefaut("1");
    pi.getFormu().getChamp("iduser").setVisible(false);
    pi.getFormu().getChamp("iduser").setDefaut("0");

    // ---------- FILLES : listes ----------
    Liste listeFilm = new Liste("idfilm", new CinemaFilm(), "titre", "id");
    pi.getFormufle().changerEnChamp(new Liste[]{ listeFilm });

    pi.getFormufle().getChamp("idfilm_0").setLibelle("Film");
    pi.getFormufle().getChamp("rang_0").setLibelle("Rang (1, 2, 3...)");

    // Masquer colonnes techniques filles
    Champ.setVisible(pi.getFormufle().getChampMulitple("id").getListeChamp(), false);
    Champ.setVisible(pi.getFormufle().getChampMulitple("idprogramme").getListeChamp(), false);
    Champ.setVisible(pi.getFormufle().getChampMulitple("etat").getListeChamp(), false);
    Champ.setVisible(pi.getFormufle().getChampMulitple("iduser").getListeChamp(), false);

    String[] colOrdre = {"idfilm", "rang"};
    pi.getFormufle().setColOrdre(colOrdre);

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) {
        pi.setTitre("Modification d'un programme");
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