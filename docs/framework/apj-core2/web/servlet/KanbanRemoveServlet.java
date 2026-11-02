/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.ClassMAPTable
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONException
 *  org.json.JSONObject
 *  utilitaire.UtilDB
 */
package web.servlet;

import bean.ClassMAPTable;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import utilitaire.UtilDB;

@WebServlet(name="KanbanRemoveServlet", urlPatterns={"/kanban/remove"})
public class KanbanRemoveServlet
extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        StringBuilder jsonBody = new StringBuilder();
        try (BufferedReader reader = req.getReader();){
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        }
        try {
            var5_6 = null;
            try (Connection c = new UtilDB().GetConn();){
                JSONObject json = new JSONObject(jsonBody.toString());
                String cardId = json.getString("cardId");
                String table = json.getString("table");
                String classe = json.getString("classe");
                System.out.println("KANBAN DUPLIQUE:");
                System.out.println("cardId = " + cardId);
                System.out.println("table = " + table);
                System.out.println("classe = " + classe);
                ClassMAPTable cm = (ClassMAPTable)Class.forName(classe).newInstance();
                cm.setNomTable(table);
                cm.setTuppleId(cardId);
                cm = cm.getById(cardId, table, c);
                cm.deleteToTable(c);
                JSONObject response = new JSONObject();
                response.put("success", true);
                response.put("message", (Object)cm.getTuppleID());
                resp.getWriter().write(response.toString());
            }
            catch (Throwable json) {
                var5_6 = json;
                throw json;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            JSONObject response = new JSONObject();
            try {
                response.put("success", false);
                response.put("message", (Object)e.getMessage());
            }
            catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
            resp.getWriter().write(response.toString());
        }
    }
}

