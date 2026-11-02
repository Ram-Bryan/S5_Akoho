/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.CGenUtil
 *  bean.ClassMAPTable
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  user.UserEJB
 *  utilitaire.UtilDB
 */
package web.servlet;

import bean.CGenUtil;
import bean.ClassMAPTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.UserEJB;
import utilitaire.UtilDB;

@WebServlet(name="CalendrierServlet", urlPatterns={"/calendrier"})
public class CalendrierServlet
extends HttpServlet {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter();){
            UserEJB u = (UserEJB)request.getSession().getAttribute("u");
            if (u == null) {
                response.setStatus(401);
                out.write("{\"error\": \"Session expir\u00c3\u00a9e.\"}");
                return;
            }
            String id = request.getParameter("id");
            String nomTable = request.getParameter("nomTable");
            String nomClasse = request.getParameter("nomClasse");
            String daty = request.getParameter("daty");
            String heure = request.getParameter("heure");
            String colDate = request.getParameter("colDate");
            String colHeure = request.getParameter("colHeure");
            String colHeureFin = request.getParameter("colHeureFin");
            String heureFin = request.getParameter("heureFin");
            ClassMAPTable classe = (ClassMAPTable)Class.forName(nomClasse).newInstance();
            classe.setNomTable(nomTable);
            classe.setTuppleId(id);
            this.updateObject(u.getUser().getTuppleID(), classe, colDate, colHeure, colHeureFin, heure, daty, heureFin);
            out.write("{\"success\": true}");
        }
    }

    public void updateObject(String u, ClassMAPTable classe, String colDate, String colHeure, String colHeureFin, String heure, String daty, String heureFin) throws Exception {
        block12: {
            try (Connection c = null;){
                c = new UtilDB().GetConn();
                classe = classe.getById(classe.getTuppleID(), classe.getNomTable(), c);
                if (classe != null) {
                    boolean withHeure = false;
                    if (colHeure != null && !colHeure.isEmpty() && !colHeure.equalsIgnoreCase("null")) {
                        Field fieldHeure = classe.getFieldByName(colHeure);
                        CGenUtil.setValChamp((ClassMAPTable)classe, (Field)fieldHeure, (Object)heure);
                        withHeure = true;
                    }
                    if (colDate != null && !colDate.isEmpty() && !colDate.equalsIgnoreCase("null")) {
                        if (!withHeure && !"00:00".equalsIgnoreCase(heure)) {
                            throw new Exception("Cet \u00e9v\u00e9nement n'a pas d'heure d\u00e9finie.");
                        }
                        Field fieldDate = classe.getFieldByName(colDate);
                        CGenUtil.setValChamp((ClassMAPTable)classe, (Field)fieldDate, (Object)this.convertDate(daty));
                    }
                    if (!(colHeureFin == null || colHeureFin.isEmpty() || colHeureFin.equalsIgnoreCase("null") || heureFin == null || heureFin.isEmpty())) {
                        Field fieldHeureFin = classe.getFieldByName(colHeureFin);
                        CGenUtil.setValChamp((ClassMAPTable)classe, (Field)fieldHeureFin, (Object)heureFin);
                    }
                    classe.updateToTableWithHisto(u, c);
                    break block12;
                }
                throw new Exception("Objet introuvable !");
            }
        }
    }

    private Date convertDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        try {
            if (date.matches("\\d{2}/\\d{2}/\\d{4}")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                return new Date(sdf.parse(date).getTime());
            }
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return new Date(sdf.parse(date).getTime());
            }
            System.out.println("CALENDRIER WARNING : format de date inconnu : " + date);
            return null;
        }
        catch (Exception e) {
            System.out.println("CALENDRIER ERROR : impossible de convertir la date : " + date);
            return null;
        }
    }

    private String escapeJson(String s) {
        if (s == null) {
            return "\"null\"";
        }
        return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
}

