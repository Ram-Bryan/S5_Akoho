/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

public enum TypeAPayerEnum {
    ECOLAGE("TAP1", 1610000.0),
    INSCRIPTION("TAP2", 145000.0),
    ASSURANCE("TAP3", 20000.0);

    private final String id;
    private final double montant;

    private TypeAPayerEnum(String id, double montant) {
        this.id = id;
        this.montant = montant;
    }

    public String id() {
        return this.id;
    }

    public double montant() {
        return this.montant;
    }
}

