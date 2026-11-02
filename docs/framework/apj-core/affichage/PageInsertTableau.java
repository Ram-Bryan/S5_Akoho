/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  user.UserEJB
 */
package affichage;

import affichage.Champ;
import affichage.ChampCalcul;
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

public class PageInsertTableau
extends PageInsert {
    private int nombreLigne = 0;

    @Override
    public int getNombreLigne() {
        return this.nombreLigne;
    }

    @Override
    public void setNombreLigne(int nombreLigne) {
        if (nombreLigne <= 0) {
            this.nombreLigne = 10;
        }
        this.nombreLigne = nombreLigne;
    }

    public PageInsertTableau(ClassMAPTable o, HttpServletRequest req, UserEJB u, int nbLigne) throws Exception {
        this.setBase(o);
        this.setReq(req);
        this.setUtilisateur(u);
        this.setNombreLigne(nbLigne);
        this.makeFormulaire();
    }

    public PageInsertTableau(ClassMAPTable o, HttpServletRequest req, int nombreLigne) throws Exception {
        this.setBase(o);
        this.setReq(req);
        this.setNombreLigne(nombreLigne);
    }

    @Override
    public void makeFormulaire() throws Exception {
        this.formu = new Formulaire();
        Champ[] t = null;
        bean.Champ[] f = ListeColonneTable.getFromListe(this.getBase(), null);
        t = new Champ[f.length - 1];
        int nbElement = 0;
        for (int i = 0; i < t.length + 1; ++i) {
            if (f[i].getNomColonne().compareToIgnoreCase(this.getBase().getAttributIDName()) == 0) continue;
            if (f[i].getTypeJava().compareToIgnoreCase("double") == 0 || f[i].getTypeJava().compareToIgnoreCase("int") == 0 || f[i].getTypeJava().compareToIgnoreCase("float") == 0) {
                t[nbElement] = new ChampCalcul(f[i].getNomColonne());
            } else if (f[i].getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
                t[nbElement] = new ChampDate(f[i].getNomColonne());
            } else {
                t[nbElement] = new Champ(f[i].getNomColonne());
                t[nbElement].setLibelle(f[i].getNomColonne());
                t[nbElement].setValeur("");
            }
            t[nbElement].setAttribut(f[i].getNomClasse());
            ++nbElement;
        }
        this.formu.setListeChamp(t);
        this.formu.setObjet(this.getBase());
        this.formu.setNbLigne(this.getNombreLigne());
    }

    @Override
    public ClassMAPTable[] getObjectAvecValeurTableau() throws Exception {
        int nombreLigne = this.getNombreLigne();
        ClassMAPTable[] liste = new ClassMAPTable[nombreLigne];
        try {
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getBase());
            int x = 0;
            for (int iLigne = 0; iLigne < nombreLigne; ++iLigne) {
                ClassMAPTable ex = (ClassMAPTable)Class.forName(this.getBase().getClassName()).newInstance();
                for (int i = 0; i < tempChamp.length; ++i) {
                    Field f = tempChamp[i];
                    String nomChamp = f.getName() + "_" + iLigne;
                    String valeur = this.getParamSansNull(nomChamp);
                    if (i == 1 && (valeur == null || valeur.compareTo("") == 0)) break;
                    if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
                        CGenUtil.setValChamp(ex, f, valeur);
                    }
                    if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0) {
                        CGenUtil.setValChamp(ex, f, Utilitaire.string_date("dd/MM/yyyy", valeur));
                    }
                    if (f.getType().getName().compareToIgnoreCase("double") == 0) {
                        CGenUtil.setValChamp(ex, f, new Double(Utilitaire.stringToDouble(valeur)));
                    }
                    if (f.getType().getName().compareToIgnoreCase("int") == 0) {
                        if (valeur == null || valeur.compareToIgnoreCase("") == 0) {
                            CGenUtil.setValChamp(ex, f, new Integer(0));
                        } else {
                            CGenUtil.setValChamp(ex, f, new Integer(valeur));
                        }
                    }
                    if (f.getType().getName().compareToIgnoreCase("float") == 0) {
                        CGenUtil.setValChamp(ex, f, new Float(Utilitaire.stringToFloat(valeur)));
                    }
                    if (i != 1) continue;
                    ++x;
                }
                liste[iLigne] = ex;
            }
            ClassMAPTable[] ret = new ClassMAPTable[x];
            for (int j = 0; j < x; ++j) {
                ret[j] = liste[j];
            }
            return ret;
        }
        catch (NumberFormatException n) {
            throw new Exception("format de nombre invalide");
        }
        catch (Exception e) {
            throw e;
        }
    }
}

