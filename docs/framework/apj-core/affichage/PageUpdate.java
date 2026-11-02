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

public class PageUpdate
extends PageInsert {
    private ClassMAPTable critere;

    public PageUpdate(ClassMAPTable o, HttpServletRequest req, UserEJB u) throws Exception {
        if (req.getParameter("acte") != null && req.getParameter("acte").equalsIgnoreCase("dupliquer")) {
            this.setCritere(o);
            this.setReq(req);
            this.setUtilisateur(u);
            this.makeCritere();
            this.getData();
            this.getBase().setTuppleId(null);
            this.makeFormulaire();
            if (this.getFormu().getChamp(this.getBase().getAttributIDName()) != null) {
                this.getFormu().getChamp(this.getBase().getAttributIDName()).setDefaut(null);
                this.getFormu().getChamp(this.getBase().getAttributIDName()).setValeur(null);
            }
        } else {
            this.setCritere(o);
            this.setReq(req);
            this.setUtilisateur(u);
            this.makeCritere();
            this.getData();
            this.makeFormulaire();
            if (this.getFormu().getChamp("id") != null) {
                this.getFormu().getChamp("id").setAutre("readonly");
            }
        }
    }

    @Override
    public void getData() throws Exception {
        ClassMAPTable[] result = (ClassMAPTable[])this.getUtilisateur().getData(this.getCritere(), null, null, null, "");
        if (result == null || result.length == 0) {
            throw new Exception("Pas de resultat pour votre consultation");
        }
        this.setBase(result[0]);
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
        Champ[] t = null;
        bean.Champ[] f = ListeColonneTable.getFromListe(this.getBase(), null);
        t = new Champ[f.length];
        for (int i = 0; i < t.length; ++i) {
            t[i] = new Champ(f[i].getNomColonne());
            t[i].setLibelle(f[i].getNomColonne());
            if (f[i].getNomColonne().compareToIgnoreCase(this.getBase().getAttributIDName()) == 0) {
                t[i].setAutre("readonly");
            }
            if (f[i].getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
                Object ret = CGenUtil.getValeurFieldByMethod(this.getBase(), f[i].getNomColonne());
                String valDate = null;
                if (ret != null) {
                    valDate = ret.toString();
                }
                t[i] = new ChampDate(f[i].getNomColonne(), "UPDATE");
                t[i].setLibelle(f[i].getNomColonne());
                t[i].setValeur(valDate);
            }
            if (f[i].getTypeJava().compareToIgnoreCase("double") == 0) {
                double val = (Double)CGenUtil.getValeurFieldByMethod(this.getBase(), f[i].getNomColonne());
                t[i].setValeur(Utilitaire.doubleWithoutExponential(val));
            } else {
                t[i].setValeur(String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase(), f[i].getNomColonne())));
            }
            t[i].setUserEJB(this.getUtilisateur());
        }
        this.formu.setListeChamp(t);
        this.formu.setObjet(this.critere);
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

