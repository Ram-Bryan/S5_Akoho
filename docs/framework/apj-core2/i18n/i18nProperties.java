/*
 * Decompiled with CFR 0.152.
 */
package i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class i18nProperties {
    private Locale locale;
    private ResourceBundle resourceBundle;

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(String local) {
    }

    public ResourceBundle getResourceBundle() {
        return this.resourceBundle;
    }

    public void setResourceBundle(String resourceBundle) {
        ResourceBundle rB;
        this.resourceBundle = rB = ResourceBundle.getBundle(resourceBundle, this.getLocale());
    }
}

