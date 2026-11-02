/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import affichage.Champ;
import affichage.Formulaire;

public class ChampCompteRecherchePrecis
extends Champ {
    public ChampCompteRecherchePrecis() {
        this.setRecherchePrecis(true);
    }

    public ChampCompteRecherchePrecis(String nomChamp, Formulaire f) {
        super(nomChamp);
        Champ c;
        this.setFormulaire(f);
        if (f != null && (c = f.getChamp(nomChamp)) != null) {
            c.setRecherchePrecis(true);
        }
    }
}

