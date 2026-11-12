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
        CinemaReservation f = new CinemaReservation();
        PageConsulte pc = new PageConsulte(f, request, u);
        String lien = (String) session.getValue("lien");
        pc.setTitre("Fiche réservation");
        pc.getBase();
        String id = pc.getBase().getTuppleID();

        pc.getChampByName("id").setLibelle("Id");
        pc.getChampByName("idclient").setLibelle("Client");
        pc.getChampByName("iddetailprogramme").setLibelle("Film programmé");
        pc.getChampByName("datereservation").setLibelle("Date de réservation");
        pc.getChampByName("idstatutreservation").setLibelle("Statut");
        pc.getChampByName("etat").setLibelle("&Eacute;tat");
        pc.getChampByName("iduser").setVisible(false);

        String pageActuel = "cinema/reservation/reservation-fiche.jsp";
        String pageModif  = "cinema/reservation/reservation-saisie.jsp";
        String pageListe  = "cinema/reservation/reservation-liste.jsp";
        String classe     = "cinema.CinemaReservation";

        Map<String, String> map = new HashMap<String, String>();
        map.put("inc/reservation-det", "");
        String tab = request.getParameter("tab");
        if (tab == null) {
            tab = "inc/reservation-det";
        }
        map.put(tab, "active");
        tab = tab + ".jsp";

        CinemaReservation res = (CinemaReservation) pc.getBase();
        int etat = res.getEtat();
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
                        <div class="box-footer">
                            <% if (etat < 11) { %>
                                
                                <%-- Bouton PAYER / VALIDER (uniquement si statut = reserve) --%>
                                <% if (ConstanteCinema.STATUTRESERVE.equals(res.getIdstatutreservation())) { %>
                                    <a class="btn btn-primary pull-right"
                                       href="<%= lien + "?but=apresTarif.jsp&acte=custom&acteDetail=payer&id=" + id + "&bute=" + pageActuel + "&classe=" + classe %>"
                                       style="margin-right: 10px">
                                       Payer / Valider
                                    </a>
                                    <a class="btn btn-secondary pull-right"
                                       href="<%= lien + "?but=" + pageModif + "&id=" + id + "&acte=update" %>"
                                       style="margin-right: 10px">Modifier</a>
                                <% } %>

                                <%-- Bouton SUPPRIMER (uniquement si statut = reserve) --%>
                                <% if (ConstanteCinema.STATUTRESERVE.equals(res.getIdstatutreservation())) { %>
                                    <a class="btn btn-danger pull-left"
                                       href="<%= lien + "?but=apresTarif.jsp&acte=delete&id=" + id + "&bute=" + pageListe + "&classe=" + classe %>">Supprimer</a>
                                <% } %>
                                
                            <% } %>
                        </div>
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
                    <li class="<%=map.get("inc/reservation-det")%>">
                        <a href="<%= lien %>?but=<%= pageActuel %>&id=<%= id %>&tab=inc/reservation-det">Détails</a>
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
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>