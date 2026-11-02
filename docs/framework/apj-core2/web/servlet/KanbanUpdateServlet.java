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

@WebServlet(name="KanbanUpdateServlet", urlPatterns={"/kanban/update"})
public class KanbanUpdateServlet
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
                String oldGroupId = json.optString("oldGroupId", null);
                String newGroupId = json.optString("newGroupId", null);
                String newGroupLabel = json.optString("newGroupLabel");
                String table = json.getString("table");
                String classe = json.getString("classe");
                String colonne = json.getString("colonne");
                System.out.println("KANBAN MOVE:");
                System.out.println("cardId = " + cardId);
                System.out.println("oldGroupId = " + oldGroupId);
                System.out.println("newGroupId = " + newGroupId);
                System.out.println("table = " + table);
                System.out.println("classe = " + classe);
                System.out.println("colonne = " + colonne);
                ClassMAPTable cm = (ClassMAPTable)Class.forName(classe).newInstance();
                cm.setNomTable(table);
                cm.setTuppleId(cardId);
                cm.changerDyn(cm, colonne, newGroupId, oldGroupId);
                JSONObject response = new JSONObject();
                response.put("success", true);
                response.put("message", (Object)(cardId + " est maintenant " + newGroupLabel));
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

