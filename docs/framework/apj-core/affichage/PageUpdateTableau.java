/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  user.UserEJB
 */
package affichage;

import affichage.Champ;
import affichage.ChampDate;
import affichage.Formulaire;
import affichage.PageInsert;
import bean.CGenUtil;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import java.lang.reflect.Field;
import javax.servlet.http.HttpServletRequest;
import user.UserEJB;
import utilitaire.Utilitaire;

public class PageUpdateTableau
extends PageInsert {
    private ClassMAPTable critere;

    public PageUpdateTableau(ClassMAPTable o, HttpServletRequest req, UserEJB u, ClassMAPTable[] id) throws Exception {
        this.setCritere(o);
        this.setReq(req);
        this.setUtilisateur(u);
        this.makeCritere();
        this.getData(id);
        this.makeFormulaire();
        if (this.getFormu().getChamp("id") != null) {
            this.getFormu().getChamp("id").setAutre("readonly");
        }
    }

    public void getData(ClassMAPTable[] id) throws Exception {
        if (id == null || id.length == 0) {
            throw new Exception("Pas de resultat pour votre consultation");
        }
        this.setNombreLigne(id.length);
        this.setBase(id[0]);
        this.setBaseTableau(id);
    }

    @Override
    public void makeHtml() {
    }

    @Override
    public void makeCritere() throws Exception {
        String valeur = this.getReq().getParameter(this.getCritere().getAttributIDName());
        Field f = CGenUtil.getField(this.getCritere(), this.getCritere().getAttributIDName());
        CGenUtil.setValChamp(this.getCritere(), f, valeur);
    }

    @Override
    public void makeFormulaire() throws Exception {
        this.formu = new Formulaire();
        Champ[][] t = null;
        bean.Champ[] f = ListeColonneTable.getFromListe(this.getBase(), null);
        t = new Champ[this.getBaseTableau().length][f.length];
        for (int j = 0; j < this.getBaseTableau().length; ++j) {
            ClassMAPTable liste = this.getBaseTableau()[j];
            for (int i = 0; i < f.length; ++i) {
                t[j][i] = new Champ(f[i].getNomColonne());
                t[j][i].setLibelle(f[i].getNomColonne());
                if (f[i].getNomColonne().compareToIgnoreCase(this.getBase().getAttributIDName()) == 0) {
                    t[j][i].setType("hidden");
                }
                if (f[i].getNomColonne().compareToIgnoreCase(this.getBase().getAttributIDName()) == 0) {
                    t[j][i].setType("hidden");
                }
                if (f[i].getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
                    Object ret = CGenUtil.getValeurFieldByMethod(this.getBaseTableau()[j], f[i].getNomColonne());
                    String valDate = null;
                    if (ret != null) {
                        valDate = ret.toString();
                    }
                    t[j][i] = new ChampDate(f[i].getNomColonne(), "UPDATE");
                    t[j][i].setLibelle(f[i].getNomColonne());
                    t[j][i].setValeur(valDate);
                }
                if (f[i].getTypeJava().compareToIgnoreCase("double") == 0) {
                    double val = (Double)CGenUtil.getValeurFieldByMethod(this.getBaseTableau()[j], f[i].getNomColonne());
                    t[j][i].setValeur(Utilitaire.doubleWithoutExponential(val));
                } else {
                    t[j][i].setValeur(String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBaseTableau()[j], f[i].getNomColonne())));
                }
                this.formu.setListeChamp(t[j]);
            }
        }
        this.formu.setListeChampTableau(t);
        this.formu.setObjet(this.critere);
        this.formu.setNbLigne(this.getNombreLigne());
    }

    @Override
    public ClassMAPTable getCritere() {
        return this.critere;
    }

    @Override
    public void setCritere(ClassMAPTable critere) {
        this.critere = critere;
    }
}

