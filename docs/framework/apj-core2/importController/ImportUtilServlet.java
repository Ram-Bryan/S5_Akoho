/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  affichage.PageUpload
 *  bean.ClassMAPTable
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  org.apache.commons.fileupload.FileItem
 *  org.apache.commons.fileupload.FileItemFactory
 *  org.apache.commons.fileupload.FileUploadException
 *  org.apache.commons.fileupload.disk.DiskFileItem
 *  org.apache.commons.fileupload.disk.DiskFileItemFactory
 *  org.apache.commons.fileupload.servlet.ServletFileUpload
 *  user.UserEJB
 *  utilitaire.UtilDB
 */
package importController;

import affichage.PageUpload;
import bean.ClassMAPTable;
import importUtils.Importer;
import importUtils.importable.Importable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import user.UserEJB;
import utilitaire.UtilDB;

@WebServlet(name="ImportUtilServlet", urlPatterns={"/ImportUtilServlet"})
public class ImportUtilServlet
extends HttpServlet {
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload((FileItemFactory)this.factory);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, ServletException, IOException, FileUploadException, Exception {
        Importer importer = new Importer();
        Importable importable = null;
        Connection c = null;
        HttpSession session = request.getSession(true);
        boolean connexionOuvert = false;
        PrintWriter out = response.getWriter();
        try {
            if (c == null) {
                c = new UtilDB().GetConn();
                c.setAutoCommit(false);
                connexionOuvert = true;
            }
            UserEJB u = (UserEJB)session.getAttribute("u");
            String bute = "";
            String lien = (String)session.getValue("lien");
            List items = this.upload.parseRequest(request);
            DiskFileItem[] listeChamp = new DiskFileItem[items.size()];
            items.toArray(listeChamp);
            PageUpload p = new PageUpload(listeChamp);
            FileItem file = null;
            ClassMAPTable objet = p.getObjectAvecValeur();
            importable = (Importable)objet;
            bute = p.getValeur("bute");
            file = (FileItem)p.getFileItems().get(0);
            importer.importFile(importable, file, String.valueOf(u.getUser().getRefuser()), c);
            c.commit();
            response.sendRedirect(lien + "?but=" + bute);
        }
        catch (Exception e) {
            e.printStackTrace();
            if (connexionOuvert) {
                try {
                    c.rollback();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                    out.println("<script language='JavaScript'> alert('" + ex.getMessage() + "');history.back(); </script>");
                }
            }
            out.println("<script language='JavaScript'> alert('" + e.getMessage() + "');history.back(); </script>");
            out.println("<script language=\"JavaScript\">history.back()</script>");
        }
        finally {
            if (connexionOuvert) {
                try {
                    c.close();
                }
                catch (SQLException ex) {
                    out.println("<script language='JavaScript'> alert('" + ex.getMessage() + "');history.back(); </script>");
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            this.processRequest(request, response);
        }
        catch (FileUploadException ex) {
            out.println("<script language=\"JavaScript\">alert('" + ex.getMessage() + "')</script>");
            out.println("<script language=\"JavaScript\">history.back()</script>");
        }
        catch (NoClassDefFoundError notf) {
            out.println("<script language=\"JavaScript\">alert('" + notf.getMessage() + "')</script>");
            out.println("<script language=\"JavaScript\">history.back()</script>");
        }
        catch (Exception ex) {
            System.out.println("OOOO");
            out.println("<script language=\"JavaScript\">alert('" + ex.getMessage() + "')</script>");
            out.println("<script language=\"JavaScript\">history.back()</script>");
        }
    }

    public String getServletInfo() {
        return "Short description";
    }
}

