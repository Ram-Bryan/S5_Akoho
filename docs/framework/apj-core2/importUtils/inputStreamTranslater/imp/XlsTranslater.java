/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.usermodel.WorkbookFactory
 */
package importUtils.inputStreamTranslater.imp;

import importUtils.inputStreamTranslater.InputStreamTranslater;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XlsTranslater
implements InputStreamTranslater {
    @Override
    public List<HashMap<String, String>> translate(InputStream inputStream, String[] fields, boolean bool) throws Exception {
        Workbook book = WorkbookFactory.create((InputStream)inputStream);
        Sheet sheet = book.getSheetAt(0);
        int[] numCell = this.populate(fields);
        int beginIndex = 0;
        List<String> columnValues = this.getColumnValues(sheet.getRow(0));
        if (this.isEntete(columnValues, fields) != bool) {
            if (bool) {
                throw new Exception("Il n y a pas d ent\u00c3\u00aate");
            }
            throw new Exception("Il y a des/un ent\u00c3\u00aate(s)");
        }
        if (bool) {
            numCell = this.getCorrespondance(columnValues, fields);
            beginIndex = 1;
        }
        int totalLigne = sheet.getLastRowNum();
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for (int i = beginIndex; i <= totalLigne; ++i) {
            HashMap<String, String> hash = this.translate(sheet.getRow(i), fields, numCell);
            list.add(hash);
        }
        return list;
    }

    protected HashMap<String, String> translate(Row row, String[] fields, int[] numCell) {
        HashMap<String, String> hash = new HashMap<String, String>();
        for (int y = 0; y < fields.length; ++y) {
            if (row.getCell(numCell[y]) != null && row.getCell(numCell[y]).getCellType() != 3) {
                row.getCell(numCell[y]).setCellType(1);
                hash.put(fields[y], row.getCell(numCell[y]).getStringCellValue());
                continue;
            }
            if (row.getCell(numCell[y]) == null || row.getCell(numCell[y]).getCellType() != 3) continue;
            hash.put(fields[y], "");
        }
        return hash;
    }

    protected int[] populate(String[] fields) {
        int[] numCell = new int[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            numCell[i] = i;
        }
        return numCell;
    }

    protected boolean isEntete(List<String> columnValues, String[] fields) {
        for (String field : fields) {
            int check = columnValues.indexOf(field.toLowerCase());
            if (check == -1) continue;
            return true;
        }
        return false;
    }

    protected int[] getCorrespondance(List<String> columnValues, String[] fields) throws Exception {
        int[] numCell = new int[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            numCell[i] = columnValues.indexOf(fields[i].toLowerCase());
            if (numCell[i] >= 0) continue;
            throw new Exception("La colonne " + fields[i] + " n existe pas dans l excel");
        }
        return numCell;
    }

    private List<String> getColumnValues(Row row) {
        ArrayList<String> list = new ArrayList<String>();
        int column = 0;
        while (row != null && row.getCell(column) != null && row.getCell(column).getCellType() != 3) {
            row.getCell(column).setCellType(1);
            list.add(row.getCell(column).getStringCellValue().toLowerCase());
            ++column;
        }
        return list;
    }
}

