/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import affichage.Champ;
import affichage.Formulaire;
import affichage.Liste;
import java.util.Iterator;
import java.util.List;

public class ListeChamp {
    Champ[] listeChamp;
    Formulaire formu;

    public Formulaire getFormu() {
        return this.formu;
    }

    public void setFormu(Formulaire formu) {
        this.formu = formu;
    }

    public ListeChamp(Champ[] ls) throws Exception {
        this.setListeChamp(ls);
    }

    public ListeChamp(List<Champ> ls) throws Exception {
        this.setListeChamp(ls);
    }

    public ListeChamp(Formulaire f, List<Champ> ls) throws Exception {
        this.setListeChamp(ls);
        this.setFormu(f);
    }

    public Champ[] getListeChamp() {
        return this.listeChamp;
    }

    public void setListeChamp(Champ[] listeChamp) {
        this.listeChamp = listeChamp;
    }

    public void setListeChamp(List<Champ> listeCham) {
        int i = 0;
        this.listeChamp = new Champ[listeCham.size()];
        Iterator<Champ> iterator = listeCham.iterator();
        while (iterator.hasNext()) {
            Champ c;
            this.listeChamp[i] = c = iterator.next();
            ++i;
        }
    }

    public void setVisible(boolean sv) throws Exception {
        for (Champ e : this.getListeChamp()) {
            e.setVisible(sv);
        }
    }

    public void setValeur(Object val) throws Exception {
        for (Champ e : this.getListeChamp()) {
            e.setValeur(val.toString());
        }
    }

    public void setAutre(String autre) throws Exception {
        for (Champ e : this.getListeChamp()) {
            e.setAutre(autre);
        }
    }

    public void setAutocomplete(String aff, String val, String nomT, String aW) throws Exception {
        for (Champ e : this.getListeChamp()) {
            e.setAutocomplete(aff, val, nomT, aW);
        }
    }

    public void setDeroulanteDependante(String autre, String nomColonneFiltreAutre, String event) throws Exception {
        int i = 0;
        for (Champ e : this.getListeChamp()) {
            Liste mere = (Liste)e;
            mere.setDeroulanteDependante(this.getFormu(), autre + "_" + i, nomColonneFiltreAutre, event);
            ++i;
        }
    }
}

