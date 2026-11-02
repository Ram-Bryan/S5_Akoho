/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package reporting.utilitaire;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.json.JSONArray;
import org.json.JSONObject;
import reporting.utilitaire.ConstanteCR;
import reporting.utilitaire.UtilitaireCR;

public class ExportJarClassesToJson {
    public String[] classNames;

    public String[] getClassNames() {
        return this.classNames;
    }

    public void setClassNames(String[] classNames) {
        this.classNames = classNames;
    }

    public List<String> exportProjectEjbClasses() {
        ArrayList<String> classes = new ArrayList<String>();
        try {
            CodeSource src = ExportJarClassesToJson.class.getProtectionDomain().getCodeSource();
            if (src == null) {
                throw new RuntimeException("Impossible de localiser le code source pour obtenir le chemin du JAR.");
            }
            URL jarUrl = src.getLocation();
            String decodedPath = URLDecoder.decode(jarUrl.getPath(), "UTF-8");
            if (!decodedPath.endsWith("teamTask-ejb.jar")) {
                decodedPath = decodedPath.replace("TeamTask.war/WEB-INF/classes/", "teamTask-ejb.jar");
            }
            JarFile jarFile = new JarFile(decodedPath);
            Enumeration<JarEntry> entries = jarFile.entries();
            ArrayList<HashMap<String, Object>> classesInfo = new ArrayList<HashMap<String, Object>>();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() || !entry.getName().endsWith(".class")) continue;
                String className = entry.getName().replace('/', '.').replace(".class", "");
                try {
                    HashMap<String, Object> classInfo = new HashMap<String, Object>();
                    Class<?> cls = Class.forName(className);
                    classInfo.put("className", cls.getName());
                    classInfo.put("package", cls.getPackage() != null ? cls.getPackage().getName() : "");
                    classes.add((String)classInfo.get("className"));
                    this.mapAttributes(classInfo, cls);
                    classesInfo.add(classInfo);
                }
                catch (Throwable ex) {
                    System.out.println("Impossible de charger : " + className + " --> " + ex.getMessage());
                }
            }
            jarFile.close();
            ObjectMapper mapper = new ObjectMapper();
            String dir = ConstanteCR.BASE_DIR + "/" + "TeamTask.war" + "/Json/";
            UtilitaireCR.createDirectory(dir);
            dir = dir + "all_classes.json";
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(dir), classesInfo);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    public void mapAttributes(Map<String, Object> classInfo, Class<?> cls) throws Exception {
        ArrayList<String> attributes = new ArrayList<String>();
        for (Class<?> currentClass = cls; currentClass != null && currentClass != Object.class; currentClass = currentClass.getSuperclass()) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (attributes.contains(field.getName())) continue;
                attributes.add(field.getName());
            }
            try {
                URL url;
                CodeSource parentSrc = currentClass.getSuperclass().getProtectionDomain().getCodeSource();
                if (parentSrc == null || (url = parentSrc.getLocation()) == null) break;
                String location = url.toString();
                String[] licationSplitted = location.split("/");
                String jarName = licationSplitted[licationSplitted.length - 1];
                if (jarName.equals("teamTask-ejb.jar")) continue;
            }
            catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
            }
            break;
        }
        classInfo.put("attributes", attributes);
    }

    public String castJsonToString() throws Exception {
        String dir = ConstanteCR.BASE_DIR + "/" + "TeamTask.war" + "/Json/";
        String jsonPath = dir = dir + "all_classes.json";
        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonPath, new String[0])), StandardCharsets.UTF_8);
        JSONArray jsonArray = new JSONArray(jsonContent);
        ArrayList<String> classNames = new ArrayList<String>();
        StringBuilder jsMap = new StringBuilder();
        jsMap.append("classesMap = {\n");
        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String className = obj.getString("className");
            JSONArray attrs = obj.getJSONArray("attributes");
            classNames.add(className);
            jsMap.append("\"").append(className).append("\": [");
            for (int j = 0; j < attrs.length(); ++j) {
                jsMap.append("\"").append(attrs.getString(j).replace("\"", "\\\"")).append("\"");
                if (j >= attrs.length() - 1) continue;
                jsMap.append(", ");
            }
            jsMap.append("]");
            if (i >= jsonArray.length() - 1) continue;
            jsMap.append(",\n");
        }
        jsMap.append("\n};");
        String[] className = classNames.toArray(new String[0]);
        this.setClassNames(className);
        return jsMap.toString();
    }
}

