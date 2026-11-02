/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import affichage.Champ;
import affichage.Formulaire;

public class ChampCompte
extends Champ {
    public ChampCompte() {
        this.setPageAppelComplete(this.getAc_classeMapping(), this.getAc_valeur(), this.getAc_nomTable());
    }

    public ChampCompte(String nomChamp, Formulaire f) throws Exception {
        super(nomChamp);
        this.setFormulaire(f);
        if (f.isEstFille()) {
            Champ.setPageAppelCompletePropre(f.getChampFille(nomChamp), "mg.cnaps.compta.ComptaCompte", nomChamp, "compta_compte", "", "", "");
        } else {
            this.setPageAppelCompletePropre("mg.cnaps.compta.ComptaCompte", nomChamp, "compta_compte", "", "", "");
        }
    }

    public ChampCompte(String nomChamp, Formulaire f, String aWhere) throws Exception {
        super(nomChamp);
        this.setFormulaire(f);
        if (f.isEstFille()) {
            Champ.setPageAppelCompletePropre(f.getChampFille(nomChamp), "mg.cnaps.compta.ComptaCompte", nomChamp, "compta_compte", "", "", aWhere);
        } else {
            this.setPageAppelCompletePropre("mg.cnaps.compta.ComptaCompte", nomChamp, "compta_compte", "", "", aWhere);
        }
    }

    public ChampCompte(String nomChamp, String ac_classeMapping, String ac_valeur, String ac_nomTable) {
        super(nomChamp);
        this.setAc_classeMapping(ac_classeMapping);
        this.setAc_valeur(ac_valeur);
        this.setAc_nomTable(ac_nomTable);
        this.setPageAppelCompletePropre(this.getAc_classeMapping(), this.getAc_valeur(), this.getAc_nomTable(), "", "", "");
    }

    public ChampCompte(String nomChamp, String ac_classeMapping, String ac_valeur, String ac_nomTable, String aWhere) {
        super(nomChamp);
        this.setAc_classeMapping(ac_classeMapping);
        this.setAc_valeur(ac_valeur);
        this.setAc_nomTable(ac_nomTable);
        this.setPageAppelCompletePropre(this.getAc_classeMapping(), this.getAc_valeur(), this.getAc_nomTable(), "", "", aWhere);
    }
}

