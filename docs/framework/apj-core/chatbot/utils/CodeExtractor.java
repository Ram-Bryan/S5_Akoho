/*
 * Decompiled with CFR 0.152.
 */
package chatbot.utils;

import chatbot.utils.AntRunner;
import java.io.File;
import java.io.FileWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utilitaire.Utilitaire;

public class CodeExtractor {
    public static void extractAndDeploy(String ant, String buildFile, String projectDir, String aiResp, String javaPath, String jspPath) throws Exception {
        Map.Entry<String, String> javaFile = CodeExtractor.extractJava(aiResp);
        if (javaFile != null) {
            CodeExtractor.saveFile(javaPath, javaFile.getKey(), javaFile.getValue());
        }
        List<Map.Entry<String, String>> jspFiles = CodeExtractor.extractJsps(aiResp, "aiGenerated_" + Utilitaire.getCurrentHeure() + "_" + Utilitaire.dateDuJour());
        for (Map.Entry<String, String> jspFile : jspFiles) {
            CodeExtractor.saveFile(jspPath, jspFile.getKey(), jspFile.getValue());
        }
        AntRunner antRunner = new AntRunner(ant);
        antRunner.runAnt(projectDir, null, buildFile);
    }

    public static void saveFile(String basePath, String fileName, String content) throws Exception {
        File file = new File(basePath, fileName);
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file);){
            writer.write(content);
        }
        System.out.println("Saved: " + file.getAbsolutePath());
    }

    public static Map.Entry<String, String> extractJava(String aiResp) {
        String javaCode = null;
        Matcher javaBlock = Pattern.compile("java\\s+([\\s\\S]*?)(?=\\n(?:jsp|sql)\\b|\\Z)", 32).matcher(aiResp);
        if (javaBlock.find()) {
            javaCode = javaBlock.group(1).trim();
        }
        if (javaCode == null) {
            return null;
        }
        String className = "Unknown";
        Matcher m = Pattern.compile("public\\s+class\\s+(\\w+)").matcher(javaCode);
        if (m.find()) {
            className = m.group(1);
        }
        return new AbstractMap.SimpleEntry<String, String>(className + ".java", javaCode);
    }

    public static List<Map.Entry<String, String>> extractJsps(String aiResp, String fallbackBaseName) {
        ArrayList<Map.Entry<String, String>> jspFiles = new ArrayList<Map.Entry<String, String>>();
        Matcher jspBlock = Pattern.compile("jsp\\s+([\\s\\S]*?)(?=\\n(?:java|sql)\\b|\\Z)", 32).matcher(aiResp);
        int counter = 1;
        while (jspBlock.find()) {
            String jspCode = jspBlock.group(1).trim();
            Matcher docMatch = Pattern.compile("Document\\s*:\\s*(\\S+)").matcher(jspCode);
            String jspFileName = docMatch.find() ? docMatch.group(1) : fallbackBaseName.toLowerCase() + "-page" + counter + ".jsp";
            jspFiles.add(new AbstractMap.SimpleEntry<String, String>(jspFileName, jspCode));
            ++counter;
        }
        return jspFiles;
    }
}

