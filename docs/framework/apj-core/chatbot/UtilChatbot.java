/*
 * Decompiled with CFR 0.152.
 */
package chatbot;

import java.lang.reflect.Field;

public class UtilChatbot {
    public static Field getDeclaredFieldIgnoreCase(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.getName().equalsIgnoreCase(fieldName)) continue;
                field.setAccessible(true);
                return field;
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public static int estimateTokens(String text) {
        if (text == null) {
            return 0;
        }
        return (int)Math.ceil((double)text.length() / 4.0);
    }

    public static String escapeQuotes(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("\"", "\\\"");
    }
}

