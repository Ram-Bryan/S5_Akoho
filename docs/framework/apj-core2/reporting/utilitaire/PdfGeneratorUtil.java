/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.openhtmltopdf.pdfboxout.PdfRendererBuilder
 *  utilitaire.Utilitaire
 */
package reporting.utilitaire;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Base64;
import reporting.utilitaire.ConstanteCR;
import reporting.utilitaire.UtilitaireCR;
import utilitaire.Utilitaire;

public class PdfGeneratorUtil {
    public static void generatePdfFromHtml(String htmlContent, String outputPdfPath) throws Exception {
        htmlContent = htmlContent.replace("&eacute;", "e").replace("&egrave;", "e").replace("&agrave;", "a").replace("&nbsp;", " ");
        htmlContent = htmlContent.replace("<!--", "").replace("-->", "");
        try (FileOutputStream os = new FileOutputStream(outputPdfPath);){
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream((OutputStream)os);
            builder.run();
        }
    }

    public static String pdfUrl(int nature, Date daty) throws Exception {
        String[] natures = new String[]{"rapport-journalier", "rapport-hebdomadaire", "rapport-mensuel"};
        String dir = ConstanteCR.BASE_DIR + "/" + "TeamTask.war" + "/Compte Rendus/pdf/";
        String xlsx = dir.replace("pdf", "xlsx");
        UtilitaireCR.createDirectory(dir);
        UtilitaireCR.createDirectory(xlsx);
        String heure = Utilitaire.heureCourante();
        String[] heures = heure.split(":");
        heure = heures[0] + "" + heures[1];
        String fileId = daty.toString().replace("-", "");
        dir = dir + natures[nature - 1] + "-" + fileId + "-" + heure + ".pdf";
        return dir;
    }

    public static String imgHtmlPdf(String url) {
        url = url.replace("\\", "/");
        String imgHtml = "<!-- <img src=\"file:///" + url + "\" alt=\"Image\" /> -->";
        return imgHtml;
    }

    public static void base64() throws Exception {
        String url = ConstanteCR.BASE_DIR + "" + "TeamTask.war" + "/Json/test.png";
        byte[] fileContent = Files.readAllBytes(Paths.get(url, new String[0]));
        String base64Image = Base64.getEncoder().encodeToString(fileContent);
        System.out.println(base64Image);
    }
}

