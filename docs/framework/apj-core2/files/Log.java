/*
 * Decompiled with CFR 0.152.
 */
package files;

public final class Log {
    int numero = 0;
    public static byte afficherTemoin = 1;

    public static final void log(String s) {
        if (afficherTemoin == 1) {
            System.out.println(" Log : ".concat(String.valueOf(String.valueOf(s))));
        }
    }

    public static void setLog(String log) {
        if (afficherTemoin != 1) {
            System.out.println(" - ".concat(String.valueOf(String.valueOf(log))));
        }
    }

    public static void afficher() {
        afficherTemoin = 1;
    }

    public static void masquer() {
        afficherTemoin = 0;
    }

    public static void logFile(String s1) {
    }
}

