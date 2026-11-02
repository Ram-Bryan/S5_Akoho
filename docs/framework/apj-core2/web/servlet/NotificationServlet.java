/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.CGenUtil
 *  bean.ClassMAPTable
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  user.UserEJB
 */
package web.servlet;

import bean.CGenUtil;
import bean.ClassMAPTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.AWTException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import notification.Notification;
import user.UserEJB;

@WebServlet(name="Notification", urlPatterns={"/Notification"})
public class NotificationServlet
extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter();){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Notification</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Notification at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("notifSys") != null) {
            try {
                Notification.notifSys(request.getParameter("notifSys"));
            }
            catch (AWTException ex) {
                ex.printStackTrace();
            }
            return;
        }
        UserEJB u = (UserEJB)request.getSession().getAttribute("u");
        if (u == null) {
            return;
        }
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
        Gson gson = new GsonBuilder().create();
        Notification[] top5 = null;
        try {
            Notification notif = new Notification();
            notif.setNomTable("notificationlibnonlu");
            String where = " and receiver = '%s' order by etat asc, daty desc, heure desc";
            where = String.format(where, u.getUser().getRefuser());
            Notification[] mesnotifs = (Notification[])CGenUtil.rechercher((ClassMAPTable)notif, null, null, (String)where);
            int n = Math.min(mesnotifs.length, 5);
            top5 = new Notification[n];
            for (int i = 0; i < n; ++i) {
                top5[i] = mesnotifs[i];
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            try {
                throw ex;
            }
            catch (Exception ex1) {
                Logger.getLogger(NotificationServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        String countryObj = gson.toJson(top5);
        out.println(countryObj);
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
}

