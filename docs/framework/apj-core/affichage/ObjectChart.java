/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import utilitaire.Utilitaire;

public class ObjectChart {
    String x;
    double y;
    String key;

    public ObjectChart(String x, String y, String key) throws ParseException {
        this.setX(x);
        this.setY(y);
        this.setKey(key);
    }

    public String getDataToString() {
        return String.format("%s;%s;%s;", Utilitaire.champNull(this.x), this.y, Utilitaire.champNull(this.key));
    }

    public ObjectChart(String x, String y) throws ParseException {
        this.setX(x);
        this.setY(y);
    }

    public ObjectChart(String x, double[] liste) {
        this.setX(x);
    }

    public ObjectChart() {
    }

    public String getX() {
        return this.x;
    }

    public void setY(String y) throws ParseException {
        if (y != null) {
            NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
            this.y = nf.parse(y).doubleValue();
        }
    }

    public void setY(double Y) {
        this.y = this.y;
    }

    public double getY() {
        return this.y;
    }

    public void setX(String x) {
        this.x = x;
    }

    public static List<ObjectChart> getListe(String[][] data, int x, int y) throws ParseException {
        int taille = 0;
        if (data != null) {
            taille = data.length;
        }
        ArrayList<ObjectChart> liste = new ArrayList<ObjectChart>(taille);
        for (int i = 0; i < taille; ++i) {
            liste.add(new ObjectChart(data[i][x], data[i][y]));
        }
        return liste;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

