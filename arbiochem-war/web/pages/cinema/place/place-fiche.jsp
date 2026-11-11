<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="user.UserEJB" %>
<%@ page import="affichage.PageConsulte" %>
<%@ page import="cinema.CinemaPlace" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String lien = (String) session.getValue("lien");

    CinemaPlace o = new CinemaPlace();
    PageConsulte pc = new PageConsulte(o, request, u);
    pc.setTitre("Fiche place");
    String id = pc.getBase().getTuppleID();

    pc.getChampByName("id").setLibelle("Id");
    pc.getChampByName("libelle").setLibelle("Libellé");
    pc.getChampByName("idrange").setLibelle("Range");
    pc.getChampByName("etat").setVisible(false);
    pc.getChampByName("iduser").setVisible(false);
    pc.setOrdre(new String[]{"id", "libelle", "idrange"});

    String pageRetour = "cinema/place/place-liste.jsp";
    String pageModif  = "cinema/place/place-saisie.jsp&acte=update";
    String classe     = "cinema.CinemaPlace";
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
                    <a class="btn btn-secondary pull-right"
                       href="<%= lien + "?but="+ pageModif +"&id=" + id %>">Modifier</a>
                </div>
                <br/>
            </div></div></div>
        </div>
    </div>
</div>
<% } catch (Exception e) { e.printStackTrace(); %>
<script language="JavaScript"> alert('<%=e.getMessage()%>'); history.back(); </script>
<% }%>