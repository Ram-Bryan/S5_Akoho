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
 */
package web.servlet;

import bean.CGenUtil;
import bean.ClassMAPTable;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user.UserEJB;

@WebServlet(name="autocomplete", urlPatterns={"/autocomplete"})
public class AutoComplete
extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter();){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Autocomplete</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Autocomplete at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = null;){
            out = response.getWriter();
            String libelle = request.getParameter("libelle");
            String nomTable = request.getParameter("nomTable");
            String classe = request.getParameter("classe");
            String colFiltre = request.getParameter("colFiltre");
            String valeur = request.getParameter("valeur");
            String affiche = request.getParameter("affiche");
            String isPrecisParam = request.getParameter("isPrecis");
            String isPropreParam = request.getParameter("isPropre");
            boolean useMotcle = Boolean.valueOf(request.getParameter("useMotcle"));
            boolean isPrecis = false;
            if (isPrecisParam != null) {
                isPrecis = Boolean.parseBoolean(isPrecisParam);
            }
            boolean isPropre = false;
            if (isPropreParam != null) {
                isPropre = Boolean.parseBoolean(isPropreParam);
            }
            String valeurRetour = request.getParameter("champRetour");
            UserEJB u = (UserEJB)request.getSession().getAttribute("u");
            if (isPropre) {
                out.println(AutoComplete.getContenuPropre(u, libelle, nomTable, classe, colFiltre, valeur, affiche, useMotcle, valeurRetour, request.getParameter("ac_Where")));
            } else if (isPrecis) {
                out.println(AutoComplete.getContenuPrecis(u, libelle, nomTable, classe, colFiltre, valeur, affiche, useMotcle, valeurRetour, request.getParameter("ac_Where")));
            } else {
                out.println(AutoComplete.getContenu(u, libelle, nomTable, classe, colFiltre, valeur, affiche, useMotcle, valeurRetour, request.getParameter("ac_Where")));
            }
        }
    }

    public static String getContenu(UserEJB u, String valeurFiltre, String nomTable, String nomClasse, String nomColoneFiltre, String nomColvaleur, String nomColAffiche, boolean useMotcle, String colRetour, String aWhere) throws Exception {
        ClassMAPTable filtre = (ClassMAPTable)Class.forName(nomClasse).newInstance();
        filtre.setNomTable(nomTable);
        String rekety = "";
        String apresWhere = "";
        if (aWhere != null && aWhere.compareTo("") != 0 && aWhere.compareToIgnoreCase("null") != 0) {
            apresWhere = " " + aWhere;
        }
        rekety = useMotcle ? "SELECT * FROM " + nomTable + " WHERE (" + CGenUtil.makeWhereMotsCles((ClassMAPTable)filtre, (String)valeurFiltre) + ")" + apresWhere : "SELECT * FROM " + nomTable + " WHERE UPPER(" + nomColoneFiltre + ") LIKE '%" + valeurFiltre.toUpperCase() + "%'" + apresWhere;
        System.out.println("REQ AUTOCOMPLETE " + rekety);
        ClassMAPTable[] listee = (ClassMAPTable[])u.getData(filtre, rekety, null);
        String valeur = "[";
        for (int i = 0; i < listee.length; ++i) {
            ClassMAPTable to = listee[i];
            String[] mts = to.getMotCles();
            String valeurId = (String)CGenUtil.getValeurFieldByMethod((ClassMAPTable)to, (String)nomColvaleur);
            String valeurAffiche = "";
            String valeurRetour = "";
            if (useMotcle) {
                valeurAffiche = CGenUtil.getValeurFieldByMotCle((ClassMAPTable)to).replace("\n", " ").replace("'", "").replace("\"", "");
                if (colRetour != null && !colRetour.isEmpty()) {
                    valeurRetour = CGenUtil.getValeurFieldByMotCle((ClassMAPTable)to, (String[])colRetour.split(";"), (String)";");
                    valeurRetour.replace("\n", " ").replace("'", "").replace("\"", "");
                }
            } else {
                Object val = CGenUtil.getValeurFieldByMethod((ClassMAPTable)to, (String)nomColAffiche);
                valeurAffiche = val.toString().replace("\n", " ").replace("'", "").replace("\"", "");
            }
            valeur = valeur + "{\"id\":\"" + valeurId + "\",\"valeur\":\"" + valeurAffiche + "\",\"retour\":\"" + valeurRetour + "\"}";
            if (i >= listee.length - 1) continue;
            valeur = valeur + ",";
        }
        valeur = valeur + "]";
        return "{\"valeure\":" + valeur + "}";
    }

    public static String getContenuPrecis(UserEJB u, String valeurFiltre, String nomTable, String nomClasse, String nomColoneFiltre, String nomColvaleur, String nomColAffiche, boolean useMotcle, String colRetour, String aWhere) throws Exception {
        ClassMAPTable filtre = (ClassMAPTable)Class.forName(nomClasse).newInstance();
        filtre.setNomTable(nomTable);
        String rekety = "";
        String apresWhere = "";
        if (aWhere != null && aWhere.compareTo("") != 0 && aWhere.compareToIgnoreCase("null") != 0) {
            apresWhere = " " + aWhere;
        }
        rekety = useMotcle ? "SELECT * FROM " + nomTable + " WHERE (" + CGenUtil.makeWhereMotsClesPrecis((ClassMAPTable)filtre, (String)valeurFiltre) + ")" + apresWhere : "SELECT * FROM " + nomTable + " WHERE UPPER(" + nomColoneFiltre + ") LIKE '" + valeurFiltre.toUpperCase() + "'" + apresWhere;
        System.out.println("REQ AUTOCOMPLETE " + rekety);
        ClassMAPTable[] listee = (ClassMAPTable[])u.getData(filtre, rekety, null);
        String valeur = "[";
        for (int i = 0; i < listee.length; ++i) {
            ClassMAPTable to = listee[i];
            String[] mts = to.getMotCles();
            String valeurId = (String)CGenUtil.getValeurFieldByMethod((ClassMAPTable)to, (String)nomColvaleur);
            String valeurAffiche = "";
            String valeurRetour = "";
            if (useMotcle) {
                valeurAffiche = CGenUtil.getValeurFieldByMotCle((ClassMAPTable)to);
                if (colRetour != null && !colRetour.isEmpty()) {
                    valeurRetour = CGenUtil.getValeurFieldByMotCle((ClassMAPTable)to, (String[])colRetour.split(";"), (String)";");
                }
            } else {
                Object val = CGenUtil.getValeurFieldByMethod((ClassMAPTable)to, (String)nomColAffiche);
                valeurAffiche = val.toString().replace("\n", " ");
            }
            valeur = valeur + "{\"id\":\"" + valeurId + "\",\"valeur\":\"" + valeurAffiche + "\",\"retour\":\"" + valeurRetour + "\"}";
            if (i >= listee.length - 1) continue;
            valeur = valeur + ",";
        }
        valeur = valeur + "]";
        return "{\"valeure\":" + valeur + "}";
    }

    public static String getContenuPropre(UserEJB u, String valeurFiltre, String nomTable, String nomClasse, String nomColoneFiltre, String nomColvaleur, String nomColAffiche, boolean useMotcle, String colRetour, String aWhere) throws Exception {
        ClassMAPTable filtre = (ClassMAPTable)Class.forName(nomClasse).newInstance();
        filtre.setNomTable(nomTable);
        String rekety = "";
        String apresWhere = "";
        if (aWhere != null && aWhere.compareTo("") != 0 && aWhere.compareToIgnoreCase("null") != 0) {
            apresWhere = " " + aWhere;
        }
        rekety = useMotcle ? "SELECT * FROM " + nomTable + " WHERE (" + CGenUtil.makeWhereMotsClesPropre((ClassMAPTable)filtre, (String)valeurFiltre) + ")" + apresWhere : "SELECT * FROM " + nomTable + " WHERE UPPER(" + nomColoneFiltre + ") LIKE '" + valeurFiltre.toUpperCase() + "%'" + apresWhere;
        System.out.println("REQ AUTOCOMPLETE " + rekety);
        ClassMAPTable[] listee = (ClassMAPTable[])u.getData(filtre, rekety, null);
        String valeur = "[";
        for (int i = 0; i < listee.length; ++i) {
            ClassMAPTable to = listee[i];
            String[] mts = to.getMotCles();
            String valeurId = (String)CGenUtil.getValeurFieldByMethod((ClassMAPTable)to, (String)nomColvaleur);
            String valeurAffiche = "";
            String valeurRetour = "";
            if (useMotcle) {
                valeurAffiche = CGenUtil.getValeurFieldByMotCle((ClassMAPTable)to);
                if (colRetour != null && !colRetour.isEmpty()) {
                    valeurRetour = CGenUtil.getValeurFieldByMotCle((ClassMAPTable)to, (String[])colRetour.split(";"), (String)";");
                }
            } else {
                Object val = CGenUtil.getValeurFieldByMethod((ClassMAPTable)to, (String)nomColAffiche);
                valeurAffiche = val.toString().replace("\n", " ");
            }
            valeur = valeur + "{\"id\":\"" + valeurId + "\",\"valeur\":\"" + valeurAffiche + "\",\"retour\":\"" + valeurRetour + "\"}";
            if (i >= listee.length - 1) continue;
            valeur = valeur + ",";
        }
        valeur = valeur + "]";
        return "{\"valeure\":" + valeur + "}";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
}

