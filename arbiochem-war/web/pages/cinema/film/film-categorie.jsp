<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsert" %>
<%@ page import="affichage.Liste" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.CinemaCategorieFilm" %>
<%@ page import="cinema.CinemaCategorie" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String mapping  = "cinema.CinemaCategorieFilm";
    String nomTable = "cinema_categorie_film";
    String apres    = "cinema/film/film-fiche.jsp";

    String idfilmParam = request.getParameter("idfilm");

    CinemaCategorieFilm o = new CinemaCategorieFilm();
    PageInsert pi = new PageInsert(o, request, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Ajouter catégorie au film");

    // 1. changerEnChamp AVANT setLibelle
    Liste listeCategorie = new Liste("idcategorie", new CinemaCategorie(), "libelle", "id");
    pi.getFormu().changerEnChamp(new Liste[]{ listeCategorie });

    // 2. Libellés
    pi.getFormu().getChamp("idcategorie").setLibelle("Catégorie");

    // 3. Cacher le champ idfilm + le pré-remplir
    pi.getFormu().getChamp("idfilm").setVisible(false);
    if (idfilmParam != null && !idfilmParam.isEmpty()) {
        pi.getFormu().getChamp("idfilm").setDefaut(idfilmParam);
    }

    pi.preparerDataFormu();
    pi.getFormu().makeHtmlInsertTabIndex();
%>
<div class="content-wrapper">
    <h1><%=pi.getTitre()%></h1>
    <form action="<%=pi.getLien()%>?but=apresTarif.jsp" method="post" name="<%=nomTable%>" id="<%=nomTable%>">
        <% out.println(pi.getFormu().getHtmlInsert()); %>
        <input name="acte"     type="hidden" value="insert">
        <input name="bute"     type="hidden" value="<%=apres%>&id=<%=idfilmParam%>">
        <input name="classe"   type="hidden" value="<%=mapping%>">
        <input name="nomtable" type="hidden" value="<%=nomTable%>">
    </form>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>