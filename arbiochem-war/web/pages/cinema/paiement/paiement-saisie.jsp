<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="affichage.PageInsert" %>
<%@ page import="affichage.Liste" %>
<%@ page import="user.UserEJB" %>
<%@ page import="cinema.CinemaPaiement" %>
<%@ page import="cinema.FactureClient" %>
<%@ page import="faturefournisseur.ModePaiement" %>
<%@ page import="utilitaire.Utilitaire" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String mapping  = "cinema.CinemaPaiement";
    String nomTable = "cinema_paiement";
    String apres    = "cinema/paiement/paiement-fiche.jsp";

    CinemaPaiement o = new CinemaPaiement();
    PageInsert pi = new PageInsert(o, request, u);
    pi.setLien((String) session.getValue("lien"));
    pi.setTitre("Saisie d'un paiement");

    // Dropdowns
    Liste listeFacture = new Liste("idfacture", new FactureClient(), "designation", "id");
    Liste listeMode    = new Liste("idmodepaiement", new ModePaiement(), "val", "id");
    pi.getFormu().changerEnChamp(new Liste[]{ listeFacture, listeMode });

    pi.getFormu().getChamp("idfacture").setLibelle("Facture");
    pi.getFormu().getChamp("daty").setLibelle("Date de paiement");
    pi.getFormu().getChamp("daty").setDefaut(Utilitaire.dateDuJour());
    pi.getFormu().getChamp("montant").setLibelle("Montant (Ar)");
    pi.getFormu().getChamp("idmodepaiement").setLibelle("Mode de paiement");
    pi.getFormu().getChamp("iduser").setVisible(false);
    pi.getFormu().getChamp("iduser").setDefaut(u.getUser().getTuppleID());

    String acte = request.getParameter("acte");
    if (acte != null && acte.equalsIgnoreCase("update")) pi.setTitre("Modification d'un paiement");

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