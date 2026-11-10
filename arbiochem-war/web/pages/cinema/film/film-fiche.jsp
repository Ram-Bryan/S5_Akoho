<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="user.UserEJB" %>
<%@ page import="affichage.PageConsulte" %>
<%@ page import="cinema.CinemaFilm" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String lien = (String) session.getValue("lien");

    CinemaFilm o = new CinemaFilm();
    PageConsulte pc = new PageConsulte(o, request, u);
    pc.setTitre("Fiche film");
    String id = pc.getBase().getTuppleID();

    pc.getChampByName("id").setLibelle("Id");
    pc.getChampByName("titre").setLibelle("Titre");
    pc.getChampByName("description").setLibelle("Description");
    pc.getChampByName("duree").setLibelle("Durée (min)");
    pc.setOrdre(new String[]{"id", "titre", "description", "duree"});

    String pageRetour = "cinema/film/film-liste.jsp";
    String pageActuel = "cinema/film/film-fiche.jsp";
    String pageModif  = "cinema/film/film-saisie.jsp&acte=update";
    String pageCat    = "cinema/film/film-categorie.jsp";
    String classe     = "cinema.CinemaFilm";

    Map<String, String> map = new HashMap<>();
    map.put("inc/film-categories", "");
    String tab = request.getParameter("tab");
    if (tab == null) tab = "inc/film-categories";
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
                       href="<%= lien + "?but=apresTarif.jsp&id=" + id + "&acte=delete&bute="+pageRetour+"&classe="+classe %>">Supprimer</a>
                    <a class="btn btn-primary pull-right" style="margin-left:8px"
                       href="<%= lien + "?but="+ pageCat +"&idfilm=" + id %>">+ Catégorie</a>
                    <a class="btn btn-secondary pull-right"
                       href="<%= lien + "?but="+ pageModif +"&id=" + id %>">Modifier</a>
                </div>
                <br/>
            </div></div></div>
        </div>
    </div>
    <div class="row m-0">
        <div class="col-md-12 nopadding">
            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                    <li class="<%=map.get("inc/film-categories")%>">
                        <a href="<%= lien %>?but=<%= pageActuel %>&id=<%= id %>&tab=inc/film-categories">Catégories</a>
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