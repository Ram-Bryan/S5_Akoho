<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsert"%>
<%@ page import="user.UserEJB" %>
<%@ page import="test.livre.Livre" %>

<% try{
    UserEJB u = (user.UserEJB) session.getValue("u");
    String mapping = "test.livre.Livre";
    String nomTable = "LIVRE";
    String apres = "test/livre/livre-fiche.jsp";

    Livre o = new Livre();
    PageInsert pi = new PageInsert(o, request, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie livre");

    pi.getFormu().getChamp("titre").setLibelle("Titre");
    pi.getFormu().getChamp("auteur").setLibelle("Auteur");
    pi.getFormu().getChamp("isbn").setLibelle("ISBN");
    pi.getFormu().getChamp("anneepublication").setLibelle("Ann&eacute;e de publication");
    pi.getFormu().getChamp("editeur").setLibelle("&Eacute;diteur");
    pi.getFormu().getChamp("prix").setLibelle("Prix");
    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("etat").setDefaut("1");
    pi.getFormu().getChamp("iduser").setVisible(false);
    pi.getFormu().getChamp("iduser").setDefaut("0");

    String[] ordre = {"titre","auteur","isbn","anneepublication","editeur","prix"};
    pi.getFormu().setOrdre(ordre);

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) pi.setTitre("Modification livre");

    pi.preparerDataFormu();
    pi.getFormu().makeHtmlInsertTabIndex();
%>
<div class="content-wrapper">
    <h1><%=pi.getTitre()%></h1>
    <form action="<%=pi.getLien()%>?but=apresTarif.jsp" method="post" name="<%=nomTable%>" id="<%=nomTable%>">
        <% out.println(pi.getFormu().getHtmlInsert()); %>
        <input name="acte" type="hidden" id="nature" value="insert">
        <input name="bute" type="hidden" id="bute" value="<%=apres%>">
        <input name="classe" type="hidden" id="classe" value="<%=mapping%>">
        <input name="nomtable" type="hidden" id="nomtable" value="<%=nomTable%>">
    </form>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>