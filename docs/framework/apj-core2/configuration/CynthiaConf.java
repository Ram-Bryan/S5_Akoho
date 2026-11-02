/*
 * Decompiled with CFR 0.152.
 */
package configuration;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CynthiaConf {
    public static Properties properties = new Properties();
    public static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public static Properties load() {
        try {
            if (properties.isEmpty()) {
                properties.load(classLoader.getResourceAsStream("apj.properties"));
            }
            return properties;
        }
        catch (IOException ex) {
            Logger.getLogger(CynthiaConf.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}

