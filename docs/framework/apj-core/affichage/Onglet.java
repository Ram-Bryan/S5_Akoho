/*
 * Decompiled with CFR 0.152.
 */
package affichage;

import java.util.HashMap;
import java.util.Map;

public class Onglet {
    private String defaut;
    private Map<String, String> listeNumero = new HashMap<String, String>();
    private Map<String, String> listePage = new HashMap<String, String>();
    private String dossier = "inc";

    public String getCurrentPage(String param) {
        String tab = "";
        tab = this.listePage.get(param) != null ? param : (this.listeNumero.get(param) != null ? this.listeNumero.get(param) : this.getDefaut());
        this.listePage.put(tab, "active");
        return this.getDossier() + "/" + tab + ".jsp";
    }

    public Onglet(String defaut) {
        this.setDefaut(defaut);
    }

    public String getDefaut() {
        return this.defaut;
    }

    public void setDefaut(String defaut) {
        this.defaut = defaut;
    }

    public Map<String, String> getListeNumero() {
        return this.listeNumero;
    }

    public void setListeNumero(Map<String, String> listeNumero) {
        this.listeNumero = listeNumero;
    }

    public Map<String, String> getListePage() {
        return this.listePage;
    }

    public void setListePage(Map<String, String> listePage) {
        this.listePage = listePage;
    }

    public String getDossier() {
        return this.dossier;
    }

    public void setDossier(String dossier) {
        this.dossier = dossier;
    }
}

