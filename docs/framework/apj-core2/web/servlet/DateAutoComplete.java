/*
 * Decompiled with CFR 0.152.
 */
package web.servlet;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAutoComplete {
    private boolean isDate = false;
    private boolean isRange = false;
    private String startDate = null;
    private String endDate = null;
    private String keywords = " and ";

    public DateAutoComplete parse(String valeurFiltre) throws Exception {
        DateAutoComplete result = new DateAutoComplete();
        if (valeurFiltre == null || valeurFiltre.trim().isEmpty()) {
            return result;
        }
        String trimValeurFiltre = valeurFiltre.trim();
        if (trimValeurFiltre.toLowerCase().contains(this.keywords)) {
            String[] dateSplit = trimValeurFiltre.split("(?i)\\s+and\\s+");
            if (dateSplit.length == 2) {
                String date1 = this.parseSingleDate(dateSplit[0].trim());
                String date2 = this.parseSingleDate(dateSplit[1].trim());
                if (date1 != null && date2 != null) {
                    result.setDate(true);
                    result.setRange(true);
                    result.setStartDate(date1);
                    result.setEndDate(date2);
                }
            }
        } else {
            String singleDate = this.parseSingleDate(trimValeurFiltre);
            if (singleDate != null) {
                result.setDate(true);
                result.setRange(false);
                result.setStartDate(singleDate);
            }
        }
        return result;
    }

    private String parseSingleDate(String dateStr) throws Exception {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        String trimmed = dateStr.trim();
        SimpleDateFormat inputFormat = null;
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (trimmed.matches("\\d{2}/\\d{2}/\\d{4}")) {
                inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            } else if (trimmed.matches("\\d{4}-\\d{2}-\\d{2}")) {
                inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            }
            if (inputFormat != null) {
                inputFormat.setLenient(false);
                Date date = inputFormat.parse(trimmed);
                return outputFormat.format(date);
            }
        }
        catch (Exception e) {
            throw new Exception("Erreur de parse pour valeur de filtre inserer: " + trimmed);
        }
        return null;
    }

    public static boolean isDateColumn(String columnName) {
        if (columnName == null) {
            return false;
        }
        String colName = columnName.toLowerCase();
        return colName.contains("date") || colName.contains("daty") || colName.equals("dt") || colName.endsWith("_dt") || colName.endsWith("_date") || colName.startsWith("date_");
    }

    public boolean isDate() {
        return this.isDate;
    }

    public void setDate(boolean date) {
        this.isDate = date;
    }

    public boolean isRange() {
        return this.isRange;
    }

    public void setRange(boolean range) {
        this.isRange = range;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}

