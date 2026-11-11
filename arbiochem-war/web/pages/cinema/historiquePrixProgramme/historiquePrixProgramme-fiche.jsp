<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="user.UserEJB" %>
<%@ page import="affichage.PageConsulte" %>
<%@ page import="cinema.CinemaHistoriquePrixProgramme" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String lien = (String) session.getValue("lien");

    CinemaHistoriquePrixProgramme o = new CinemaHistoriquePrixProgramme();
    PageConsulte pc = new PageConsulte(o, request, u);
    pc.setTitre("Fiche prix programme");
    String id = pc.getBase().getTuppleID();

    pc.getChampByName("id").setLibelle("Id");
    pc.getChampByName("idprogramme").setLibelle("Programme");
    pc.getChampByName("prix").setLibelle("Prix (Ar)");
    pc.getChampByName("datedebut").setLibelle("Date d'effet");
    pc.setOrdre(new String[]{"id", "idprogramme", "prix", "datedebut"});

    String pageRetour = "cinema/historiquePrixProgramme/historiquePrixProgramme-liste.jsp";
    String pageModif  = "cinema/historiquePrixProgramme/historiquePrixProgramme-saisie.jsp&acte=update";
    String classe     = "cinema.CinemaHistoriquePrixProgramme";
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