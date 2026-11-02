/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bean.AdminGen
 *  bean.ClassMAPTable
 *  javax.servlet.ServletException
 *  javax.servlet.annotation.MultipartConfig
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  javax.servlet.http.Part
 */
package web.upload;

import bean.AdminGen;
import bean.ClassMAPTable;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet(name="UploadFile", urlPatterns={"/upload_excel"})
@MultipartConfig(fileSizeThreshold=0x100000, maxFileSize=0xA00000L, maxRequestSize=0x1400000L)
public class UploadFile
extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(this.getServletContext().getRealPath(""));
        String uploadPath = this.getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        String previousUrl = request.getRequestURL().toString().replace("upload_excel", request.getParameter("previousUrl"));
        File uploadDir = new File(uploadPath);
        String deploymentsPath = UploadFile.getDeploymentsPath(uploadDir) + File.separator + UPLOAD_DIR;
        File realUploadDir = new File(deploymentsPath);
        if (!realUploadDir.exists()) {
            realUploadDir.mkdirs();
        }
        Part part = request.getPart("file");
        String fileName = this.extractFileName(request.getPart("file"));
        if (fileName != null && !fileName.isEmpty()) {
            File targetFile = new File(deploymentsPath + File.separator + fileName);
            if (targetFile.exists()) {
                targetFile.delete();
            }
            part.write(targetFile.getAbsolutePath());
        }
        String filePath = deploymentsPath + File.separator + this.extractFileName(request.getPart("file"));
        HttpSession session = request.getSession();
        try {
            ClassMAPTable[] result = AdminGen.fromExcel((String)filePath, (ClassMAPTable)((ClassMAPTable)Class.forName(request.getParameter("classeUpload")).newInstance()));
            session.setAttribute("resultat", (Object)result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(previousUrl);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String token : contentDisp.split(";")) {
            if (!token.trim().startsWith("filename")) continue;
            return token.substring(token.indexOf("=") + 2, token.length() - 1);
        }
        return null;
    }

    public static String getDeploymentsPath(File fullPath) {
        for (File current = fullPath; current != null; current = current.getParentFile()) {
            if (!current.getName().equals("deployments") && !current.getName().equals("webapps")) continue;
            return current.getAbsolutePath();
        }
        throw new IllegalStateException("Le dossier 'deployments' ou 'webapps' n'a pas \u00c3\u00a9t\u00c3\u00a9 trouv\u00c3\u00a9 dans le chemin : " + fullPath.getAbsolutePath());
    }
}

