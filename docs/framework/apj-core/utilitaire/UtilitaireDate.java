/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilitaireDate {
    public static int differenceEntreDeuxDate(java.sql.Date debut, java.sql.Date fin) {
        long MILLISECONDS_PER_DAY = 86400000L;
        long delta = 0L;
        delta = debut.before(fin) ? fin.getTime() - debut.getTime() : debut.getTime() - fin.getTime();
        return (int)(delta / 86400000L);
    }

    public static java.sql.Date stringToDate(String format, String daty) {
        java.sql.Date retour = null;
        try {
            if (daty == null || daty == "") {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date convertedCurrentDate = sdf.parse(daty);
            retour = new java.sql.Date(convertedCurrentDate.getTime());
            String date = sdf.format(convertedCurrentDate);
            System.out.println(date);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error parsing date");
        }
        return retour;
    }
}

