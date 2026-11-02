/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.Part
 */
package utilitaire;

import bean.Constante;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class UtilUpload {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String fileUploadWithDesiredFilePathAndName(HttpServletRequest request, HttpServletResponse response, String fileName_p, String redir) throws IOException, ServletException {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        String fullName = "";
        String newFileName = "";
        boolean efaNandalo = false;
        try {
            for (Part part : request.getParts()) {
                if (!efaNandalo) {
                    inputStream = request.getPart(part.getName()).getInputStream();
                    int i = inputStream.available();
                    byte[] b = new byte[i];
                    inputStream.read(b);
                    String fileName = "";
                    String partHeader = part.getHeader("content-disposition");
                    for (String temp : part.getHeader("content-disposition").split(";")) {
                        if (!temp.trim().startsWith("filename")) continue;
                        fileName = temp.substring(temp.indexOf(61) + 1).trim().replace("", "");
                    }
                    if (fileName_p != null && !fileName_p.isEmpty()) {
                        String extension = fileName.substring(fileName.indexOf(".")).replace("\"", "");
                        newFileName = (fileName_p + extension).replace("\"", "");
                    } else {
                        newFileName = fileName.replace("\"", "");
                    }
                    String uploadDir = Constante.uploadDirLocation;
                    fullName = uploadDir + "/" + newFileName;
                    outputStream = new FileOutputStream(fullName);
                    outputStream.write(b);
                    inputStream.close();
                }
                efaNandalo = true;
                System.out.println("File Uploaded successfully in " + fullName);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (redir != null && !redir.isEmpty()) {
                response.sendRedirect(redir);
            }
        }
        return newFileName;
    }

    public static String getImagePathServ(String urlImage) {
        return Constante.uploadedDir + "/" + urlImage;
    }
}

