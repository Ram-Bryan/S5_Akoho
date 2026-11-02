/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

public enum ModePaiementEnum {
    CHEQUE("pay02", "Cheque"),
    ESPECE("pay01", "Espece");

    private final String id;
    private final String val;

    private ModePaiementEnum(String id, String val) {
        this.id = id;
        this.val = val;
    }

    public String id() {
        return this.id;
    }

    public String val() {
        return this.val;
    }
}

