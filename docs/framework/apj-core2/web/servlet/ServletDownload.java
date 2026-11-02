/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  affichage.TableauRecherche
 *  bean.CGenUtil
 *  bean.ClassMAPTable
 *  bean.ListeColonneTable
 *  com.google.gson.Gson
 *  com.itextpdf.text.DocListener
 *  com.itextpdf.text.Document
 *  com.itextpdf.text.PageSize
 *  com.itextpdf.text.html.simpleparser.HTMLWorker
 *  com.itextpdf.text.pdf.PdfWriter
 *  javax.servlet.ServletException
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.annotation.WebServlet
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.StringEscapeUtils
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 *  utilitaire.Utilitaire
 */
package web.servlet;

import affichage.TableauRecherche;
import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import com.google.gson.Gson;
import com.itextpdf.text.DocListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utilitaire.Utilitaire;
import web.servlet.UtilitaireFormat;

@WebServlet(name="ServletDownload", urlPatterns={"/download"})
public class ServletDownload
extends HttpServlet {
    private String normalizeDateStringForExcel(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        if (!trimmed.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return value;
        }
        SimpleDateFormat in = new SimpleDateFormat("dd/MM/yyyy");
        in.setLenient(false);
        SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date parsed = in.parse(trimmed);
            return out.format(parsed);
        }
        catch (Exception e) {
            return value;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        block59: {
            this.processRequest(arg0, arg1);
            try (ServletOutputStream os = arg1.getOutputStream();){
                ClassMAPTable[] c;
                Field[] fields;
                ClassMAPTable o;
                String libColExcelParam;
                Gson gson2;
                String colExcelParam;
                String ext = arg0.getParameter("ext");
                if (ext == null || ext.trim().isEmpty()) {
                    ext = "csv";
                }
                if ("xls".equalsIgnoreCase(ext)) {
                    return;
                }
                System.out.println("****** ext " + ext);
                String awhere = arg0.getParameter("awhere");
                if (awhere == null) {
                    awhere = "";
                } else {
                    System.out.println("================Tonga ato ilay awhere");
                    System.out.println("awhere = " + awhere);
                }
                arg1.setContentType("text/plain");
                SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
                String d = fd.format(new java.util.Date());
                String titre = arg0.getParameter("titre");
                if ((titre == null || titre.trim().isEmpty() || "null".equalsIgnoreCase(titre.trim())) && ((titre = arg0.getParameter("nomTable")) == null || titre.trim().isEmpty() || "null".equalsIgnoreCase(titre.trim()))) {
                    titre = "export";
                }
                titre = titre.trim().replaceAll("[\\\\/:*?\"<>|]", "_");
                String fileName = titre + "-" + d + "." + ext;
                arg1.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                os = arg1.getOutputStream();
                System.out.println("" + fileName);
                String type = arg0.getParameter("donnee");
                if (type == null) {
                    type = arg0.getParameter("type");
                }
                if (type == null) {
                    type = "0";
                }
                String logo = this.getServletContext().getRealPath(File.separator + "assets" + File.separator + "img" + File.separator + "logo.png");
                if (type.compareTo("0") == 0) {
                    System.out.println("0 : ");
                    String htmlStringBasPage = "";
                    if (arg0.getParameter("recap") != null && arg0.getParameter("nombreEncours") != null) {
                        int nombre = Utilitaire.stringToInt((String)arg0.getParameter("nombreEncours"));
                        double montant = Utilitaire.stringToDouble((String)arg0.getParameter("recap"));
                        htmlStringBasPage = UtilitaireFormat.generateBasPage(nombre, montant);
                    }
                    if (ext.compareToIgnoreCase("pdf") == 0) {
                        boolean estPaysage = false;
                        if ("1".equals(arg0.getParameter("landscape"))) {
                            estPaysage = true;
                        }
                        arg1.setContentType("application/pdf");
                        arg1.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                        String htmlStringEntete = UtilitaireFormat.generateEntete(titre, logo);
                        String tableContent = arg0.getParameter("table");
                        if (tableContent != null) {
                            htmlStringEntete = htmlStringEntete + tableContent.replace('*', '\"').replaceAll("\u00a0", " ");
                        }
                        String fullHtml = htmlStringEntete + htmlStringBasPage + "</body></html>";
                        fullHtml = fullHtml.replaceAll("(?is)<style.*?>.*?</style>", "");
                        Document document = null;
                        document = estPaysage ? new Document(PageSize.A4.rotate(), 20.0f, 20.0f, 20.0f, 20.0f) : new Document(PageSize.A4, 20.0f, 20.0f, 20.0f, 20.0f);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        try {
                            PdfWriter.getInstance((Document)document, (OutputStream)baos);
                            document.open();
                            HTMLWorker htmlWorker = new HTMLWorker((DocListener)document);
                            htmlWorker.parse((Reader)new StringReader(fullHtml));
                            document.close();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("PDF Created!");
                        arg1.setContentLength(baos.size());
                        baos.writeTo((OutputStream)os);
                        os.flush();
                    } else if (ext.compareToIgnoreCase("csv") == 0) {
                        arg1.setContentType("text/csv; charset=UTF-8");
                        String donnee = arg0.getParameter(ext);
                        if (donnee != null) {
                            os.write(donnee.replace('*', '\"').replaceAll("\u00a0", " ").getBytes());
                        }
                    } else if (ext.compareToIgnoreCase("xml") == 0) {
                        arg1.setContentType("text/xml; charset=UTF-8");
                        String donnee = arg0.getParameter(ext);
                        if (donnee != null) {
                            if ((donnee = donnee.replace('*', '\"').replaceAll("\u00a0", " ")).startsWith("null")) {
                                donnee = donnee.substring(4).trim();
                            }
                            if (donnee.trim().isEmpty()) {
                                donnee = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau></tableau>\r\n";
                            }
                            os.write(donnee.getBytes("UTF-8"));
                        }
                    } else {
                        String donnee = arg0.getParameter(ext);
                        if (donnee != null) {
                            os.write(donnee.replace('*', '\"').replaceAll("\u00a0", " ").getBytes());
                        }
                    }
                    os.flush();
                    break block59;
                }
                if (type.compareTo("1") != 0) break block59;
                System.out.println("1 : ");
                String enteteParam = arg0.getParameter("entete");
                String enteteLibelleParam = arg0.getParameter("entetelibelle");
                String[] entete = null;
                String[] entetelibelle = null;
                if (enteteParam != null && !enteteParam.trim().isEmpty()) {
                    entete = Utilitaire.split((String)enteteParam, (String)";");
                }
                if (enteteLibelleParam != null && !enteteLibelleParam.trim().isEmpty()) {
                    entetelibelle = Utilitaire.split((String)enteteLibelleParam, (String)";");
                }
                if (!(entete != null && entete.length != 0 || (colExcelParam = arg0.getParameter("colonneExcel")) == null || colExcelParam.trim().isEmpty())) {
                    try {
                        gson2 = new Gson();
                        entete = (String[])gson2.fromJson(colExcelParam, String[].class);
                    }
                    catch (Exception gson2) {
                        // empty catch block
                    }
                }
                if (!(entetelibelle != null && entetelibelle.length != 0 || (libColExcelParam = arg0.getParameter("libColonneExcel")) == null || libColExcelParam.trim().isEmpty())) {
                    try {
                        gson2 = new Gson();
                        entetelibelle = (String[])gson2.fromJson(libColExcelParam, String[].class);
                    }
                    catch (Exception gson3) {
                        // empty catch block
                    }
                }
                if ((o = (ClassMAPTable)arg0.getSession().getAttribute("critere")) == null && arg0.getParameter("className") != null) {
                    String className = arg0.getParameter("className");
                    o = (ClassMAPTable)Class.forName(className).newInstance();
                }
                if (o != null && arg0.getParameter("nomTable") != null && !arg0.getParameter("nomTable").trim().isEmpty()) {
                    o.setNomTable(arg0.getParameter("nomTable"));
                }
                if ((entete == null || entete.length == 0) && o != null && (fields = ListeColonneTable.getFieldListeHeritage((ClassMAPTable)o)) != null && fields.length > 0) {
                    entete = new String[fields.length];
                    for (int i = 0; i < fields.length; ++i) {
                        entete[i] = fields[i].getName();
                    }
                }
                if (entete != null && (entetelibelle == null || entetelibelle.length != entete.length)) {
                    entetelibelle = entete;
                }
                if ((c = (ClassMAPTable[])CGenUtil.rechercher((ClassMAPTable)o, null, null, (String)awhere)) == null) {
                    c = new ClassMAPTable[]{};
                }
                TableauRecherche tr = new TableauRecherche(c, entete);
                if (entetelibelle != null && entetelibelle.length > 0) {
                    tr.setLibelleAffiche(entetelibelle);
                }
                if (c.length > 0) {
                    tr.makeHtml();
                }
                String htmlStringBasPage = "";
                if (arg0.getParameter("recap") != null && !arg0.getParameter("recap").trim().isEmpty()) {
                    int nombre = tr.getData() != null ? tr.getData().length : c.length;
                    double montant = Utilitaire.stringToDouble((String)arg0.getParameter("recap"));
                    htmlStringBasPage = UtilitaireFormat.generateBasPage(nombre, montant);
                }
                if (ext.compareToIgnoreCase("xml") == 0) {
                    arg1.setContentType("text/xml; charset=UTF-8");
                    String donnee = tr.getExpxml();
                    if (donnee == null || donnee.trim().isEmpty()) {
                        donnee = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \r\n <tableau></tableau>\r\n";
                    } else if (donnee.startsWith("null")) {
                        donnee = donnee.substring(4).trim();
                    }
                    os.write(donnee.getBytes("UTF-8"));
                } else if (ext.compareToIgnoreCase("pdf") == 0) {
                    boolean estPaysage = false;
                    if ("1".equals(arg0.getParameter("landscape"))) {
                        estPaysage = true;
                    }
                    arg1.setContentType("application/pdf");
                    arg1.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                    Document document = null;
                    document = estPaysage ? new Document(PageSize.A4.rotate(), 20.0f, 20.0f, 20.0f, 20.0f) : new Document(PageSize.A4, 20.0f, 20.0f, 20.0f, 20.0f);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try {
                        PdfWriter.getInstance((Document)document, (OutputStream)baos);
                        document.open();
                        String htmlStringEntete = UtilitaireFormat.generateEntete(titre, logo);
                        String tableHtml = tr.getHtmlPDF();
                        String fullHtml = htmlStringEntete + (tableHtml != null ? tableHtml : "") + htmlStringBasPage + "</body></html>";
                        fullHtml = fullHtml.replaceAll("(?is)<style.*?>.*?</style>", "");
                        HTMLWorker htmlWorker = new HTMLWorker((DocListener)document);
                        htmlWorker.parse((Reader)new StringReader(fullHtml));
                        document.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("PDF Created!");
                    arg1.setContentLength(baos.size());
                    baos.writeTo((OutputStream)os);
                    os.flush();
                } else if (ext.compareToIgnoreCase("csv") == 0) {
                    arg1.setContentType("text/csv; charset=UTF-8");
                    System.out.println(tr.getExpcsv());
                    System.out.println("OS :: " + os);
                    if (tr.getExpcsv() != null) {
                        os.write(tr.getExpcsv().getBytes());
                    }
                }
                os.flush();
            }
        }
    }

    protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        this.doPost(arg0, arg1);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String ext = request.getParameter("ext");
            if (ext.equalsIgnoreCase("xls")) {
                this.exporterExcel(request, response);
            }
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    private CellStyle getHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBoldweight((short)700);
        headerStyle.setFont(boldFont);
        headerStyle.setBorderTop((short)1);
        headerStyle.setBorderBottom((short)1);
        headerStyle.setBorderLeft((short)1);
        headerStyle.setBorderRight((short)1);
        return headerStyle;
    }

    public CellStyle getDataStyle(Workbook workbook) {
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderTop((short)1);
        dataStyle.setBorderBottom((short)1);
        dataStyle.setBorderLeft((short)1);
        dataStyle.setBorderRight((short)1);
        return dataStyle;
    }

    public CellStyle getTitleStyle(Workbook workbook) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBoldweight((short)700);
        titleFont.setFontHeightInPoints((short)12);
        titleStyle.setFont(titleFont);
        return titleStyle;
    }

    public void exporterExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream out = null;
        try {
            int rowNum;
            String titre;
            String requete = request.getParameter("requete");
            String type = request.getParameter("donnee");
            String nomTable = request.getParameter("nomTable");
            if (requete != null) {
                requete = StringEscapeUtils.unescapeHtml4((String)requete);
            }
            if (requete == null || requete.trim().isEmpty() || requete.equalsIgnoreCase("null")) {
                requete = "SELECT * FROM " + request.getParameter("nomTable") + " WHERE 1=1 " + (request.getParameter("awhere") != null ? request.getParameter("awhere") : "");
            }
            if (type != null && type.equalsIgnoreCase("1")) {
                requete = requete.replaceAll("(?i)\\s*where\\s+r\\s+between\\s+\\d+\\s+and\\s+\\d+", "");
                requete = requete.replaceAll("(?i)\\s*where\\s+rownum\\s*<=\\s*\\d+", "");
            }
            Gson gson = new Gson();
            String[] colonneExcel = (String[])gson.fromJson(request.getParameter("colonneExcel"), String[].class);
            String[] libColonneExcel = (String[])gson.fromJson(request.getParameter("libColonneExcel"), String[].class);
            String className = request.getParameter("className");
            ClassMAPTable o = (ClassMAPTable)Class.forName(className).newInstance();
            o.setNomTable(nomTable);
            ClassMAPTable[] c = (ClassMAPTable[])CGenUtil.rechercher((ClassMAPTable)o, (String)requete);
            if (!(colonneExcel != null && colonneExcel.length != 0 || request.getParameter("entete") == null || request.getParameter("entete").trim().isEmpty())) {
                colonneExcel = Utilitaire.split((String)request.getParameter("entete"), (String)";");
            }
            if (!(libColonneExcel != null && libColonneExcel.length != 0 || request.getParameter("entetelibelle") == null || request.getParameter("entetelibelle").trim().isEmpty())) {
                libColonneExcel = Utilitaire.split((String)request.getParameter("entetelibelle"), (String)";");
            }
            if (colonneExcel == null || colonneExcel.length == 0) {
                colonneExcel = libColonneExcel;
            }
            if (libColonneExcel == null || libColonneExcel.length == 0) {
                libColonneExcel = colonneExcel;
            }
            if (((titre = request.getParameter("titre")) == null || titre.trim().isEmpty() || "null".equalsIgnoreCase(titre.trim())) && ((titre = request.getParameter("nomTable")) == null || titre.trim().isEmpty() || "null".equalsIgnoreCase(titre.trim()))) {
                titre = "Liste";
            }
            titre = titre.trim().replaceAll("[\\\\/:*?\"<>|]", "_");
            SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
            String d = fd.format(new java.util.Date());
            String fileName = titre + "-" + d + ".xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(titre);
            CellStyle headerStyle = this.getHeaderStyle((Workbook)workbook);
            CellStyle dataStyle = this.getDataStyle((Workbook)workbook);
            CellStyle titleStyle = this.getTitleStyle((Workbook)workbook);
            Row headerRow = sheet.createRow(0);
            for (rowNum = 0; rowNum < libColonneExcel.length; ++rowNum) {
                Cell cell = headerRow.createCell(rowNum);
                cell.setCellValue(StringEscapeUtils.unescapeHtml4((String)libColonneExcel[rowNum]));
                cell.setCellStyle(headerStyle);
            }
            rowNum = 1;
            ClassMAPTable[] var32 = c;
            int var23 = c.length;
            for (int var24 = 0; var24 < var23; ++var24) {
                ClassMAPTable cm = var32[var24];
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < colonneExcel.length; ++i) {
                    Method getter = CGenUtil.getMethod((ClassMAPTable)cm, (String)colonneExcel[i]);
                    Object value = getter != null ? getter.invoke(cm, new Object[0]) : null;
                    Cell cell = row.createCell(i);
                    if (value == null) {
                        cell.setCellValue("");
                        cell.setCellStyle(dataStyle);
                        continue;
                    }
                    if (value instanceof Date) {
                        Date date = (Date)value;
                        cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(date));
                        cell.setCellStyle(dataStyle);
                        continue;
                    }
                    if (value instanceof Timestamp) {
                        cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(((Timestamp)value).getTime())));
                        cell.setCellStyle(dataStyle);
                        continue;
                    }
                    if (value instanceof java.util.Date) {
                        cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format((java.util.Date)value));
                        cell.setCellStyle(dataStyle);
                        continue;
                    }
                    if (!(value instanceof Integer || value instanceof Long || value instanceof Short || value instanceof Byte || value instanceof Float || value instanceof Double)) {
                        if (value instanceof Boolean) {
                            cell.setCellValue(((Boolean)value).booleanValue());
                            cell.setCellStyle(dataStyle);
                            continue;
                        }
                        String str = value.toString();
                        str = StringEscapeUtils.unescapeHtml4((String)str);
                        str = this.normalizeDateStringForExcel(str);
                        cell.setCellValue(str);
                        cell.setCellStyle(dataStyle);
                        continue;
                    }
                    cell.setCellValue(((Number)value).doubleValue());
                    cell.setCellStyle(dataStyle);
                }
            }
            for (int i = 0; i < libColonneExcel.length; ++i) {
                sheet.autoSizeColumn(i);
            }
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            out = response.getOutputStream();
            workbook.write((OutputStream)out);
            if (workbook instanceof XSSFWorkbook) {
                workbook.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de l'import : " + e.getMessage());
        }
        finally {
            out.flush();
            out.close();
        }
    }
}

