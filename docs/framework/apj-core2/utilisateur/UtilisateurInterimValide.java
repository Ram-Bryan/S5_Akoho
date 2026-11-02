/*
 * Decompiled with CFR 0.152.
 */
package utilisateur;

import utilisateur.UtilisateurInterim;

public class UtilisateurInterimValide
extends UtilisateurInterim {
    private int rang;

    public UtilisateurInterimValide() {
        super.setNomTable("UTILISATEUR_INTERIM_VALIDE");
    }

    public int getRang() {
        return this.rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }
}

