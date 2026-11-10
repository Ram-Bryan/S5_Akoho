<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="user.UserEJB" %>
<%@ page import="affichage.PageConsulte" %>
<%@ page import="cinema.CinemaReservation" %>
<%@ page import="constante.ConstanteEtat" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String lien = (String) session.getValue("lien");

    CinemaReservation o = new CinemaReservation();
    PageConsulte pc = new PageConsulte(o, request, u);
    pc.setTitre("Fiche réservation");
    String id = pc.getBase().getTuppleID();

    pc.getChampByName("id").setLibelle("Id");
    pc.getChampByName("idclient").setLibelle("Client");
    pc.getChampByName("idprogramme").setLibelle("Programme");
    pc.getChampByName("datereservation").setLibelle("Date de réservation");
    pc.getChampByName("idstatutreservation").setLibelle("Statut");
    pc.getChampByName("etat").setLibelle("&Eacute;tat");
    pc.getChampByName("iduser").setVisible(false);

    String[] ordre = {"id", "idclient", "idprogramme", "datereservation", "idstatutreservation", "etat"};
    pc.setOrdre(ordre);

    String pageRetour      = "cinema/reservation/reservation-liste.jsp";
    String pageActuel      = "cinema/reservation/reservation-fiche.jsp";
    String pageModif       = "cinema/reservation/reservation-saisie.jsp&acte=update";
    String pageApresDelete = "cinema/reservation/reservation-liste.jsp";
    String classe          = "cinema.CinemaReservation";

    Map<String, String> map = new HashMap<>();
    map.put("inc/reservation-det", "");
    String tab = request.getParameter("tab");
    if (tab == null) tab = "inc/reservation-det";
    map.put(tab, "active");
    tab = tab + ".jsp";
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
                       href="<%= lien + "?but=apresTarif.jsp&id=" + id + "&acte=delete&bute="+pageApresDelete+"&classe="+classe %>">Supprimer</a>
                    <% if (o.getEtat() < ConstanteEtat.getEtatValider()){ %>
                        <a class="btn btn-secondary pull-right"
                           href="<%= lien + "?but="+ pageModif +"&id=" + id %>"
                           style="margin-right:10px">Modifier</a>
                        <a class="btn btn-primary pull-right"
                           href="<%= lien + "?but=apresTarif.jsp&acte=valider&classe="+classe+"&nomtable=cinema_reservation&bute="+pageActuel+"&id=" + id %>"
                           style="margin-right:10px">Viser</a>
                    <% } %>
                </div>
                <br/>
            </div></div></div>
        </div>
    </div>
    <div class="row m-0">
        <div class="col-md-12 nopadding">
            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                    <li class="<%=map.get("inc/reservation-det")%>">
                        <a href="<%= lien %>?but=<%= pageActuel %>&id=<%= id %>&tab=inc/reservation-det">Places réservées</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <jsp:include page="<%= tab %>">
                        <jsp:param name="id" value="<%= id %>" />
                    </jsp:include>
                </div>
            </div>
        </div>
    </div>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>