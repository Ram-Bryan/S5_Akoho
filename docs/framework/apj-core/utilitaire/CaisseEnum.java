/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

public enum CaisseEnum {
    CAISSEITU("CE000541", "Caisse ITU"),
    EFFETARECEVOIR("CE000112", "EffetARecevoir");

    private final String idcaisse;
    private final String desccaisse;

    private CaisseEnum(String idcaisse, String desccaisse) {
        this.idcaisse = idcaisse;
        this.desccaisse = desccaisse;
    }

    public String idcaisse() {
        return this.idcaisse;
    }

    public String desccaisse() {
        return this.desccaisse;
    }
}

