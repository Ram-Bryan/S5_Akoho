/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.poi.openxml4j.opc.OPCPackage
 *  org.apache.poi.xwpf.usermodel.XWPFDocument
 *  org.apache.poi.xwpf.usermodel.XWPFParagraph
 *  org.apache.poi.xwpf.usermodel.XWPFRun
 *  org.apache.poi.xwpf.usermodel.XWPFTable
 *  org.apache.poi.xwpf.usermodel.XWPFTableCell
 *  org.apache.poi.xwpf.usermodel.XWPFTableRow
 */
package utilitaire;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import utilitaire.Utilitaire;

public class WordUtil {
    public void generateWordByTemplateTableTable(String docSource, Object[] o, String docName, HttpServletResponse response) throws Exception {
        XWPFDocument doc = new XWPFDocument(OPCPackage.open((String)docSource));
        for (int i = 0; i < o.length; ++i) {
            this.generateWordByTemplateTable(doc, o[i]);
        }
        response.setContentType("text/plain");
        response.setContentType("application/ms-word");
        response.setHeader("Content-disposition", "attachment;fileName=" + docName + ".docx");
        ServletOutputStream out = response.getOutputStream();
        doc.write((OutputStream)out);
        out.close();
    }

    public void generateWordByTemplate(XWPFDocument doc, Object o, String docName) throws Exception {
        String[] listColonne = Utilitaire.getNomColonne(o);
        String[] listColonneUpper = this.toUpperFirst(listColonne);
        for (XWPFParagraph p : doc.getParagraphs()) {
            List runs = p.getRuns();
            if (runs == null) continue;
            for (XWPFRun r : runs) {
                String text = r.getText(0);
                for (int i = 0; i < listColonne.length; ++i) {
                    if (!text.contains(listColonne[i])) continue;
                    Method methodGet = this.getMethodObject(o, listColonneUpper[i], "get");
                    text = text.replace(listColonne[i], (String)methodGet.invoke(o, null));
                    r.setText(text);
                }
            }
        }
    }

    public void generateWordByTemplateTable(XWPFDocument doc, Object o) throws Exception {
        String[] listColonne = Utilitaire.getNomColonne(o);
        String[] listColonneUpper = this.toUpperFirst(listColonne);
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            String text = r.getText(0);
                            for (int i = 0; i < listColonne.length; ++i) {
                                if (!text.contains(listColonne[i])) continue;
                                Method methodGet = this.getMethodObject(o, listColonneUpper[i], "get");
                                text = text.replace(listColonne[i], String.valueOf(methodGet.invoke(o, null)));
                                r.setText(text, 0);
                            }
                        }
                    }
                }
            }
        }
    }

    public String[] toUpperFirst(String[] list) {
        String[] listUpper = new String[list.length];
        for (int i = 0; i < list.length; ++i) {
            char[] separe = list[i].toCharArray();
            String up = new String(list[i].toUpperCase());
            char[] upper = up.toCharArray();
            separe[0] = upper[0];
            listUpper[i] = new String(separe);
        }
        return listUpper;
    }

    public Method getMethodObject(Object o, String attribut, String fonction) throws Exception {
        try {
            Method[] listeMethods = o.getClass().getMethods();
            int nb = listeMethods.length;
            for (int a = 0; a < nb; ++a) {
                if (!listeMethods[a].getName().equals(fonction + attribut)) continue;
                return listeMethods[a];
            }
            return null;
        }
        catch (Exception e) {
            throw new Exception("Erreur dans WordUtil getMethodObject :" + e.getMessage());
        }
    }
}

