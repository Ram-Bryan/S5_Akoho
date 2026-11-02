/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package affichage;

import javax.servlet.http.HttpServletRequest;

public class ListeAffichage {
    HttpServletRequest request;
    private String[] listeFiltre;
    private String[] valeurFiltre;
    private int nbFiltre;

    public ListeAffichage(HttpServletRequest req, String[] listeF) {
        this.request = req;
        this.setListeFiltre(listeF);
    }

    public String[] getListeFiltre() {
        return this.listeFiltre;
    }

    public void setListeFiltre(String[] listeFiltre) {
        this.listeFiltre = listeFiltre;
    }

    public void setValeurFiltre(String[] valeurFiltre) {
        this.valeurFiltre = valeurFiltre;
    }

    public String[] getValeurFiltre() {
        return this.valeurFiltre;
    }

    public void setNbFiltre(int nbFiltre) {
        this.nbFiltre = nbFiltre;
    }

    public int getNbFiltre() {
        return this.nbFiltre;
    }

    public void getValeur() {
        for (int i = 0; i < this.listeFiltre.length; ++i) {
            String temp = this.request.getParameter(this.listeFiltre[i]);
            if (temp == null || temp.compareToIgnoreCase("") == 0) continue;
            this.valeurFiltre[i] = temp;
        }
    }
}

