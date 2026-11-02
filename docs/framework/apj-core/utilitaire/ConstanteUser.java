/*
 * Decompiled with CFR 0.152.
 */
package utilitaire;

public class ConstanteUser {
    private static final int rangChefDeService = 4;
    private static final int rangDG = 9;
    public static final int rangDir = 5;
    public static final int rangMedecin = 1;
    public static final String[] actionParRole = new String[]{"insert", "update", "delete", "validate"};
    public static final String[] listePacquet = new String[]{"tous", "vente", "achat", "paie", "compta", "stock"};
    public static final String[] listePacquetID = new String[]{"", "vente", "achat", "paie", "compta", "stock"};

    public static int getRangDG() {
        return 9;
    }

    public static int getRangChefDeService() {
        return 4;
    }
}

