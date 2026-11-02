/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  affichage.Page
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package web.servlet;

import affichage.Page;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="Deroulante", urlPatterns={"/Deroulante"})
public class Deroulante
extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = null;){
            String action;
            String nomColAffiche;
            String nomColvaleur;
            String valeurFiltre;
            String nomColoneFiltre;
            String nomTable;
            this.processRequest(request, response);
            out = response.getWriter();
            String nomClasse = request.getParameter("nomClasse");
            if (nomClasse == null || nomClasse.compareToIgnoreCase("") == 0) {
                nomClasse = "bean.TypeObjet";
            }
            if ((nomTable = request.getParameter("nomTable")) == null || nomTable.compareToIgnoreCase("") == 0) {
                nomTable = "place";
            }
            if ((nomColoneFiltre = request.getParameter("nomColoneFiltre")) == null || nomColoneFiltre.compareToIgnoreCase("") == 0) {
                nomColoneFiltre = "desce";
            }
            if ((valeurFiltre = request.getParameter("valeurFiltre")) == null || valeurFiltre.compareToIgnoreCase("") == 0) {
                valeurFiltre = request.getParameter("desce");
            }
            if ((nomColvaleur = request.getParameter("nomColvaleur")) == null || nomColvaleur.compareToIgnoreCase("") == 0) {
                nomColvaleur = "id";
            }
            if ((nomColAffiche = request.getParameter("nomColAffiche")) == null || nomColAffiche.compareToIgnoreCase("") == 0) {
                nomColAffiche = "val";
            }
            if ((action = request.getParameter("action")) != null && action.equalsIgnoreCase("wittiret")) {
                out.println(Page.getContenuServletWithTiret((String)valeurFiltre, (String)nomTable, (String)nomClasse, (String)nomColoneFiltre, (String)nomColvaleur, (String)nomColAffiche, (boolean)true));
            } else {
                out.println(Page.getContenuServlet((String)valeurFiltre, (String)nomTable, (String)nomClasse, (String)nomColoneFiltre, (String)nomColvaleur, (String)nomColAffiche));
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
}

