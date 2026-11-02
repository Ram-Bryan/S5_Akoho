/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  user.UserEJB
 */
package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.UserEJB;

@WebServlet(name="DeletePj", urlPatterns={"/DeletePj"})
public class DeletePj
extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idtodelete = request.getParameter("idpj");
        String nomtable = request.getParameter("nomtable");
        String procedure = request.getParameter("procedure");
        String but = request.getParameter("but");
        String bute = request.getParameter("bute");
        String id = request.getParameter("id");
        String enfant = request.getParameter("enfant");
        String dossier = request.getParameter("dossier");
        UserEJB u = null;
        try {
            u = (UserEJB)request.getSession().getAttribute("u");
            u.deleteUploadedPj(nomtable, idtodelete);
            String lien = request.getSession().getAttribute("lien").toString();
            response.sendRedirect(request.getContextPath() + "/pages/" + lien + "?but=" + but + "&bute=" + bute + "&id=" + id + "&nomtable=" + nomtable + "&procedure=" + procedure + "&enfant=" + enfant + "&dossier=" + dossier);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
}

