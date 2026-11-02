/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  affichage.PageCalendrier
 *  bean.CGenUtil
 *  bean.ClassMAPTable
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  user.UserEJB
 */
package web.servlet;

import affichage.PageCalendrier;
import bean.CGenUtil;
import bean.ClassMAPTable;
import evenement.Evenement;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.UserEJB;

@WebServlet(name="CalendrierData", urlPatterns={"/calendrier-data"})
public class CalendrierDataServlet
extends HttpServlet {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = null;){
            String daty2Converted;
            String daty1Converted;
            out = response.getWriter();
            String periode = request.getParameter("periode");
            if (periode == null || periode.isEmpty()) {
                response.setStatus(400);
                out.write("{\"error\": \"Param\u00c3\u00a8tres manquants : periode requis.\"}");
                return;
            }
            UserEJB u = (UserEJB)request.getSession().getAttribute("u");
            if (u == null) {
                response.setStatus(401);
                out.write("{\"error\": \"Session expir\u00c3\u00a9e.\"}");
                return;
            }
            String[] periodeParts = periode.split("-");
            if (periodeParts.length != 2) {
                response.setStatus(400);
                out.write("{\"error\": \"Format de p\u00c3\u00a9riode invalide. Attendu : YYYY-MM.\"}");
                return;
            }
            String annee = periodeParts[0];
            String mois = periodeParts[1];
            String premierJour = annee + "-" + mois + "-01";
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(annee), Integer.parseInt(mois) - 1, 1);
            int dernierJourInt = cal.getActualMaximum(5);
            String dernierJour = annee + "-" + mois + "-" + String.format("%02d", dernierJourInt);
            String daty1 = request.getParameter("daty1");
            String daty2 = request.getParameter("daty2");
            String dateDebut = premierJour;
            if (daty1 != null && !daty1.isEmpty() && (daty1Converted = this.convertDate(daty1)).compareTo(premierJour) > 0) {
                dateDebut = daty1Converted;
            }
            String dateFin = dernierJour;
            if (daty2 != null && !daty2.isEmpty() && (daty2Converted = this.convertDate(daty2)).compareTo(dernierJour) < 0) {
                dateFin = daty2Converted;
            }
            if (dateDebut.compareTo(dateFin) > 0) {
                response.setStatus(200);
                out.write("{\"events\": []}");
                return;
            }
            String aWhere = request.getParameter("aWhere");
            String apresWhere = "";
            if (aWhere != null && !aWhere.isEmpty() && !aWhere.equalsIgnoreCase("null")) {
                apresWhere = " AND " + aWhere;
            }
            String[] colInt = new String[]{"daty"};
            String[] valInt = new String[]{dateDebut, dateFin};
            ClassMAPTable[] resultats = (Evenement[])CGenUtil.rechercher((ClassMAPTable)new Evenement(), (String[])colInt, (String[])valInt, (String)apresWhere);
            PageCalendrier pc = new PageCalendrier();
            pc.setMockEventsData(resultats);
            response.setStatus(200);
            out.write("{\"events\": " + pc.getMockEventsData() + "}");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        try (PrintWriter out = null;){
            java.sql.Date nouvelleDate;
            out = response.getWriter();
            UserEJB u = (UserEJB)request.getSession().getAttribute("u");
            if (u == null) {
                response.setStatus(401);
                out.write("{\"error\": \"Session expir\u00c3\u00a9e.\"}");
                return;
            }
            String id = request.getParameter("id");
            String datyStr = request.getParameter("daty");
            String heure = request.getParameter("heure");
            String startHourStr = request.getParameter("startHour");
            datyStr = datyStr.replace("-", "/");
            if (id == null || id.isEmpty() || datyStr == null || datyStr.isEmpty() || heure == null || heure.isEmpty()) {
                response.setStatus(400);
                out.write("{\"error\": \"Param\u00c3\u00a8tres manquants : id, daty, heure requis.\"}");
                return;
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date parsed = sdf.parse(datyStr);
                nouvelleDate = new java.sql.Date(parsed.getTime());
            }
            catch (Exception e) {
                response.setStatus(400);
                out.write("{\"error\": \"Format de date invalide. Attendu : dd-MM-yyyy.\"}");
                if (out != null) {
                    out.close();
                }
                return;
            }
            double startHour = 0.0;
            if (startHourStr != null && !startHourStr.isEmpty()) {
                try {
                    startHour = Double.parseDouble(startHourStr);
                }
                catch (NumberFormatException e) {
                    System.out.println("CALENDRIER WARNING : startHour invalide : " + startHourStr);
                }
            }
            Evenement ev = new Evenement();
            ev.setId(id);
            Evenement[] resultats = (Evenement[])CGenUtil.rechercher((ClassMAPTable)ev, null, null, (String)"");
            if (resultats == null || resultats.length == 0) {
                response.setStatus(404);
                out.write("{\"error\": \"\u00c3\u0089v\u00c3\u00a9nement introuvable : id=" + id + "\"}");
                return;
            }
            Evenement evenement = resultats[0];
            evenement.setDaty(nouvelleDate);
            evenement.setHeure(heure);
            evenement.setStartHour(startHour);
            u.updateObject((ClassMAPTable)evenement);
            System.out.println("CALENDRIER UPDATE OK : id=" + id + " daty=" + datyStr + " heure=" + heure + " startHour=" + startHour);
            response.setStatus(200);
            out.write("{\"success\": true, \"id\": \"" + id + "\",\"daty\": \"" + datyStr + "\",\"heure\": \"" + heure + "\",\"startHour\": " + startHour + "}");
        }
    }

    private String convertDate(String date) {
        if (date == null || date.isEmpty()) {
            return date;
        }
        try {
            if (date.matches("\\d{2}/\\d{2}/\\d{4}")) {
                SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                return output.format(input.parse(date));
            }
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return date;
            }
            System.out.println("CALENDRIER WARNING : format de date inconnu : " + date);
            return date;
        }
        catch (Exception e) {
            System.out.println("CALENDRIER ERROR : impossible de convertir la date : " + date);
            return date;
        }
    }
}

