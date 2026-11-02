/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

import java.util.Properties;

public class PropertiesLoader {
    private static PropertiesLoader propertiesLoader;
    private Properties properties;

    private PropertiesLoader() {
        try {
            this.properties = new Properties();
            this.properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("apj.properties"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PropertiesLoader getInstance() {
        if (propertiesLoader == null) {
            propertiesLoader = new PropertiesLoader();
        }
        return propertiesLoader;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}

