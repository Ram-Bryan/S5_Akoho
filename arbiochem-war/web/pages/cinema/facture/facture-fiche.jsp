<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="cinema.*" %>
<%@ page import="user.*" %>
<%@ page import="affichage.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

<%
    UserEJB u = (user.UserEJB) session.getValue("u");
%>
<%
    try {
        CinemaFactureClient f = new CinemaFactureClient();
        PageConsulte pc = new PageConsulte(f, request, u);
        String lien = (String) session.getValue("lien");
        pc.setTitre("Fiche facture");
        pc.getBase();
        String id = pc.getBase().getTuppleID();

        pc.getChampByName("id").setLibelle("Id");
        pc.getChampByName("numero").setLibelle("Numéro");
        pc.getChampByName("idreservation").setLibelle("Réservation");
        pc.getChampByName("datefacture").setLibelle("Date de facture");
        pc.getChampByName("etat").setLibelle("&Eacute;tat");
        pc.getChampByName("iduser").setVisible(false);

        String pageActuel = "cinema/facture/facture-fiche.jsp";
        String pageListe  = "cinema/facture/facture-liste.jsp";

        // On récupère la réservation liée pour l'onglet
        CinemaFactureClient facture = (CinemaFactureClient) pc.getBase();
        String idreservation = facture.getIdreservation();

        Map<String, String> map = new HashMap<String, String>();
        map.put("inc/facture-det", "");
        String tab = request.getParameter("tab");
        if (tab == null) {
            tab = "inc/facture-det";
        }
        map.put(tab, "active");
        tab = tab + ".jsp";
%>

<div class="content-wrapper">
    <h1 class="box-title">
        <a href="<%= lien + "?but=" + pageListe %>"><i class="fa fa-angle-left"></i></a>
        <%= pc.getTitre() %>
    </h1>

    <div class="row m-0">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <div class="box-fiche">
                <div class="box">
                    <div class="box-body">
                        <% out.println(pc.getHtml()); %>
                        <br/>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row m-0">
        <div class="col-md-12 nopadding">
            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                    <li class="<%=map.get("inc/facture-det")%>">
                        <a href="<%= lien %>?but=<%= pageActuel %>&id=<%= id %>&tab=inc/facture-det">Détails (Places payées)</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <jsp:include page="<%= tab %>">
                        <jsp:param name="id" value="<%= id %>" />
                        <jsp:param name="idreservation" value="<%= idreservation %>" />
                    </jsp:include>
                </div>
            </div>
        </div>
    </div>
</div>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>