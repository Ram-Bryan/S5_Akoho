<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="user.UserEJB" %>
<%@ page import="affichage.PageConsulte" %>
<%@ page import="cinema.CinemaFactureClient" %>
<%@ page import="constante.ConstanteEtat" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String lien = (String) session.getValue("lien");

    CinemaFactureClient o = new CinemaFactureClient();
    PageConsulte pc = new PageConsulte(o, request, u);
    pc.setTitre("Fiche facture");
    String id = pc.getBase().getTuppleID();

    pc.getChampByName("id").setLibelle("Id");
    pc.getChampByName("numero").setLibelle("Numero");
    pc.getChampByName("idreservation").setLibelle("Reservation");
    pc.getChampByName("datefacture").setLibelle("Date facture");
    pc.getChampByName("etat").setLibelle("Etat");
    pc.getChampByName("iduser").setVisible(false);
    pc.setOrdre(new String[]{"id","numero","idreservation","datefacture","etat"});

    String pageRetour = "cinema/facture/facture-liste.jsp";
    String pageActuel = "cinema/facture/facture-fiche.jsp";
    String pageModif  = "cinema/facture/facture-saisie.jsp&acte=update";
    String classe     = "cinema.CinemaFactureClient";
%>
<div class="content-wrapper">
    <h1 class="box-title">
        <a href="<%= lien + "?but=" + pageRetour%>"><i class="fa fa-angle-left"></i></a>
        <%=pc.getTitre()%>
    </h1>
    <div class="row m-0">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <div class="box-fiche"><div class="box"><div class="box-body">
                <% out.println(pc.getHtml()); %>
                <br/>
                <div class="box-footer">
                    <a class="btn btn-danger pull-left"
                       href="<%= lien + "?but=apresTarif.jsp&id=" + id + "&acte=delete&bute="+pageRetour+"&classe="+classe %>">Supprimer</a>
                    <% if (o.getEtat() < ConstanteEtat.getEtatValider()){ %>
                        <a class="btn btn-secondary pull-right" style="margin-right:10px"
                           href="<%= lien + "?but="+ pageModif +"&id=" + id %>">Modifier</a>
                        <a class="btn btn-primary pull-right" style="margin-right:10px"
                           href="<%= lien + "?but=apresTarif.jsp&acte=valider&classe="+classe+"&nomtable=cinema_facture_client&bute="+pageActuel+"&id=" + id %>">Viser</a>
                    <% } %>
                </div>
                <br/>
            </div></div></div>
        </div>
    </div>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>