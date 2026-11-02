/*
 * Decompiled with CFR 0.152.
 */
package affichage;

public enum DashboardGraphType {
    BAR("bar"),
    STACKED_BAR("bar"),
    LINE("line"),
    PIE("pie"),
    DOUGHNUT("doughnut"),
    UNDEFINED(""),
    CARD("card"),
    KPI("kpi");

    private final String val;

    private DashboardGraphType(String val) {
        this.val = val;
    }

    public String getVal() {
        return this.val;
    }
}

