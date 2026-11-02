/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JRException
 *  net.sf.jasperreports.engine.JasperExportManager
 *  net.sf.jasperreports.engine.JasperPrint
 */
package reporting;

import configuration.CynthiaConf;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import reporting.ReportingUtils;
import reporting.UtilitaireServeurPDF;

public class ReportingCdn {
    static Properties prop = CynthiaConf.load();
    static String chemin = prop.getProperty("cdnPDF");

    public static String getExtension(ReportingUtils.ReportType reportType) {
        switch (reportType) {
            case PDF: {
                return ".pdf";
            }
            case XLSX: {
                return ".xlsx";
            }
            case XLS: {
                return ".xls";
            }
        }
        return null;
    }

    public static String cheminFonct(Fonctionnalite fonctionnalite) {
        Properties prop = CynthiaConf.load();
        switch (fonctionnalite) {
            case TRESORERIE: {
                return prop.getProperty("cdnTresorerie");
            }
            case DN: {
                return prop.getProperty("cdnDN");
            }
            case PAIE: {
                return prop.getProperty("cdnPaie");
            }
            case RECETTE: {
                return prop.getProperty("cdnRecette");
            }
            case DEFAUT: {
                return "/";
            }
        }
        return null;
    }

    public static String cheminReport(Fonctionnalite fonctionnalite) {
        return chemin + ReportingCdn.cheminFonct(fonctionnalite) + "/";
    }

    public static File getFile(String filename, Fonctionnalite fonctionnalite) {
        File file = new File(ReportingCdn.cheminReport(fonctionnalite) + "/" + filename);
        return file;
    }

    private static void exportFileServeurXLS(JasperPrint print, Fonctionnalite fonctionnalite, String fileName) throws FileNotFoundException, JRException {
        String fonct = ReportingCdn.cheminFonct(fonctionnalite);
        FileOutputStream output = new FileOutputStream(new File(chemin + fonct + "/" + fileName + ".xlsx"));
        ReportingUtils reporting = new ReportingUtils();
        reporting.exportReport(print, ReportingUtils.ReportType.XLSX, output);
    }

    private static void exportFileServeurPDF(JasperPrint print, Fonctionnalite fonctionnalite, String fileName) throws FileNotFoundException, JRException {
        String fonct = ReportingCdn.cheminFonct(fonctionnalite);
        FileOutputStream output = new FileOutputStream(new File(chemin + fonct + "/" + fileName + ".pdf"));
        JasperExportManager.exportReportToPdfStream((JasperPrint)print, (OutputStream)output);
    }

    public static void exportFile(JasperPrint print, Fonctionnalite fonctionnalite, String fileName, ReportingUtils.ReportType typeFile) throws FileNotFoundException, JRException {
        switch (typeFile) {
            case XLSX: {
                ReportingCdn.exportFileServeurXLS(print, fonctionnalite, fileName);
            }
            case PDF: {
                ReportingCdn.exportFileServeurPDF(print, fonctionnalite, fileName);
            }
        }
    }

    public static void exportFile(JasperPrint print, Fonctionnalite fonctionnalite, String fileName) throws FileNotFoundException, JRException {
        ReportingCdn.exportFile(print, fonctionnalite, fileName, ReportingUtils.ReportType.PDF);
    }

    public static void exportFileXLS(JasperPrint print, Fonctionnalite fonctionnalite, String fileName) throws FileNotFoundException, JRException {
        ReportingCdn.exportFile(print, fonctionnalite, fileName, ReportingUtils.ReportType.XLSX);
    }

    public static boolean checkExistingFile(Fonctionnalite fonctionnalite, String fileName) throws FileNotFoundException {
        File file = ReportingCdn.getFile(fileName, fonctionnalite);
        return file.exists();
    }

    public static void deleteFiles(File path, String id) throws Exception {
        if (path.exists()) {
            path.delete();
            UtilitaireServeurPDF.deleteFromServeur(id);
        }
    }

    public static enum Fonctionnalite {
        TRESORERIE,
        DN,
        PAIE,
        RECETTE,
        DEFAUT;

    }
}

