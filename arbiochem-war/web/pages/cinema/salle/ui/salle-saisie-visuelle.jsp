<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="cinema.CinemaCategorieRange" %>
<%@ page import="bean.CGenUtil" %>
<%@ page import="utilitaire.UtilDB" %>
<%@ page import="java.sql.Connection" %>

<%
    Connection c = null;
    CinemaCategorieRange[] categories = null;
    try {
        c = new UtilDB().GetConn();
        categories = (CinemaCategorieRange[]) CGenUtil.rechercher(new CinemaCategorieRange(), null, null, c, "");
    } finally {
        if (c != null) try { c.close(); } catch(Exception e){}
    }
    String lien = (String) session.getValue("lien");
%>

<style>
    /* Marges pour aérer le formulaire */
    .custom-box-body {
        padding: 30px 40px !important; 
    }
    .custom-box-footer {
        padding: 15px 40px 25px 40px !important;
        background-color: transparent !important;
        border-top: 1px solid #f4f4f4;
    }
    
    /* Style du tableau */
    .range-table th {
        background-color: #f9fafc;
        color: #555;
        text-transform: uppercase;
        font-size: 12px;
        letter-spacing: 0.5px;
        padding: 12px 15px !important;
        border-bottom: 2px solid #eaeaea !important;
    }
    .range-table td {
        vertical-align: middle !important;
        padding: 12px 15px !important;
        border-top: 1px solid #f0f0f0 !important;
    }
    
    /* Style des inputs */
    .range-table .form-control {
        border: 1px solid #ddd;
        box-shadow: none;
        border-radius: 4px;
        height: 38px;
    }
    .range-table .form-control:focus {
        border-color: #3c8dbc;
        box-shadow: 0 0 5px rgba(60,141,188,0.3);
    }
    .input-salle-nom {
        font-size: 18px;
        height: 45px !important;
        border: 1px solid #ddd;
        box-shadow: none;
        border-radius: 4px;
        max-width: 400px; /* Limite la largeur pour faire propre */
    }
    .input-salle-nom:focus {
        border-color: #3c8dbc;
        box-shadow: 0 0 5px rgba(60,141,188,0.3);
    }
</style>

<div class="content-wrapper">
    <h1 class="box-title" style="margin-bottom: 20px;">
        <a href="<%= lien %>?but=cinema/salle/salle-saisie.jsp"><i class="fa fa-angle-left"></i></a>
        Création visuelle d'une Salle
    </h1>
    
    <div class="row">
        <div class="col-md-12">
            <div class="box box-primary" style="border-top-color: #3c8dbc;">
                
                <form action="<%= lien %>?but=cinema/salle/ui/apresSalleVisuelle.jsp" method="post" id="salleForm">
                    
                    <div class="box-body custom-box-body">
                        
                        <!-- Champ Nom de la Salle : Bloc simple, prend toute la largeur du conteneur -->
                        <div class="form-group" style="margin-bottom: 40px;">
                            <label style="font-weight: 600; margin-bottom: 8px; display: block;">Nom de la Salle</label>
                            <input type="text" name="libelle" class="form-control input-salle-nom" required placeholder="Ex: Salle 1">
                        </div>

                        <!-- Tableau des Ranges : Passe automatiquement en dessous -->
                        <div class="table-responsive">
                            <table class="table table-hover range-table" id="rangesTable" style="margin-bottom: 20px;">
                                <thead>
                                    <tr>
                                        <th style="width: 25%;">Nom de la Range (ex: A)</th>
                                        <th style="width: 35%;">Catégorie</th>
                                        <th style="width: 20%;">Nombre de places</th>
                                        <th style="width: 20%; text-align: center;">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- Lignes ajoutées par JS -->
                                </tbody>
                            </table>
                        </div>
                        
                        <!-- Bouton Ajouter centré -->
                        <div class="text-center" style="margin-top: 15px;">
                            <button type="button" class="btn btn-success btn-flat" onclick="ajouterLigne()" style="border-radius: 20px; padding: 8px 25px;">
                                <i class="fa fa-plus"></i> Ajouter une Range
                            </button>
                        </div>
                    </div>
                    
                    <!-- Footer avec bouton Enregistrer à droite -->
                    <div class="box-footer custom-box-footer">
                        <button type="submit" class="btn btn-primary btn-flat pull-right" style="padding: 8px 25px;">
                            <i class="fa fa-save"></i> Enregistrer la Salle
                        </button>
                    </div>
                </form>
                
            </div>
        </div>
    </div>
</div>

<script>
    let optionsCat = `<option value="">-- Sélectionner une catégorie --</option>`;
    <% if(categories != null) { 
        for(CinemaCategorieRange cat : categories) { %>
            optionsCat += `<option value="<%=cat.getId()%>"><%=cat.getLibelle()%></option>`;
    <% } } %>

    function ajouterLigne() {
        let tbody = document.querySelector('#rangesTable tbody');
        let tr = document.createElement('tr');
        
        tr.innerHTML = `
            <td>
                <input type="text" data-field="nomrange" class="form-control" required placeholder="Ex: A">
            </td>
            <td>
                <select data-field="idcategorie" class="form-control" required>
                    \${optionsCat}
                </select>
            </td>
            <td>
                <input type="number" data-field="nbplaces" class="form-control" min="1" value="5" required>
            </td>
            <td class="text-center">
                <button type="button" class="btn btn-danger btn-sm" onclick="supprimerLigne(this)" title="Supprimer">
                    <i class="fa fa-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(tr);
        renumeroter(); 
    }

    function supprimerLigne(btn) {
        btn.closest('tr').remove();
        renumeroter(); 
    }

    function renumeroter() {
        let tbody = document.querySelector('#rangesTable tbody');
        let rows = tbody.querySelectorAll('tr');
        rows.forEach((tr, index) => {
            tr.querySelector('[data-field="nomrange"]').name = 'nomrange_' + index;
            tr.querySelector('[data-field="idcategorie"]').name = 'idcategorie_' + index;
            tr.querySelector('[data-field="nbplaces"]').name = 'nbplaces_' + index;
        });
    }

    document.addEventListener('DOMContentLoaded', () => ajouterLigne());
</script>