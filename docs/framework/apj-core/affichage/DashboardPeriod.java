/*
 * Decompiled with CFR 0.152.
 */
package affichage;

public enum DashboardPeriod {
    JOUR("jour", "Par jour"),
    SEMAINE("semaine", "Par semaine"),
    MOIS("mois", "Par mois"),
    ANNEE("annee", "Par ann&eacute;e");

    private final String value;
    private final String label;

    private DashboardPeriod(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }
}

