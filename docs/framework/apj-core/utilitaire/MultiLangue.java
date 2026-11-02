/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MultiLangue {
    public static Map<String, String> fileToHash(FileReader fileReader) {
        HashMap<String, String> result = new HashMap<String, String>();
        try (BufferedReader br = new BufferedReader(fileReader);){
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] paires;
                for (String paire : paires = ligne.split(";;")) {
                    String[] kv = paire.split("::", 2);
                    if (kv.length != 2) continue;
                    result.put(kv[0].trim(), kv[1].trim());
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

