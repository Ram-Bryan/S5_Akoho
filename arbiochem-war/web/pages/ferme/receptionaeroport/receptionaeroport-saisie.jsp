<%@page import="bean.CGenUtil"%>
<%@page import="affichage.PageUpdateMultiple"%>
<%@page import="ferme.receptionaeroport.*"%>
<%@page import="user.UserEJB"%>
<%@page import="affichage.PageInsertMultiple"%>
<%@page import="affichage.Liste"%>
<%@page import="bean.TypeObjet"%>
<%@page import="affichage.PageInsert"%>
<%@page import="utilitaire.Utilitaire"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="magasin.Magasin" %>
<%@ page import="java.util.Date" %>
<%@ page import="ferme.configuration.QualitePoussin" %>
<%@ page import="ferme.lieu.Lieu" %>



<%
    try{
    UserEJB u = null;
    u = (UserEJB) session.getValue("u");
    ReceptionPoussinAeroport mere = new ReceptionPoussinAeroport();
    ReceptionPoussinAeroportDetail fille = new ReceptionPoussinAeroportDetail();
    String idLot = request.getParameter("idLot");
    int nombreLigne = 10;
    PageInsertMultiple pi = new PageInsertMultiple(mere, fille, request, nombreLigne, u);
    pi.setLien((String) session.getValue("lien"));
    pi.getFormu().getChamp("idLot").setLibelle("Lot");
    pi.getFormu().getChamp("daty").setLibelle("Date");
    pi.getFormu().getChamp("heureDepart").setLibelle("Heure de d&eacute;part");
    pi.getFormu().getChamp("heureDepart").setType("time");
    pi.getFormu().getChamp("heureArrive").setLibelle("Heure d'arriv&eacute;e");
    pi.getFormu().getChamp("heureArrive").setType("time");
    pi.getFormu().getChamp("heureArrive").setDefaut(
    utilitaire.Utilitaire.heureCouranteHM());

    pi.getFormu().getChamp("etat").setVisible(false);
    pi.getFormu().getChamp("nbrCartonMale").setLibelle("Nombre de cartons m&acirc;les");
    pi.getFormu().getChamp("nbrCartonFemelle").setLibelle("Nombre de cartons femelles");

    pi.getFormu().getChamp("qteRecus").setLibelle(
    "Quantite recus");

    pi.getFormu().getChamp("nbMort").setLibelle("Morts");
    pi.getFormu().getChamp("nbMort").setDefaut("0");

    pi.getFormu().getChamp("nbPerdu").setLibelle("Perdus");
    pi.getFormu().getChamp("nbPerdu").setDefaut("0");

    pi.getFormu().getChamp("idLot").setPageAppelComplete("ferme.lot.Lot","id","LOT");
    pi.getFormu().getChamp("idLot").setPageAppelInsert("ferme/lot/lot-saisie.jsp","id;nomlot");


    pi.getFormu().getChamp("nbrCartonMale").setVisible(false);
    pi.getFormu().getChamp("nbrCartonMale").setDefaut("0");
    pi.getFormu().getChamp("nbrCartonFemelle").setVisible(false);
    pi.getFormu().getChamp("nbrCartonFemelle").setDefaut("0");

    Lieu lieuAeroport = new Lieu();
    lieuAeroport.setNomTable("LIEU_AEROPORT");
    affichage.Liste listeLieu = new affichage.Liste("idLieu", lieuAeroport, "libelle", "id");
    pi.getFormu().changerEnChamp(new affichage.Liste[]{ listeLieu });
    pi.getFormu().getChamp("idLieu").setLibelle("Lieu de réception");

    pi.getFormufle().getChamp("idSexe_0").setLibelle("Sexe");
    pi.getFormufle().getChamp("qte_0").setLibelle("Quantit&eacute;");
    affichage.Champ.setVisible(pi.getFormufle().getChampFille("id"), false);
    affichage.Champ.setVisible(pi.getFormufle().getChampFille("idMere"), false);

    affichage.Liste[] listef = new Liste[2];
    TypeObjet sexe=new TypeObjet();
    sexe.setNomTable("SEXE");
    listef[0]=new Liste("idSexe",sexe,"val","id");
    QualitePoussin qp=new QualitePoussin();
    listef[1]=new Liste("idQualitePoussin",qp,"val","id");
    pi.getFormufle().changerEnChamp(listef);
    pi.getFormufle().getChamp("idSexe_0").setLibelle("Sexe");
    pi.getFormufle().getChamp("idQualitePoussin_0").setLibelle("Qualit&eacute; poussin");

    String classeMere = "ferme.receptionaeroport.ReceptionPoussinAeroport";
    String classeFille = "ferme.receptionaeroport.ReceptionPoussinAeroportDetail";
    String butApresPost = "ferme/receptionaeroport/receptionaeroport-fiche.jsp";
    String colonneMere = "idMere";

    String[] colOrdre = {"id", "idQualitePoussin","idsexe","qte"};
    pi.getFormufle().setColOrdre(colOrdre);
        if (idLot != null && !idLot.isEmpty()){
        pi.getFormu().getChamp("idLot").setDefaut(idLot);
        pi.getFormu().getChamp("idLot").setAutre("readonly");
    }


    pi.preparerDataFormu();

    String idLotParam = request.getParameter("idLot");
    if (idLotParam != null && !idLotParam.trim().isEmpty()) {
        // pi.getFormu().getChamp("idLot").setDefaut(idLotParam);
        // pi.getFormu().getChamp("idLot").setAutre("readonly");

        java.sql.Connection tmpConn = null;
        try {
            tmpConn = new utilitaire.UtilDB().GetConn();
            ferme.lot.Lot lot = new ferme.lot.Lot();
            lot = (ferme.lot.Lot) lot.getById(idLotParam, null, tmpConn);

            if (lot != null && lot.getIdBlFille() != null && !lot.getIdBlFille().trim().isEmpty()) {
                faturefournisseur.As_BonDeLivraison_Fille blLine =
                    new faturefournisseur.As_BonDeLivraison_Fille();
                blLine = (faturefournisseur.As_BonDeLivraison_Fille)
                    blLine.getById(lot.getIdBlFille(), null, tmpConn);

                if (blLine != null) {
                    String qteStr = new java.text.DecimalFormat("0.##").format(blLine.getQuantite());
                    pi.getFormu().getChamp("qteRecus").setDefaut(qteStr);

                    if (blLine.getNumbl() != null) {
                        faturefournisseur.As_BonDeLivraison blHeader =
                            new faturefournisseur.As_BonDeLivraison();
                        blHeader = (faturefournisseur.As_BonDeLivraison)
                            blHeader.getById(blLine.getNumbl(), null, tmpConn);
                        if (blHeader != null && blHeader.getDaty() != null) {
                            String dateStr = utilitaire.Utilitaire.datetostring(blHeader.getDaty());
                            pi.getFormu().getChamp("daty").setDefaut(dateStr);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (tmpConn != null) try { tmpConn.close(); } catch (Exception ex) {}
        }
    }



    pi.getFormu().makeHtmlInsertTabIndex();
    // pi.getFormufle().makeHtmlInsertTableauIndex();

%>
<div class="content-wrapper">
    <!-- A modifier -->
    <%
        if (request.getParameter("acte")!=null && request.getParameter("acte")!=""){
            out.println("<h1>Modification du reception de poussin &agrave; l'aeroport</h1>");
        }else{
            out.println("<h1>Saisie d'un reception de poussin &agrave; l'aeroport</h1>");
        }
    %>
    <form id="formId" class='container' action="<%=pi.getLien()%>?but=apresMultiple.jsp" method="post" >
        <%
            out.println(pi.getFormu().getHtmlInsert());
        %>
        <div id="butfillejsp">
            <%
                // out.println(pi.getFormufle().getHtmlTableauInsert());
            %>
        </div>
        <input name="acte" type="hidden" id="nature" value="insertSansControle">
        <input name="bute" type="hidden" id="bute" value="<%= butApresPost %>">
        <input name="classe" type="hidden" id="classe" value="<%= classeMere %>">
        <input name="classefille" type="hidden" id="classefille" value="<%= classeFille %>">
        <input name="nombreLigne" type="hidden" id="nombreLigne" value="<%= nombreLigne %>">
        <input name="colonneMere" type="hidden" id="colonneMere" value="<%= colonneMere %>">
    </form>
</div>
<%
	} catch (Exception e) {
		e.printStackTrace();
%>
    <script language="JavaScript">
        alert('<%=e.getMessage()%>');
        history.back();
    </script>
<% }%>
