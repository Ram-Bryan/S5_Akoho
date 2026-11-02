/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  configuration.CynthiaConf
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.xssf.usermodel.XSSFCell
 *  org.apache.poi.xssf.usermodel.XSSFRow
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 *  reporting.ReportingCdn
 *  reporting.ReportingCdn$Fonctionnalite
 */
package utilitaire;

import bean.CGenUtil;
import bean.ClassMAPTable;
import configuration.CynthiaConf;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import reporting.ReportingCdn;

public class UtilExcel {
    static XSSFRow row;
    static Properties prop;
    static String chemin;

    public static boolean checkExist(String fileName) {
        File f = new File(chemin + fileName);
        return f.exists();
    }

    public int countRowExcel(String file) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(new File(chemin + file));
        XSSFWorkbook workbook = new XSSFWorkbook((InputStream)fis);
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        Iterator rowIterator = spreadsheet.iterator();
        int a = 0;
        while (rowIterator.hasNext()) {
            row = (XSSFRow)rowIterator.next();
            ++a;
        }
        fis.close();
        return a;
    }

    public static void createExcel(String nomfichier, String nomfeuille, String[] entete, ReportingCdn.Fonctionnalite fonctionnalite, List donnee) throws Exception {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet(nomfeuille);
            FileOutputStream out = new FileOutputStream(new File(ReportingCdn.cheminReport((ReportingCdn.Fonctionnalite)fonctionnalite) + nomfichier));
            spreadsheet = workbook.getSheetAt(0);
            int rowid = 0;
            row = spreadsheet.createRow(rowid++);
            int cellid = 0;
            for (int a = 0; a < entete.length; ++a) {
                XSSFCell cell = row.createCell(cellid++);
                cell.setCellValue(entete[a]);
            }
            for (int i = 0; i < donnee.size(); ++i) {
                Field[] fields = donnee.get(i).getClass().getDeclaredFields();
                cellid = 0;
                row = spreadsheet.createRow(rowid++);
                for (int e = 0; e < fields.length; ++e) {
                    Object o = CGenUtil.getValeurFieldByMethod((ClassMAPTable)donnee.get(i), fields[e]);
                    XSSFCell cell = row.createCell(cellid++);
                    if (fields[e].getType().getName().equalsIgnoreCase("java.lang.String")) {
                        cell.setCellValue((String)o);
                    }
                    if (fields[e].getType().getName().equalsIgnoreCase("java.sql.Date") && o != null) {
                        cell.setCellValue((java.util.Date)o);
                    }
                    if (fields[e].getType().getName().equalsIgnoreCase("int")) {
                        cell.setCellValue((double)((Integer)o).intValue());
                    }
                    if (!fields[e].getType().getName().equalsIgnoreCase("double")) continue;
                    cell.setCellValue(((Double)o).doubleValue());
                }
            }
            workbook.write((OutputStream)out);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void createexcel(String nomfichier, String nomfeuille, String[] entete, List donnee) throws Exception {
        UtilExcel.createExcel(nomfichier, nomfeuille, entete, ReportingCdn.Fonctionnalite.DN, donnee);
    }

    public static void fromObjectToExcelFlexible(String outputPath, ClassMAPTable[] data) throws Exception {
        int i;
        if (data == null || data.length == 0) {
            return;
        }
        Field[] fields = data[0].getFieldList();
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Export");
        Row headerRow = sheet.createRow(0);
        int col = 0;
        for (Field field : fields) {
            Cell cell = headerRow.createCell(col++);
            cell.setCellValue(field.getName());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (i = 0; i < data.length; ++i) {
            ClassMAPTable obj = data[i];
            Row row = sheet.createRow(i + 1);
            col = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(obj);
                Cell cell = row.createCell(col++);
                if (value == null) {
                    cell.setCellValue("");
                    continue;
                }
                if (value instanceof String) {
                    cell.setCellValue((String)value);
                    continue;
                }
                if (value instanceof Number) {
                    cell.setCellValue(((Number)value).doubleValue());
                    continue;
                }
                if (value instanceof Date) {
                    String formatted = sdf.format((Date)value);
                    cell.setCellValue(formatted);
                    continue;
                }
                cell.setCellValue(value.toString());
            }
        }
        for (i = 0; i < fields.length; ++i) {
            sheet.autoSizeColumn(i);
        }
        try (FileOutputStream out = new FileOutputStream(outputPath);){
            workbook.write((OutputStream)out);
        }
    }

    public boolean deleteDirectory(File path) {
        boolean resultat = true;
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; ++i) {
                if (files[i].isDirectory()) {
                    resultat &= this.deleteDirectory(files[i]);
                    continue;
                }
                resultat &= files[i].delete();
            }
        }
        return resultat;
    }

    public void addrowonexcel(String fichier, XSSFRow rowtoadd) throws Exception {
        try {
            File file = new File(chemin + fichier);
            FileInputStream fIP = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook((InputStream)fIP);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator rowIterator = spreadsheet.iterator();
            int id = 0;
            while (rowIterator.hasNext()) {
                row = (XSSFRow)rowIterator.next();
                ++id;
            }
            row = spreadsheet.createRow(id);
            Iterator cellIterator = rowtoadd.cellIterator();
            int cellid = 0;
            while (cellIterator.hasNext()) {
                Cell cell = (Cell)cellIterator.next();
                XSSFCell cell2 = row.createCell(cellid++);
                switch (cell.getCellType()) {
                    case 0: {
                        cell2.setCellValue(cell.getNumericCellValue());
                        break;
                    }
                    case 1: {
                        cell2.setCellValue(cell.getStringCellValue());
                    }
                }
            }
            FileOutputStream out = new FileOutputStream(new File(chemin + fichier));
            workbook.write((OutputStream)out);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    static {
        prop = CynthiaConf.load();
        chemin = prop.getProperty("cdnPDF") + prop.getProperty("cdnDN") + "/";
    }
}

