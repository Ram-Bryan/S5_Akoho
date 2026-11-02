/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package affichage;

import affichage.Formulaire;
import affichage.PageRecherche;
import bean.ClassMAPTable;
import javax.servlet.http.HttpServletRequest;

public class PageRechercheUpdate
extends PageRecherche {
    Formulaire formuLigne;
    String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Formulaire getFormuLigne() {
        return this.formuLigne;
    }

    public void setFormuLigne(Formulaire formuLigne) {
        this.formuLigne = formuLigne;
    }

    public void creerFormuLigne(String[] tabAffiche) throws Exception {
        this.formuLigne = new Formulaire();
        this.formuLigne.setObjet(this.getBase());
        this.formuLigne.makeChampFormuLigne(tabAffiche);
    }

    public PageRechercheUpdate(ClassMAPTable o, HttpServletRequest req, String[] vrt, String[] listInterval, int nbRange, String[] tabAff, String ids) throws Exception {
        super(o, req, vrt, listInterval, nbRange, tabAff);
        this.creerFormuLigne(tabAff);
        this.setId(ids);
    }

    @Override
    public void creerObjetPage(String[] libEnteteDefaut, String[] colSom) throws Exception {
        super.creerObjetPage(libEnteteDefaut, colSom);
        this.getTableau().setIds(this.getId());
        this.getTableau().setFormu(this.getFormuLigne());
    }
}

