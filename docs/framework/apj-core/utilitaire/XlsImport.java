/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.DataFormatter
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.usermodel.WorkbookFactory
 *  user.UserEJBBean
 */
package utilitaire;

import bean.CGenUtil;
import bean.Champ;
import bean.ClassEtat;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import user.UserEJBBean;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class XlsImport {
    private static Workbook workbook;
    private static Sheet sheet;

    public static void importerAvecId(String fileUrl, ClassMAPTable o) throws Exception {
        try (Connection conn = null;){
            conn = new UtilDB().GetConn();
            workbook = WorkbookFactory.create((File)new File(fileUrl));
            sheet = workbook.getSheetAt(0);
            int indice = 1;
            Row row = sheet.getRow(indice);
            while (row != null) {
                Champ[] listeChamps;
                int cellNumber = row.getPhysicalNumberOfCells();
                if (cellNumber != (listeChamps = ListeColonneTable.getFromListe(o, null)).length - 1) {
                    throw new Exception("erreur");
                }
                for (int co = 0; co < cellNumber; ++co) {
                    Cell c = row.getCell(co);
                    if (listeChamps[co + 1].getTypeJava().equals("java.lang.String")) {
                        String valeur = "";
                        try {
                            valeur = c.getStringCellValue();
                        }
                        catch (Exception ex) {
                            try {
                                valeur = String.valueOf((int)c.getNumericCellValue());
                            }
                            catch (Exception e) {
                                valeur = Utilitaire.format(c.getDateCellValue());
                            }
                        }
                        CGenUtil.setValChamp(o, listeChamps[co + 1].getNomClasse(), valeur);
                        continue;
                    }
                    if (listeChamps[co + 1].getTypeJava().equals("int")) {
                        CGenUtil.setValChamp(o, listeChamps[co + 1].getNomClasse(), new Integer((int)c.getNumericCellValue()));
                        continue;
                    }
                    if (listeChamps[co + 1].getTypeJava().equals("double")) {
                        CGenUtil.setValChamp(o, listeChamps[co + 1].getNomClasse(), new Double(c.getNumericCellValue()));
                        continue;
                    }
                    if (!listeChamps[co + 1].getTypeJava().equals("java.sql.Date")) continue;
                    CGenUtil.setValChamp(o, listeChamps[co + 1].getNomClasse(), new Date(c.getDateCellValue().getTime()));
                }
                o.construirePK(conn);
                o.insertToTable();
                row = sheet.getRow(++indice);
            }
        }
    }

    public static void importerTous(String fileUrl, ClassMAPTable o) throws Exception {
        try {
            workbook = WorkbookFactory.create((File)new File(fileUrl));
            sheet = workbook.getSheetAt(0);
            int indice = 1;
            Row row = sheet.getRow(indice);
            while (row != null) {
                Champ[] listeChamps;
                int cellNumber = row.getPhysicalNumberOfCells();
                if (cellNumber != (listeChamps = ListeColonneTable.getFromListe(o, null)).length) {
                    throw new Exception("erreur");
                }
                for (int co = 0; co < cellNumber; ++co) {
                    Cell c = row.getCell(co);
                    if (listeChamps[co].getTypeJava().equals("java.lang.String")) {
                        String valeur = "";
                        try {
                            valeur = c.getStringCellValue();
                        }
                        catch (Exception ex) {
                            try {
                                valeur = String.valueOf((int)c.getNumericCellValue());
                            }
                            catch (Exception e) {
                                valeur = Utilitaire.format(c.getDateCellValue());
                            }
                        }
                        CGenUtil.setValChamp(o, listeChamps[co].getNomClasse(), valeur);
                        continue;
                    }
                    if (listeChamps[co].getTypeJava().equals("int")) {
                        CGenUtil.setValChamp(o, listeChamps[co].getNomClasse(), new Integer((int)c.getNumericCellValue()));
                        continue;
                    }
                    if (listeChamps[co].getTypeJava().equals("double")) {
                        CGenUtil.setValChamp(o, listeChamps[co].getNomClasse(), new Double(c.getNumericCellValue()));
                        continue;
                    }
                    if (!listeChamps[co].getTypeJava().equals("java.sql.Date")) continue;
                    CGenUtil.setValChamp(o, listeChamps[co].getNomClasse(), new Date(c.getDateCellValue().getTime()));
                }
                o.insertToTable();
                row = sheet.getRow(++indice);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur lors de l'import du fichier Excel");
        }
    }

    public static void importerAvecId(String fileUrl, ClassMAPTable o, String sheetAt) throws Exception {
        try {
            Connection conn = new UtilDB().GetConn();
            workbook = WorkbookFactory.create((File)new File(fileUrl));
            sheet = workbook.getSheet(sheetAt);
            int indice = 1;
            Row row = sheet.getRow(indice);
            while (row != null) {
                Champ[] listeChamps;
                int cellNumber = row.getPhysicalNumberOfCells();
                if (cellNumber != (listeChamps = ListeColonneTable.getFromListe(o, null)).length - 1) {
                    throw new Exception("erreur");
                }
                for (int co = 0; co < cellNumber; ++co) {
                    Cell c = row.getCell(co);
                    if (listeChamps[co + 1].getTypeJava().equals("java.lang.String")) {
                        String valeur = "";
                        try {
                            valeur = c.getStringCellValue();
                        }
                        catch (Exception ex) {
                            try {
                                valeur = String.valueOf((int)c.getNumericCellValue());
                            }
                            catch (Exception e) {
                                valeur = Utilitaire.format(c.getDateCellValue());
                            }
                        }
                        CGenUtil.setValChamp(o, listeChamps[co + 1].getNomClasse(), valeur);
                        continue;
                    }
                    if (listeChamps[co + 1].getTypeJava().equals("int")) {
                        CGenUtil.setValChamp(o, listeChamps[co + 1].getNomClasse(), new Integer((int)c.getNumericCellValue()));
                        continue;
                    }
                    if (listeChamps[co + 1].getTypeJava().equals("double")) {
                        CGenUtil.setValChamp(o, listeChamps[co + 1].getNomClasse(), new Double(c.getNumericCellValue()));
                        continue;
                    }
                    if (!listeChamps[co + 1].getTypeJava().equals("java.sql.Date")) continue;
                    CGenUtil.setValChamp(o, listeChamps[co + 1].getNomClasse(), new Date(c.getDateCellValue().getTime()));
                }
                o.construirePK(conn);
                o.insertToTable();
                row = sheet.getRow(++indice);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur lors de l'import du fichier Excel");
        }
    }

    public static void importerTous(String fileUrl, ClassMAPTable o, String sheetAt) throws Exception {
        try {
            workbook = WorkbookFactory.create((File)new File(fileUrl));
            sheet = workbook.getSheet(sheetAt);
            int indice = 1;
            Row row = sheet.getRow(indice);
            while (row != null) {
                Champ[] listeChamps;
                int cellNumber = row.getPhysicalNumberOfCells();
                if (cellNumber != (listeChamps = ListeColonneTable.getFromListe(o, null)).length) {
                    throw new Exception("erreur");
                }
                for (int co = 0; co < cellNumber; ++co) {
                    Cell c = row.getCell(co);
                    if (listeChamps[co].getTypeJava().equals("java.lang.String")) {
                        String valeur = "";
                        try {
                            valeur = c.getStringCellValue();
                        }
                        catch (Exception ex) {
                            try {
                                valeur = String.valueOf((int)c.getNumericCellValue());
                            }
                            catch (Exception e) {
                                valeur = Utilitaire.format(c.getDateCellValue());
                            }
                        }
                        CGenUtil.setValChamp(o, listeChamps[co].getNomClasse(), valeur);
                        continue;
                    }
                    if (listeChamps[co].getTypeJava().equals("int")) {
                        CGenUtil.setValChamp(o, listeChamps[co].getNomClasse(), new Integer((int)c.getNumericCellValue()));
                        continue;
                    }
                    if (listeChamps[co].getTypeJava().equals("double")) {
                        CGenUtil.setValChamp(o, listeChamps[co].getNomClasse(), new Double(c.getNumericCellValue()));
                        continue;
                    }
                    if (!listeChamps[co].getTypeJava().equals("java.sql.Date")) continue;
                    CGenUtil.setValChamp(o, listeChamps[co].getNomClasse(), new Date(c.getDateCellValue().getTime()));
                }
                o.insertToTable();
                row = sheet.getRow(++indice);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erreur lors de l'import du fichier Excel");
        }
    }

    private static boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (Cell cell : row) {
            String value;
            if (cell == null || (value = new DataFormatter().formatCellValue(cell).trim()).isEmpty()) continue;
            return false;
        }
        return true;
    }

    public static ClassMAPTable[] toObjectFlexible(String fileUrl, ClassMAPTable o) throws Exception {
        ArrayList<ClassMAPTable> ob = new ArrayList<ClassMAPTable>();
        Field[] fields = o.getFieldList();
        Workbook workbook = WorkbookFactory.create((File)new File(fileUrl));
        Sheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);
        HashMap<String, Integer> columnIndexByFieldName = new HashMap<String, Integer>();
        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); ++i) {
            Cell cell = headerRow.getCell(i);
            if (cell == null) continue;
            String header = cell.getStringCellValue().trim();
            columnIndexByFieldName.put(header.toLowerCase(), i);
        }
        DataFormatter formatter = new DataFormatter();
        NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);
        for (int rowIndex = 1; rowIndex < sheet.getLastRowNum(); ++rowIndex) {
            Row row = sheet.getRow(rowIndex);
            if (XlsImport.isRowEmpty(row)) continue;
            ClassMAPTable obj = (ClassMAPTable)o.getClass().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            for (Field field : fields) {
                Cell cell;
                String fieldName = field.getName();
                Integer colIndex = (Integer)columnIndexByFieldName.get(fieldName.toLowerCase());
                if (colIndex == null || (cell = row.getCell(colIndex.intValue())) == null) continue;
                try {
                    String cleaned;
                    DataFormatter formatterr = new DataFormatter();
                    String stringValue = formatterr.formatCellValue(cell).trim();
                    Object finalValue = null;
                    if (field.getType().equals(String.class)) {
                        finalValue = stringValue;
                    } else if (field.getType().equals(Integer.TYPE) || field.getType().equals(Integer.class)) {
                        cleaned = stringValue.replace(",", ".").replaceAll("[^0-9.-]", "").replace("-", "");
                        finalValue = cleaned.isEmpty() ? null : Integer.valueOf(Integer.parseInt(cleaned.split("\\.")[0]));
                    } else if (field.getType().equals(Double.TYPE) || field.getType().equals(Double.class)) {
                        cleaned = stringValue.replace(",", ".").replaceAll("[^0-9.-]", "").replace("-", "");
                        finalValue = cleaned.isEmpty() ? null : Double.valueOf(Double.parseDouble(cleaned));
                    } else if (field.getType().equals(Date.class)) {
                        if (!stringValue.isEmpty()) {
                            Date daty = Date.valueOf(stringValue);
                            finalValue = daty;
                        }
                    } else {
                        finalValue = stringValue;
                    }
                    if (finalValue == null) continue;
                    CGenUtil.setValChamp(obj, field, finalValue);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("Error processing field: " + fieldName + " - " + e.getMessage());
                }
            }
            ob.add(obj);
        }
        return ob.toArray(new ClassMAPTable[0]);
    }

    public static ClassMAPTable[] toObject(String fileUrl, ClassMAPTable o) throws Exception {
        ClassMAPTable[] rst = null;
        ArrayList ob = new ArrayList();
        Class<?> c = Class.forName(o.getClassName());
        Field[] champs = c.getDeclaredFields();
        workbook = WorkbookFactory.create((File)new File(fileUrl));
        sheet = workbook.getSheetAt(0);
        int indice = 1;
        Row row = sheet.getRow(indice);
        rst = new ClassMAPTable[sheet.getPhysicalNumberOfRows() - 1];
        int ind = 0;
        while (row != null) {
            Object obj = c.newInstance();
            int cellNumber = row.getPhysicalNumberOfCells();
            Object value = null;
            if (cellNumber != champs.length) {
                throw new Exception("erreur import, colonnes non synchronis\u00ef\u00bf\u00bds");
            }
            for (int co = 0; co < champs.length; ++co) {
                Method method = obj.getClass().getMethod("set" + Utilitaire.convertDebutMajuscule(champs[co].getName()), champs[co].getType());
                if (champs[co].getType().getName().contains("String")) {
                    try {
                        method.invoke(obj, row.getCell(co).getStringCellValue());
                    }
                    catch (Exception e) {
                        try {
                            method.invoke(obj, String.valueOf(row.getCell(co).getNumericCellValue()));
                        }
                        catch (Exception exp) {
                            try {
                                method.invoke(obj, String.valueOf((int)row.getCell(co).getNumericCellValue()));
                            }
                            catch (Exception excep) {
                                method.invoke(obj, String.valueOf(row.getCell(co).getDateCellValue()));
                            }
                        }
                    }
                    continue;
                }
                if (champs[co].getType().getName().contains("int")) {
                    method.invoke(obj, row.getCell(co).getNumericCellValue());
                    continue;
                }
                if (champs[co].getType().getName().contains("double")) {
                    method.invoke(obj, row.getCell(co).getNumericCellValue());
                    continue;
                }
                if (!champs[co].getType().getName().contains("Date")) continue;
                method.invoke(obj, new Date(row.getCell(co).getDateCellValue().getTime()));
            }
            ob.add(obj);
            ++ind;
            row = sheet.getRow(++indice);
        }
        rst = (ClassMAPTable[])ob.toArray();
        return rst;
    }

    public static Object[] toObject(InputStream is, ClassMAPTable o) throws Exception {
        ClassMAPTable[] rst = null;
        ArrayList ob = new ArrayList();
        Class<?> c = Class.forName(o.getClassName());
        Field[] champs = c.getDeclaredFields();
        workbook = WorkbookFactory.create((InputStream)is);
        sheet = workbook.getSheetAt(0);
        int indice = 0;
        Row row = sheet.getRow(indice);
        rst = new ClassMAPTable[sheet.getPhysicalNumberOfRows() - 1];
        int ind = 0;
        while (row != null) {
            Object obj = c.newInstance();
            int cellNumber = row.getPhysicalNumberOfCells();
            Object value = null;
            System.out.println("CELL : " + cellNumber + " et CHAMPS : " + champs.length);
            for (int co = 0; co < cellNumber; ++co) {
                Method method = obj.getClass().getMethod("set" + Utilitaire.convertDebutMajuscule(champs[co].getName()), champs[co].getType());
                System.out.println("value : " + row.getCell(co));
                if (champs[co].getType().getName().contains("String")) {
                    try {
                        method.invoke(obj, row.getCell(co).getStringCellValue());
                    }
                    catch (Exception e) {
                        try {
                            String stringToPut = String.valueOf(row.getCell(co).getNumericCellValue());
                            if (stringToPut.split(".").length > 1 && Double.valueOf(stringToPut.split(".")[1]) > 0.0) {
                                method.invoke(obj, String.valueOf(row.getCell(co).getNumericCellValue()));
                                continue;
                            }
                            method.invoke(obj, String.valueOf((int)row.getCell(co).getNumericCellValue()));
                        }
                        catch (Exception exp) {
                            try {
                                method.invoke(obj, String.valueOf((int)row.getCell(co).getNumericCellValue()));
                            }
                            catch (Exception excep) {
                                method.invoke(obj, String.valueOf(row.getCell(co).getDateCellValue()));
                            }
                        }
                    }
                    continue;
                }
                if (champs[co].getType().getName().contains("int")) {
                    method.invoke(obj, row.getCell(co).getNumericCellValue());
                    continue;
                }
                if (champs[co].getType().getName().contains("double")) {
                    try {
                        if (row.getCell(co).getStringCellValue() == null || row.getCell(co).getStringCellValue().equals("") || !row.getCell(co).getStringCellValue().contains(".")) continue;
                        String valeur = row.getCell(co).getStringCellValue();
                        if ("".equals(valeur)) {
                            valeur = "0.0";
                        }
                        System.out.println("valeur = " + valeur);
                        method.invoke(obj, Double.valueOf(valeur));
                    }
                    catch (Exception e) {
                        method.invoke(obj, row.getCell(co).getNumericCellValue());
                    }
                    continue;
                }
                if (!champs[co].getType().getName().contains("Date")) continue;
                method.invoke(obj, new Date(row.getCell(co).getDateCellValue().getTime()));
            }
            ob.add(obj);
            ++ind;
            row = sheet.getRow(++indice);
        }
        return ob.toArray();
    }

    public static Object[] toObject(InputStream is, ClassMAPTable o, int ligneDebut) throws Exception {
        ClassMAPTable[] rst = null;
        ArrayList ob = new ArrayList();
        Class<?> c = Class.forName(o.getClassName());
        Field[] champs = c.getDeclaredFields();
        workbook = WorkbookFactory.create((InputStream)is);
        sheet = workbook.getSheetAt(0);
        int indice = 0;
        Row row = sheet.getRow(indice);
        rst = new ClassMAPTable[sheet.getPhysicalNumberOfRows() - 1];
        int ind = 0;
        while (row != null) {
            Object obj = c.newInstance();
            int cellNumber = row.getPhysicalNumberOfCells();
            Object value = null;
            if (cellNumber != champs.length) {
                throw new Exception("erreur import, colonnes non synchronis\u00ef\u00bf\u00bds");
            }
            for (int co = 0; co < champs.length; ++co) {
                Method method = obj.getClass().getMethod("set" + Utilitaire.convertDebutMajuscule(champs[co].getName()), champs[co].getType());
                if (champs[co].getType().getName().contains("String")) {
                    try {
                        method.invoke(obj, row.getCell(co).getStringCellValue());
                    }
                    catch (Exception e) {
                        try {
                            method.invoke(obj, String.valueOf((int)row.getCell(co).getNumericCellValue()));
                        }
                        catch (Exception exp) {
                            method.invoke(obj, String.valueOf(row.getCell(co).getDateCellValue()));
                        }
                    }
                    continue;
                }
                if (champs[co].getType().getName().contains("nteger")) {
                    method.invoke(obj, row.getCell(co).getNumericCellValue());
                    continue;
                }
                if (champs[co].getType().getName().contains("double")) {
                    method.invoke(obj, row.getCell(co).getNumericCellValue());
                    continue;
                }
                if (!champs[co].getType().getName().contains("Date")) continue;
                method.invoke(obj, new Date(row.getCell(co).getDateCellValue().getTime()));
            }
            ob.add(obj);
            ++ind;
            row = sheet.getRow(++indice);
        }
        return ob.toArray();
    }

    public static ClassEtat[] importClassEtatUser(InputStream is, ClassEtat o) throws Exception {
        ClassEtat[] rst = null;
        UserEJBBean u = new UserEJBBean();
        String user = u.getUser().getTuppleID();
        int etat = 1;
        ArrayList<ClassEtat> ob = new ArrayList<ClassEtat>();
        Class<?> c = Class.forName(o.getClassName());
        Field[] champs = c.getDeclaredFields();
        workbook = WorkbookFactory.create((InputStream)is);
        sheet = workbook.getSheetAt(0);
        int indice = 0;
        Row row = sheet.getRow(indice);
        rst = new ClassEtat[sheet.getPhysicalNumberOfRows() - 1];
        int ind = 0;
        while (row != null) {
            Object obj = c.newInstance();
            int cellNumber = row.getPhysicalNumberOfCells();
            Object value = null;
            if (cellNumber != champs.length || cellNumber + 2 != champs.length) {
                throw new Exception("erreur import, colonnes non synchronis\u00ef\u00bf\u00bds");
            }
            for (int co = 0; co < champs.length; ++co) {
                if (champs[co].getName().compareToIgnoreCase("etat") == 0 || champs[co].getName().compareToIgnoreCase("iduser") == 0) continue;
                Method method = obj.getClass().getMethod("set" + Utilitaire.convertDebutMajuscule(champs[co].getName()), champs[co].getType());
                if (champs[co].getType().getName().contains("String")) {
                    try {
                        method.invoke(obj, row.getCell(co).getStringCellValue());
                    }
                    catch (Exception e) {
                        try {
                            method.invoke(obj, String.valueOf((int)row.getCell(co).getNumericCellValue()));
                        }
                        catch (Exception exp) {
                            method.invoke(obj, String.valueOf(row.getCell(co).getDateCellValue()));
                        }
                    }
                    continue;
                }
                if (champs[co].getType().getName().contains("Integer")) {
                    method.invoke(obj, row.getCell(co).getNumericCellValue());
                    continue;
                }
                if (champs[co].getType().getName().contains("Double")) {
                    method.invoke(obj, row.getCell(co).getNumericCellValue());
                    continue;
                }
                if (!champs[co].getType().getName().contains("Date")) continue;
                method.invoke(obj, new Date(row.getCell(co).getDateCellValue().getTime()));
            }
            ClassEtat cEtat = (ClassEtat)obj;
            cEtat.setEtat(etat);
            cEtat.setIduser(user);
            ob.add(cEtat);
            ++ind;
            row = sheet.getRow(++indice);
        }
        return rst;
    }

    public static Object[] toObjectChamp(InputStream is, ClassMAPTable o) throws Exception {
        ClassMAPTable[] rst = null;
        ArrayList ob = new ArrayList();
        Class<?> c = Class.forName(o.getClassName());
        workbook = WorkbookFactory.create((InputStream)is);
        sheet = workbook.getSheetAt(0);
        int indice = 1;
        Row row = sheet.getRow(indice);
        rst = new ClassMAPTable[sheet.getPhysicalNumberOfRows() - 1];
        int ind = 0;
        Row tmp = sheet.getRow(0);
        int dim = tmp.getPhysicalNumberOfCells();
        String[] lsName = new String[dim];
        Field[] champs = new Field[dim];
        for (int i = 0; i < dim; ++i) {
            System.out.println("Champ ---->" + tmp.getCell(i).getStringCellValue());
            lsName[i] = tmp.getCell(i).getStringCellValue() + "";
            champs[i] = c.getDeclaredField(tmp.getCell(i).getStringCellValue());
        }
        while (row != null) {
            System.out.println("indice ------>" + indice);
            Object obj = c.newInstance();
            Object value = null;
            for (int co = 0; co < dim; ++co) {
                Method method = obj.getClass().getMethod("set" + Utilitaire.convertDebutMajuscule(champs[co].getName()), champs[co].getType());
                if (champs[co].getType().getName().contains("String")) {
                    try {
                        method.invoke(obj, row.getCell(co).getStringCellValue());
                    }
                    catch (Exception e) {
                        try {
                            method.invoke(obj, String.valueOf((int)row.getCell(co).getNumericCellValue()));
                        }
                        catch (Exception exp) {
                            method.invoke(obj, String.valueOf(row.getCell(co).getDateCellValue()));
                        }
                    }
                    continue;
                }
                if (champs[co].getType().getName().contains("nteger")) {
                    method.invoke(obj, row.getCell(co).getNumericCellValue());
                    continue;
                }
                if (champs[co].getType().getName().contains("double")) {
                    method.invoke(obj, row.getCell(co).getNumericCellValue());
                    continue;
                }
                if (!champs[co].getType().getName().contains("Date")) continue;
                method.invoke(obj, new Date(row.getCell(co).getDateCellValue().getTime()));
            }
            ob.add(obj);
            ++ind;
            row = sheet.getRow(++indice);
        }
        return ob.toArray();
    }

    public static Object[] toObjectChampAnnonce(InputStream is, ClassMAPTable o) throws Exception {
        ClassMAPTable[] rst = null;
        ArrayList ob = new ArrayList();
        Class<?> c = Class.forName(o.getClassName());
        workbook = WorkbookFactory.create((InputStream)is);
        sheet = workbook.getSheetAt(0);
        int indice = 1;
        Row row = sheet.getRow(indice);
        rst = new ClassMAPTable[sheet.getPhysicalNumberOfRows() - 1];
        int ind = 0;
        Row tmp = sheet.getRow(0);
        int dim = tmp.getPhysicalNumberOfCells();
        String[] lsName = new String[dim];
        Field[] champs = new Field[dim];
        for (int i = 0; i < dim; ++i) {
            lsName[i] = tmp.getCell(i).getStringCellValue() + "";
            champs[i] = c.getDeclaredField(tmp.getCell(i).getStringCellValue());
        }
        while (row != null) {
            Object obj = c.newInstance();
            Object value = null;
            for (int co = 0; co < dim; ++co) {
                Method method = obj.getClass().getMethod("set" + Utilitaire.convertDebutMajuscule(champs[co].getName()), champs[co].getType());
                if (row.getCell(co) == null || row.getCell(co).getCellType() == 3) continue;
                if (champs[co].getType().getName().contains("String")) {
                    try {
                        if (!row.getCell(co).getStringCellValue().isEmpty()) {
                            method.invoke(obj, row.getCell(co).getStringCellValue());
                            continue;
                        }
                        System.out.println(" stringgg : " + champs[co].getName());
                    }
                    catch (Exception e) {
                        try {
                            method.invoke(obj, String.valueOf((int)row.getCell(co).getNumericCellValue()));
                        }
                        catch (Exception exp) {
                            method.invoke(obj, String.valueOf(row.getCell(co).getDateCellValue()));
                        }
                    }
                    continue;
                }
                if (champs[co].getType().getName().contains("nt")) {
                    int val = 0;
                    val = row.getCell(co).getCellType() == 0 ? (int)row.getCell(co).getNumericCellValue() : Utilitaire.stringToInt(row.getCell(co).getStringCellValue());
                    method.invoke(obj, val);
                    continue;
                }
                if (champs[co].getType().getName().contains("ouble")) {
                    double val = 0.0;
                    val = row.getCell(co).getCellType() == 0 ? row.getCell(co).getNumericCellValue() : Utilitaire.stringToDouble(row.getCell(co).getStringCellValue());
                    method.invoke(obj, val);
                    continue;
                }
                if (!champs[co].getType().getName().contains("ate")) continue;
                method.invoke(obj, Utilitaire.stringDate(row.getCell(co).getStringCellValue()));
            }
            ob.add(obj);
            ++ind;
            row = sheet.getRow(++indice);
        }
        return ob.toArray();
    }

    public static Object[] toObject2(InputStream is, ClassMAPTable o) throws Exception {
        ClassMAPTable[] rst = null;
        ArrayList ob = new ArrayList();
        Class<?> c = Class.forName(o.getClassName());
        Field[] champs = c.getDeclaredFields();
        workbook = WorkbookFactory.create((InputStream)is);
        sheet = workbook.getSheetAt(0);
        int indice = 0;
        Row row = sheet.getRow(indice);
        rst = new ClassMAPTable[sheet.getPhysicalNumberOfRows() - 1];
        int ind = 1;
        row = sheet.getRow(++indice);
        while (row != null) {
            int co;
            Object obj = c.newInstance();
            int cellNumber = row.getPhysicalNumberOfCells();
            Object value = null;
            int nbRowInvalid = 0;
            for (co = 0; co < cellNumber; ++co) {
                if (row.getCell(co) != null && row.getCell(co).getCellType() != 3 && row.getCell(co).toString().trim().compareTo("") != 0) continue;
                ++nbRowInvalid;
            }
            if (nbRowInvalid >= cellNumber) break;
            for (co = 0; co < champs.length; ++co) {
                String valeur;
                System.out.println("------------------" + co);
                Method method = obj.getClass().getMethod("set" + Utilitaire.convertDebutMajuscule(champs[co].getName()), champs[co].getType());
                if (champs[co].getType().getName().contains("String")) {
                    try {
                        Object[] objectArray = new Object[1];
                        objectArray[0] = row.getCell(co, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                        method.invoke(obj, objectArray);
                    }
                    catch (Exception e) {
                        try {
                            String stringToPut = String.valueOf(row.getCell(co).getNumericCellValue());
                            if (stringToPut.split(".").length > 1 && Double.valueOf(stringToPut.split(".")[1]) > 0.0) {
                                method.invoke(obj, String.valueOf(row.getCell(co).getNumericCellValue()));
                                continue;
                            }
                            method.invoke(obj, String.valueOf((int)row.getCell(co).getNumericCellValue()));
                        }
                        catch (Exception exp) {
                            try {
                                method.invoke(obj, String.valueOf((int)row.getCell(co).getNumericCellValue()));
                            }
                            catch (Exception excep) {
                                method.invoke(obj, String.valueOf(row.getCell(co).getDateCellValue()));
                            }
                        }
                    }
                    continue;
                }
                if (champs[co].getType().getName().contains("int")) {
                    valeur = row.getCell(co).toString();
                    method.invoke(obj, (int)Double.parseDouble(valeur));
                    continue;
                }
                if (champs[co].getType().getName().contains("double")) {
                    try {
                        if (row.getCell(co).getStringCellValue() == null || row.getCell(co).getStringCellValue().equals("") || !row.getCell(co).getStringCellValue().contains(".")) continue;
                        valeur = row.getCell(co).getStringCellValue();
                        if ("".equals(valeur)) {
                            valeur = "0.0";
                        }
                        System.out.println("valeur = " + valeur);
                        method.invoke(obj, Double.valueOf(valeur));
                    }
                    catch (Exception e) {
                        method.invoke(obj, row.getCell(co).getNumericCellValue());
                    }
                    continue;
                }
                if (!champs[co].getType().getName().contains("Date")) continue;
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                if (row.getCell(co).getCellType() == 0) {
                    format = new SimpleDateFormat("MM/dd/yy");
                }
                DataFormatter formatter = new DataFormatter();
                String dateString = formatter.formatCellValue(row.getCell(co));
                java.util.Date parsed = format.parse(dateString);
                Date sql = new Date(parsed.getTime());
                method.invoke(obj, sql);
            }
            ob.add(obj);
            ++ind;
            row = sheet.getRow(++indice);
        }
        return ob.toArray();
    }

    public static Object[] genToObject(InputStream is, ClassMAPTable o) throws Exception {
        ArrayList ob = new ArrayList();
        Class<?> c = Class.forName(o.getClassName());
        Field[] champs = c.getDeclaredFields();
        workbook = WorkbookFactory.create((InputStream)is);
        sheet = workbook.getSheetAt(0);
        int indice = 1;
        Row row = sheet.getRow(indice);
        int ind = 0;
        while (row != null) {
            Object obj = c.newInstance();
            int cellNumber = row.getPhysicalNumberOfCells();
            Object value = null;
            if (cellNumber != champs.length) {
                if (ind == 0) {
                    throw new Exception("erreur import, colonnes non synchronises");
                }
                ++ind;
                row = sheet.getRow(++indice);
                continue;
            }
            for (int co = 0; co < champs.length; ++co) {
                Method method = obj.getClass().getMethod("set" + Utilitaire.convertDebutMajuscule(champs[co].getName()), champs[co].getType());
                if (champs[co].getType().getSimpleName().contains("String")) {
                    method.invoke(obj, row.getCell(co).getStringCellValue());
                    continue;
                }
                if (champs[co].getType().getSimpleName().compareToIgnoreCase("int") == 0) {
                    method.invoke(obj, (int)row.getCell(co).getNumericCellValue());
                    continue;
                }
                if (champs[co].getType().getSimpleName().contains("double")) {
                    method.invoke(obj, row.getCell(co).getNumericCellValue());
                    continue;
                }
                if (!champs[co].getType().getSimpleName().contains("Date")) continue;
                method.invoke(obj, new Date(row.getCell(co).getDateCellValue().getTime()));
            }
            ob.add(obj);
            ++ind;
            row = sheet.getRow(++indice);
        }
        System.out.println("indice = " + indice);
        return ob.toArray();
    }
}

