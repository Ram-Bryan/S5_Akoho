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
import affichage.Page;
import bean.CGenUtil;
import bean.ClassFille;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import constante.ConstanteAffichage;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import user.UserEJB;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class PageInsertMultiple
extends Page {
    Formulaire formufle;
    private int nombreLigne = 0;
    private ClassMAPTable fille;
    private String[] tabIndice;
    private ClassMAPTable critere;
    private int tailleFille = 0;

    public PageInsertMultiple(ClassMAPTable bse, ClassMAPTable fle, HttpServletRequest req, int nbLine, UserEJB u) throws Exception {
        if (req.getParameter("acte") != null && req.getParameter("acte").equalsIgnoreCase("update")) {
            this.setCritere(bse);
            this.setReq(req);
            this.setUtilisateur(u);
            this.makeCritere();
            this.getData();
            this.setFille(fle);
            this.makeCritereFle();
            this.getDataFle();
            this.setTailleFille(this.getDataFille().length);
            this.setNombreLigne(this.getTailleFille() + 10);
            this.makeFormulaireUpt();
            this.formufle.setDataFille(this.getDataFille());
            this.formufle.setEstFille(true);
        } else if (req.getParameter("acte") != null && req.getParameter("acte").equalsIgnoreCase("dupliquer")) {
            this.setCritere(bse);
            this.setReq(req);
            this.setUtilisateur(u);
            this.makeCritere();
            this.getData();
            this.getBase().setTuppleId(null);
            this.setFille(fle);
            this.makeCritereFle();
            this.getDataFle();
            for (ClassMAPTable c : this.getDataFille()) {
                c.setTuppleId(null);
                if (!(c instanceof ClassFille)) continue;
                c.setValChamp(((ClassFille)c).getLiaisonMere(), null);
            }
            this.setTailleFille(this.getDataFille().length);
            this.setNombreLigne(this.getTailleFille() + 10);
            this.makeFormulaireUpt();
            if (this.getFormu().getChamp(this.getBase().getAttributIDName()) != null) {
                this.getFormu().getChamp(this.getBase().getAttributIDName()).setDefaut(null);
                this.getFormu().getChamp(this.getBase().getAttributIDName()).setValeur(null);
            }
            this.formufle.setDataFille(this.getDataFille());
            this.formufle.setCheckAuto(true, this.getTailleFille());
            this.formufle.setEstFille(true);
        } else {
            this.setBase(bse);
            this.setFille(fle);
            this.setReq(req);
            this.setUtilisateur(u);
            this.setNombreLigne(nbLine);
            this.makeFormulaire();
            this.formufle.setEstFille(true);
        }
    }

    public PageInsertMultiple(ClassMAPTable bse, ClassMAPTable fle, HttpServletRequest req, int nbLine) throws Exception {
        this.setBase(bse);
        this.setFille(fle);
        this.setReq(req);
        this.setNombreLigne(nbLine);
    }

    public PageInsertMultiple(ClassMAPTable bse, ClassMAPTable fle, HttpServletRequest req, int nbLine, String[] tabIndice) throws Exception {
        this.setBase(bse);
        this.setFille(fle);
        this.setReq(req);
        this.setNombreLigne(nbLine);
        this.setTabIndice(tabIndice);
        this.makeFormulaire();
    }

    public void preparerDataFormu() throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            this.formu.getAllData(c);
            this.formufle.getAllDataFille(c);
        }
    }

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
            if (champbse[i].getNomColonne().compareToIgnoreCase(this.getBase().getAttributIDName()) == 0) continue;
            if (champbse[i].getTypeJava().compareToIgnoreCase("double") == 0 || champbse[i].getTypeJava().compareToIgnoreCase("int") == 0 || champbse[i].getTypeJava().compareToIgnoreCase("float") == 0) {
                tbse[nbElement] = new ChampCalcul(champbse[i].getNomColonne());
            } else if (champbse[i].getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
                tbse[nbElement] = new ChampDate(champbse[i].getNomColonne());
            } else {
                tbse[nbElement] = new Champ(champbse[i].getNomColonne());
                tbse[nbElement].setLibelle(champbse[i].getNomColonne());
                tbse[nbElement].setValeur("");
            }
            tbse[nbElement].setAttribut(champbse[i].getNomClasse());
            ++nbElement;
        }
        this.formu.setListeChamp(tbse);
        this.formu.setObjet(this.getBase());
        int nbElementfle = 0;
        for (int ligne = 0; ligne < this.getNombreLigne(); ++ligne) {
            for (int i = 0; i < champfle.length; ++i) {
                if (champfle[i].getTypeJava().compareToIgnoreCase("double") == 0 || champfle[i].getTypeJava().compareToIgnoreCase("int") == 0 || champfle[i].getTypeJava().compareToIgnoreCase("float") == 0) {
                    tfle[nbElementfle] = new ChampCalcul(champfle[i].getNomColonne() + "_" + ligne);
                } else if (champfle[i].getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
                    tfle[nbElementfle] = new ChampDate(champfle[i].getNomColonne() + "_" + ligne);
                    tfle[nbElementfle].setTypeData(ConstanteAffichage.typeDaty);
                } else {
                    tfle[nbElementfle] = new Champ(champfle[i].getNomColonne() + "_" + ligne);
                    tfle[nbElementfle].setLibelle(champfle[i].getNomColonne() + "_" + ligne);
                    tfle[nbElementfle].setValeur("");
                }
                tfle[nbElementfle].setAttribut(champfle[i].getNomClasse());
                ++nbElementfle;
            }
        }
        this.formufle.setListeChamp(tfle);
        this.formufle.setObjet(this.getFille());
        this.formufle.setNbLigne(this.getNombreLigne());
    }

    public void makeFormulaireUpt() throws Exception {
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
            tbse[nbElement].setAttribut(champbse[i].getNomClasse());
            ++nbElement;
        }
        Champ ctemp = new Champ(this.getBase().getAttributIDName());
        ctemp.setVisible(false);
        if (this.getReq() != null && this.getReq().getParameter("acte") != null && this.getReq().getParameter("acte").equalsIgnoreCase("dupliquer")) {
            ctemp.setDefaut(null);
            ctemp.setValeur(null);
        } else {
            ctemp.setDefaut(this.getReq().getParameter(this.getBase().getAttributIDName()));
        }
        ArrayList<Champ> temp = new ArrayList<Champ>(Arrays.asList(tbse));
        temp.add(ctemp);
        this.formu.setListeChamp(temp.toArray(new Champ[temp.size()]));
        this.formu.setObjet(this.critere);
        int nbElementfle = 0;
        for (int ligne = 0; ligne < this.getNombreLigne(); ++ligne) {
            for (int i = 0; i < champfle.length; ++i) {
                tfle[nbElementfle] = new Champ(champfle[i].getNomColonne() + "_" + ligne);
                tfle[nbElementfle].setLibelle(champfle[i].getNomColonne() + "_" + ligne);
                if (champfle[i].getTypeJava().compareToIgnoreCase("double") == 0) {
                    if (ligne < this.getTailleFille()) {
                        double val = (Double)CGenUtil.getValeurFieldByMethod(this.getBaseTableau()[ligne], champfle[i].getNomColonne());
                        tfle[nbElementfle].setValeur(Utilitaire.doubleWithoutExponential(val, this.getBase().getNombreApresVirgule()));
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
                tfle[nbElementfle].setAttribut(champfle[i].getNomClasse());
                ++nbElementfle;
            }
        }
        this.formufle.setListeChamp(tfle);
        this.formufle.setObjet(this.getFille());
        this.formufle.setNbLigne(this.getNombreLigne());
    }

    @Override
    public ClassMAPTable getObjectAvecValeur() throws Exception {
        try {
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getBase());
            for (int i = 0; i < tempChamp.length; ++i) {
                Field f = tempChamp[i];
                String valeur = this.getParamSansNull(f.getName());
                this.getBase().setMode("modif");
                if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0 && f.getName().compareToIgnoreCase("liaisonMere") != 0 && f.getName().compareToIgnoreCase("liaisonFille") != 0) {
                    CGenUtil.setValChamp(this.getBase(), f, valeur);
                }
                if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, Utilitaire.string_date("dd/MM/yyyy", valeur));
                }
                if (f.getType().getName().compareToIgnoreCase("double") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, new Double(Utilitaire.stringToDouble(valeur)));
                }
                if (f.getType().getName().compareToIgnoreCase("int") == 0) {
                    if (valeur == null || valeur.compareToIgnoreCase("") == 0) {
                        CGenUtil.setValChamp(this.getBase(), f, new Integer(0));
                    } else {
                        CGenUtil.setValChamp(this.getBase(), f, new Integer(Utilitaire.stringToInt(valeur)));
                    }
                }
                if (f.getType().getName().compareToIgnoreCase("float") != 0) continue;
                CGenUtil.setValChamp(this.getBase(), f, new Float(Utilitaire.stringToFloat(valeur)));
            }
            return this.getBase();
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
        String[] tIndice = this.getTabIndice();
        if (tIndice == null) {
            return new ClassMAPTable[0];
        }
        ClassMAPTable[] liste = new ClassMAPTable[tIndice.length];
        try {
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getFille());
            int x = 0;
            Object ex = null;
            for (int iLigne = 0; iLigne < nombreLigne; ++iLigne) {
                ex = (ClassMAPTable)Class.forName(this.getFille().getClassName()).newInstance();
                for (int indice = 0; indice < tIndice.length; ++indice) {
                    String ligne = "" + iLigne;
                    if (!ligne.equals(tIndice[indice])) continue;
                    for (int i = 0; i < tempChamp.length; ++i) {
                        Field f = tempChamp[i];
                        String nomChamp = f.getName() + "_" + iLigne;
                        String valeur = this.getParamSansNull(nomChamp);
                        if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0 && f.getName().compareToIgnoreCase("liaisonMere") != 0 && f.getName().compareToIgnoreCase("liaisonFille") != 0) {
                            CGenUtil.setValChamp((ClassMAPTable)ex, f, valeur);
                        }
                        if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0) {
                            CGenUtil.setValChamp((ClassMAPTable)ex, f, Utilitaire.string_date("dd/MM/yyyy", valeur));
                        }
                        if (f.getType().getName().compareToIgnoreCase("double") == 0) {
                            CGenUtil.setValChamp((ClassMAPTable)ex, f, new Double(Utilitaire.stringToDouble(valeur)));
                        }
                        if (f.getType().getName().compareToIgnoreCase("int") == 0) {
                            if (valeur == null || valeur.compareToIgnoreCase("") == 0) {
                                CGenUtil.setValChamp((ClassMAPTable)ex, f, new Integer(0));
                            } else {
                                CGenUtil.setValChamp((ClassMAPTable)ex, f, new Integer(Utilitaire.stringToInt(valeur)));
                            }
                        }
                        if (f.getType().getName().compareToIgnoreCase("float") == 0) {
                            CGenUtil.setValChamp((ClassMAPTable)ex, f, new Float(Utilitaire.stringToFloat(valeur)));
                        }
                        if (i != 0) continue;
                        ++x;
                    }
                    liste[indice] = ex;
                }
            }
            Class<?> targetClass = ex != null ? ex.getClass() : this.getFille().getClass();
            int countOk = 0;
            for (int j = 0; j < tIndice.length; ++j) {
                if (liste[j] == null) continue;
                ++countOk;
            }
            ClassMAPTable[] ret = (ClassMAPTable[])Array.newInstance(targetClass, countOk);
            int idx = 0;
            for (int j = 0; j < tIndice.length; ++j) {
                if (liste[j] == null) continue;
                ret[idx++] = liste[j];
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

    public ClassMAPTable[] getObjectFilleAvecValeur() throws Exception {
        int nombreLigne = this.getNombreLigne();
        String[] tIndice = this.getTabIndice();
        if (tIndice != null) {
            for (String sInd : tIndice) {
                int valInd = Utilitaire.stringToInt(sInd);
                if (valInd < nombreLigne) continue;
                nombreLigne = valInd + 1;
            }
        }
        if (tIndice == null) {
            throw new Exception("Vous devez cocher");
        }
        ClassMAPTable[] liste = new ClassMAPTable[tIndice.length];
        try {
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getFille());
            int x = 0;
            Object ex = null;
            for (int iLigne = 0; iLigne < nombreLigne; ++iLigne) {
                ex = (ClassMAPTable)Class.forName(this.getFille().getClassName()).newInstance();
                for (int indice = 0; indice < tIndice.length; ++indice) {
                    String ligne = "" + iLigne;
                    if (!ligne.equals(tIndice[indice])) continue;
                    for (int i = 0; i < tempChamp.length; ++i) {
                        Field f = tempChamp[i];
                        String nomChamp = f.getName() + "_" + iLigne;
                        String valeur = this.getParamSansNull(nomChamp);
                        if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0 && f.getName().compareToIgnoreCase("liaisonMere") != 0 && f.getName().compareToIgnoreCase("liaisonFille") != 0) {
                            CGenUtil.setValChamp((ClassMAPTable)ex, f, valeur);
                        }
                        if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0) {
                            CGenUtil.setValChamp((ClassMAPTable)ex, f, Utilitaire.string_date("dd/MM/yyyy", valeur));
                        }
                        if (f.getType().getName().compareToIgnoreCase("double") == 0) {
                            CGenUtil.setValChamp((ClassMAPTable)ex, f, new Double(Utilitaire.stringToDouble(valeur)));
                        }
                        if (f.getType().getName().compareToIgnoreCase("int") == 0) {
                            if (valeur == null || valeur.compareToIgnoreCase("") == 0) {
                                CGenUtil.setValChamp((ClassMAPTable)ex, f, new Integer(0));
                            } else {
                                CGenUtil.setValChamp((ClassMAPTable)ex, f, new Integer(Utilitaire.stringToInt(valeur)));
                            }
                        }
                        if (f.getType().getName().compareToIgnoreCase("float") == 0) {
                            CGenUtil.setValChamp((ClassMAPTable)ex, f, new Float(Utilitaire.stringToFloat(valeur)));
                        }
                        if (i != 0) continue;
                        ++x;
                    }
                    liste[indice] = ex;
                }
            }
            Class<?> targetClass = ex != null ? ex.getClass() : this.getFille().getClass();
            int countOk = 0;
            for (int j = 0; j < tIndice.length; ++j) {
                if (liste[j] == null) continue;
                ++countOk;
            }
            ClassMAPTable[] ret = (ClassMAPTable[])Array.newInstance(targetClass, countOk);
            int idx = 0;
            for (int j = 0; j < tIndice.length; ++j) {
                if (liste[j] == null) continue;
                ret[idx++] = liste[j];
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

    @Override
    public ClassMAPTable getObjectAvecValeur(HashMap<String, String> listeValeur) throws Exception {
        try {
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getBase());
            for (int i = 0; i < tempChamp.length; ++i) {
                Field f = tempChamp[i];
                String valeur = this.getParamSansNull(f.getName(), listeValeur);
                this.getBase().setMode("insert");
                if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, valeur);
                }
                if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, Utilitaire.string_date("dd/MM/yyyy", valeur));
                }
                if (f.getType().getName().compareToIgnoreCase("double") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, new Double(Utilitaire.stringToDouble(valeur)));
                }
                if (f.getType().getName().compareToIgnoreCase("int") == 0) {
                    if (valeur == null || valeur.compareToIgnoreCase("") == 0) {
                        CGenUtil.setValChamp(this.getBase(), f, new Integer(0));
                    } else {
                        CGenUtil.setValChamp(this.getBase(), f, new Integer(Utilitaire.stringToInt(valeur)));
                    }
                }
                if (f.getType().getName().compareToIgnoreCase("float") != 0) continue;
                CGenUtil.setValChamp(this.getBase(), f, new Float(Utilitaire.stringToFloat(valeur)));
            }
            return this.getBase();
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

    public ClassMAPTable getObjectFilleAvecValeur(HashMap<String, String> listeValeur) throws Exception {
        try {
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getFille());
            ClassMAPTable ex = null;
            for (int i = 0; i < tempChamp.length; ++i) {
                Field f = tempChamp[i];
                String valeur = this.getParamSansNull(f.getName(), listeValeur);
                this.getFille().setMode("modif");
                ex = (ClassMAPTable)Class.forName(this.getFille().getClassName()).newInstance();
                if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0 && f.getName().compareToIgnoreCase("liaisonMere") != 0 && f.getName().compareToIgnoreCase("liaisonFille") != 0) {
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
                        CGenUtil.setValChamp(ex, f, new Integer(Utilitaire.stringToInt(valeur)));
                    }
                }
                if (f.getType().getName().compareToIgnoreCase("float") != 0) continue;
                CGenUtil.setValChamp(ex, f, new Float(Utilitaire.stringToFloat(valeur)));
            }
            return this.getFille();
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

    @Override
    public void makeHtml() {
    }

    @Override
    public int getNombreLigne() {
        return this.nombreLigne;
    }

    @Override
    public void setNombreLigne(int nombreLigne) {
        this.nombreLigne = nombreLigne;
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

    public void setDefautFille(ClassMAPTable[] fille) throws Exception {
        this.setNombreLigne(fille.length + 3);
        if (fille.length == 0) {
            return;
        }
        if (fille.length < 10) {
            this.setNombreLigne(fille.length + 10);
        } else if (this.getNombreLigne() < fille.length) {
            this.setNombreLigne(fille.length + 3);
        }
        int nombreLigneExistante = this.getFormufle().getNbLigne();
        if (nombreLigneExistante < this.getNombreLigne()) {
            this.getFormufle().ajouterLignes(this.getNombreLigne() - nombreLigneExistante);
        }
        bean.Champ[] listeChamp = ListeColonneTable.getFromListe(this.getFille(), null);
        this.getFormufle().setCheckAuto(true, fille.length);
        for (int ligne = 0; ligne < fille.length; ++ligne) {
            if (fille[ligne] == null) continue;
            for (bean.Champ c : listeChamp) {
                try {
                    if (c.getNomClasse().getName().compareToIgnoreCase(fille[ligne].getAttributIDName()) == 0) continue;
                    if (c.getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
                        String formattedDate = Utilitaire.formatterDaty(CGenUtil.getValeurInsert(fille[ligne], c.getNomClasse().getName()));
                        this.getFormufle().getChamp(c.getNomClasse().getName() + "_" + ligne).setDefaut(formattedDate);
                    }
                    if (c.getTypeJava().compareToIgnoreCase("double") == 0) {
                        String formattedAr = Utilitaire.formaterAr(CGenUtil.getValeurInsert(fille[ligne], c.getNomClasse().getName()));
                        this.getFormufle().getChamp(c.getNomClasse().getName() + "_" + ligne).setDefaut(formattedAr);
                        continue;
                    }
                    this.getFormufle().getChamp(c.getNomClasse().getName() + "_" + ligne).setDefaut(CGenUtil.getValeurInsert(fille[ligne], c.getNomClasse().getName()));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setFormufle(Formulaire formufle) {
        this.formufle = formufle;
    }

    public String[] getTabIndice() {
        return this.tabIndice;
    }

    public void setTabIndice(String[] tabIndice) {
        this.tabIndice = tabIndice;
    }

    public PageInsertMultiple(ClassMAPTable fle, HttpServletRequest req, int nbLine, UserEJB u) throws Exception {
        this.setFille(fle);
        this.setReq(req);
        this.setUtilisateur(u);
        this.setNombreLigne(nbLine);
        this.makeFormulaireFille();
    }

    public void makeFormulaireFille() throws Exception {
        this.formu = new Formulaire();
        this.formufle = new Formulaire();
        Champ[] tfle = null;
        bean.Champ[] champfle = ListeColonneTable.getFromListe(this.getFille(), null);
        tfle = new Champ[champfle.length - 1];
        int nbElementfle = 0;
        for (int i = 0; i < tfle.length + 1; ++i) {
            if (champfle[i].getNomColonne().compareToIgnoreCase(this.getFille().getAttributIDName()) == 0) continue;
            if (champfle[i].getTypeJava().compareToIgnoreCase("double") == 0 || champfle[i].getTypeJava().compareToIgnoreCase("int") == 0 || champfle[i].getTypeJava().compareToIgnoreCase("float") == 0) {
                tfle[nbElementfle] = new ChampCalcul(champfle[i].getNomColonne());
            } else if (champfle[i].getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
                tfle[nbElementfle] = new ChampDate(champfle[i].getNomColonne());
            } else {
                tfle[nbElementfle] = new Champ(champfle[i].getNomColonne());
                tfle[nbElementfle].setLibelle(champfle[i].getNomColonne());
                tfle[nbElementfle].setValeur("");
            }
            ++nbElementfle;
        }
        this.formufle.setListeChamp(tfle);
        this.formufle.setNbLigne(this.getNombreLigne());
    }

    public void preparerDataFormuFille() throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            this.formufle.getAllData(c);
        }
    }

    public PageInsertMultiple(ClassMAPTable fle, HttpServletRequest req, int nbLine, String[] tabIndice) throws Exception {
        this.setFille(fle);
        this.setReq(req);
        this.setNombreLigne(nbLine);
        this.setTabIndice(tabIndice);
    }

    public ClassMAPTable getCritere() {
        return this.critere;
    }

    public void setCritere(ClassMAPTable critere) {
        this.critere = critere;
    }

    public int getTailleFille() {
        return this.tailleFille;
    }

    public void setTailleFille(int tailleFille) {
        this.tailleFille = tailleFille;
    }

    public void makeCritere() throws Exception {
        String valeur = this.getReq().getParameter(this.getCritere().getAttributIDName());
        Field f = CGenUtil.getField(this.getCritere(), this.getCritere().getAttributIDName());
        CGenUtil.setValChamp(this.getCritere(), f, valeur);
    }

    public void getData() throws Exception {
        ClassMAPTable[] result = (ClassMAPTable[])this.getUtilisateur().getData(this.getCritere(), null, null, null, "");
        if (result == null || result.length == 0) {
            throw new Exception("Pas de resultat pour votre consultation");
        }
        this.setBase(result[0]);
    }

    public void getDataFle() throws Exception {
        ClassMAPTable[] result = (ClassMAPTable[])this.getUtilisateur().getData(this.getFille(), null, null, null, "");
        if (result == null || result.length == 0) {
            result = new ClassMAPTable[]{};
        }
        this.setDataFille(result);
    }

    public void setDataFille(ClassMAPTable[] fille) throws Exception {
        this.setBaseTableau(fille);
    }

    public ClassMAPTable[] getDataFille() throws Exception {
        return this.getBaseTableau();
    }

    public void makeCritereFle() throws Exception {
        String valeur = this.getReq().getParameter(this.getCritere().getAttributIDName());
        if (this.getFille() instanceof ClassFille) {
            Field f = CGenUtil.getField(this.getFille(), ((ClassFille)this.getFille()).getLiaisonMere());
            CGenUtil.setValChamp(this.getFille(), f, valeur);
        }
    }
}

