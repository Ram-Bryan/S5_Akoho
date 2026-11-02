/*
 * Decompiled with CFR 0.152.
 */
package chatbot;

import java.sql.Connection;

public interface ClassIA {
    default public String getNomTableIA() {
        return "";
    }

    default public String getUrlSaisie() {
        return "";
    }

    default public String getUrlListe() {
        return "";
    }

    default public String getUrlAnalyse() {
        return "";
    }

    default public ClassIA getClassSaisie() {
        return null;
    }

    default public ClassIA getClassListe() {
        return null;
    }

    default public ClassIA getClassAnalyse() {
        return null;
    }

    default public String getSaisiePromptCompletion(Connection c) {
        return "";
    }

    public static Class<? extends ClassIA> getCorrespondingClass(Class<? extends ClassIA>[] classes, String nomTable) throws Exception {
        for (Class<? extends ClassIA> classIA : classes) {
            ClassIA instance = classIA.newInstance();
            if (!instance.getNomTableIA().equals(nomTable)) continue;
            return instance.getClass();
        }
        throw new Exception("Class linked to table " + nomTable + " not found");
    }

    public static ClassIA getCorrespondingClass(Class<? extends ClassIA>[] classes, String nomTable, String type) throws Exception {
        for (Class<? extends ClassIA> classIA : classes) {
            ClassIA instance = classIA.newInstance();
            if (!instance.getNomTableIA().equals(nomTable)) continue;
            switch (type) {
                case "saisie": {
                    return instance.getClassSaisie();
                }
                case "liste": 
                case "lister": {
                    return instance.getClassListe();
                }
                case "analyse": {
                    return instance.getClassAnalyse();
                }
            }
        }
        throw new Exception("Class linked to table " + nomTable + " not found");
    }
}

