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
import bean.ClassFille;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import user.UserEJB;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class PageUpdateMultiple
extends PageInsert {
    private ClassMAPTable critere;
    Formulaire formufle;
    private ClassMAPTable fille;
    private int nombreLigne = 0;
    private int tailleFille = 0;
    private String[] tabIndice;

    public PageUpdateMultiple(ClassMAPTable bse, ClassMAPTable fle, HttpServletRequest req, int nbLine, String[] tabIndice) throws Exception {
        this.setBase(bse);
        this.setFille(fle);
        this.setReq(req);
        this.setNombreLigne(nbLine);
        this.setTabIndice(tabIndice);
    }

    public PageUpdateMultiple(ClassMAPTable o, ClassMAPTable fle, ClassMAPTable[] fille, HttpServletRequest req, UserEJB u, int nombreLigne) throws Exception {
        if (req.getParameter("acte") != null && req.getParameter("acte").equalsIgnoreCase("dupliquer")) {
            this.setCritere(o);
            this.setBase(o);
            this.setReq(req);
            this.setUtilisateur(u);
            this.makeCritere();
            this.getData();
            this.getBase().setTuppleId(null);
            this.setFille(fle);
            this.setDataFille(fille);
            for (ClassMAPTable c : this.getDataFille()) {
                c.setTuppleId(null);
                if (!(c instanceof ClassFille)) continue;
                c.setValChamp(((ClassFille)c).getLiaisonMere(), null);
            }
            this.setTailleFille(fille.length);
            this.setNombreLigne(this.getTailleFille() + nombreLigne);
            this.makeFormulaire();
            if (this.getFormu().getChamp(this.getBase().getAttributIDName()) != null) {
                this.getFormu().getChamp(this.getBase().getAttributIDName()).setDefaut(null);
                this.getFormu().getChamp(this.getBase().getAttributIDName()).setValeur(null);
            }
            this.formufle.setDataFille(fille);
            this.formufle.setEstFille(true);
        } else {
            this.setCritere(o);
            this.setBase(o);
            this.setReq(req);
            this.setUtilisateur(u);
            this.makeCritere();
            this.getData();
            this.setFille(fle);
            this.setDataFille(fille);
            this.setTailleFille(fille.length);
            this.setNombreLigne(this.getTailleFille() + nombreLigne);
            this.makeFormulaire();
            if (this.getFormu().getChamp("id") != null) {
                this.getFormu().getChamp("id").setAutre("readonly");
            }
            this.formufle.setDataFille(fille);
            this.formufle.setEstFille(true);
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

    public void setDataFille(ClassMAPTable[] fille) throws Exception {
        this.setBaseTableau(fille);
    }

    public ClassMAPTable[] getDataFille() throws Exception {
        return this.getBaseTableau();
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
        this.formufle = new Formulaire();
        Champ[] tbse = null;
        Champ[] tfle = null;
        bean.Champ[] champbse = ListeColonneTable.getFromListe(this.getBase(), null);
        bean.Champ[] champfle = ListeColonneTable.getFromListe(this.getFille(), null);
        tbse = new Champ[champbse.length - 1];
        tfle = new Champ[champfle.length * this.getNombreLigne()];
        int nbElement = 0;
        for (int i = 0; i < tbse.length + 1; ++i) {
            tbse[nbElement] = new Champ(champbse[i].getNomColonne());
            tbse[nbElement].setLibelle(champbse[i].getNomColonne());
            if (champbse[i].getNomColonne().compareToIgnoreCase(this.getBase().getAttributIDName()) == 0) continue;
            if (champbse[i].getTypeJava().compareToIgnoreCase("double") == 0) {
                double val = (Double)CGenUtil.getValeurFieldByMethod(this.getBase(), champbse[i].getNomColonne());
                tbse[nbElement].setValeur(Utilitaire.doubleWithoutExponential(val));
            } else if (champbse[i].getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
                Object ret = CGenUtil.getValeurFieldByMethod(this.getBase(), champbse[i].getNomColonne());
                String valDate = null;
                if (ret != null) {
                    valDate = ret.toString();
                }
                tbse[nbElement] = new ChampDate(champbse[i].getNomColonne(), "UPDATE");
                tbse[nbElement].setLibelle(champbse[i].getNomColonne());
                tbse[nbElement].setValeur(valDate);
            } else {
                tbse[nbElement].setValeur(String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBase(), champbse[i].getNomColonne())));
            }
            ++nbElement;
        }
        this.formu.setListeChamp(tbse);
        this.formu.setObjet(this.critere);
        int nbElementfle = 0;
        for (int ligne = 0; ligne < this.getNombreLigne(); ++ligne) {
            for (int i = 0; i < champfle.length; ++i) {
                tfle[nbElementfle] = new Champ(champfle[i].getNomColonne() + "_" + ligne);
                tfle[nbElementfle].setLibelle(champfle[i].getNomColonne() + "_" + ligne);
                if (champfle[i].getTypeJava().compareToIgnoreCase("double") == 0) {
                    if (ligne < this.getTailleFille()) {
                        double val = (Double)CGenUtil.getValeurFieldByMethod(this.getBaseTableau()[ligne], champfle[i].getNomColonne());
                        tfle[nbElementfle].setValeur(Utilitaire.doubleWithoutExponential(val));
                    } else {
                        tfle[nbElementfle] = new ChampCalcul(champfle[i].getNomColonne() + "_" + ligne);
                    }
                } else if (champfle[i].getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
                    if (ligne < this.getTailleFille()) {
                        Object ret = CGenUtil.getValeurFieldByMethod(this.getBaseTableau()[ligne], champfle[i].getNomColonne());
                        String valDate = null;
                        if (ret != null) {
                            valDate = ret.toString();
                        }
                        tfle[nbElementfle] = new ChampDate(champfle[i].getNomColonne() + "_" + ligne, "UPDATE");
                        tfle[nbElementfle].setLibelle(champfle[i].getNomColonne());
                        tfle[nbElementfle].setValeur(valDate);
                    } else {
                        tfle[nbElementfle] = new ChampDate(champfle[i].getNomColonne() + "_" + ligne);
                    }
                } else if (ligne < this.getTailleFille()) {
                    tfle[nbElementfle].setValeur(String.valueOf(CGenUtil.getValeurFieldByMethod(this.getBaseTableau()[ligne], champfle[i].getNomColonne())));
                }
                ++nbElementfle;
            }
        }
        this.formufle.setListeChamp(tfle);
        this.formufle.setObjet(this.getFille());
        this.formufle.setNbLigne(this.getNombreLigne());
    }

    @Override
    public ClassMAPTable getCritere() {
        return this.critere;
    }

    @Override
    public void setCritere(ClassMAPTable critere) {
        this.critere = critere;
    }

    public ClassMAPTable getFille() {
        return this.fille;
    }

    public void setFille(ClassMAPTable fille) {
        this.fille = fille;
    }

    public Formulaire getFormufle() {
        return this.formufle;
    }

    public void setFormufle(Formulaire formufle) {
        this.formufle = formufle;
    }

    public void setDefautFille(ClassMAPTable[] fille) throws Exception {
        if (fille.length == 0) {
            return;
        }
        if (fille.length < 10) {
            this.setNombreLigne(fille.length + 10);
        } else if (this.getNombreLigne() < fille.length) {
            this.setNombreLigne(fille.length + 3);
        }
        bean.Champ[] listeChamp = ListeColonneTable.getFromListe(this.getFille(), null);
        for (int ligne = 0; ligne < fille.length; ++ligne) {
            for (bean.Champ c : listeChamp) {
                try {
                    this.getFormufle().getChamp(c.getNomClasse().getName() + "_" + ligne).setDefaut(CGenUtil.getValeurInsert(fille[ligne], c.getNomClasse().getName()));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void preparerDataFormu() throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            this.formu.getAllData(c);
            this.formufle.getAllDataFille(c);
        }
    }

    @Override
    public int getNombreLigne() {
        return this.nombreLigne;
    }

    @Override
    public void setNombreLigne(int nombreLigne) {
        this.nombreLigne = nombreLigne;
    }

    public int getTailleFille() {
        return this.tailleFille;
    }

    public void setTailleFille(int tailleFille) {
        this.tailleFille = tailleFille;
    }

    public String[] getTabIndice() {
        return this.tabIndice;
    }

    public void setTabIndice(String[] tabIndice) {
        this.tabIndice = tabIndice;
    }

    public ClassMAPTable[] getObjectFilleAvecValeur() throws Exception {
        String[] tIndice = this.getReq().getParameterValues("ids");
        if (tIndice == null || tIndice.length < 1) {
            throw new Exception("Vous devez cocher");
        }
        Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getFille());
        Class<?> targetClass = Class.forName(this.getFille().getClassName());
        ClassMAPTable[] liste = (ClassMAPTable[])Array.newInstance(targetClass, tIndice.length);
        try {
            for (int k = 0; k < tIndice.length; ++k) {
                int iLigne = Integer.parseInt(tIndice[k]);
                ClassMAPTable ex = (ClassMAPTable)Class.forName(this.getFille().getClassName()).newInstance();
                for (int i = 0; i < tempChamp.length; ++i) {
                    Field f = tempChamp[i];
                    String nomChamp = f.getName() + "_" + iLigne;
                    String valeur = this.getParamSansNull(nomChamp);
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
                        int valeurSplit;
                        String[] split;
                        if (valeur == null || valeur.compareToIgnoreCase("") == 0 || valeur.startsWith("0.")) {
                            CGenUtil.setValChamp(ex, f, new Integer(0));
                        } else if (valeur.contains(".")) {
                            split = valeur.split(".");
                            valeurSplit = 0;
                            if (split.length >= 1) {
                                valeurSplit = Utilitaire.stringToInt(split[0]);
                            }
                            CGenUtil.setValChamp(ex, f, new Integer(valeurSplit));
                        } else if (valeur.contains(",")) {
                            split = valeur.split(",");
                            valeurSplit = 0;
                            if (split.length >= 1) {
                                valeurSplit = Utilitaire.stringToInt(split[0]);
                            }
                            CGenUtil.setValChamp(ex, f, new Integer(valeurSplit));
                        } else {
                            CGenUtil.setValChamp(ex, f, new Integer(valeur));
                        }
                    }
                    if (f.getType().getName().compareToIgnoreCase("float") != 0) continue;
                    CGenUtil.setValChamp(ex, f, new Float(Utilitaire.stringToFloat(valeur)));
                }
                liste[k] = ex;
            }
            return liste;
        }
        catch (NumberFormatException n) {
            n.printStackTrace();
            throw new Exception("format de nombre invalide");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ClassMAPTable[] getObjectFilleAvecValeurSansControle() throws Exception {
        int nombreLigne = this.getNombreLigne();
        String[] tIndice = this.getReq().getParameterValues("ids");
        ClassMAPTable[] liste = new ClassMAPTable[tIndice.length];
        try {
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getFille());
            int x = 0;
            for (int iLigne = 0; iLigne < nombreLigne; ++iLigne) {
                ClassMAPTable ex = (ClassMAPTable)Class.forName(this.getFille().getClassName()).newInstance();
                for (int indice = 0; indice < tIndice.length; ++indice) {
                    String ligne = "" + iLigne;
                    if (!ligne.equals(tIndice[indice])) continue;
                    for (int i = 0; i < tempChamp.length; ++i) {
                        Field f = tempChamp[i];
                        String nomChamp = f.getName() + "_" + iLigne;
                        String valeur = this.getParamSansNull(nomChamp);
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
                            int valeurSplit;
                            String[] split;
                            if (valeur == null || valeur.compareToIgnoreCase("") == 0 || valeur.startsWith("0.")) {
                                CGenUtil.setValChamp(ex, f, new Integer(0));
                            } else if (valeur.contains(".")) {
                                split = valeur.split(".");
                                valeurSplit = 0;
                                if (split.length >= 1) {
                                    valeurSplit = Utilitaire.stringToInt(split[0]);
                                }
                                CGenUtil.setValChamp(ex, f, new Integer(valeurSplit));
                            } else if (valeur.contains(",")) {
                                split = valeur.split(",");
                                valeurSplit = 0;
                                if (split.length >= 1) {
                                    valeurSplit = Utilitaire.stringToInt(split[0]);
                                }
                                CGenUtil.setValChamp(ex, f, new Integer(valeurSplit));
                            } else {
                                CGenUtil.setValChamp(ex, f, new Integer(valeur));
                            }
                        }
                        if (f.getType().getName().compareToIgnoreCase("float") == 0) {
                            CGenUtil.setValChamp(ex, f, new Float(Utilitaire.stringToFloat(valeur)));
                        }
                        if (i != 0) continue;
                        ++x;
                    }
                    liste[indice] = ex;
                }
            }
            ClassMAPTable[] ret = new ClassMAPTable[x];
            int countOk = 0;
            for (int j = 0; j < tIndice.length; ++j) {
                if (liste[j] == null) continue;
                ret[countOk] = liste[j];
                ++countOk;
            }
            return ret;
        }
        catch (NumberFormatException n) {
            n.printStackTrace();
            throw new Exception("format de nombre invalide");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

