/*
 * Decompiled with CFR 0.152.
 */
package fichiers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Lecture {
    private static FileInputStream inputStream = null;
    private static InputStreamReader lecteur = null;
    private static BufferedReader buffer = null;

    public static ArrayList<String> lireFichier(String cheminFichier) throws Exception {
        ArrayList<String> liste = null;
        String ligne = null;
        try {
            if (cheminFichier == null || cheminFichier.compareTo("") == 0) {
                throw new Exception("Chemin invalide");
            }
            liste = new ArrayList<String>();
            inputStream = new FileInputStream(cheminFichier);
            lecteur = new InputStreamReader((InputStream)inputStream, "ISO-8859-1");
            buffer = new BufferedReader(lecteur, 8192);
            while ((ligne = buffer.readLine()) != null) {
                liste.add(ligne);
            }
            ArrayList<String> arrayList = liste;
            return arrayList;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (buffer != null) {
                buffer.close();
            }
            if (lecteur != null) {
                lecteur.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writetofile(String path, String element) {
        try (PrintWriter writer = null;){
            writer = new PrintWriter(new File(path), "UTF-8");
            writer.write(element);
        }
    }
}

