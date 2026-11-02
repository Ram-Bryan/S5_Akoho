/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

public enum ExecCREnum {
    BATCH_PATH("");

    private final String path;

    private ExecCREnum(String path) {
        this.path = path;
    }

    public String path() {
        return this.path;
    }
}

