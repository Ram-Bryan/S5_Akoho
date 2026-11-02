/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package reporting.utilitaire;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExcelGeneratorUtil {
    public static void convertHtmlToExcel(String html, String outputFilePath) throws IOException {
        Document doc = Jsoup.parse((String)html);
        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        CellStyle infoStyle = workbook.createCellStyle();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        Elements sections = doc.select("div.crcontent");
        for (Element section : sections) {
            Element mainTable;
            Object row2;
            String sheetName;
            String string = sheetName = section.selectFirst("h3") != null ? section.selectFirst("h3").text() : "SansTitre";
            if (sheetName.length() > 31) {
                sheetName = sheetName.substring(0, 31);
            }
            String originalName = sheetName;
            int counter = 1;
            while (workbook.getSheet(sheetName) != null) {
                sheetName = originalName + "_" + counter++;
            }
            Sheet sheet = workbook.createSheet(sheetName);
            int rowIndex = 0;
            Element totalTable = section.selectFirst("table.total-table");
            if (totalTable != null) {
                Elements totalRows = totalTable.select("tr");
                for (Object row2 : totalRows) {
                    Row excelRow = sheet.createRow(rowIndex++);
                    Elements cols = row2.select("td, th");
                    for (int i = 0; i < cols.size(); ++i) {
                        Cell cell = excelRow.createCell(i);
                        String value = ((Element)cols.get(i)).text();
                        cell.setCellValue(value);
                        if (row2.select("th").size() <= 0) continue;
                        cell.setCellStyle(headerStyle);
                    }
                }
                ++rowIndex;
            }
            if ((mainTable = section.selectFirst("table.crcontentfille")) == null) continue;
            Elements rows = mainTable.select("tr");
            if (rows.isEmpty() || mainTable.select("tbody tr").isEmpty()) {
                row2 = sheet.createRow(rowIndex++);
                Cell cell = row2.createCell(0);
                cell.setCellValue("Aucune donn\u00c3\u00a9e disponible");
                cell.setCellStyle(infoStyle);
                continue;
            }
            row2 = rows.iterator();
            while (row2.hasNext()) {
                Element row3 = (Element)row2.next();
                Row excelRow = sheet.createRow(rowIndex++);
                Elements cols = row3.select("th, td");
                for (int i = 0; i < cols.size(); ++i) {
                    Cell cell = excelRow.createCell(i);
                    String value = ((Element)cols.get(i)).text();
                    try {
                        double numericValue = Double.parseDouble(value.replace(",", "."));
                        cell.setCellValue(numericValue);
                    }
                    catch (NumberFormatException e) {
                        cell.setCellValue(value);
                    }
                    if (row3.select("th").size() <= 0) continue;
                    cell.setCellStyle(headerStyle);
                }
            }
            if (rows.isEmpty()) continue;
            int colCount = rows.first().select("th, td").size();
            for (int i = 0; i < colCount; ++i) {
                sheet.autoSizeColumn(i);
            }
        }
        try (FileOutputStream out = new FileOutputStream(outputFilePath);){
            workbook.write((OutputStream)out);
        }
        workbook.close();
        System.out.println("Export HTML \u00e2\u0086\u0092 Excel r\u00c3\u00a9ussi : " + outputFilePath);
    }
}

