/*
 * Decompiled with CFR 0.152.
 */
package bean;

import bean.LibelleAffichage;
import java.lang.reflect.Field;

public class Champ {
    String nomColonne;
    String type;
    String typeJava;
    int size;
    int precision;
    Field nomClasse;

    public Champ(Field f) {
        this.nomClasse = f;
        this.nomColonne = f.getName();
        this.typeJava = f.getType().getName();
        this.size = 10;
        this.javaToSql();
    }

    public Champ(Field f, int precision) {
        this.nomClasse = f;
        this.nomColonne = f.getName();
        this.typeJava = f.getType().getName();
        this.size = 10;
        this.setPrecision(precision);
        this.javaToSql();
    }

    public Field getField() {
        return null;
    }

    public Champ(String nomCol, String typ, int val) {
        this.nomColonne = nomCol;
        this.type = typ;
        this.size = val;
        this.sqlToJava();
    }

    public Champ(String nomCol, String typ, int val, int precis) {
        this.nomColonne = nomCol;
        this.type = typ;
        this.size = val;
        this.setPrecision(precis);
        this.sqlToJava();
    }

    public Champ(String nomCol, String typ) {
        this.nomColonne = nomCol;
        this.type = typ;
    }

    public Field toField() throws Exception {
        return this.nomClasse;
    }

    public static Field[] toField(Champ[] lchamp) throws Exception {
        Field[] retour = new Field[lchamp.length];
        for (int i = 0; i < lchamp.length; ++i) {
            retour[i] = lchamp[i].toField();
        }
        return retour;
    }

    public String getNomColonne() {
        return this.nomColonne;
    }

    public String getTypeColonne() {
        return this.type;
    }

    public String getTypeJava() {
        return this.typeJava;
    }

    public int getSize() {
        return this.size;
    }

    public void sqlToJava() {
        if (this.type.compareToIgnoreCase("character varying") == 0 || this.type.compareToIgnoreCase("char") == 0 || this.type.compareToIgnoreCase("varchar2") == 0) {
            this.typeJava = "java.lang.String";
        }
        if (this.type.compareToIgnoreCase("geometry") == 0) {
            this.typeJava = "org.postgis.PGgeometry";
        }
        if (this.type.compareToIgnoreCase("Number") == 0 && this.precision == 0 && this.size < 10) {
            this.typeJava = "int";
        }
        if (this.type.compareToIgnoreCase("Number") == 0 && this.precision >= 0 && this.size >= 10) {
            this.typeJava = "double";
        }
        if (this.type.compareToIgnoreCase("Number") == 0 && (this.precision >= 0 || this.size >= 10)) {
            this.typeJava = "double";
        }
        if (this.type.compareToIgnoreCase("Long") == 0) {
            this.typeJava = "double";
        }
        if (this.type.compareToIgnoreCase("Date") == 0) {
            this.typeJava = "java.sql.Date";
        }
        if (this.type.compareToIgnoreCase("blob") == 0) {
            this.typeJava = "java.io.InputStream";
        }
        if (this.type.contains("timestamp")) {
            this.typeJava = "java.sql.Timestamp";
        }
        if (this.type.contains("Time")) {
            this.typeJava = "java.sql.Time";
        }
        if (this.type.compareToIgnoreCase("Number") == 0 && (this.precision >= 0 || this.size >= 10)) {
            this.typeJava = "double";
        }
    }

    public void javaToSql() {
        if (this.typeJava.compareToIgnoreCase("java.lang.String") == 0 || this.typeJava.compareToIgnoreCase("char") == 0) {
            this.type = "Varchar2";
        }
        if (this.typeJava.compareToIgnoreCase("int") == 0 || this.typeJava.compareToIgnoreCase("float") == 0 || this.typeJava.compareToIgnoreCase("Double") == 0) {
            this.type = "Number";
        }
        if (this.typeJava.compareToIgnoreCase("java.sql.Date") == 0) {
            this.type = "Date";
        }
        if (this.typeJava.compareToIgnoreCase("java.sql.Time") == 0) {
            this.type = "Time";
        }
        if (this.typeJava.compareToIgnoreCase("java.io.InputStream") == 0) {
            this.type = "blob";
        }
        if (this.typeJava.compareToIgnoreCase("java.sql.Timestamp") == 0) {
            this.type = "Timestamp(6)";
        }
        if (this.typeJava.compareToIgnoreCase("org.postgis.PGgeometry") == 0) {
            this.type = "geometry";
        }
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public Field getNomClasse() {
        return this.nomClasse;
    }

    public void setNomClasse(Field nomClasse) {
        this.nomClasse = nomClasse;
    }

    public String toString() {
        return this.getNomColonne();
    }

    public String getLibelleAfficheAnnotation() {
        LibelleAffichage annotation;
        if (this.getNomClasse() != null && (annotation = this.getNomClasse().getAnnotation(LibelleAffichage.class)) != null && annotation.value() != null && !annotation.value().trim().isEmpty()) {
            return annotation.value();
        }
        return this.getNomColonne();
    }
}

