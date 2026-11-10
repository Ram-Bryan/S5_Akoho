<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="user.UserEJB" %>
<%@ page import="affichage.PageConsulte" %>
<%@ page import="cinema.CinemaProgramme" %>
<%@ page import="constante.ConstanteEtat" %>

<% try {
    UserEJB u = (user.UserEJB) session.getValue("u");
    String lien = (String) session.getValue("lien");

    CinemaProgramme o = new CinemaProgramme();
    PageConsulte pc = new PageConsulte(o, request, u);
    pc.setTitre("Fiche programme");
    String id = pc.getBase().getTuppleID();

    pc.getChampByName("id").setLibelle("Id");
    pc.getChampByName("idfilm").setLibelle("Film");
    pc.getChampByName("idsalle").setLibelle("Salle");
    pc.getChampByName("datedebut").setLibelle("Date début");
    pc.getChampByName("heuredebut").setLibelle("Heure début");
    pc.getChampByName("datefin").setLibelle("Date fin");
    pc.getChampByName("heurefin").setLibelle("Heure fin");
    pc.getChampByName("prixbase").setLibelle("Prix base");
    pc.getChampByName("etat").setLibelle("&Eacute;tat");
    pc.getChampByName("iduser").setVisible(false);
    pc.setOrdre(new String[]{"id", "idfilm", "idsalle", "datedebut", "heuredebut", "datefin", "heurefin", "prixbase", "etat"});

    String pageRetour = "cinema/programme/programme-liste.jsp";
    String pageActuel = "cinema/programme/programme-fiche.jsp";
    String pageModif  = "cinema/programme/programme-saisie.jsp&acte=update";
    String classe     = "cinema.CinemaProgramme";
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
                        <a class="btn btn-secondary pull-right"
                           href="<%= lien + "?but="+ pageModif +"&id=" + id %>"
                           style="margin-right:10px">Modifier</a>
                        <a class="btn btn-primary pull-right"
                           href="<%= lien + "?but=apresTarif.jsp&acte=valider&classe="+classe+"&nomtable=cinema_programme&bute="+pageActuel+"&id=" + id %>"
                           style="margin-right:10px">Viser</a>
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