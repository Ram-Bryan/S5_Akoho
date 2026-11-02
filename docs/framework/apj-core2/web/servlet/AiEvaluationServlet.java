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

@WebServlet(name="Evaluation", urlPatterns={"/ai-evaluation"})
public class AiEvaluationServlet
extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");
        String prompt = request.getParameter("prompt");
        if (prompt == null || prompt.trim().isEmpty()) {
            response.setStatus(400);
            response.getWriter().write("Erreur : Aucun param\u00c3\u00a8tre 'prompt' re\u00c3\u00a7u.");
            return;
        }
        try {
            String apiUrl = request.getParameter("apiUrl");
            String apiKey = request.getParameter("apiKey");
            Page p = new Page();
            String evaluation = p.getEvaluation(apiUrl, apiKey, prompt);
            PrintWriter out = response.getWriter();
            if (evaluation != null && !evaluation.isEmpty()) {
                response.setStatus(200);
                out.write(evaluation);
            } else {
                response.setStatus(500);
                out.write("Erreur : L'\u00c3\u00a9valuation est vide.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("Erreur serveur : " + e.getMessage());
        }
    }
}

