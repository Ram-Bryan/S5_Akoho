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
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import user.UserEJB;
import utilitaire.UtilDB;
import utilitaire.Utilitaire;

public class PageInsert
extends Page {
    protected String champReturn;
    protected String champUrl;
    private ClassMAPTable critere;

    public ClassMAPTable getCritere() {
        return this.critere;
    }

    public void setCritere(ClassMAPTable critere) {
        this.critere = critere;
    }

    public String getChampReturn() {
        return this.champReturn;
    }

    public void setChampReturn(String champReturn) {
        this.champReturn = champReturn;
    }

    public String getChampUrl() {
        return this.champUrl;
    }

    public void setChampUrl(String champUrl) {
        this.champUrl = champUrl;
    }

    public PageInsert() {
    }

    public PageInsert(ClassMAPTable o, HttpServletRequest req, UserEJB u) throws Exception {
        this.setUtilisateur(u);
        this.setReq(req);
        if (req.getParameter("acte") != null && req.getParameter("acte").equalsIgnoreCase("update")) {
            this.setCritere(o);
            this.makeCritere();
            this.getData();
            this.makeFormulaireUpt();
            if (this.getFormu().getChamp("id") != null) {
                this.getFormu().getChamp("id").setAutre("readonly");
            }
        } else if (req.getParameter("acte") != null && req.getParameter("acte").equalsIgnoreCase("dupliquer")) {
            this.setCritere(o);
            this.makeCritere();
            this.getData();
            this.getBase().setTuppleId(null);
            this.makeFormulaireUpt();
            if (this.getFormu().getChamp(this.getBase().getAttributIDName()) != null) {
                this.getFormu().getChamp(this.getBase().getAttributIDName()).setDefaut(null);
                this.getFormu().getChamp(this.getBase().getAttributIDName()).setValeur(null);
            }
        } else {
            this.setBase(o);
            this.makeFormulaire();
        }
    }

    public PageInsert(ClassMAPTable o, HttpServletRequest req) throws Exception {
        this.setBase(o);
        this.setReq(req);
        this.makeFormulaire();
    }

    public void makeFormulaireFF() throws Exception {
    }

    public void makeFormulaire() throws Exception {
        this.setChampReturn(this.getReq().getParameter("champReturn"));
        this.setChampUrl(this.getReq().getParameter("champUrl"));
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
            t[nbElement].setUserEJB(this.getUtilisateur());
            t[nbElement].setAttribut(f[i].getNomClasse());
            ++nbElement;
        }
        this.formu.setListeChamp(t);
        this.formu.setObjet(this.getBase());
    }

    public void makeFormulaireUpt() throws Exception {
        this.formu = new Formulaire();
        Champ[] t = null;
        bean.Champ[] f = ListeColonneTable.getFromListe(this.getBase(), null);
        t = new Champ[f.length];
        for (int i = 0; i < t.length; ++i) {
            t[i] = new Champ(f[i].getNomColonne());
            t[i].setLibelle(f[i].getNomColonne());
            t[i].setUserEJB(this.getUtilisateur());
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
            t[i].setAttribut(f[i].getNomClasse());
        }
        this.formu.setListeChamp(t);
        this.formu.setObjet(this.critere);
    }

    public void preparerDataFormu() throws Exception {
        try (Connection c = null;){
            c = new UtilDB().GetConn();
            this.formu.getAllData(c);
        }
    }

    @Override
    public void makeHtml() {
    }

    public void setFichierUpload() {
        this.getBase().setFichierUploadInsert(this.getParamSansNull("fichierUploadInsert"));
        this.getBase().setFichierUploadInsertData(this.getParamSansNull("fichierUploadInsertData"));
        this.getBase().setFichierUploadInsertName(this.getParamSansNull("fichierUploadInsertName"));
    }

    @Override
    public ClassMAPTable getObjectAvecValeur() throws Exception {
        try {
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getBase());
            this.setFichierUpload();
            for (int i = 0; i < tempChamp.length; ++i) {
                Field f = tempChamp[i];
                String valeur = this.getParamSansNull(f.getName());
                this.getBase().setMode("modif");
                if (f.getType().getName().compareToIgnoreCase("java.lang.String") == 0) {
                    if (valeur == null || valeur != null && valeur.compareTo("") == 0) {
                        valeur = this.getParamSansNull(f.getName() + "libelle");
                    }
                    CGenUtil.setValChamp(this.getBase(), f, valeur);
                }
                if (f.getType().getName().compareToIgnoreCase("java.sql.Date") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, Utilitaire.string_date("dd/MM/yyyy", valeur));
                }
                if (f.getType().getName().compareToIgnoreCase("double") == 0) {
                    CGenUtil.setValChamp(this.getBase(), f, new Double(Utilitaire.stringToDouble(valeur)));
                }
                if (f.getType().getName().compareToIgnoreCase("int") == 0) {
                    int valeurSplit;
                    String[] split;
                    if (valeur == null || valeur.compareToIgnoreCase("") == 0 || valeur.startsWith("0.")) {
                        CGenUtil.setValChamp(this.getBase(), f, new Integer(0));
                    } else if (valeur.contains(".")) {
                        split = valeur.split(".");
                        valeurSplit = 0;
                        if (split.length >= 1) {
                            valeurSplit = Utilitaire.stringToInt(split[0]);
                        }
                        CGenUtil.setValChamp(this.getBase(), f, new Integer(valeurSplit));
                    } else if (valeur.contains(",")) {
                        split = valeur.split(",");
                        valeurSplit = 0;
                        if (split.length >= 1) {
                            valeurSplit = Utilitaire.stringToInt(split[0]);
                        }
                        CGenUtil.setValChamp(this.getBase(), f, new Integer(valeurSplit));
                    } else {
                        CGenUtil.setValChamp(this.getBase(), f, new Integer(valeur));
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
            throw e;
        }
    }

    public String getHtmlAddOnPopup() {
        String rep = "";
        if (this.champUrl != null && this.champReturn != null) {
            rep = rep + "<input type=\"hidden\" name=\"champReturn\" value=\"" + this.champReturn + "\" />";
            rep = rep + "<input type=\"hidden\" name=\"champUrl\" value=\"" + this.champUrl + "\" />";
            rep = rep + "<input type=\"hidden\" name=\"rajoutLien\" value=\"champReturn-champUrl" + this.getRajoutLienAPartirChampUrl() + "\" />";
        }
        return rep;
    }

    private String getRajoutLienAPartirChampUrl() {
        String[] champs = this.champUrl.split(";");
        if (champs[0].compareToIgnoreCase(champs[1]) != 0) {
            return "-" + champs[1];
        }
        return "";
    }

    @Override
    public ClassMAPTable getObjectAvecValeur(HashMap<String, String> listeValeur) throws Exception {
        try {
            Field[] tempChamp = ListeColonneTable.getFieldListeHeritage(this.getBase());
            for (int i = 0; i < tempChamp.length; ++i) {
                Field f = tempChamp[i];
                String valeur = this.getParamSansNull(f.getName(), listeValeur);
                this.getBase().setMode("modif");
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
                        CGenUtil.setValChamp(this.getBase(), f, new Integer(valeur));
                    }
                }
                if (f.getType().getName().compareToIgnoreCase("float") != 0) continue;
                CGenUtil.setValChamp(this.getBase(), f, new Float(Utilitaire.stringToFloat(valeur)));
            }
            return this.getBase();
        }
        catch (NumberFormatException n) {
            throw new Exception("format de nombre invalide");
        }
        catch (Exception e) {
            throw e;
        }
    }

    public void getData() throws Exception {
        ClassMAPTable[] result = (ClassMAPTable[])this.getUtilisateur().getData(this.getCritere(), null, null, null, "");
        if (result == null || result.length == 0) {
            throw new Exception("Pas de resultat pour votre consultation");
        }
        this.setBase(result[0]);
    }

    public void makeCritere() throws Exception {
        String valeur = this.getReq().getParameter(this.getCritere().getAttributIDName());
        Field f = CGenUtil.getField(this.getCritere(), this.getCritere().getAttributIDName());
        CGenUtil.setValChamp(this.getCritere(), f, valeur);
    }
}

