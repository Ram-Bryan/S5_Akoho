/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  user.UserEJB
 */
package affichage;

import affichage.Champ;
import affichage.Page;
import bean.CGenUtil;
import bean.ClassEtat;
import bean.ClassMAPTable;
import bean.ListeColonneTable;
import java.lang.reflect.Field;
import javax.servlet.http.HttpServletRequest;
import user.UserEJB;
import utilitaire.Utilitaire;

public class PageConsulteMultiple
extends Page {
    private Champ[] listeChamp;
    private Champ[][] listeChampfle;
    private ClassMAPTable critere;
    private ClassMAPTable criterefille;
    private ClassMAPTable[] listeFille;
    private String[] libAffichage;
    private String[] libAffichagefille;
    private String cssTableau = "monographe";
    private String apres;
    private String apresLienPage = "";
    private String donnee;
    private String bute = "";
    private String titre;
    private boolean canUploadFile = true;
    private String fichier_table_cible = "ATTACHER_FICHIER";
    private String fichier_table_procedure = "getSeq_attacher_fichier";
    private String nomClasseFilleADuplique = "";
    private String nomColonneMere = "";
    private String nomTableBudget = "";

    public String getNomTableBudget() {
        return this.nomTableBudget;
    }

    public void setNomTableBudget(String nomTableBudget) {
        this.nomTableBudget = nomTableBudget;
    }

    public String getBute() {
        return this.bute;
    }

    public String[] getLibAffichagefille() {
        return this.libAffichagefille;
    }

    public void setLibAffichagefille(String[] libAffichagefille) {
        if (this.getListeChampfle() != null && this.getListeChampfle().length > 0 && this.getListeChampfle()[0].length > 0) {
            for (int i = 0; i < this.getListeChampfle()[0].length; ++i) {
                Champ c = this.getListeChampfle()[0][i];
                c.setLibelle(libAffichagefille[i]);
            }
        }
    }

    public void setBute(String bute) {
        this.bute = bute;
    }

    public Champ[][] getListeChampfle() {
        return this.listeChampfle;
    }

    public void setListeChampfle(Champ[][] listeChampfle) {
        this.listeChampfle = listeChampfle;
    }

    public ClassMAPTable[] getListeFille() {
        return this.listeFille;
    }

    public void setListeFille(ClassMAPTable[] listeFille) {
        this.listeFille = listeFille;
    }

    public Champ[] getListeChamp() {
        return this.listeChamp;
    }

    public void setListeChamp(Champ[] listeChamp) {
        this.listeChamp = listeChamp;
    }

    public ClassMAPTable getCritere() {
        return this.critere;
    }

    public void setCritere(ClassMAPTable critere) {
        this.critere = critere;
    }

    public ClassMAPTable getCriterefille() {
        return this.criterefille;
    }

    public void setCriterefille(ClassMAPTable criterefille) {
        this.criterefille = criterefille;
    }

    public String[] getLibAffichage() {
        return this.libAffichage;
    }

    public void setLibAffichage(String[] libAffichage) {
        for (int i = 0; i < libAffichage.length; ++i) {
            Champ c = this.getListeChamp()[i];
            c.setLibelle(libAffichage[i]);
        }
    }

    public String getApresLienPage() {
        return this.apresLienPage;
    }

    public void setApresLienPage(String apresLienPage) {
        this.apresLienPage = apresLienPage;
    }

    public PageConsulteMultiple(ClassMAPTable o, ClassMAPTable fille, String nomColMere, HttpServletRequest req, UserEJB u) throws Exception {
        try {
            this.setCritere(o);
            this.setCriterefille(fille);
            this.setNomColonneMere(nomColMere);
            this.setReq(req);
            this.makeCritere();
            this.setUtilisateur(u);
            this.getData();
            this.makeChamp();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public void getData() throws Exception {
        ClassMAPTable[] result = (ClassMAPTable[])this.getUtilisateur().getData(this.getCritere(), null, null, null, "");
        ClassMAPTable[] resultfille = (ClassMAPTable[])this.getUtilisateur().getData(this.getCriterefille(), null, null, null, " and " + this.getNomColonneMere() + " = '" + this.getCritere().getTuppleID() + "'");
        if (result == null || result.length == 0) {
            throw new Exception("Pas de resultat pour votre consultation");
        }
        this.setBase(result[0]);
        this.setListeFille(resultfille);
    }

    public void makeChamp() throws Exception {
        try {
            Champ[] listeChamp = null;
            Champ[][] listeChampfle = null;
            bean.Champ[] t = ListeColonneTable.getFromListe(this.getBase(), null);
            listeChamp = new Champ[t.length];
            for (int i = 0; i < t.length; ++i) {
                listeChamp[i] = new Champ(t[i].getNomColonne());
                listeChamp[i].setLibelle(t[i].getNomColonne());
                Object tempVal = CGenUtil.getValeurFieldByMethod(this.getBase(), t[i].getNomColonne());
                String valeur = String.valueOf(tempVal);
                if (valeur != null && valeur.contains("'")) {
                    valeur = valeur.replaceAll("'", "&apos;");
                }
                if (valeur != null && valeur.contains("\"")) {
                    valeur = valeur.replaceAll("\"", " ");
                }
                if (tempVal == null) {
                    valeur = "";
                } else if (t[i].getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
                    valeur = Utilitaire.formatterDaty(valeur);
                } else if (t[i].getTypeJava().compareToIgnoreCase("double") == 0) {
                    valeur = Utilitaire.formaterAr(valeur);
                }
                listeChamp[i].setValeur(valeur);
            }
            this.setListeChamp(listeChamp);
            bean.Champ[] tfle = ListeColonneTable.getFromListe(this.getCriterefille(), null);
            int tmpIndice = 0;
            if (this.listeFille.length > 0) {
                listeChampfle = new Champ[this.listeFille.length][tfle.length];
                for (int j = 0; j < this.listeFille.length; ++j) {
                    listeChampfle[j] = new Champ[tfle.length];
                    for (int i = 0; i < tfle.length; ++i) {
                        listeChampfle[j][i] = new Champ(tfle[i].getNomColonne());
                        listeChampfle[j][i].setLibelle(tfle[i].getNomColonne());
                        Object tempVal = CGenUtil.getValeurFieldByMethod(this.listeFille[j], tfle[i].getNomColonne());
                        String valeur = String.valueOf(tempVal);
                        if (valeur != null && valeur.contains("'")) {
                            valeur = valeur.replaceAll("'", "&apos;");
                        }
                        if (valeur != null && valeur.contains("\"")) {
                            valeur = valeur.replaceAll("\"", " ");
                        }
                        if (tempVal == null) {
                            valeur = "";
                        } else if (tfle[i].getTypeJava().compareToIgnoreCase("java.sql.Date") == 0) {
                            valeur = Utilitaire.formatterDaty(valeur);
                        } else if (tfle[i].getTypeJava().compareToIgnoreCase("double") == 0) {
                            valeur = Utilitaire.formaterAr(valeur);
                        }
                        listeChampfle[j][i].setValeur(valeur);
                        ++tmpIndice;
                    }
                }
            } else {
                listeChampfle = new Champ[1][tfle.length];
                for (int j = 0; j < 1; ++j) {
                    listeChampfle[j] = new Champ[tfle.length];
                    for (int i = 0; i < tfle.length; ++i) {
                        listeChampfle[j][i] = new Champ(tfle[i].getNomColonne());
                        listeChampfle[j][i].setLibelle(tfle[i].getNomColonne());
                        ++tmpIndice;
                    }
                }
            }
            this.setListeChampfle(listeChampfle);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public void makeCritere() throws Exception {
        String valeur = this.getReq().getParameter(this.getCritere().getAttributIDName());
        Field f = CGenUtil.getField(this.getCritere(), this.getCritere().getAttributIDName());
        CGenUtil.setValChamp(this.getCritere(), f, valeur);
    }

    @Override
    public void makeHtml() {
        try {
            int i;
            String temp = "";
            Class[] paramType = new Class[]{Integer.TYPE};
            if (this.getListeChamp().length < 13) {
                temp = temp + "<div class='row'>";
                temp = temp + "<div class='col-md-3'></div><div class='col-md-6'><div class='box'><div class='box-title with-border' style='margin-top:-100px;'><h1 class='box-title'><a onclick='history.back()'><i class='fa fa-arrow-circle-left'></i></a>" + this.getTitre() + " " + this.getCritere().getTuppleID() + "</h1></div><div class='box-body'>";
                temp = temp + "<table class='table table-bordered'>";
                for (i = 0; i < this.getListeChamp().length; ++i) {
                    String lienFin;
                    Champ champ = this.getListeChamp()[i];
                    String lienDebut = champ.getLien() == null ? "" : " <a href='" + champ.getLien().getLien() + "&" + champ.getLien().getQueryString() + this.getListeChamp()[i].getValeur() + "'>";
                    String string = lienFin = champ.getLien() == null ? "" : "</a>";
                    if (this.getListeChamp()[i].getTypeData().compareToIgnoreCase("date") == 0) {
                        // empty if block
                    }
                    if (this.getListeChamp()[i].isPhoto()) {
                        temp = temp + "<tr>";
                        temp = temp + "<td class='" + this.getListeChamp()[i].getCssLibelle() + "'><B>" + this.getListeChamp()[i].getLibelle() + "</B></td>";
                        temp = temp + "</tr>";
                        continue;
                    }
                    if (!this.getListeChamp()[i].getVisible()) continue;
                    temp = temp + "<tr>";
                    temp = temp + "<td class='" + this.getListeChamp()[i].getCssLibelle() + "'><B>" + this.getListeChamp()[i].getLibelle() + "</B></td>";
                    temp = this.getListeChamp()[i].getNom().compareToIgnoreCase("etat") == 0 && this.getBase() instanceof ClassEtat ? temp + "<td>" + lienDebut + "<b>" + ((ClassEtat)this.getBase()).getEtatText(Utilitaire.stringToInt(this.getListeChamp()[i].getValeur())) + "</b>" + lienFin + "</td>" : temp + "<td>" + lienDebut + this.getListeChamp()[i].getValeur() + lienFin + "</td>";
                    temp = temp + "</tr>";
                }
                temp = temp + "<input type='hidden' name='id' value='" + this.getListeChamp()[0].getValeur() + "'/>";
                temp = temp + "</table>";
                temp = temp + "<br/></div></div></div></div>";
            } else {
                String lienFin;
                String lienDebut;
                Champ champ;
                int i2;
                int nb2 = this.getListeChamp().length / 2;
                temp = temp + "<div class='row'>";
                temp = temp + "<div class='col-md-12'><div><div class='box'><div class='box-title with-border' style='margin-top:-100px;'><h1 class='box-title'><a onclick='history.back()'><i class='fa fa-arrow-circle-left'></i></a>" + this.getTitre() + " " + this.getCritere().getTuppleID() + "</h1></div><div class='box-body'>";
                temp = temp + "<div class='col-md-6'>";
                temp = temp + "<table class='table table-bordered'>";
                for (i2 = 0; i2 < nb2; ++i2) {
                    champ = this.getListeChamp()[i2];
                    lienDebut = champ.getLien() == null ? "" : " <a href='" + champ.getLien().getLien() + "&" + champ.getLien().getQueryString() + this.getListeChamp()[i2].getValeur() + "'>";
                    String string = lienFin = champ.getLien() == null ? "" : "</a>";
                    if (this.getListeChamp()[i2].getTypeData().compareToIgnoreCase("date") == 0) {
                        // empty if block
                    }
                    if (this.getListeChamp()[i2].isPhoto()) {
                        temp = temp + "<tr>";
                        temp = temp + "<td class='" + this.getListeChamp()[i2].getCssLibelle() + "'><B>" + this.getListeChamp()[i2].getLibelle() + "</B></td>";
                        temp = temp + "</tr>";
                        continue;
                    }
                    if (!this.getListeChamp()[i2].getVisible()) continue;
                    temp = temp + "<tr>";
                    temp = temp + "<td class='" + this.getListeChamp()[i2].getCssLibelle() + "'><B>" + this.getListeChamp()[i2].getLibelle() + "</B></td>";
                    temp = this.getListeChamp()[i2].getNom().compareToIgnoreCase("etat") == 0 && this.getBase() instanceof ClassEtat ? temp + "<td>" + lienDebut + "<b>" + ((ClassEtat)this.getBase()).getEtatText(Utilitaire.stringToInt(this.getListeChamp()[i2].getValeur())) + "</b>" + lienFin + "</td>" : temp + "<td>" + lienDebut + this.getListeChamp()[i2].getValeur() + lienFin + "</td>";
                    temp = temp + "</tr>";
                }
                temp = temp + "<input type='hidden' name='id' value='" + this.getListeChamp()[0].getValeur() + "'/>";
                temp = temp + "</table>";
                temp = temp + "</div>";
                temp = temp + "<div class='col-md-6'>";
                temp = temp + "<table class='table table-bordered'>";
                for (i2 = nb2; i2 < this.getListeChamp().length; ++i2) {
                    champ = this.getListeChamp()[i2];
                    lienDebut = champ.getLien() == null ? "" : " <a href='" + champ.getLien().getLien() + "&" + champ.getLien().getQueryString() + this.getListeChamp()[i2].getValeur() + "'>";
                    String string = lienFin = champ.getLien() == null ? "" : "</a>";
                    if (this.getListeChamp()[i2].getTypeData().compareToIgnoreCase("date") == 0) {
                        // empty if block
                    }
                    if (this.getListeChamp()[i2].isPhoto()) {
                        temp = temp + "<tr>";
                        temp = temp + "<td class='" + this.getListeChamp()[i2].getCssLibelle() + "'><B>" + this.getListeChamp()[i2].getLibelle() + "</B></td>";
                        temp = temp + "</tr>";
                        continue;
                    }
                    if (!this.getListeChamp()[i2].getVisible()) continue;
                    temp = temp + "<tr>";
                    temp = temp + "<td class='" + this.getListeChamp()[i2].getCssLibelle() + "'><B>" + this.getListeChamp()[i2].getLibelle() + "</B></td>";
                    temp = this.getListeChamp()[i2].getNom().compareToIgnoreCase("etat") == 0 && this.getBase() instanceof ClassEtat ? temp + "<td>" + lienDebut + "<b>" + ((ClassEtat)this.getBase()).getEtatText(Utilitaire.stringToInt(this.getListeChamp()[i2].getValeur())) + "</b>" + lienFin + "</td>" : temp + "<td>" + lienDebut + this.getListeChamp()[i2].getValeur() + lienFin + "</td>";
                    temp = temp + "</tr>";
                }
                temp = temp + "</table>";
                temp = temp + "</div>";
                temp = temp + "<br/></div></div></div></div></div>";
            }
            if (this.getListeChampfle() != null && this.getListeChampfle().length > 0) {
                temp = temp + "<div class='row'>";
                temp = temp + "<div class='row col-md-12'>";
                temp = temp + "<div class='box'>";
                temp = temp + "<div class='box box-primary'>";
                temp = temp + "<div class='box-body'>";
                temp = temp + "<table class=\"table table-bordered\">";
                temp = temp + "<tr>";
                for (i = 0; i < this.getListeChampfle()[0].length; ++i) {
                    Champ c = this.getListeChampfle()[0][i];
                    if (c.getNom().compareToIgnoreCase("etat") == 0 && this.getCriterefille() instanceof ClassEtat || !c.getVisible()) continue;
                    c.setAutre("tabindex='" + (i + 1) + "'" + c.getAutre());
                    temp = temp + "<th style='background-color:#bed1dd' ><label for='" + this.getListeChampfle()[0][i].getLibelle() + "' " + c.getAutre() + ">" + this.getListeChampfle()[0][i].getLibelle() + "</label></th>";
                }
                temp = temp + "</tr>";
                for (int iLigne = 0; iLigne < this.getListeFille().length; ++iLigne) {
                    String troisiemeTd = "";
                    temp = temp + "<tr>";
                    for (int iCol = 0; iCol < this.getListeChampfle()[iLigne].length; ++iCol) {
                        String lienFin;
                        Champ ch = this.getListeChampfle()[iLigne][iCol];
                        String lienDebut = ch.getLien() == null ? "" : " <a href='" + ch.getLien().getLien() + "&" + ch.getLien().getQueryString() + ch.getValeur() + "'>";
                        String string = lienFin = ch.getLien() == null ? "" : "</a>";
                        if (ch.getNom().compareToIgnoreCase("etat") == 0 && this.getCriterefille() instanceof ClassEtat || !this.getListeChampfle()[0][iCol].getVisible()) continue;
                        temp = ch.getNom().compareToIgnoreCase("etat") == 0 && this.getCriterefille() instanceof ClassEtat ? temp + "<td>" + lienDebut + "<b>" + ((ClassEtat)this.getCriterefille()).getEtatText(Utilitaire.stringToInt(ch.getValeur())) + "</b>" + lienFin + "</td>" : temp + "<td>" + lienDebut + ch.getValeur() + lienFin + "</td>";
                    }
                    temp = temp + "<td><a href='" + this.getLien() + "?but=apresInsertMultiple.jsp&idmultiple=" + this.getListeFille()[iLigne].getTuppleID() + "&acte=deleteMultiple&classe=" + this.getCriterefille().getClass().getName() + "&idmere=" + this.getBase().getTuppleID() + "&bute=" + this.getBute() + "'><span class='glyphicon glyphicon-remove'></span></a></td>";
                    temp = temp + "</tr>";
                }
                temp = temp + "</table>";
                temp = temp + "</div>";
                temp = temp + "<div class='box-footer'>";
                temp = temp + "</div>";
                temp = temp + "</div>";
                temp = temp + "</div>";
                temp = temp + "</div>";
                temp = temp + "</div>";
            }
            this.setHtml(temp);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getNomColonneMere() {
        return this.nomColonneMere;
    }

    public void setNomColonneMere(String nomColonneMere) {
        this.nomColonneMere = nomColonneMere;
    }

    public String getNomClasseFilleADuplique() {
        return this.nomClasseFilleADuplique;
    }

    public void setNomClasseFilleADuplique(String nomClasseFilleADuplique) {
        this.nomClasseFilleADuplique = nomClasseFilleADuplique;
    }

    @Override
    public String getTitre() {
        return this.titre;
    }

    @Override
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public PageConsulteMultiple() {
    }

    public boolean isCanUploadFile() {
        return this.canUploadFile;
    }

    public void setCanUploadFile(boolean canUploadFile) {
        this.canUploadFile = canUploadFile;
    }

    public String getFichier_table_cible() {
        return this.fichier_table_cible;
    }

    public void setFichier_table_cible(String fichier_table_cible) {
        this.fichier_table_cible = fichier_table_cible;
    }

    public String getFichier_table_procedure() {
        return this.fichier_table_procedure;
    }

    public void setFichier_table_procedure(String fichier_table_procedure) {
        this.fichier_table_procedure = fichier_table_procedure;
    }

    public Champ getChampByName(String nomChamp) {
        for (int i = 0; i < this.getListeChamp().length; ++i) {
            if (this.getListeChamp()[i].getNom().compareToIgnoreCase(nomChamp) != 0) continue;
            return this.getListeChamp()[i];
        }
        return null;
    }

    public Champ getChampFilleByName(String nomChamp) {
        for (int i = 0; i < this.getListeChampfle()[0].length; ++i) {
            if (this.getListeChampfle()[0][i].getNom().compareToIgnoreCase(nomChamp) != 0) continue;
            return this.getListeChampfle()[0][i];
        }
        return null;
    }

    public String getBasPagePask() {
        String uploadXhtml = this.isCanUploadFile() ? "<td style=\"padding:5px;\"><a class=\"btn btn-success\" href=\"" + this.getReq().getSession().getAttribute("lien") + "?but=pageupload.jsp&id=" + this.getReq().getParameter("id") + "&nomtable=" + this.fichier_table_cible + "&procedure=" + this.fichier_table_procedure + "&bute=" + this.getReq().getParameter("but") + "\">Attacher fichier</a></td>" : "";
        String retour = "";
        retour = retour + "<table border=0 cellpadding=0 cellspacing=0 align=center style=\"text-align: center;\">";
        retour = retour + "<tr>" + uploadXhtml;
        retour = retour + "</tr>";
        retour = retour + "</table>";
        return retour;
    }
}

