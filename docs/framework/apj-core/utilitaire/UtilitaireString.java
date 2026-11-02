/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

public class UtilitaireString {
    public static boolean testMotDansUnePhrase(String phrase, int indice, String mot) {
        char[] ph = phrase.toCharArray();
        char[] mo = mot.toCharArray();
        for (int i = 0; i < mo.length; ++i) {
            if (i + indice <= ph.length - 1 && Character.toLowerCase(mo[i]) == Character.toLowerCase(ph[indice + i])) continue;
            return false;
        }
        return true;
    }
}

