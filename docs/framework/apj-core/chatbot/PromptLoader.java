/*
 * Decompiled with CFR 0.152.
 */
package chatbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PromptLoader {
    private static final Map<String, String> cache = new HashMap<String, String>();

    public static String load(String name) {
        if (cache.containsKey(name)) {
            return cache.get(name);
        }
        try {
            String content;
            String resourcePath = "/src/resources/prompts/" + name + ".txt";
            InputStream in = PromptLoader.class.getResourceAsStream(resourcePath);
            if (in == null) {
                throw new RuntimeException("Prompt file not found in classpath: " + resourcePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));){
                content = reader.lines().collect(Collectors.joining("\n"));
            }
            cache.put(name, content);
            return content;
        }
        catch (IOException e) {
            throw new RuntimeException("Prompt file not found: " + name, e);
        }
    }

    public static String format(String name, Map<String, String> params) {
        String template = PromptLoader.load(name);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                template = template.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        return template;
    }
}

