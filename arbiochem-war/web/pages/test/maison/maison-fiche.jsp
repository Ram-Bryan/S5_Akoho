<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="user.UserEJB" %>
<%@ page import="affichage.PageConsulte" %>
<%@ page import="test.maison.Maison" %>
<%@ page import="constante.ConstanteEtat" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<% try{
    UserEJB u = (user.UserEJB)session.getValue("u");
    String lien = (String) session.getValue("lien");

    Maison o = new Maison();
    PageConsulte pc = new PageConsulte(o, request, u);
    pc.setTitre("Fiche de la maison");
    String id = pc.getBase().getTuppleID();

    pc.getChampByName("id").setLibelle("Id");
    pc.getChampByName("nom").setLibelle("Nom");
    pc.getChampByName("adresse").setLibelle("Adresse");
    pc.getChampByName("ville").setLibelle("Ville");
    pc.getChampByName("codepostal").setLibelle("Code postal");
    pc.getChampByName("surface").setLibelle("Surface (m&sup2;)");
    pc.getChampByName("anneconstruction").setLibelle("Ann&eacute;e");
    pc.getChampByName("etat").setLibelle("&Eacute;tat");
    pc.getChampByName("iduser").setVisible(false);

    String[] ordre = {"id","nom","adresse","ville","codepostal","surface","anneconstruction","etat"};
    pc.setOrdre(ordre);

    String pageRetour      = "test/maison/maison-liste.jsp";
    String pageActuel      = "test/maison/maison-fiche.jsp";
    String pageModif       = "test/maison/maison-saisie.jsp&acte=update";
    String pageApresDelete = "test/maison/maison-liste.jsp";
    String classe          = "test.maison.Maison";

    Map<String, String> map = new HashMap<>();
    map.put("inc/piece-det", "");                    
    String tab = request.getParameter("tab");
    if (tab == null) tab = "inc/piece-det";          
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
                    <a class="btn btn-danger pull-left" href="<%= lien + "?but=apresTarif.jsp&id=" + id + "&acte=delete&bute="+pageApresDelete+"&classe="+classe %>">Supprimer</a>
                    <% if (o.getEtat() < ConstanteEtat.getEtatValider()){ %>
                        <a class="btn btn-secondary pull-right" href="<%= lien + "?but="+ pageModif +"&id=" + id %>" style="margin-right:10px">Modifier</a>
                        <a class="btn btn-primary pull-right" href="<%= lien + "?but=apresTarif.jsp&acte=valider&classe="+classe+"&nomtable=MAISON&bute="+pageActuel+"&id=" + id %>" style="margin-right:10px">Viser</a>
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
                    <li class="<%=map.get("test/maison/inc/piece-det")%>">
                        <a href="<%= lien %>?but=<%= pageActuel %>&id=<%= id %>&tab=inc/piece-det">Pi&egrave;ces</a>
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