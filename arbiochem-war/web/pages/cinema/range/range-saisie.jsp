<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsertMultiple" %>
<%@ page import="affichage.Champ" %>
<%@ page import="affichage.Liste" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.CinemaRange" %>
<%@ page import="cinema.CinemaPlace" %>
<%@ page import="cinema.CinemaSalle" %>
<%@ page import="cinema.CinemaCategorieRange" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String classeMere    = "cinema.CinemaRange";
    String classeFille   = "cinema.CinemaPlace";
    String nomTableFille = "cinema_place";
    String colonneMere   = "idrange";
    String apres         = "cinema/range/range-fiche.jsp";
    int    taille        = 15;

    CinemaRange mere = new CinemaRange();
    CinemaPlace fille = new CinemaPlace();

    PageInsertMultiple pi = new PageInsertMultiple(mere, fille, request, taille, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie d'une range");

    // ---------- MÈRE : dropdowns ----------
    Liste listeSalle     = new Liste("idsalle",          new CinemaSalle(),          "libelle", "id");
    Liste listeCategorie = new Liste("idcategorierange", new CinemaCategorieRange(), "libelle", "id");
    pi.getFormu().changerEnChamp(new Liste[]{ listeSalle, listeCategorie });

    pi.getFormu().getChamp("libelle").setLibelle("Libellé");
    pi.getFormu().getChamp("idsalle").setLibelle("Salle");
    pi.getFormu().getChamp("idcategorierange").setLibelle("Catégorie de range");
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("etat").setDefaut("1");
    pi.getFormu().getChamp("iduser").setVisible(false);
    pi.getFormu().getChamp("iduser").setDefaut("0");

    // ---------- FILLES : libellé place ----------
    pi.getFormufle().getChamp("libelle_0").setLibelle("Libellé place");

    Champ.setVisible(pi.getFormufle().getChampMulitple("id").getListeChamp(), false);
    Champ.setVisible(pi.getFormufle().getChampMulitple("idrange").getListeChamp(), false);
    Champ.setVisible(pi.getFormufle().getChampMulitple("etat").getListeChamp(), false);
    Champ.setVisible(pi.getFormufle().getChampMulitple("iduser").getListeChamp(), false);

    String[] colOrdre = {"libelle"};
    pi.getFormufle().setColOrdre(colOrdre);

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) {
        pi.setTitre("Modification d'une range");
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